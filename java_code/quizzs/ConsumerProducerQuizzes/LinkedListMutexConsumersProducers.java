package quizzs.ConsumerProducerQuizzes;

/*
    multiple consumers, multiple producers, a linked list and one mutex
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListMutexConsumersProducers {
    static final Object syncObj = new Object();
    static LinkedList<String> list = new LinkedList<>();

    static public void main(String[] Args){
        List<Thread> consumers = new ArrayList<>();
        List<Thread> producers = new ArrayList<>();


        for(int i = 0; i < 10; ++i){
            consumers.add(new Thread(LinkedListMutexConsumersProducers::consumer));
        }

        for(int i = 0; i < 2; ++i){
            producers.add(new Thread(LinkedListMutexConsumersProducers::producer));
        }

        for(Thread thread: consumers){
            thread.start();
        }

        for(Thread thread: producers){
            thread.start();
        }

        try {

            for(Thread thread: producers){
                thread.join();
            }

            for(Thread thread: consumers){
                thread.join();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void producer(){
        while(true){
            synchronized (syncObj){
                list.add(String.valueOf(list.size()));
            }
        }
    }

    static void consumer(){
        while(true) {
            synchronized (syncObj) {
                if (!list.isEmpty()) {
                    System.err.println(list.removeLast());
                }
            }
        }
    }
}

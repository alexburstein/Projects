package quizzs.ConsumerProducerQuizzes;

/*
    multiple consumers, multiple producers, a linked list, a mutex, a semaphore.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedListMutexSemaphoreConsumersProducers {
    static final ReentrantLock lock = new ReentrantLock();
    static final Semaphore sem = new Semaphore(0);
    static LinkedList<String> list = new LinkedList<>();

    static public void main(String[] Args){
        List<Thread> consumers = new ArrayList<>();
        List<Thread> producers = new ArrayList<>();


        for(int i = 0; i < 4; ++i){
            consumers.add(new Thread(LinkedListMutexSemaphoreConsumersProducers::consumer));
        }

        for(int i = 0; i < 10; ++i){
            producers.add(new Thread(LinkedListMutexSemaphoreConsumersProducers::producer));
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
            lock.lock();
            list.add(String.valueOf(list.size()));
            sem.release();
            lock.unlock();
            try {Thread.sleep(0, 1);} catch (InterruptedException ignore) { // optional
            }
        }
    }

    static void consumer(){
        while(true) {
            try {sem.acquire();} catch (InterruptedException ignore) {}
            lock.lock();
            System.err.println(list.removeLast());
            lock.unlock();
        }
    }
}

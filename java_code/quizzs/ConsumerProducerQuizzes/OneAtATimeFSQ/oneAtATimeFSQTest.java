package quizzs.ConsumerProducerQuizzes.OneAtATimeFSQ;

import java.util.*;

public class oneAtATimeFSQTest {
    static private int maxSize;
    static private final Set<Thread> set = new HashSet<>();
    static private oneAtATimeFSQ<String> queue;
    static public void main(String[] Args){
        queue = new oneAtATimeFSQ<>(5);

        List<Thread> consumerThreads = new ArrayList<>();
        List<Thread> producerThreads = new ArrayList<>();

        for(int i = 0; i < 4; ++i){
            consumerThreads.add(new Thread(oneAtATimeFSQTest::consumer));
        }

        for(int i = 0; i < 4; ++i){
            producerThreads.add(new Thread(oneAtATimeFSQTest::producer));
        }

        for(Thread thread: consumerThreads){
            thread.start();
        }

        for(Thread thread: producerThreads){
            thread.start();
        }

        System.err.println("this might take few seconds...");

        try {

            for(Thread thread: producerThreads){
                thread.join();
            }

            for(Thread thread: consumerThreads){
                thread.join();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assert (maxSize == 5);
        System.err.println("all well!");

    }


    static void producer(){
        for(int i = 0; i < 1000000; ++i){
            queue.offer(String.valueOf(queue.size()));
        }
    }


    static void consumer(){
        for(int i = 0; i < 1000000; ++i){
            maxSize = Math.max(Integer.parseInt(Objects.requireNonNull(queue.poll())), maxSize);
        }
    }

}

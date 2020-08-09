package quizzs.ConsumerProducerQuizzes.OneOfEachAtATime;

import java.util.*;

public class oneOfEachAtATimeFSQTest {
    static private int maxSize;
    static private oneOfEachAtATimeFSQ<String> queue;
    static public void main(String[] Args){
        queue = new oneOfEachAtATimeFSQ<>(5);

        List<Thread> consumerThreads = new ArrayList<>();
        List<Thread> producerThreads = new ArrayList<>();

        for(int i = 0; i < 4; ++i){
            consumerThreads.add(new Thread(oneOfEachAtATimeFSQTest::consumer));
        }

        for(int i = 0; i < 4; ++i){
            producerThreads.add(new Thread(oneOfEachAtATimeFSQTest::producer));
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

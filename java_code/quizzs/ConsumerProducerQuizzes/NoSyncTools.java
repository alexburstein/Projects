package quizzs.ConsumerProducerQuizzes;

/*
    one consumer, one producer. no sync tools. busy waiting.
 */

import java.util.concurrent.atomic.AtomicInteger;

public class NoSyncTools {
    static AtomicInteger ai = new AtomicInteger(0);

    static public void main(String[] Args){
        Thread producer = new Thread(()->{
            while (true) {
                while (ai.get() < 1);
                System.err.println(ai.decrementAndGet());
            }
        });
        Thread consumer = new Thread(()->{
            while (true) {
                while (ai.get() > 0);
                System.err.println(ai.incrementAndGet());
            }
        });

        producer.start();
        consumer.start();
    }
}

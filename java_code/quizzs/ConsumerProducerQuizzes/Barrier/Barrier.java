package quizzs.ConsumerProducerQuizzes.Barrier;

/*
    a barrier.
    many consumers. one producer.
    the producer needs to produce a message before the consumers consume.
    all consumers need to consume the message before the producer is allowed to update it.
    use: 1 semaphore. 1 lock. 1 conditional variable.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    static final private ReentrantLock lock = new ReentrantLock(true);
    static final private Semaphore sem = new Semaphore(0);
    static private final Condition cond = lock.newCondition();
    static private final int NUM_OF_CONSUMERS = 10;
    static private final int NUM_OF_MESSAGE_CHANGES = 100000;

    static private int version = 0;
    static private String message;

    static void consumer(){
        for (int i = 0; i < NUM_OF_MESSAGE_CHANGES; ++i){
            try {
                lock.lock();
                sem.release();
                try {
                    cond.await();
                } catch (InterruptedException ignore) {}
            }
            finally {
                lock.unlock();
            }
        }
    }

    static void producer(){
        for (int i = 0; i < NUM_OF_MESSAGE_CHANGES; ++i){

            for(int x = 0; x < NUM_OF_CONSUMERS; ++x){
                try {sem.acquire();} catch (InterruptedException ignore) {}
            }
            try {
                lock.lock();
                cond.signalAll();
                message = "this is message number: " + (++version);
            }
            finally {
                lock.unlock();
            }
        }
    }


    static public void main(String[] Args) {
    List<Thread> consumers = new ArrayList<>();
    Thread producer = new Thread(Barrier::producer);

    for(int i = 0; i < NUM_OF_CONSUMERS; ++i){
        consumers.add(new Thread(Barrier::consumer));
    }

    for(Thread thread: consumers){
        thread.start();
    }

    producer.start();

    try {
        producer.join();

        for(Thread thread: consumers){
            thread.join();
        }

    }
    catch (InterruptedException ignore){}

        System.err.println("OK"); // TODO automated tests
    }
}

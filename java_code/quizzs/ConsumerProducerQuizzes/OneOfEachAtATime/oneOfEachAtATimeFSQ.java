package quizzs.ConsumerProducerQuizzes.OneOfEachAtATime;

/*
    implement FSQ (fixed sized queue) with one mutex and two semaphores.
    only one thread should be able to access the queue at once.
 */

import org.jetbrains.annotations.NotNull;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class oneOfEachAtATimeFSQ<T> extends AbstractQueue<T> {
    private final Queue<T> q;
    static final private ReentrantLock producerLock = new ReentrantLock();
    static final private ReentrantLock consumerLock = new ReentrantLock();

    static final private Semaphore minSem = new Semaphore(0);
    static private Semaphore maxSem;

    public oneOfEachAtATimeFSQ(int size){
        maxSem = new Semaphore(size);
        q = new ConcurrentLinkedQueue<>();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return q.iterator();
    }

    @Override
    public int size() {
        return q.size();
    }

    @Override
    public boolean offer(T t) {
        try {maxSem.acquire();} catch (InterruptedException ignored) {}
        boolean offerResult;
        try {
            producerLock.lock();
            offerResult = q.offer(t);
        } finally {
            producerLock.unlock();
        }
        minSem.release();

        return offerResult;
    }

    @Override
    public T poll() {
        try {minSem.acquire();} catch (InterruptedException ignored) {}
        T t;
        try {
            consumerLock.lock();
            t = q.poll();
        } finally {
            consumerLock.unlock();
        }
        maxSem.release();

        return t;
    }

    @Override
    public T peek() {
        return q.peek();
    }
}

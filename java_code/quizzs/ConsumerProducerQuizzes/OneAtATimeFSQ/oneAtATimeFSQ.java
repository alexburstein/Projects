package quizzs.ConsumerProducerQuizzes.OneAtATimeFSQ;

/*
    implement FSQ (fixed sized queue) with one mutex and two semaphores.
    only one thread should be able to access the queue at once.
 */

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class oneAtATimeFSQ<T> extends AbstractQueue<T> {
    private final Queue<T> q;
    static final private ReentrantLock lock = new ReentrantLock();
    static final private Semaphore minSem = new Semaphore(0);
    static private Semaphore maxSem;

    public oneAtATimeFSQ(int size){
        maxSem = new Semaphore(size);
        q = new LinkedList<>();
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
            lock.lock();
            offerResult = q.offer(t);
        } finally {
            lock.unlock();
        }
        minSem.release();

        return offerResult;
    }

    @Override
    public T poll() {
        try {minSem.acquire();} catch (InterruptedException ignored) {}
        T t;
        try {
            lock.lock();
            t = q.poll();
        } finally {
            lock.unlock();
        }
        maxSem.release();

        return t;
    }

    @Override
    public T peek() {
        return q.peek();
    }
}

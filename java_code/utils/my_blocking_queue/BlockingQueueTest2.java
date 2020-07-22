package utils.my_blocking_queue;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class BlockingQueueTest2 {
    private static final int SIZE = 50000;
    private final BlockingQueue<Integer> quInt = new BlockingQueue<>();
    private final Thread[] threads = new Thread[SIZE];

    @Test
   public void enqueueAndDequeue() throws InterruptedException {
        assertTrue(quInt.isEmpty());
        assertEquals(0, quInt.size());

        for (int i = 0; i < SIZE; i += 2) {
            int finalI = i;
            threads[i] = new Thread(() -> quInt.enqueue(finalI));
            threads[i].start();

            threads[i+1] = new Thread(() -> {
                try {
                    quInt.dequeue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i+1].start();
        }
        for (Thread thread : threads) thread.join();

        assertTrue(quInt.isEmpty());
        assertEquals(0, quInt.size());
    }

    @Test
   public void dequeueTime() throws InterruptedException {
        assertTrue(quInt.isEmpty());
        assertEquals(0, quInt.size());

        for (int i = 0; i < SIZE; ++i) {
            @SuppressWarnings("unused")
			int finalI = i;
            threads[i] = new Thread(() -> {
                try {
                    assertNull(quInt.dequeue(1, TimeUnit.MILLISECONDS));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) thread.join();

        assertTrue(quInt.isEmpty());
        assertEquals(0, quInt.size());
    }
}
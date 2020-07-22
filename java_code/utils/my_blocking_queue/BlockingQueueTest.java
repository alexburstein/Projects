package utils.my_blocking_queue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockingQueueTest {
	BlockingQueue<Integer> queue = new BlockingQueue<>();

	@Test
	public void enqueueTest() {
		
		Thread enqueuer = new Thread(()-> {
			for (int i = 0; i < 1000; ++i) {
				queue.enqueue(i);
			}			
		});
		enqueuer.start();
		
		Thread dequeuer = new Thread(()-> {
			for (int i = 0; i < 1000; ++i) {
				try {
					System.err.println(queue.dequeue());
				} catch (InterruptedException e) {
					System.out.println("exception throwen");
					e.printStackTrace();
				}			
			}	
		});
		dequeuer.start();
		
		try {
			enqueuer.join();
			dequeuer.join();
		} catch (InterruptedException e) {
			System.out.println("exception throwen");
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void enqueueDequeueTest() {
		Thread dequeuer = new Thread(()-> {
			for (int i = 0; i < 1000; ++i) {
				try {
					System.err.println(queue.dequeue());
				} catch (InterruptedException e) {
					System.out.println("exception throwen");
					e.printStackTrace();
				}			
			}	
		});
		
		Thread enqueuer = new Thread(()-> {
			for (int i = 0; i < 1000; ++i) {
				queue.enqueue(i);
			}			
		});
		
		dequeuer.start();
		
		try {Thread.sleep(1000);} catch (InterruptedException ignored) {}
		
		enqueuer.start();
		
		
		try {
			enqueuer.join();
			dequeuer.join();
		} catch (InterruptedException e) {
			System.out.println("exception throwen");
			e.printStackTrace();
		}
	}
	
	@Test
	public void removeTest() {
		for (int i = 0; i < 1000; ++i) {
			queue.enqueue(i);
		}
		
		assertEquals(queue.size(), 1000);
		
		queue.remove(1);
	}
}

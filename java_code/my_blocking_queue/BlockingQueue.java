package my_blocking_queue;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<E> {
	private final Queue<E> priorityQueue;
	private final Semaphore elmInPQSemaphore = new Semaphore(0);
	private final Lock reLock = new ReentrantLock();
	private static final int DEFAULT_CAPACITY = 11;

	public BlockingQueue(int reqCapacity){
		this(reqCapacity, null);
	}

	public BlockingQueue(int reqCapacity, Comparator<? super E> cmp){
		priorityQueue = new PriorityQueue<>(reqCapacity , cmp);
	}
	 
	public BlockingQueue() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * adds the data element to the PQ.
	 * @param data element
	 */
	public void enqueue(E data) {
		Objects.requireNonNull(data);
		try {
			reLock.lock();
		
			if (priorityQueue.add(data)) {
				elmInPQSemaphore.release();	
			}
		}
		finally {
			reLock.unlock();			
		}
	}

	/**
	 * removes the head of the queue and returns it. blocks the thread if queue is empty
	 * @return the head of the queue.
	 * @throws InterruptedException if the thread is interrupted when thread is blocked.
	 */
	public E dequeue() throws InterruptedException {
		E element;
		
		elmInPQSemaphore.acquire();
		
		try {
			reLock.lockInterruptibly();			
			element = priorityQueue.poll();
		}
		catch(InterruptedException e) {
			elmInPQSemaphore.release();
			throw e;
		}

		finally {
			reLock.unlock();			
		}

		return element;
	}

	/**
	 * removes the head of the queue and returns it if is available during given time.
	 * @param unit of time definition.
	 * @param timeout number of elements of the time unit 
	 * @return the head of the queue. null if time was up.
	 * @throws InterruptedException if the thread is interrupted when thread is blocked.
	 */
	public E dequeue(long timeout, TimeUnit unit) throws InterruptedException {
		E element = null;

		if (elmInPQSemaphore.tryAcquire(timeout, unit)){

			try {
				reLock.lockInterruptibly();
				element = priorityQueue.poll();				
			}
			catch (InterruptedException e) {
				elmInPQSemaphore.release();
				throw e;
			}
			
			reLock.unlock();				
		}

		return element;	
	}

	/**
	 * removes an element from the PQ.
	 * @param data to remove
	 * @return true if the PQ was altered. false otherwise.
	 */
	public boolean remove (E data) {	
		boolean isRemoved = false;

		if (elmInPQSemaphore.tryAcquire()){
			reLock.lock();
			isRemoved = priorityQueue.remove(data);
			reLock.unlock();
			
			if (!isRemoved) {
				elmInPQSemaphore.release();			
			}
		}

		return isRemoved;
	}
	
	/**
	 * 
	 * @return number of elements in queue
	 */
	public int size(){
		int size;
		reLock.lock();
		size = priorityQueue.size();
		reLock.unlock();

		return size;
	}
	
	/**
	 * 
	 * @return true if the queue is empty, false otherwise.
	 */
    public boolean isEmpty(){		
		return priorityQueue.isEmpty();
    }
}
package my_enhanced_thread_pool;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import my_enhanced_thread_pool.ThreadPool.Priority;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadPoolTest1 {
	static ThreadPool tp;
	int cntr = 0;
	static Set<Thread> set;

	
	
	@Before
	public void creation() {
		tp = new ThreadPool(10);
		set = Collections.synchronizedSet(new HashSet<>());
				;
	}
	
	@After
	public void destruction() {
		tp.shutdown();
		try {
			tp.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		
		set.clear();
	}
	

	@Test
	public void timeTest() {
		Long plea = null;
		boolean isTimedOut = false;
		
		tp.setNumThreads(5);
		
		Long startTime = System.nanoTime();
				
		Future<Long> answer = tp.submit(()->{Thread.sleep(10000); return System.nanoTime();}, Priority.MAX_PRIORITY);
		assertFalse(answer.isDone());
		try {
			plea = answer.get(3, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			System.err.println("Interrupted Exception unexpected");
		} catch (ExecutionException e1) {
			System.err.println("Execution Exception unexpected");
		} catch (TimeoutException e1) {
			System.out.println("Timeout Exception function ok!");
			isTimedOut = true;
		}
		
		assertTrue(isTimedOut);
		if (plea != null) {
			System.out.println(plea);
		}

		try {
			System.out.println("it took only " +  TimeUnit.MILLISECONDS.convert((answer.get() - startTime), TimeUnit.NANOSECONDS) + " milis so far");

		} catch (InterruptedException | ExecutionException e) {
			System.out.println("exception thrown");
		}
		
		for (int i = 0; i < 8; ++i) {
			tp.execute(this::Beep);			
		}
	
		
		tp.pause();
		
		Collection<Runnable> tasksLeft = tp.shutdownNow();
		
		tp.resume();
		
			try {
				tp.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {}
		
			
			for (Runnable task: tasksLeft) {
				task.run();
			}
	}
	
	void Beep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		
		System.out.println(Thread.currentThread().getName() + " preforms task");
	}
	
	
	@Test 
	public void runnableSubmitTest() {	
		set.clear();
		for (int i = 0; i < 2000; ++i) {
			tp.submit(this::countMe, Priority.NORMAL_PRIORITY);
		}
		
		try {
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) {
		}
		assertEquals(10, set.size(), "not as expected");
		
		tp.setNumThreads(1);
		try {
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) {
		}
		set.clear();
		
		for (int i = 0; i < 2000; ++i) {
			tp.submit(this::countMe, Priority.NORMAL_PRIORITY);
		}
		
		try {
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) {
		}
		
		System.err.println("num of active threads is =" + Thread.activeCount());
		
		assertEquals(1, set.size(), "not as expected");
	}
	
	@Test
	public void pauseResumeTest() {

		for (int i = 0; i < 20; ++i) {
			tp.submit(this::printMyName, Priority.NORMAL_PRIORITY);
		}
		
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
		}
		
		System.err.println(" this is size of set = " + set.size());
		tp.pause();
		
		
		System.err.println("setting one more special task");
		for (int i = 0; i < 10; ++i) {
			tp.submit(()->{try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			} System.err.println("hey, this is fun!");}, Priority.ONE);
		}
		
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
		}
	
		tp.resume();
		
		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException e) {
		}
	}
	
	@Test
	public void shutDownTest() {
		for (int i = 0; i < 20; ++i) {
			tp.submit(this::printMyName, Priority.NORMAL_PRIORITY);
		}
		
		System.err.println("sent them to print their name");
		
		tp.shutdown();
		
		try {
			boolean res = tp.awaitTermination(1, TimeUnit.MICROSECONDS);
			assertFalse(res);
		} catch (InterruptedException e) {
			System.err.println("exception thrown");
		}
		
		try {
			boolean res = tp.awaitTermination(5, TimeUnit.SECONDS);
			assertTrue(res);
		} catch (InterruptedException e) {
			System.err.println("exception thrown");
		}
		
	}
	
	@Test
	public void shutDownNow() {
		for (int i = 0; i < 20; ++i) {
			tp.submit(this::printMyName, Priority.NORMAL_PRIORITY);
		}
		
		tp.shutdownNow();
		
		
		try {
			assertFalse(tp.awaitTermination(1, TimeUnit.MICROSECONDS));
		} catch (InterruptedException e) {
		}
		
		
		try {
			assertTrue(tp.awaitTermination(6, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
		}
		
	}
	
	@Test
	public void futureTests() {
		
		Future<Integer> future1 = tp.submit(()->{return 1;}, Priority.NORMAL_PRIORITY);

		try {
			assertEquals(future1.get(), 1);
		} catch (InterruptedException | ExecutionException e) {
			System.err.println("exception thrown");
		}
		
		List<Future<Integer>> futureList = new ArrayList<>();
		for (int i = 0; i < 20; ++i) {
			final int y = i;
			futureList.add(tp.submit(()->{Thread.sleep(5000); return y;}, Priority.NORMAL_PRIORITY));
		}
		
		
		for (Future<Integer> future: futureList) {
			future.cancel(true);
		}
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}
		int cnt = 0;
		
		for (Future<Integer> future: futureList) {
			++cnt;
			assertTrue(future.isCancelled(),"cnt num is " + cnt);
		}
		
		Future<Integer> future2 = tp.submit(()->{Thread.sleep(5000);return 1;}, Priority.NORMAL_PRIORITY);

		try {
			assertNull(future2.get(2, TimeUnit.SECONDS));
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
		}
		
		
		
	}
	
	@Test
	public void priorityTest() {
		
		tp.pause();
		
		for (int i = 0; i < 200; ++i) {
			tp.submit(()-> {System.err.println("Im high priority " + Thread.currentThread().getId());}, Priority.MAX_PRIORITY);			
		}

		for (int i = 0; i < 200; ++i) {
			tp.submit(()-> {System.err.println("Im NORMAL_PRIORITY priority " + Thread.currentThread().getId());}, Priority.NORMAL_PRIORITY);			
		}
		
		for (int i = 0; i < 200; ++i) {
			tp.submit(()-> {System.err.println("Im low priority " + Thread.currentThread().getId());}, Priority.ONE);			
		}
		
		tp.resume();
		tp.pause();

		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
		tp.resume();
		set.clear();
		
		for (int i = 0; i < 2000; ++i) {
			tp.submit(this::countMe, Priority.ONE);			
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
		assertEquals(set.size(), 10);
		
	}
	
	
	@Test
	public void setingWhilePause() {
		
		set.clear();
		
		tp.pause();
		tp.setNumThreads(2);
		set.clear();

		for (int i = 0; i < 10; ++i) {
			tp.submit(()-> {try {
				Thread.sleep(100);
				System.err.println(Thread.activeCount());
			} catch (InterruptedException e) {}}, Priority.NORMAL_PRIORITY);
		}
		
		for (int i = 0; i < 200; ++i) {
			tp.submit(this::countMe, Priority.MAX_PRIORITY);
		}


		tp.resume();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {}


		
		assertEquals(2, set.size());
		
		tp.pause();
		tp.setNumThreads(20);

		set.clear();
		
		for (int i = 0; i < 200; ++i) {
			tp.submit(this::countMe, Priority.NORMAL_PRIORITY);
		}
		tp.resume();

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {}
		
		assertEquals(20, set.size());

		
	}
	
	
	void incrementMe() {
		++cntr;
	}
	
	void countMe() {
		set.add(Thread.currentThread());
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
		}
		System.err.println(Thread.currentThread().getId() + " " + set.size() +" = set size in threads");

	}
	
	void printMyName() {
		System.err.println(Thread.currentThread().getId());
		set.add(Thread.currentThread());
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
		}

	}
}

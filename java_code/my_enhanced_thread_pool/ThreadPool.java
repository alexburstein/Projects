package my_enhanced_thread_pool;

import org.jetbrains.annotations.NotNull;
import utils.my_blocking_queue.BlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class ThreadPool implements Executor {
	private final BlockingQueue<Task<?>> taskQueue = new BlockingQueue<>();
	private final ConcurrentHashMap<Long, Worker> workerMap = new ConcurrentHashMap<>();
	private final Semaphore threadsWaitShutDownSem = new Semaphore(0);
	private final Semaphore pauseSema = new Semaphore(0);
	private boolean enableNewTasks = true;
	private boolean isPaused = false;
	private int numOfThreads;
	private final static int HIGHEST_PRIORITY = Priority.MAX_PRIORITY.ordinal() + 1;
	private final static int LOWEST_PRIORITY = Priority.MIN_PRIORITY.ordinal() - 1;

	/**
	 * basic Ctor
	 * @param numOfThreads - requested num of threads. should be positive number.
	 */
	public ThreadPool(int numOfThreads) {
		if (1 > numOfThreads) {
			throw new IllegalArgumentException("unable to accept negetive number of threads");
		}

		this.numOfThreads = numOfThreads;
		initiateThreads(this.numOfThreads);
	}

	@SuppressWarnings("unused")
	public enum Priority {
		MIN_PRIORITY(1),
		ONE(1),
		TWO(2),
		THREE(3),
		FOUR(4),
		NORMAL_PRIORITY(5),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT(8),
		NINE(9),
		TEN(10),
		MAX_PRIORITY(10);

		Priority(int priority) {
		}
	}
	
	
	private void initiateThreads(int threadsToInit) {
		for (int i = 0; i < threadsToInit; ++i) {
			Worker thread = new Worker();
			workerMap.put(thread.getId(), thread);
			thread.start();
		}
	}
		
	/**
	 * add a new task to the threadPool.
	 * @param <T> return type of the call
	 * @param callToAction a callable method to be queued
	 * @param priority - task priority determines the tasks place in the order of execution
	 * @return a future object of type T
	 */
	public <T> Future<T> submit(Callable<T> callToAction, Priority priority) {
		return submit(callToAction, priority.ordinal());
	}
	
	/**
	 * add a new task to the threadPool.
	 * @param FuncToRun a runnable method to be queued.
	 * @param priority - task priority determines the tasks place in the order of execution.
	 * @return a future object of no particular type.
	 */
	public Future<?> submit(Runnable FuncToRun, Priority priority) {
		return submit(Executors.callable(FuncToRun, null), priority.ordinal());
	}

	/**
	 * add a new task to the threadPool.
	 * @param <T> return type of the call
	 * @param FuncToRun - a runnable method to be queued.
	 * @param priority - task priority determines the tasks place in the order of execution.
	 * @param callReturnValue - the return value for the future.
	 * @return a future object of type T.
	 */

	@SuppressWarnings("unused")
	public <T> Future<T> submit(Runnable FuncToRun, Priority priority, T callReturnValue) {
		return submit(Executors.callable(FuncToRun, callReturnValue), priority.ordinal());
	}
	
	private void submit(Runnable toRun, int priority) {
		submit(Executors.callable(toRun, null), priority);
	}
	
	private <T> Future<T> submit(Callable<T> callToAction, int priority) {
		if (!enableNewTasks) {
			return null;			
		}
		
		Objects.requireNonNull(callToAction);	
		Task<T> task = new Task<>(callToAction, priority);
		taskQueue.enqueue(task);
		return task.future;		
	}
	
	@Override
	public void execute(@NotNull Runnable toRun) {
		submit(Executors.callable(toRun, null), Priority.NORMAL_PRIORITY);
	}
		
	/**
	 * activate only after shutDown() is activated.
	 * blocks the HelloWorld.main thread until time is up, or all threads are dead. 
	 * @param time - number of elements of time, to wait.
	 * @param unit - the chosen time unit.
	 * @return true if all threads are dead before time is up. false otherwise.
	 * @throws InterruptedException if an interruption is thrown during wait to join threads (should not happen)
	 */
	public boolean awaitTermination(long time, TimeUnit unit) throws InterruptedException {
		boolean isAcuired = threadsWaitShutDownSem.tryAcquire(numOfThreads, time, unit);
		
		for (Thread thread: workerMap.values()) {
			thread.join();
		}
		
		return isAcuired;
	}

	/**
	 * shuts down the ability to submit new tasks.
	 */
	public void shutdown() {
		if (!enableNewTasks) {
			return;
		}
		if (isPaused) {
			resume();
		}
		
		induceTasks(this::killThreadBeforeShutDown, numOfThreads, LOWEST_PRIORITY);
		enableNewTasks = false;
	}
	
	/**
	 * shuting down the thread pool as soon as possible.
	 * exporting all tasks to the user.
	 * blocks all submits.
	 * sends interupts to all running threads.
	 * @return list of left over tasks for the user.
	 */
	public List<Runnable> shutdownNow() {
		List<Runnable> list = dequeueThreadMapToList();
		
		for (Thread t: workerMap.values()) {
			t.interrupt();
		}
		
		shutdown();
		return list;
	}
	
	private List<Runnable> dequeueThreadMapToList() {
		List<Runnable> list = new ArrayList<>();

		try {
			for (Task<?> task = taskQueue.dequeue(1, TimeUnit.MICROSECONDS)
				 ; task != null
					; task = taskQueue.dequeue(1, TimeUnit.MICROSECONDS)) {
			
				list.add(task);
			}
		}
		catch (InterruptedException e) {
			/* Do nothing */
		}
		return list;
	}
	
	/**
	 * pauses the execution of the queued tasks until resume() is invoked.
	 */
	public void pause() {
		if (isPaused) {
			return;
		}
		
		induceTasks(this::pauseThreads, numOfThreads, HIGHEST_PRIORITY);			
		isPaused = true;
	}

	/**
	 * resumes the execution of the queued tasks if pause() was called.
	 */
	public void resume() {
		if (!isPaused) {
			return;
		}
		
		isPaused = false;	
		pauseSema.release(numOfThreads);
	}
	
	public void setNumThreads(int reqNumOfThreads) {
		if (reqNumOfThreads < 0) {
			throw new IllegalArgumentException("cant set negetive thread number");
		}
		
		int threadNumDiff = reqNumOfThreads - numOfThreads;

		numOfThreads += threadNumDiff;

		if (threadNumDiff > 0) {
			if (isPaused) {
				induceTasks(this::pauseThreads, threadNumDiff, HIGHEST_PRIORITY);
			}
			
			initiateThreads(threadNumDiff);
		}
		else if (threadNumDiff < 0) {
			induceTasks(this::killThread, -threadNumDiff, HIGHEST_PRIORITY);
			if (isPaused) {
				pauseSema.release(-threadNumDiff);
			}
		}
	}
	
	/**
	 * changes the number of worker threads in the thread pool
	 * @param num number of threads
	 */
	
	private void induceTasks(Runnable toRun, int num, int priority) {
		for (int i = 0; i < num; ++i) {
			submit(toRun, priority);
		}
	}
	
	private void killThread() {
		workerMap.get(Thread.currentThread().getId()).turnThreadOff();
	}
	
	private void killThreadBeforeShutDown() {
		workerMap.get(Thread.currentThread().getId()).turnThreadOff();
		threadsWaitShutDownSem.release();
	}
	
	private void pauseThreads() {
		try {
			pauseSema.acquire();
		} catch (InterruptedException e) {/* do nothing*/}
	}
	
	private class Worker extends Thread{
		private boolean threadIsOn = true;
		
		@Override
		public void run() {
			while (threadIsOn) {
				try {
					taskQueue.dequeue().run();
				} 
				catch (Exception e) {
					/* keep running in the loop while thread is on*/
				}
			}
			workerMap.remove(Thread.currentThread().getId());
		}
		
		private void turnThreadOff() {
			threadIsOn = false;
		}
	}
	
	private static class Task<V> implements Comparable<Task<V>>, Runnable {
		private final Callable<V> callable;
		private final FutureObj<V> future = new FutureObj<>();
		private final int taskPriority;
		private Thread taskPerformer;
		
		private Task(Callable<V> callable, int priority) {
			this.callable = callable;
			taskPriority = priority;
		}
		
		@Override
		public void run() {
			taskPerformer =  Thread.currentThread();
			if (!future.isCancelled()) {					
				try {
					future.setReturnValue(callable.call());
				
				} catch (InterruptedException exc) {
					future.isCancelled = true;
				} catch (Exception exc) {
					setExceptionToFuture(exc);
				}
				future.isDone = true;
			}
		}
		
		private void setExceptionToFuture(Exception exc) {
			future.exc = exc;
		}
		
		@Override
		public int compareTo(Task<V> otherTask) {
			return otherTask.taskPriority - taskPriority;
		}
		
		private class FutureObj<T> implements Future<T>{
			private T futureResult;
			private Exception exc;
			private volatile boolean isDone = false;
			private volatile boolean isCancelled = false;
			private final Semaphore waitFuture = new Semaphore(0);
			
			private void setReturnValue(T callReturnValue) {
				futureResult = callReturnValue;
				waitFuture.release();
				isDone = true;
			}
			
			@Override
			public boolean cancel(boolean interuptIfRunning) {
				if (!isDone) {
					if (interuptIfRunning && taskPerformer != null) {
						taskPerformer.interrupt();
					}
					
					isCancelled = true;
				}
				
				return isCancelled;
			}

			@Override
			public T get() throws InterruptedException, ExecutionException {
				waitFuture.acquire();
				T callReturnValue = futureResult;			
				waitFuture.release();
				reThrowCallException();
				
				return callReturnValue;
			}
			
			@Override
			public T get(long time, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				T t;
				
				if (waitFuture.tryAcquire(time, unit)){
					t = futureResult;
					waitFuture.release();
					reThrowCallException();
					return t;
				}
				
				throw new TimeoutException("Time up for getting return value for task");
			}
			
			@Override
			public boolean isCancelled() {
				return isCancelled;
			}
			
			@Override
			public boolean isDone() {
				return isDone || isCancelled;
			}
			
			private void reThrowCallException() throws ExecutionException {
				if (future.exc != null) {
					throw new ExecutionException("see cause",exc);
				}
			}
		}
	}
}
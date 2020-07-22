package my_enhanced_thread_pool;

import org.junit.Before;
import org.junit.Test;
import my_enhanced_thread_pool.ThreadPool.Priority;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ThreadPoolTest2 {


    @Before
    public void createThreadPool(){
    }

    @Test
    public void testSimpleSubmit() throws InterruptedException{
        ThreadPool pool = new ThreadPool(48);
        Future<Integer> res = pool.submit(()->{
                                    System.out.println("FirstTask");
                    return Integer.valueOf(3);},
                                    Priority.ONE);
        pool.execute(()-> System.out.println("Second"));
        pool.submit(()-> System.out.println("Should be first priority though"),
                Priority.MAX_PRIORITY);

        try{
            Integer g = res.get();
            assertEquals(Integer.valueOf(3), g);
        }catch(InterruptedException exc){

        }catch(ExecutionException exc){

        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void testMultipleSubmitsWithGrowingNumberOfThreads() throws InterruptedException{

        int initNumOfThreads = Thread.activeCount();
        ThreadPool pool = new ThreadPool(1);
        int times = 100000;
        int[] arr = new int[times];
        for(int i = 0; i < times; ++i){
            int finalI = i;
            pool.execute(()-> {System.out.println("Task #" + finalI + "-");
                                                arr[finalI] = 1;});
        }
        pool.setNumThreads(50);
        try{
            Thread.sleep(3000);
        }catch(Exception exc){

        }

        assertEquals(50 + initNumOfThreads, Thread.activeCount());
        for (int i = 0; i < times; ++i){
            assertEquals(1, arr[i]);
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void testMultipleSubmitsWithShrinkingNumberOfThreads() throws InterruptedException{
        ThreadPool pool = new ThreadPool(1000);
        int times = 10000;
        int[] arr = new int[times];
        for(int i = 0; i < times; ++i){
        	int finalI = i;

            pool.submit(()->{arr[finalI] = 1; System.err.println("Task #" + finalI + "-"); return 1;}, Priority.EIGHT);
        }

        pool.setNumThreads(1);

        try{
            Thread.sleep(10000);
        }catch(Exception exc){

        }
        for (int i = 0; i < times; ++i){
            assertEquals("this is the num "+ i ,1, arr[i]);
        }

        System.out.println("%" + Thread.activeCount());
        assertEquals(3, Thread.activeCount());

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void testPause() throws InterruptedException{

        ThreadPool pool = new ThreadPool(200);

        int numOfIdxs = 20000;
        int[] arr = new int[numOfIdxs];

        pool.pause();

        for(int i = 0; i < numOfIdxs; ++i) {
            int finalI = i;
            pool.execute(() -> {
                System.err.println("Task #" + finalI + "-");
                arr[finalI]++;
            });
        }

        System.out.println("Now we start to roll bro");
        pool.resume();

        try{
            Thread.sleep(5000);
        }catch(Exception ex){}

        for (int i = 0; i < numOfIdxs; ++i){
           assertEquals(1, arr[i]);
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void IntegrationsetNumOfThreadsAndPause() throws InterruptedException{
        ThreadPool pool = new ThreadPool(520);

        int times = 10000;
        int[] arr = new int[times];

        for(int i = 0; i < times; ++i){
            int finalI = i;
            pool.execute(()-> {System.out.println("Task #" + finalI + "-");
                arr[finalI]++;});
        }

        pool.pause();
        pool.setNumThreads(3);
        pool.resume();

        try{
            Thread.sleep(5000);
        }catch(Exception ex){}

        for (int i = 0; i < times; ++i){
            assertEquals(1, arr[i]);
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void shutdownTestAwaitTrue() throws InterruptedException{
        ThreadPool pool = new ThreadPool(2);

        int times = 10000;
        int[] arr = new int[times];

        for(int i = 0; i < times; ++i){
            int finalI = i;
            pool.execute(()-> {System.out.println("Task #" + finalI + "-");
                arr[finalI]++;});
        }

        pool.shutdown();

        try{
            assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));
        }catch(InterruptedException exc){}

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void shutdownTestAwaitFalse() throws InterruptedException{
        ThreadPool pool = new ThreadPool(5);

        int times = 10000;
        int[] arr = new int[times];

        for(int i = 0; i < times; ++i){
            int finalI = i;
            pool.execute(()-> {System.out.println("Task #" + finalI + "-");
                arr[finalI]++;});
        }

        pool.shutdown();
        try{
            assertFalse(pool.awaitTermination(1, TimeUnit.MICROSECONDS));
        }catch(InterruptedException exc){}

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void shutdownNowTest() {
        ThreadPool pool = new ThreadPool(5);

        int times = 10000;
        int[] arr = new int[times];

        for(int i = 0; i < times; ++i){
            int finalI = i;
            pool.execute(()-> {
                    System.out.println("Task #" + finalI + "-");
                    arr[finalI]++;
                }
            );
        }

		pool.shutdownNow();

        try{
            Thread.sleep(200);
        }catch(InterruptedException exc){}

        boolean res = true;
        for (int i = 0; i < times; ++i){

            if (1 != arr[i]){
                res = false;
                break;
            }
        }

        assertFalse(res);
    }

    @Test
    public void futureCancelTesting(){
        ThreadPool pool = new ThreadPool(4);
        int times = 10000;
        int[] arr = new int[times];

        for (int i = 0; i < times; ++i){
            int finalI = i;
            pool.submit(()-> {
                        System.out.println("Task #" + finalI + "-");
                        arr[finalI]++;
                    }, Priority.MAX_PRIORITY
            );
        }

        Future<?> result = pool.submit(()-> {System.err.println("CANCELED"); try{Thread.sleep(100);} catch (Exception r){}}, Priority.ONE);

        result.cancel(false);

        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
        
        assertTrue(result.isCancelled() || result.isDone());

    }

    @Test
    public void futureIsDoneTesting(){
        ThreadPool pool = new ThreadPool(4);
        int times = 10000;
        int[] arr = new int[times];

        for (int i = 0; i < times; ++i){
            int finalI = i;
            pool.submit(()-> {
                        System.out.println("Task #" + finalI + "-");
                        arr[finalI] = 1;
                    }, Priority.MAX_PRIORITY
            );
        }

        Future<?> result = pool.submit(()-> System.err.println("CANCELED"),
                Priority.NORMAL_PRIORITY);

        
        while (!result.isDone()){
        	System.err.println("here");
        }

        assertTrue(result.isDone());
        assertFalse(result.isCancelled());
        
        
        for (int i = 0; i < times; ++i){
            int finalI = i;
            pool.submit(()-> {
                        System.out.println("Task #" + finalI + "-");
                        arr[finalI] = 1;
                    }, Priority.MAX_PRIORITY
            );
        }

        result = pool.submit(()-> {try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} System.err.println("CANCELED");},
                Priority.NORMAL_PRIORITY);

        result.cancel(true);
        
        try {Thread.sleep(100);} catch (InterruptedException e) {}

        assertTrue(result.isDone());
        assertTrue(result.isCancelled());

    }
}
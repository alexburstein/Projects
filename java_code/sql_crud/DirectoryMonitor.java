package sql_crud;

/*
 * a component of the file Monitor & remote backUp Application.
 * watches for changes in the given directory. when a change is observed, a watchKey is sent to the EventHandler.
 */

import utils.observer.CallBack;
import utils.observer.Dispatcher;

import java.io.IOException;
import java.nio.file.*;

public class DirectoryMonitor implements Runnable{
    private final Path filePath;
    private final Dispatcher<WatchKey> changeDispatcher = new Dispatcher<>();
    private WatchService watchService;
    private boolean monitorOn;
    private Thread curThread;

	/**
	 * Ctor
	 * @param origFilePathString a string path to the file that we are interested to monitor.
	 */
	public DirectoryMonitor(String origFilePathString){
		filePath = Paths.get(origFilePathString);
    }

    private void initWatchService() {
    	try {
			watchService = FileSystems.getDefault().newWatchService();
			Path parentDir = filePath.getParent();
			parentDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
		}
    	catch (IOException ignored) {}
    	monitorOn = true;
    }

	/**
	 *
	 * @param callback to register with the dispatcher, to get the notifications on event.
	 */
	public void register(CallBack<WatchKey> callback){
    	changeDispatcher.register(callback);
    }

	/**
	 *
	 * @param callback to unregister with the dispatcher, to stop getting notifications on event.
	 */
	public void unregister(CallBack<WatchKey> callback){
    	changeDispatcher.unregister(callback);
    }
    
    @Override
    public void run(){
    	curThread = Thread.currentThread();
    	initWatchService();

    	while(monitorOn) {
    		WatchKey watchKey;
			try {watchKey = watchService.take();} catch (InterruptedException e) {continue;}
			changeDispatcher.notifyAll(watchKey);
			watchKey.reset();
    	}
    	try {watchService.close();}		catch (IOException e) {e.printStackTrace();}
    }

	/**
	 * used to stop the activity of the directory monitor.
	 */
	public void stopMonitoring(){
    	monitorOn = false;
    	curThread.interrupt();
    }
}
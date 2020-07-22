package observer;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Dispatcher<T> implements AutoCloseable {
    private final Set<CallBack<T>> observers = new LinkedHashSet<>();
    private boolean isClosed = false;

	/**
     * @param cb - an instance of the callback class to be added.
     */
    public void register(CallBack<T> cb) {
    	requireNotClosed();
    	Objects.requireNonNull(cb);
    	if (cb.getDispatcher() != null) {
    		throw new IllegalArgumentException();
    	}
    	cb.setDispatcher(this);
    	observers.add(cb);
    }

    /**
     * removes the given observer from the set of observers that would be notified on call of notifyAll.
     * activates the observers onDispatcherDeath method.
     * if dispatcher closed, IllegalStateException would be thrown
     * @param cb the callBack that is registered.
	 */
    public void unregister(CallBack<T> cb) {
    	requireNotClosed();
    	Objects.requireNonNull(cb);
    	cb.stop();

		observers.remove(cb);
	}
    
    /**
     * activates the onEvent method of all observers subscribed to the current dispatcher.
     * @param event to be sent to all observers.
     */
    public void notifyAll(T event) {
    	requireNotClosed();
    	
    	for (CallBack<T> ob: observers) {
    		ob.update(event);
    	}
    }

    @Override
    public void close(){
    	isClosed = true;
    	    	
    	for (CallBack<T> ob: observers) {
    		ob.stop();
    	}
    	
    	observers.clear();
    }
    
    private void requireNotClosed() {
    	if (isClosed) {
    		throw new IllegalStateException();
    	}
    }
}
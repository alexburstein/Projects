package utils.observer;

import java.util.function.Consumer;

public abstract class Observer<T> {

	private final CallBack<T> cb;
	private T data; 
	

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
		
	public Observer() {
		cb = new CallBack<>(this::Action, (Void)-> System.out.println("stop func activated"));
	}
	
	public Observer(Consumer<T> updateFunc) {
		cb = new CallBack<>(updateFunc, (Void) -> System.out.println("stop func activated"));
	}
	
	
	public Observer(Consumer<T> updateFunc, Consumer<Void> stopFunc) {
		cb = new CallBack<>(updateFunc, stopFunc);
	}
	
	public void register(DispatcherHolder<T> Subject) {
		Subject.register(cb);
	}
	
	public void unregister() {
		cb.getDispatcher().unregister(cb);
		cb.stop();
	}
	
	abstract void Action(T d);
}
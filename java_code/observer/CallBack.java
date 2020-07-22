package observer;

import java.util.Objects;
import java.util.function.Consumer;

public class CallBack<T> {
	private Dispatcher<T> dispatcher;
	private final Consumer<T> updateFunc;
	private final Consumer<Void> stopFunc;

	public CallBack(Consumer<T> updateFunc, Consumer<Void> stopFunc){
		this.updateFunc = Objects.requireNonNull(updateFunc);
		this.stopFunc = Objects.requireNonNull(stopFunc);
	}
	
	public CallBack(Consumer<T> updateFunc){
		this(updateFunc, (Void)->{});
	}

	public Dispatcher<T> getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher<T> dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public void update(T data) {
		updateFunc.accept(data);
	}
	
	public void stop() {
		stopFunc.accept(null);
		dispatcher = null;
	}
}

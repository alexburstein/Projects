package observer;

/**
 * An Observer implementation, for Observer design pattern.
 * Made in 17. 12. 19.
 * by Alex Burstein
 * fs712 IRLD, Ramat-Gan Israel
 * Reviewed by Grisha Tsukerman.
 */

public class DispatcherHolder<T> {

	private T data;
	public Dispatcher<T> dispatcher = new Dispatcher<>();
	
	public T getdata() {
		return data;
	}

	public void setdata(T data) {
		this.data = data;
		dispatcher.notifyAll(data);
	}

	public void register(CallBack<T> cb) {
		dispatcher.register(cb);
	}
	
	
}

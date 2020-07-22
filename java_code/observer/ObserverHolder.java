package observer;

import java.util.function.Consumer;

public class ObserverHolder {
	
	public static void main(String[] args) {
		
		DispatcherHolder<Integer> subject = new DispatcherHolder<>();
		
		Observer<Integer> observer1 = new Obs1<>();
		Observer<Integer> observer2 = new Obs1<>(ObserverHolder:: notified);
		Observer<Integer> observer3 = new Obs2<>();

		observer1.setData(1);
		observer2.setData(1);
		observer3.setData(44);
		observer1.register(subject);
		observer2.register(subject);
		observer3.register(subject);

		/* changes the observed field, and notifies all about it */
		subject.setdata(0);
		System.out.println();
		
		/* stop the second and third observer */
		observer2.unregister();
		observer3.unregister();
		System.out.println();

		for (int i = 0; i < 5; ++i) {
			subject.setdata(i);
		}
		
		System.out.println();
		
		/* register the second and third observer */
		observer2.register(subject);
		observer3.register(subject);
		System.out.println();
		subject.dispatcher.close();
		
		/* all observers terminated from dispatcher list */
		for (int i = 0; i < 3; ++i) {
			subject.setdata(i);
			System.out.println("****************** no one listens to subject");
		}
		
		System.out.println();

		/* observer3 changes field of observer according to subject update */
		observer3.register(subject);

		for (int i = 1; i < 10; ++i) {
			subject.setdata(i);
		}
	}
	
	public static <T>void notified(T data){
		System.out.println("Second Observer notified of new data = " + data);
	}
	
	static class Obs1<T> extends Observer<T>{

		public Obs1() {
			super();
		}
		
		public Obs1(Consumer<T> updateFunc) {
			super(updateFunc);
		}

		@Override
		void Action(T d) {
			String obState = "First Observer waits for = " + 
								getData() + " Subject broadcasting on = " + d ;
			
			if (d.equals(getData())) {		
				System.out.println(obState + " ) <-- a Match !;");
			}
			else {
				System.out.println(obState);
			}
		}
	}
	
	static class Obs2<T> extends Observer<T>{

		@Override
		void Action(T d) {
			
				System.out.println("Third observer = old data is " +
								this.getData() + " replace with data from subject " + d);
				this.setData(d);
		}
	}
}
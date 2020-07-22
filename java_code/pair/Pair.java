package pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Pair<K, V> implements Map.Entry<K, V>{
	private final K key;
	private V value;
	
	private Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * 
	 * @param <K> key type
	 * @param <V> value type
	 * @param key element
	 * @param value element
	 * @return a Pair of those two elements
	 */
	public static <K, V> Pair<K, V> of(K key, V value){
		return new Pair<>(key, value);
	}
	
	/**
	 * 
	 * @param <K> key type
	 * @param <V> value type
	 * @param pair if null throw exception
	 * @return a new swapped pair
	 */
	public static <K, V> Pair<V, K> swap(Pair<K, V> pair){
		Objects.requireNonNull(pair);
		return new Pair<>(pair.getValue(), pair.getKey());
	}
	
	/**
	 * 
	 * @param <T> type of the element
	 * @param arr - an array of elements
	 * @return a pair of minimum and maximum from the array
	 */
	public static <T extends Comparable<? super T>> Pair<T,T> minMax(T[] arr){
		return minMax(arr, Pair::compareToToComparator);
	}

	public static <T> Pair<T,T> minMax(List<T> list, Comparator<? super T> cmp){
		T max = list.get(0);
		T min = list.get(0);

		for (int i = 0; i < list.size() - 1; i +=2) {
			T localMax = list.get(i);
			T localMin = list.get(i);

			if (localMax.equals(findMax(list.get(i), (list.get(i + 1)), cmp))) {
				localMin = list.get(i + 1);
			}
			else {
				localMax = list.get(i + 1);
			}

			max = findMax(localMax, max, cmp);
			min = findMin(localMin, min, cmp);
		}

		if (list.size() % 2 != 0) {
			max = findMax(list.get(list.size() - 1), max, cmp);
			min = findMin(list.get(list.size() - 1), min, cmp);
		}

		return Pair.of(min, max);
	}

		public static <T> Pair<T,T> minMax(T[] arr, Comparator<? super T> cmp){
		T max = arr[0];
		T min = arr[0];

		for (int i = 0; i < arr.length - 1; i +=2) {
			T localMax = arr[i];
			T localMin = arr[i];
			
			if (localMax.equals(findMax(arr[i], (arr[i+1]), cmp))) {
				localMin = arr[i+1];
			}
			else {
				localMax = arr[i+1];
			}
			
			max = findMax(localMax, max, cmp);
			min = findMin(localMin, min, cmp);
		}
		
		if (arr.length % 2 != 0) {
			max = findMax(arr[arr.length - 1], max, cmp);
			min = findMin(arr[arr.length - 1], min, cmp);
		}
		
		return Pair.of(min, max);
	}
	
	private static <T> T findMax(T first, T second, Comparator<? super T> cmp) {
		return cmp.compare(first, second) > 0 ? first : second;
	}
	
	private static <T> T findMin(T first, T second, Comparator<? super T> cmp) {
		return cmp.compare(first, second) > 0 ? second: first;
	}
	
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Pair)) {
			return false;
		}
		
		Pair<?, ?> pair = (Pair<?,?>) obj;
		
		return Objects.equals(pair.getKey(), this.key) && Objects.equals(pair.getValue(), this.value);
	}
	
	@Override
	public String toString() {
		return ("Pair [key:" + key + ", value:" + value + "]");
	}
	
	private static <T extends Comparable<? super T>> int compareToToComparator(T t1, T t2) {
		return t1.compareTo(t2);
	}
}

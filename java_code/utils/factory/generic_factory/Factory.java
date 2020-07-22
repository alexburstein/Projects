package utils.factory.generic_factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Factory<K, T, D> {
	private final Map<K, Function<D, ? extends T>> map = new HashMap<>();
	

	/**
	 * activating the method in the key
	 * @param key for the function
	 */
	public T create(K key, D data) {
		return map.get(key).apply(data);
	}

	public T create(K key) {
		return create(key,null);
	}

	/**
	 * adds a method to the utils.factory.
	 * @param key for the function
	 * @param func for placing in the map. if null, will throw NullPointerException
	 */
	public Function<D, ? extends T> add(K key, Function<D, ? extends T> func) {
		Objects.requireNonNull(func);
		return map.put(key, func);
	}
}

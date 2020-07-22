package factory.singleton;

import factory.generic_factory.Factory;

import java.util.function.Function;

public class FactorySingleton {
	private final Factory<String, Void, String> factory = new Factory<>();

	private FactorySingleton(){
		add("create client", this::passOnToDB);
		add("add product", this::passOnToDB);
		add("iot register", this::passOnToDB);
		add("iot update", this::passOnToDB);
	}

	private Void passOnToDB(String s){
		return null;
	}

	private static class Helper{
		private static final FactorySingleton instance = new FactorySingleton();
	}

	public static FactorySingleton getInstanceOf(){
		return Helper.instance;
	}

	public Void create(String key, String data){
		return factory.create(key, data);
	}

	public Void create(String key){
		return factory.create(key, null);
	}

	public Function<String,? extends Void> add(String key, Function<String,Void> func){
		return factory.add(key, func);
	}
}


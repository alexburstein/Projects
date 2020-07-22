package factory.generic_factory;

import java.util.function.Function;

public class FactoryTestAndExcersize {

	public static void main(String[] args) {
		Factory<Integer, String, String> stringFactory = new Factory<>();

		lambdaFunc(stringFactory);
		AnonymusClassFunc(stringFactory);
		staticFunc(stringFactory);
		withoutParameter(stringFactory);

		/* creating an instance of a class implementing Function  */
		Function<String, String> x = new InstncMgr();
		stringFactory.add(4, x);
		
		/* prints all the elements that were added to the factory */
		printEm(stringFactory);
		
		
		/* new factory, recieves "this" of class Nested2  */
		Factory<Integer, String, Nested2> Nested2Factory = new Factory<>();
		Nested2Factory.add(1, Nested2::printhehe);
		System.out.println(Nested2Factory.create(1, new Nested2()));
	}
		

	static void printEm(Factory<Integer, String, String> factory) {
		System.out.println(factory.create(1, "lambda"));
		System.out.println(factory.create(2, "Anonymus"));
		System.out.println(factory.create(3, "static"));
		System.out.println(factory.create(4, "instance"));
		System.out.println(factory.create(5));
	}

	
	static void withoutParameter(Factory<Integer, String, String> factory) {
		factory.add(5, (noMatter)-> "withoutParameter");
	}

	static void lambdaFunc(Factory<Integer, String, String> factory) {
		factory.add(1, (s)-> s);
	}
	
	static void AnonymusClassFunc(Factory<Integer, String, String> factory) {
		factory.add(2, new Function<String, String>() {

			@Override
			public String apply(String t) {
				return t;
			}
		});
	}
	
	static void staticFunc(Factory<Integer, String, String> factory) {
		factory.add(3, NestedClass::giMeBack);
	}


	public void nonStaticFunc(Factory<Integer, String, Nested2> factory) {
		factory.add(1, Nested2::printhehe);
	}
	
	static public class NestedClass{
		static String giMeBack(String s) {
			return s;
		}
	}
	
	static public class Nested2{
		String printhehe() {
			return "nonStatic 5";
		}
	}	
	
	static class InstncMgr implements Function<String, String> {
		public String apply(String t) {
			return t + " ok";
		}
	}
}
	

	

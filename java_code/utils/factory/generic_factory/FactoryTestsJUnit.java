package utils.factory.generic_factory;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryTestsJUnit {
	
	@Test
	public void createFactory() {
		Factory<Integer,Else, Class<?>> factory = new Factory<>();
		factory.add(1, FactoryTestsJUnit::CtorInit);
		
		Else els = factory.create(1, Else.class);
		Else ells = factory.create(1, Else2.class);
		
		assertEquals(9, els.returnNum());
		Else2 ell = (Else2) ells;
		assertEquals("I read you saied: hello", ell.pringString("hello"));
		
		factory.add(2, (classObj)->FactoryTestsJUnit.CtorInit(classObj));
		
		els = factory.create(2, Else.class);
		assertEquals(9, els.returnNum());
	}
	
	@Test
	public void moreTest() {
		Factory<Integer,Else, String> factory = new Factory<>();
		factory.add(1, Else3::new);
		factory.add(2, (s)-> new Else3(s));
		Else e = factory.create(1);
		Else e2 = factory.create(2);
		assertEquals(9, e.returnNum());
		assertEquals(9, e2.returnNum());
		
		Else3 e3 = (Else3)e;
		Else3 e4 = (Else3)e2;
		
		assertEquals("hey!", e3.returnHey());
		assertEquals("hey!", e4.returnHey());

	}
	
	
	@Test
	public void fewMore() {
		Factory<Integer, Else, String> factory = new Factory<>();
		
		factory.add(2, new Function<String, Else>() {
			@Override
			public Else apply(String t) {
				return new Else2();
			}
		});
		
		Nested n = new Nested();
		factory.add(3, n::returnMe);

		Else e1 = factory.create(2);
		Else e2 = factory.create(3);

		assertEquals(9, e1.returnNum());
		assertEquals(9, e2.returnNum());
	}
	
	
	public static class Nested {
		Else2 heyItsMe = new Else3("hello");
		
		public Else2 returnMe(String s) {
			return heyItsMe;
		}
	}
	
	
	private static Constructor<?> getDefaultCtor(Class<?> c) throws Exception {
		Constructor<?>[] ctors = c.getDeclaredConstructors();
		for (Constructor<?> ctor: ctors) {
			if (ctor.getParameterCount() == 0) {
			    ctor.setAccessible(true);
			    return ctor;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T CtorInit(Class<?> s) {
		T t = null;
		try {
			Constructor<?> 	ctr = getDefaultCtor(s);
		
			t = (T) ctr.newInstance();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}

	static class Else {
		Integer number;


		public Else() {
			number  = 9;
		}

		public Integer returnNum() {
			return number;
		}

	}


	static class Else2 extends Else {

		public String pringString(String s) {
			return "I read you saied: " + s;
		}
	}

	static class Else3 extends Else2 {

		public Else3(String s){

		}

		public String returnHey() {
			return "hey!";
		}
	}
}

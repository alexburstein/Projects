package utils.pair;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {

		String name = "alex";
		Integer num = 1;
		
		Pair<Integer, String> person = Pair.of(num, name);
		
		System.out.println("toString " + person.toString());
		System.out.println("hashcode " + person.hashCode());
		System.out.println("name is (value)  " + person.getValue());
		System.out.println("key" + person.getKey());
		
		String name2 = "66";
		Integer num2 = 66; 
		
		Pair<Integer, String> person2 = Pair.of(num2, name2);

		if (person.equals(person2))
		{
			System.out.println("gets different as equal");
		}
		
		System.out.println(person2.hashCode());

		
		if (!person.equals(person))
		{
			System.out.println("gets equal as different");
		}
		
		Integer a = 11;
		Integer b = 22;
		
		Pair<Integer, Integer> being = Pair.of(a, b);

		System.out.println(being.toString());
		System.out.println(Pair.swap(being).toString());

		Integer[] arr = {1, 88, 4, 6, 7, 8, -2};
		
		System.out.println(Pair.minMax(arr).toString());
		
		
		System.out.println(Pair.minMax(arr, (o1, o2) -> {return o1 - o2;}));
		
		
		List<String> list = new ArrayList<>();
		
		System.out.println(list.getClass());
	}
}




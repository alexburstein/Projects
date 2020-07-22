package sql_crud.tests;

import sql_crud.CRUD;
import sql_crud.SqlCrudImp;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JDBC_Tests {
	private static CRUD<Integer, String> crud = null;

	@BeforeClass
	public static void init(){
		String pathToMySql = "jdbc:mysql://localhost:3306/";
		String user = "alex";
		String passWord = "speaker";
		try {
			crud = new SqlCrudImp(pathToMySql, user, passWord);
		} catch (Throwable throwable) {
			throwable.getMessage();
			throwable.printStackTrace();
		}
	}

	@Test
	public void createReadTest() {
		init();
		try {
			int num1 = crud.create(String.valueOf(1));
			for (int i = 2; i <= 10; ++i){
				int num2 = crud.create(String.valueOf(i));
				assertEquals(num1 + i - 1, num2);
			}

			for (int i = 0; i < 10; ++i){
				Integer res = Integer.valueOf(crud.read(num1 + i));
				assertEquals(i + 1, res);
			}
			crud.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateDelete() {
		init();
		String res;
		String res2;
		String res3;

		int i = crud.create("a line");
		res = crud.read(i);
		res2 = crud.update(i, "this is a first replacement");
		assertEquals(res2,res);
		assertEquals(res, "a line");

		res2 = crud.update(i, "this is a second replacement");
		assertNotEquals(res2, res);

		crud.create("a new line");

		res3 = crud.delete(i);

		assertNotEquals(res2, res3);
		assertEquals("this is a first replacement", res2);

		assertEquals("this is a second replacement", res3);


		try {
			crud.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

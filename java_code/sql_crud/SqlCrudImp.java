package sql_crud;

import java.sql.*;

public class SqlCrudImp implements CRUD<Integer, String> {
	private Connection con;
	private Statement stmt = null;
	private ResultSet res = null;

	public SqlCrudImp() throws SQLException, ClassNotFoundException {
		this("jdbc:mysql://localhost:XXXX/","XXXXX","XXXXX");
	}

	public SqlCrudImp(String pathToSqlServer, String userName, String password) throws SQLException, ClassNotFoundException {
		connectToSql(pathToSqlServer + "?useSSL=false", userName, password);
		createTableIfNotThere();
	}

	private void connectToSql(String pathToSqlServer, String userName, String password) throws SQLException, ClassNotFoundException{
		try {Class.forName("com.mysql.jdbc.Driver");}
		catch (ClassNotFoundException e){throw new ClassNotFoundException("problem with driver", e);}
		try {con = DriverManager.getConnection(pathToSqlServer, userName, password);}
		catch (SQLException e) {throw new SQLException("unable to connect to DB", e);}
		stmt = con.createStatement();
	}

	private void createTableIfNotThere() {
		try {
			String statementStr = "USE backup";
			stmt.execute(statementStr);

			statementStr = "CREATE TABLE IF NOT EXISTS syslog_backup "
								 + "(line_num int NOT NULL AUTO_INCREMENT, lineString varchar(800) "
								 + ",PRIMARY KEY (line_num))";

			stmt.execute(statementStr);
		}
		catch (SQLException e) { System.err.println("exception when connecting to SQL DB");}
	}

    /**
     * appends string to new line at end of file
     * @param data - the string to append to the file
     * @return number of line
     */
    @Override
    public Integer create(String data) {
       	int rowNum = 0;

    	try {
    		String statementStr = "INSERT INTO syslog_backup VALUES (null,'" + data + "')";
    		stmt.executeUpdate(statementStr);
    		statementStr = "SELECT line_num FROM syslog_backup ORDER BY line_num DESC LIMIT 1";
    		res = stmt.executeQuery(statementStr);
    		res.next();
    		rowNum = res.getInt(1);
		}
    	catch (SQLException e) {
    		System.err.println("exception while creating a new row");
    		e.printStackTrace();
    	}
    	    	
    	return rowNum;
    }
    
    /**
     * UNUSED
     */
    @Override
    public String read(Integer key){
    	String statementStr = "SELECT lineString FROM syslog_backup WHERE line_num = " + key;
    	String resString = null;
    	
    	try {
			res = stmt.executeQuery(statementStr);
			if (!res.last()) {
				return null;
			}
			resString = res.getString(1);
		} 
    	catch (SQLException e) {
			System.err.println("exception while attempting to read a row in the DB");
		}

        return resString;
    }

    /**
     * replaces the current line indicated by the key, with a new String
     * @param key - number of line to replace
     * @param newData - new string to replace the old data in the line indicated by key.
     */
    @Override
    public String update(Integer key, String newData){
    	String oldLine = read(key);
    	String statementStr = "UPDATE syslog_backup SET lineString = '" +newData+ "' WHERE line_num = "+ key;
    	
    	System.err.println("DEBUG: updating data = " + newData + " on key =" + key);
    	
    	try {
    		stmt.execute(statementStr);
		} 
    	catch (SQLException e) {
			System.err.println("exception while attempting to update a row in the DB");
    	}
    	
    	return oldLine;
    }

    /**
     * deletes the last element. ignores the given key.
     * @param key - if the given key is the last key, the autoincrement will reset to this key.
     * @return the old value in sake key.
     */
    @Override
    public String delete(Integer key){
       	String oldLine = read(key);
		String statementStr = "DELETE from syslog_backup order by line_num desc limit 1";

    	try {
			stmt.execute(statementStr);
			// Will reset the autoIncrement only if the key is the last element in the table.
			statementStr = "ALTER TABLE syslog_backup AUTO_INCREMENT =" + key;
			stmt.execute(statementStr);
		} 
    	catch (SQLException e) {
    		System.err.println("exception while attempting to create a new row in the DB");
    	}
		return oldLine;    
	}

    /**
     * makes sure every resource is closed
     */
	@Override
	public void close() {
		try {
	    	String statementStr = "COMMIT";
    		stmt.execute(statementStr);
    		res.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("exception while attempting to close the DB");
		}
	}
}
package model;


import java.sql.*;
import java.util.Scanner;

/**
 * A class that connects the java code to the SQL database to facilitate Queries.
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class DBConnection {
	
	private static final String DB_URL = "jdbc:mysql://localhost/workshop2?useSSL=false";

	//private static final String DB_USER = "1DV607";
	//private static final String DB_PASSWORD = "1DV607";
	private static Connection connection = null;
	private static Statement statement = null;
	
	private static void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Scanner scanner = new Scanner(DBConnection.class.getResourceAsStream("UserAndPassword.txt"));
			String DB_USER = "";
			String DB_PASSWORD = "";
			if (scanner.hasNextLine())
				DB_USER = scanner.nextLine();
			
			if (scanner.hasNextLine())
				DB_PASSWORD = scanner.nextLine();
			
			scanner.close();
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			statement = connection.createStatement();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes a query and returns a the results
	 * @param query The SQL query
	 * @return the result set of the query execution
	 * @throws SQLException if the SQL connection fails
	 */
	public static ResultSet executeQuery(String query) throws SQLException {
		
		if (connection == null || statement == null)
			initialize();
		
			ResultSet rs = statement.executeQuery(query);
			return rs;

		
	}
	
	/**
	 * Executes an update query.
	 * @param query the SQL querry
	 * @throws SQLException if the SQL connection fails
	 */
	public static void execute(String query) throws SQLException {
		if (connection == null || statement == null)
			initialize();
			statement.executeUpdate(query);
	}
	

}
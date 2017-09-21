package model;

import java.sql.*;

public class DBConnection {
	
	private static final String DB_URL = "jdbc:mysql://localhost/workshop2?useSSL=false";

	private static final String DB_USER = "1DV607";
	private static final String DB_PASSWORD = "1DV607";

	private static Connection connection = null;
	private static Statement statement = null;
	
	private static void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			statement = connection.createStatement();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery(String query) throws SQLException {
		
		if (connection == null || statement == null)
			initialize();
		
			ResultSet rs = statement.executeQuery(query);
			return rs;

		
	}
	
	public static void execute(String query) throws SQLException {
		if (connection == null || statement == null)
			initialize();
			statement.executeUpdate(query);
	}
	

}
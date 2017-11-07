package model;


import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * A class doing all the operations for updating and retreiving information from the database
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class DBConnection {
	
	private static final String DB_URL = "jdbc:mysql://localhost/workshop2?useSSL=false";

	private Connection connection = null;
	private Statement statement = null;
	
	// Initiazlize the connection with the mysql database.
	private void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Scanner scanner = new Scanner(DBConnection.class.getResourceAsStream("UserAndPassword.txt"));
			System.out.println("Test Output");
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
	 * Executes a query and returns the results
	 * @param query The SQL query
	 * @return the result set of the query execution
	 * @throws SQLException if the SQL connection fails
	 */
	private ResultSet executeQuery(String query) throws SQLException {
		
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
	private void execute(String query) throws SQLException {
		if (connection == null || statement == null)
			initialize();
			statement.executeUpdate(query);
	}
	
	/**
	 * Stores a member in the database. The member will not be stored if there's already a
	 * member with the same personal number in the database.
	 * @param member The member to be stored
	 * @return true if the member has been successfully stored, false otherwise.
	 */
	public boolean storeMember(Member member) {

		try {

			if (!checkPersonalNumber(member.getPersonalNumber()))
				return false;

			String s = String.format("INSERT INTO members (name, pnumber) VALUES ('%s', '%S');", member.getName(),
					member.getPersonalNumber());
			execute(s);

			s = "SELECT LAST_INSERT_ID();";

			ResultSet rs = executeQuery(s);
			rs.next();
			member.setMemberId(rs.getInt(1));
			rs.close();

			member.setStored(true);

			ArrayList<Boat> boats = member.getBoatList();
			for (Boat boat : boats) {
				storeBoat(boat);
			}

			return true;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 * Gets a member by the member's personal number
	 * @param personalNumber member's personal number 
	 * @return the member if he/she has been found, null otherwise.
	 */
	public Member getMember(String personalNumber) {

		String s = String.format("SELECT * FROM members WHERE pnumber = '%s';", personalNumber);
		
		try {
			ResultSet rs = executeQuery(s);
			if (!rs.next())
				return null;
			
			Member member = new Member(rs.getString(2), rs.getString(3));
			member.setMemberId(rs.getInt(1));
			member.setStored(true);
			rs.close();
			
			Boat[] boats = getMemberBoats(member);
			if (boats != null)
				for (int i=0; i<boats.length; i++)
					member.addBoat(boats[i]);
			
			return member;
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	
	/**
	 * Checks if a member can be stored in the database. A member can be stored in the database
	 * if there's not stored members with the same personal member (i.e database doesn't allow
	 * duplicate personal numbers).
	 * @param personalNumber The personal number to be checked 
	 * @return true if the member can be stored in the database, false otherwise.
	 */
	public boolean checkPersonalNumber(String personalNumber) {

		try {
			String s = String.format("SELECT * FROM members WHERE pnumber = '%s';", personalNumber);
			ResultSet rs = executeQuery(s);
			boolean b = rs.next();
			rs.close();
			return !b;
		}

		catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Deletes a member from the database.
	 * @param member the member to be deleted 
	 * @return true if the member has been found and deleted, false otherwise.
	 */
	public boolean deleteMember(Member member) {
		if (!checkMemberId(member))
			return false;

		try {

			String s = String.format("DELETE FROM members WHERE id ='%d';", member.getMemberId());
			execute(s);
			member.setStored(false);
			return true;
		}

		catch (SQLException e) {
			return false;
		}
	}

	
	// Checks if the member is stored in the database by checking the member's ID. 
	// Returns true if the member is stored, false otherwise.
	private boolean checkMemberId(Member member) {

		try {
			String s = String.format("SELECT * FROM members WHERE id = '%d';", member.getMemberId());
			ResultSet rs = executeQuery(s);
			boolean b = rs.next();
			rs.close();
			return b;
		}

		catch (SQLException e) {
			return false;
		}

	}

	/**
	 * Updates a member's name.
	 * @param member The member needed to be updated.
	 * @param newName The member's new name.
	 * @return true if the member has been found and updated, false otherwise.
	 */
	public boolean updateMemberName(Member member, String newName) {

		if (!checkMemberId(member))
			return false;

		try {
			String s = String.format("UPDATE members SET name = '%s' WHERE id = '%d';", newName, member.getMemberId());
			execute(s);
			return true;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Stores the member's unstored boats. This method will only work if the member is 
	 * already stored in the database.
	 * @param member Member whose boats list is to be updated
	 * @return true if the member has been found in the database and the boats list updated, false otherwise.
	 */
	public boolean updateMemberBoatStorage(Member member) {
		if (!member.isStored())
			return false;

		ArrayList<Boat> boats = member.getBoatList();
		for (Boat boat:boats) {

			if (boat.isStored())
				continue;

			storeBoat(boat);

		}
		
		return true;

	}

	/**
	 * Stores a boat in the database.
	 * @param boat the boat to be stored
	 * @return true if the boat has been successfully stored, false otherwise.
	 */
	public boolean storeBoat(Boat boat) {

		try {
			String s = String.format(Locale.ROOT, "INSERT INTO boats (type, length, owner) VALUES ('%S', %f, %d);",
					boat.getType().toString(), boat.getLength(), boat.getOwner().getMemberId());
			execute(s);

			s = "SELECT LAST_INSERT_ID();";
			ResultSet rs = executeQuery(s);
			rs.next();
			boat.setBoatId(rs.getInt(1));
			rs.close();
			
			boat.setStored(true);
			return true;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Removes a boat from the database.
	 * @param boat The boat to be removed
	 * @return true if the boat has been found and removed, false otherwise.
	 */
	public boolean deleteBoat(Boat boat) {

		if (!checkBoat(boat))
			return false;

		try {

			String s = String.format("DELETE FROM boats WHERE id = %d;", boat.getBoatId());
			execute(s);
			boat.setStored(false);
			return true;

		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	// Checks if a boat is stored in the database by searching in the database by boat ID.
	private boolean checkBoat(Boat boat) {

		try {
			String s = String.format("SELECT * from boats WHERE id = '%d';", boat.getBoatId());
			ResultSet rs = executeQuery(s);
			boolean b = rs.next();
			rs.close();
			return b;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 * Get all members stored in the database. 
	 * @return a Member[] array of all the stored members or null if there are no members.
	 */
	public Member[] getMembers() {
		
		String s = String.format("SELECT * FROM members;");
		
		try {
			
			ResultSet rs = executeQuery(s);
			if (!rs.next())
				return null;
			
			rs.last();
			Member[] members = new Member[rs.getRow()];
			rs.beforeFirst();
			
			for (int i=0; rs.next(); i++) {
				
				members[i] = new Member(rs.getString(2), rs.getString(3));
				members[i].setMemberId(rs.getInt(1));
				members[i].setStored(true);
				
			}
			
			rs.close();
			
			for (int i=0; i<members.length; i++) {
				
				Boat[] boats = getMemberBoats(members[i]);
				for (int j=0; boats != null && j<boats.length; j++)
					members[i].addBoat(boats[j]);
			}
			
			return members;
			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	/**
	 * Gets all the boats owned by a member. 
	 * @param member The member whose boats list is to be returned
	 * @return a Boat[] array of all the member's boats or null if the member doesn't have any boats.
	 */
	public Boat[] getMemberBoats(Member member) {
		
		if (!member.isStored())
			return null;
		
		String s = String.format("SELECT * FROM boats WHERE owner = %d ORDER BY boats.id;", member.getMemberId());
		
		try {
			
			ResultSet rs = executeQuery(s);
			if (!rs.next())
				return null;
			
			rs.last();
			Boat[] boats = new Boat[rs.getRow()];
			rs.beforeFirst();
			
			for (int i=0; rs.next(); i++) {
				
				boats[i] = new Boat(member, Boat.BoatType.valueOf(rs.getString(2).toUpperCase()), rs.getDouble(3));
				boats[i].setBoatId(rs.getInt(1));
				boats[i].setStored(true);
				
			}
			
			rs.close();
			return boats;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Gets all the boats stored in the database 
	 * @return a Boat[] of all the stored boats
	 */
	public Boat[] getBoats() {
		
		Member[] members = getMembers();
		ArrayList<Boat> boats = new ArrayList<Boat>();
		
		for (int i=0; i<members.length; i++) {
			
			Boat[] memberBoats = getMemberBoats(members[i]);
			for (int j=0; j<memberBoats.length; j++)
				boats.add(memberBoats[j]);
			
		}
		
		return boats.toArray(new Boat[0]);
		
	}
	
	/**
	 * Updates a boat type.
	 * @param boat The boat to be updated.
	 * @param newType The new type.
	 * @return true if the boat has been found and updated, false otherwise.
	 */
	public boolean updateBoatType(Boat boat, Boat.BoatType newType) {
		
		if (!boat.isStored())
			return false;
		
		try {
			
			String s = String.format("UPDATE boats SET type = '%S' WHERE id = %d;", newType.toString(), boat.getBoatId());
			execute(s);
			return true;
			
		}
		
		catch (SQLException e) {
			return false;
		}
		
	}
	
	/**
	 * Updates a boat length
	 * @param boat the boat to be updated
	 * @param newLength the new length 
	 * @return true if the boat has been found and updated, false otherwise.
	 */
	public boolean updateBoatLength(Boat boat, double newLength) {
		
		if (!boat.isStored())
			return false;
		
		try {
			String s = String.format(Locale.ROOT, "UPDATE boats SET length = %f WHERE id = %d;", newLength, boat.getBoatId());
			execute(s);
			return true;
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * Gets a boat by ID from the database 
	 * @param id the boat's ID
	 * @return the boat if found, null otherwise
	 */
	public Boat getBoat(int id) {
		
		String s = String.format("select members.id, members.name, members.pnumber, boats.id, boats.type, boats.length from members, boats WHERE boats.id = %d AND members.id = boats.owner;", id);
		
		try {
			ResultSet rs = executeQuery(s);
			if (!rs.next())
				return null;
			
			
			
			Member member = new Member(rs.getString(2), rs.getString(3));
			member.setMemberId(rs.getInt(1));
			member.setStored(true);
			
			Boat boat = new Boat(member, Boat.BoatType.valueOf(rs.getString(5).toUpperCase()), rs.getDouble(6));
			boat.setBoatId(rs.getInt(4));
			boat.setStored(true);
			rs.close();
			
			return boat;
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
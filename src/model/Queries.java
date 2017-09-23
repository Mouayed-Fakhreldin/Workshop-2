package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A class for all the queries for storing and retreiving members and boats from the database.
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw.
 *
 */
public class Queries {

	
	/**
	 * Stores a member in the database. The member will not be stored if there's already a
	 * member with the same personal number in the database.
	 * @param member The member to be stored
	 * @return true if the member has been successfully stored, false otherwise.
	 */
	public static boolean storeMember(Member member) {

		try {

			if (!checkPersonalNumber(member.getPersonalNumber()))
				return false;

			String s = String.format("INSERT INTO members (name, pnumber) VALUES ('%s', '%S');", member.getName(),
					member.getPersonalNumber());
			DBConnection.execute(s);

			s = "SELECT LAST_INSERT_ID();";

			ResultSet rs = DBConnection.executeQuery(s);
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
	public static Member getMember(String personalNumber) {

		String s = String.format("SELECT * FROM members WHERE pnumber = '%s';", personalNumber);
		
		try {
			ResultSet rs = DBConnection.executeQuery(s);
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
	public static boolean checkPersonalNumber(String personalNumber) {

		try {
			String s = String.format("SELECT * FROM members WHERE pnumber = '%s';", personalNumber);
			ResultSet rs = DBConnection.executeQuery(s);
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
	public static boolean deleteMember(Member member) {
		if (!checkMemberId(member))
			return false;

		try {

			String s = String.format("DELETE FROM members WHERE id ='%d';", member.getMemberId());
			DBConnection.execute(s);
			member.setStored(false);
			return true;
		}

		catch (SQLException e) {
			return false;
		}
	}

	
	// Checks if the member is stored in the database by checking the member's ID. 
	// Returns true if the member is stored, false otherwise.
	private static boolean checkMemberId(Member member) {

		try {
			String s = String.format("SELECT * FROM members WHERE id = '%d';", member.getMemberId());
			ResultSet rs = DBConnection.executeQuery(s);
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
	public static boolean updateMemberName(Member member, String newName) {

		if (!checkMemberId(member))
			return false;

		try {
			String s = String.format("UPDATE members SET name = '%s' WHERE id = '%d';", newName, member.getMemberId());
			DBConnection.execute(s);
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
	public static boolean updateMemberBoatStorage(Member member) {
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
	public static boolean storeBoat(Boat boat) {

		try {
			String s = String.format("INSERT INTO boats (type, length, owner) VALUES ('%S', %f, %d);",
					boat.getType().toString(), boat.getLength(), boat.getOwner().getMemberId());
			DBConnection.execute(s);

			s = "SELECT LAST_INSERT_ID();";
			ResultSet rs = DBConnection.executeQuery(s);
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
	public static boolean deleteBoat(Boat boat) {

		if (!checkBoat(boat))
			return false;

		try {

			String s = String.format("DELETE FROM boats WHERE id = %d;", boat.getBoatId());
			DBConnection.execute(s);
			boat.setStored(false);
			return true;

		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	// Checks if a boat is stored in the database by searching in the database by boat ID.
	private static boolean checkBoat(Boat boat) {

		try {
			String s = String.format("SELECT * from boats WHERE id = '%d';", boat.getBoatId());
			ResultSet rs = DBConnection.executeQuery(s);
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
	public static Member[] getMembers() {
		
		String s = String.format("SELECT * FROM members;");
		
		try {
			
			ResultSet rs = DBConnection.executeQuery(s);
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
	public static Boat[] getMemberBoats(Member member) {
		
		if (!member.isStored())
			return null;
		
		String s = String.format("SELECT * FROM boats WHERE owner = %d ORDER BY boats.id;", member.getMemberId());
		
		try {
			
			ResultSet rs = DBConnection.executeQuery(s);
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
	public static Boat[] getBoats() {
		
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
	public static boolean updateBoatType(Boat boat, Boat.BoatType newType) {
		
		if (!boat.isStored())
			return false;
		
		try {
			
			String s = String.format("UPDATE boats SET type = '%S' WHERE id = %d;", newType.toString(), boat.getBoatId());
			DBConnection.execute(s);
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
	public static boolean updateBoatLength(Boat boat, double newLength) {
		
		if (!boat.isStored())
			return false;
		
		try {
			String s = String.format("UPDATE boats SET length = %f WHERE id = %d;", newLength, boat.getBoatId());
			DBConnection.execute(s);
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
	public static Boat getBoat(int id) {
		
		String s = String.format("select members.id, members.name, members.pnumber, boats.id, boats.type, boats.length from members, boats WHERE boats.id = %d AND members.id = boats.owner;", id);
		
		try {
			ResultSet rs = DBConnection.executeQuery(s);
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

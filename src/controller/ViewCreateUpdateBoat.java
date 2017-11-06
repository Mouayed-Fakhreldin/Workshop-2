package controller;

import model.Boat;
import model.DBConnection;
import model.Member;
import model.Boat.BoatType;

/**
 * A controller class which handles and connects the Boats class in the view
 * package with the model classes and the Database. This controller class contains methods
 * for retreiving, updating, and creating boats from the database.  
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class ViewCreateUpdateBoat {
	
	private DBConnection dbConnection = new DBConnection();
	/**
	 * Gets the available boat types.
	 * @return a String array of the boat types.
	 */
	public String[] getBoatTypes() {
		
		BoatType[] types = BoatType.values();
		String[] boatTypes = new String[types.length];
		for (int i=0; i<types.length; i++)
			boatTypes[i] = types[i].toString();
		return boatTypes;
	}
	
	/**
	 * Adds a boat to a member's boat list and stores in the database.
	 * @param selectedPersonalNumber The personal number of the owner of the boat
	 * @param typeChoice The user's choice number for the boat type.
	 * @param length The length of the boat (must be at least 1 meter).
	 * @throws IllegalArgumentException if length is less than one.
	 */
	public void addBoat(String selectedPersonalNumber, int typeChoice, double length) {
		
		if (length < 1)
			throw new IllegalArgumentException("Length can only be equal or bigger than 1");
		
		Member member = dbConnection.getMember(selectedPersonalNumber);
		typeChoice--;
		BoatType[] types = BoatType.values();
		BoatType type = null;
		
		for (int i=0; i<types.length; i++) {
			if (typeChoice == i) {
				type = types[i];
			}
		}
		
		Boat boat = new Boat(member, type, length);
		dbConnection.storeBoat(boat);
	}
	
	/**
	 * Gets the list of boats registered for a certain member 
	 * @param selectedPersonalNumber The personal number of the owner of the boats
	 * @return list of boats as a string array or null if the member does not own any boats. Each string represents a boat with the form: "id, type, length"
	 */
	public String[] getBoats(String selectedPersonalNumber) {
		
		Member member = dbConnection.getMember(selectedPersonalNumber);
		Boat[] boats = dbConnection.getMemberBoats(member);
		if (boats == null)
			return null;
		
		String[] boatsStrings = new String[boats.length];
		for (int i=0; i<boats.length; i++) {
			StringBuilder s = new StringBuilder();
			s.append(boats[i].getBoatId());
			s.append(", ");
			s.append(boats[i].getType());
			s.append(", ");
			s.append(boats[i].getLength());
			boatsStrings[i] = s.toString();
		}
		
		return boatsStrings;
		
	}
	
	/**
	 * Gets a boat by ID from the database.
	 * @param id the boat's ID
	 * @return the boat as a String or null if the boat has not been found. The boat string will be in the form of "id, type, length"
	 */
	public String getBoatById(int id) {
		
		Boat boat = dbConnection.getBoat(id);
		if (boat == null)
			return null;
		
		String s = "" + boat.getBoatId();
		s += ", ";
		s += boat.getType().toString();
		s += ", ";
		s += boat.getLength();
		return s;
	}
	
	/**
	 * Removes a boat from the database 
	 * @param id The ID of the boat to be removed 
	 * @return true if the boat has been successfully removed, false otherwise.
	 */
	public boolean removeBoat(int id) {
		
		Boat boat = dbConnection.getBoat(id);
		return dbConnection.deleteBoat(boat);
		
	}
	
	/**
	 * Updates the length of a boat in the database.
	 * @param id The ID of the boat to be updated
	 * @param newLength The new length of the boat 
	 * @return true if the boat has been successfully updated, false otherwise.
	 * @throws IllegalArgumentException if length is less than one.
	 */
	public boolean updateBoatLength(int id, double newLength) {
		
		if (newLength <1)
			throw new IllegalArgumentException("Length can only be equal to or bigger than 1");
		
		Boat boat = dbConnection.getBoat(id);
		return dbConnection.updateBoatLength(boat, newLength);
	}
	
	/**
	 * Updates the boat type in the database.
	 * @param id the ID of the boat to be updated
	 * @param type Thye new type of the boat 
	 * @return true if the boat has been successfully updated, false otherwise.
	 */
	public boolean updateBoatType(int id, String type) {
		Boat boat = dbConnection.getBoat(id);
		BoatType newType = BoatType.valueOf(type.toUpperCase());
		return dbConnection.updateBoatType(boat, newType);
	}
}

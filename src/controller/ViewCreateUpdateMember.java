package controller;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.Queries;

/**
 * A controller class which handles and connects the Members class in the view
 * package with the model classes and the Database. This controller class contains methods
 * for retreiving, updating, and creating members from the database.  
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class ViewCreateUpdateMember {
	
	/**
	 * Checks if the name the user inputs for creating a memaber is valid. Name can not 
	 * be empty or null and can not contain too many spaces.
	 * @param name The name to be checked 
	 * @param message The validation message.
	 */
	public static void checkName(String name, Message message) {
		
		if (name == null || name.equals("")) {
			message.setMessage("Names cannot be empty");
			message.setValidated(false);
		}
		
		else if(name.equalsIgnoreCase("back")) {
			message.setValidated(true);
		}
		
		else if(!Member.checkName(name)) {
			message.setMessage("Too many spaces and too few characters");
			message.setValidated(false);
		}
		
		else
			message.setValidated(true);
		
	}
	
	/**
	 * Checks if the personal number the user inputs for creating a memaber is valid.
	 * Personal number needs to be a 10 digit number.
	 * @param personalNumber The personal number to be checked
	 * @param message The validation message.
	 */
	public static void checkPersonalNumberCreate(String personalNumber, Message message) {
		
		if (personalNumber != null && personalNumber.equalsIgnoreCase("back")) {
			message.setValidated(true);
			return;
		}
		
		if (personalNumber == null || personalNumber.length() != 10) {
			message.setValidated(false);
			message.setMessage("Please input 10 digit personal number!");
			return;
		}
		
		else {
			for (int i=0; i<personalNumber.length(); i++) {
				if (!Character.isDigit(personalNumber.charAt(i))) {
					message.setValidated(false);
					message.setMessage("Personal number cannot contain non-digit characters!");
					return;
				}
			}
		}
		
		if (!Queries.checkPersonalNumber(personalNumber)) {
			message.setMessage("Personal number is in use!");
			message.setValidated(false);
			return;
		}
		
		message.setValidated(true);
		
	}
	
	/**
	 * Searches for a personal number in the database. If the personal number has been found,
	 * the Message will be validated.
	 * @param personalNumber The personal number to be searched for.
	 * @param message The validation message.
	 */
	public static void checkPersonalNumber(String personalNumber, Message message) {
		
		if (personalNumber != null && personalNumber.equalsIgnoreCase("back")) {
			message.setValidated(true);
			return;
		}
		
		if (personalNumber == null || personalNumber.length() != 10) {
			message.setValidated(false);
			message.setMessage("Please input 10 digit personal number!");
			return;
		}
		
		else {
			for (int i=0; i<personalNumber.length(); i++) {
				if (!Character.isDigit(personalNumber.charAt(i))) {
					message.setValidated(false);
					message.setMessage("Personal number cannot contain non-digit characters!");
					return;
				}
			}
		}
		
		if (Queries.checkPersonalNumber(personalNumber)) {
			message.setMessage("Member has not been found. Please make sure the personal number is correct");
			message.setValidated(false);
			return;
		}
		
		message.setValidated(true);
	}
	
	/**
	 * Checks if the name the user inputs for updating a memaber's name is valid. Name can not 
	 * be empty or null and can not contain too many spaces.
	 * @param name The name to be checked 
	 * @param message The validation message.
	 */
	public static void checkNameUpdate(String name, Message message) {
		
		if (name == null || name.equals("")) {
			message.setMessage("Names cannot be empty");
			message.setValidated(false);
		}
		
		else if(!Member.checkName(name)) {
			message.setMessage("Too many spaces and too few characters");
			message.setValidated(false);
		}
		
		else
			message.setValidated(true);
		
	}
	
	/**
	 * Gets all the members from the database with the number of owned boats for each member.
	 * @return a compact list as a String
	 */
	public static String compactList() {

		Member[] members = Queries.getMembers();
		if (members == null)
			return "No members registered\n------------------------------------------------------";
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < members.length; i++) {

			if (i > 0)
				builder.append("\n");
			builder.append("Name: ");
			builder.append(members[i].getName());

			builder.append("\nMember ID: ");
			builder.append(members[i].getMemberId());

			builder.append("\nNumber of owned boats: ");
			builder.append(members[i].getBoatList().size());

			builder.append("\n------------------------------------------");
		}

		return builder.toString();
	}

	/**
	 * Gets all the members from the database with details about the owned boats.
	 * @return a verbose list as a String
	 */
	public static String verboseList() {

		Member[] members = Queries.getMembers();
		if (members == null)
			return "No members registered\n------------------------------------------------------";
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < members.length; i++) {

			if (i > 0)
				builder.append("\n");
			builder.append("Name: ");
			builder.append(members[i].getName());

			builder.append("\nMember ID: ");
			builder.append(members[i].getMemberId());
			
			builder.append("\nMember's Personal number: ");
			builder.append(members[i].getPersonalNumber());

			builder.append("\nNumber of owned boats: ");
			builder.append(members[i].getBoatList().size());

			if (members[i].getBoatList().size() == 0)
				builder.append("\n----------------------------------------");
			
			else {
				
				builder.append("\n\nBoats list:\n");
				ArrayList<Boat> boats = members[i].getBoatList();
				for (Boat boat:boats) {

					builder.append("Boat ID: ");
					builder.append(boat.getBoatId());
					builder.append(", ");
					
					builder.append(boat.getType().toString());
					builder.append(": ");
					
					builder.append(boat.getLength());
					builder.append(" meters.\n");
					
				}
				
				builder.append("------------------------------------------------");
				
			}
		}

		return builder.toString();

	}
	
	
	/**
	 * Creates a member and adds it to the database. Member will not be added to the database
	 * if the member's personal number already exists in the database (i.e no duplicate personal
	 * numbers).
	 * @param name The member's name
	 * @param personalNumber The member's personal number 
	 * @return true if the member has been created and added to the database, false otherwise.
	 */
	public static boolean createMember(String name, String personalNumber) {
		
		return Queries.storeMember(new Member(name, personalNumber));
		
	}
	
	/**
	 * Updates a member' name by looking for a member by personal number. 
	 * @param personalNumber The member's personal number 
	 * @param newName The member's new name
	 * @return true if the member's name has been updated successfully, false otherwise.
	 */
	public static boolean updateName(String personalNumber, String newName) {
		
		if (Queries.checkPersonalNumber(personalNumber))
			return false;
		
		return Queries.updateMemberName(Queries.getMember(personalNumber), newName);
		
	}
	
	/**
	 * Gets member's information including number of owned boats by searching through
	 * personal number.
	 * @param personalNumber Member's personal number
	 * @return The member's information as a String or null if the member has not been found.
	 */
	public static String getMemberInfo(String personalNumber) {
		
		if (Queries.checkPersonalNumber(personalNumber))
			return null;
		Member member = Queries.getMember(personalNumber);
		String s = "";
		s += "Name: ";
		s += member.getName();
		
		s += "\nMember ID: ";
		s += member.getMemberId();
		
		s += "\nNumber of owned boats: ";
		s += member.getBoatList().size();
		
		return s;
		
	}
	
	/**
	 * Gets a member's name by searching through the member's personal number
	 * @param personalNumber Member's personal number
	 * @return Member's name or null if the member has not been found.
	 */
	public static String getMemberName(String personalNumber) {
		if (Queries.checkPersonalNumber(personalNumber))
			return null;
		Member member = Queries.getMember(personalNumber);
		return member.getName();
	}
	
	/**
	 * Deletes a member from the database by searching through personal number
	 * @param personalNumber Member's personal number
	 * @return true if the member has been removed, false otherwise.
	 */
	public static boolean deleteMember(String personalNumber) {
		
		if (Queries.checkPersonalNumber(personalNumber))
			return false;
		
		Member member = Queries.getMember(personalNumber);
		return Queries.deleteMember(member);
	}
	
	
}

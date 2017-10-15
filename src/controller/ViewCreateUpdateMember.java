package controller;

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
	 * be empty, null, and can not contain too many spaces and can not contain characters which are not alphabetic and not a space.
	 * @param name The name to be checked 
	 * @param message The validation message.
	 */
	public static void checkName(String name, Message message) {

		try {
			@SuppressWarnings("unused")
			Member member = new Member(name, "1111111111");
			message.setValidated(true);

		}

		catch (IllegalArgumentException e) {
			message.setValidated(false);
			message.setMessage(e.getMessage());
		}

	}

	/**
	 * Checks if a string is valid as a personal number. The personal number has to be a 10-digit number
	 * @param personalNumber the personal number to be checked
	 * @param message the validation message.
	 */
	public static void checkPersonalNumberValidity(String personalNumber, Message message) {

		try {
			@SuppressWarnings("unused")
			Member member = new Member("Mouayed", personalNumber);
			message.setValidated(true);
		}

		catch (IllegalArgumentException e) {
			message.setValidated(false);
			message.setMessage(e.getMessage());
		}
	}

	/**
	 * Searches for a personal number in the database. If the personal number has been found,
	 * the Message will be validated.
	 * @param personalNumber The personal number to be searched for.
	 * @param message The validation message.
	 */
	public static void checkPersonalNumber(String personalNumber, Message message) {

		checkPersonalNumberValidity(personalNumber, message);
		if (!message.isValidated())
			return;

		if (Queries.checkPersonalNumber(personalNumber)) {
			message.setMessage("Member has not been found. Please make sure the personal number is correct");
			message.setValidated(false);
			return;
		}

		message.setValidated(true);
	}


	/**
	 * Gets all the members from the database with the number of owned boats for each member.
	 * @return an array of Strings or null if no members have been found. Each String in the array represents a member in the form: "id, name, personalNumber, numberOfBoats"
	 */
	public static String[] membersList() {

		Member[] members = Queries.getMembers();
		if (members == null)
			return null;

		String [] list = new String[members.length];
		for (int i = 0; i < members.length; i++) {
			String s = "" + members[i].getMemberId();
			s += ", ";
			s += members[i].getName();
			s += ", ";
			s += members[i].getPersonalNumber();
			s += ", ";
			s += members[i].getBoatList().size();
			list[i] = s;
		}

		return list;
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
	 * @return The member's information as a String or null if the member has not been found. Returned member string will in the form of: "id, name, personalNumber, numberOfBoats"
	 */
	public static String getMemberInfo(String personalNumber) {

		if (Queries.checkPersonalNumber(personalNumber))
			return null;
		Member member = Queries.getMember(personalNumber);
		String s = "";
		s += member.getMemberId();
		s += ", ";
		s += member.getName();
		s += ", ";
		s += member.getPersonalNumber();
		s += ", ";
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

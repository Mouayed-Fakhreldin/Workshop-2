package view;

import java.lang.reflect.InvocationTargetException;


import controller.Message;
import controller.ViewCreateUpdateMember;

/**
 * The view package class which handles all the user's input/output operations about 
 * adding and updating members.
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class Members {
	
	private MainScreen mainScreen;
	private Boats boats;
	
	
	public Members(MainScreen mainScreen, Boats boats) {
		this.mainScreen = mainScreen;
		this.boats = boats;
	}
	
	/**
	 * Checks if the list choice input is valid (Verbose list or compact list)
	 * @param s the user's input
	 * @param message the validation message
	 */
	public void checkListChoice(String s, Message message) {

		if (s == null) {
			message.setValidated(false);
			message.setMessage("Please pick a choice!");
		}

		else if (!(s.equals("1") || s.equals("2") || s.equalsIgnoreCase("back"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}

		else
			message.setValidated(true);

	}
	
	/**
	 * Checks if the main menu choice input is valid
	 * @param s the user's input
	 * @param message the validation message
	 */
	public void checkChoice(String s, Message message) {
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") )) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
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
	public void checkPersonalNumberCreate(String personalNumber, Message message) {
		
		if (personalNumber != null && personalNumber.equalsIgnoreCase("back")) {
			message.setValidated(true);
			return;
		}
		
		// Check that the user input is a valid personal number
		ViewCreateUpdateMember.checkPersonalNumberValidity(personalNumber, message);
		
		// Check that the personal number is not in use
		if (message.isValidated()) {
			ViewCreateUpdateMember.checkPersonalNumber(personalNumber, message);
			message.setValidated(!message.isValidated());
			if (!message.isValidated())
				message.setMessage("Personal number is in use");
		}
	}
	
	/**
	 * Checks that user's input for searching for a member by personal number is valid. This method checks that the input is a valid personal number and that database has a member stored with this personal number
	 * @param s The user's input 
	 * @param message The validation message.
	 */
	public void checkSearchMemberInput(String s, Message message) {
		
		if (s != null && s.equalsIgnoreCase("back")) {
			message.setValidated(true);
			return;
		}
		
		ViewCreateUpdateMember.checkPersonalNumber(s, message);
	}
	
	/**
	 * Create member screen
	 */
	void createMember()  {
		
		String name = "";				// New member's name
		String personalNumber = "";		// New member's personal number
		
		try {
			
			name = UtilClass.validatedInput("New member's name (or type back to go back to main menu)", ViewCreateUpdateMember.class.getMethod("checkName", MainScreen.args), mainScreen.scanner, null);
			if (name.equalsIgnoreCase("back")) {
				System.out.println("------------------------------------------------");
				return;
			}
				
			
			personalNumber = UtilClass.validatedInput("New member's personal number (or type back to go back to main menu)", Members.class.getMethod("checkPersonalNumberCreate", MainScreen.args), mainScreen.scanner, this);
			if (personalNumber.equalsIgnoreCase("back")) {
				System.out.println("------------------------------------------------");
				return;
			}
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (ViewCreateUpdateMember.createMember(name, personalNumber))
			System.out.println("Member successfully created!\n---------------------------------");
		
	}
	
	/**
	 * List members choice screen
	 */
	void viewMembers() {
		
		String choice = "";
		System.out.println("\n1) Compact List\n2) Verbose List");
		try {
			choice = UtilClass.validatedInput("Choice (or type back to go back to main menu)", Members.class.getMethod("checkListChoice", MainScreen.args), mainScreen.scanner, this);
			if (choice.equalsIgnoreCase("back"))
				return;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		System.out.println("---------------");
		
		if (choice.equals("1")) {
			String[] membersList = ViewCreateUpdateMember.membersList();
			if (membersList == null) {
				System.out.println("No members registered !!!!!!");
				return;
			}
				
			for (String member:membersList) {
				String[] s = member.split(", ");
				StringBuilder builder = new StringBuilder("Member ID: ");
				builder.append(s[0]);
				builder.append(", Name: ");
				builder.append(s[1]);
				builder.append(", Number of owned boats: ");
				builder.append(s[3]);
				System.out.println(builder.toString());
			}
			
			System.out.println("--------------------------------------------------------------------------");
		}
		
		else {
			String [] membersList = ViewCreateUpdateMember.membersList();
			if (membersList == null) {
				System.out.println("No members registered !!!!!!");
				return;
			}
			for (String member:membersList) {
				String[] s = member.split(", ");
				StringBuilder builder = new StringBuilder("Member ID: ");
				builder.append(s[0]);
				builder.append(", Name: ");
				builder.append(s[1]);
				builder.append(", personal number: ");
				builder.append(s[2]);
				builder.append(", Number of owned boats: ");
				builder.append(s[3]);
				System.out.println(builder.toString());
				this.mainScreen.selectedPersonalNumber = s[2];
				String[] boats = this.boats.getPrettyBoats();
				if (boats != null)	
					for (int i=0; i<boats.length; i++)
						System.out.println(boats[i]);
				System.out.println("--------------------------");
			}
		}
		
	}
	
	/**
	 * Search for a member screen
	 */
	void searchMember() {
		
		try {
			mainScreen.selectedPersonalNumber = UtilClass.validatedInput("Input member's personal number (or type back to go back to main menu)", Members.class.getMethod("checkSearchMemberInput", MainScreen.args), mainScreen.scanner, this);
			if (mainScreen.selectedPersonalNumber.equalsIgnoreCase("back"))
				return;
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		
		
		searchMember2();
		
	}
	
	/**
	 * The screen showing the options after the member has been chosen (by personal number)
	 */
	void searchMember2() {
		
		while (true) {
			
			boolean shouldGoBack = false;
			
			String member = ViewCreateUpdateMember.getMemberInfo(mainScreen.selectedPersonalNumber);
			String[] s = member.split(", ");
			StringBuilder builder = new StringBuilder("Member ID: ");
			builder.append(s[0]);
			builder.append(", Name: ");
			builder.append(s[1]);
			builder.append(", Number of owned boats: ");
			builder.append(s[3]);
			System.out.println(builder.toString());
			
			System.out.println();
			
			System.out.println("1) View Boats List\n2) Update boats\n3) Update member's name.\n4) Delete member.\n5) back\n---------------------------------------------");
			String choice = "";
			
			try {
				choice = UtilClass.validatedInput("Choice: ", Members.class.getMethod("checkChoice", MainScreen.args), mainScreen.scanner, this);
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
						
			switch (choice) {
				case "1":
					boats.viewBoatsList();
					break;
				case "2":
					boats.updateBoats();
					break;
				case "3":
					updateName();
					break;
				case "4":
					shouldGoBack = deleteMember();
					break;
				case "5":
					return;
	
			}
			
			if (shouldGoBack)
				return;
			
		}	
		
	}
	
	/**
	 * Update member's name screen
	 */
	void updateName() {
		
		String newName = "";
		
		try {
			newName = UtilClass.validatedInput("New member's name (or type back to go back to the previous menu)", ViewCreateUpdateMember.class.getMethod("checkName", MainScreen.args), mainScreen.scanner, null);
			if (newName.equalsIgnoreCase("back"))
				return;
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (ViewCreateUpdateMember.updateName(mainScreen.selectedPersonalNumber, newName))
			System.out.println("Member's name successfully updated!\n---------------------------------");
		
		
	}
	
	/**
	 * Deletes a member screen. Confirms with the user if he/she is sure about removing membership
	 * @return true if the user has confirmed to remove the member
	 */
	boolean deleteMember() {
		
		String confirmQuestion = String.format("Are you sure you want to delete the membership of %s, %s ? Y for yes, N for no", ViewCreateUpdateMember.getMemberName(mainScreen.selectedPersonalNumber), mainScreen.selectedPersonalNumber);
		String validatedInput = "";
		
		try {
			validatedInput = UtilClass.validatedInput(confirmQuestion, UtilClass.class.getMethod("checkYesNoChoice", MainScreen.args), mainScreen.scanner, null);
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (validatedInput.equalsIgnoreCase("y")) {
			ViewCreateUpdateMember.deleteMember(mainScreen.selectedPersonalNumber);
			System.out.println("Memaber has been removed\n--------------------------------------------------------");
			return true;
		}
		
		else {
			return false;
		}
		
		
	}
}

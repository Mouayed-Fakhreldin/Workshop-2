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
	
	/**
	 * Checks if the list choice input is valid (Verbose list or compact list)
	 * @param s the user's input
	 * @param message the validation message
	 */
	public static void checkListChoice(String s, Message message) {

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
	public static void checkChoice(String s, Message message) {
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") )) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}
	
	/**
	 * Create member screen
	 */
	static void createMember()  {
		
		String name = "";				// New member's name
		String personalNumber = "";		// New member's personal number
		
		try {
			
			name = UtilClass.validatedInput("New member's name (or type back to go back to main menu)", ViewCreateUpdateMember.class.getMethod("checkName", Main.args));
			if (name.equalsIgnoreCase("back")) {
				System.out.println("------------------------------------------------");
				return;
			}
				
			
			personalNumber = UtilClass.validatedInput("New member's personal number (or type back to go back to main menu)", ViewCreateUpdateMember.class.getMethod("checkPersonalNumberCreate", Main.args));
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
	static void viewMembers() {
		
		String choice = "";
		System.out.println("\n1) Compact List\n2) Verbose List");
		try {
			choice = UtilClass.validatedInput("Choice (or type back to go back to main menu)", Members.class.getMethod("checkListChoice", Main.args));
			if (choice.equalsIgnoreCase("back"))
				return;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		System.out.println("---------------");
		if (choice.equals("1"))
			System.out.println(ViewCreateUpdateMember.compactList());
		
		else
			System.out.println(ViewCreateUpdateMember.verboseList());
		
	}
	
	/**
	 * Search for a member screen
	 */
	static void searchMember() {
		
		
		try {
			Main.selectedPersonalNumber = UtilClass.validatedInput("Input member's personal number (or type back to go back to main menu)", ViewCreateUpdateMember.class.getMethod("checkPersonalNumber", Main.args));
			if (Main.selectedPersonalNumber.equalsIgnoreCase("back"))
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
	static void searchMember2() {
		
		while (true) {
			
			boolean shouldGoBack = false;
			System.out.println(ViewCreateUpdateMember.getMemberInfo(Main.selectedPersonalNumber));
			System.out.println();
			
			System.out.println("1) View Boats List\n2) Update boats\n3) Update member's name.\n4) Delete member.\n5) back\n---------------------------------------------");
			String choice = "";
			
			try {
				choice = UtilClass.validatedInput("Choice: ", Members.class.getMethod("checkChoice", Main.args));
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
						
			switch (choice) {
				case "1":
					Boats.viewBoatsList();
					break;
				case "2":
					Boats.updateBoats();
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
	static void updateName() {
		
		String newName = "";
		
		try {
			newName = UtilClass.validatedInput("New member's name (or type back to go back to the previous menu)", ViewCreateUpdateMember.class.getMethod("checkNameUpdate", Main.args));
			if (newName.equalsIgnoreCase("back"))
				return;
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (ViewCreateUpdateMember.updateName(Main.selectedPersonalNumber, newName))
			System.out.println("Member's name successfully updated!\n---------------------------------");
		
		
	}
	
	/**
	 * Deletes a member screen. Confirms with the user if he/she is sure about removing membership
	 * @return true if the user has confirmed to remove the member
	 */
	static boolean deleteMember() {
		
		String confirmQuestion = String.format("Are you sure you want to delete the membership of %s, %s ? Y for yes, N for no", ViewCreateUpdateMember.getMemberName(Main.selectedPersonalNumber), Main.selectedPersonalNumber);
		String validatedInput = "";
		
		try {
			validatedInput = UtilClass.validatedInput(confirmQuestion, UtilClass.class.getMethod("checkYesNoChoice", Main.args));
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (validatedInput.equalsIgnoreCase("y")) {
			ViewCreateUpdateMember.deleteMember(Main.selectedPersonalNumber);
			System.out.println("Memaber has been removed\n--------------------------------------------------------");
			return true;
		}
		
		else {
			return false;
		}
		
		
	}
}

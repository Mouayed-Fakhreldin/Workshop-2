package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import controller.CreateMember;
import controller.UtilClass;
import controller.ViewCreateUpdateBoat;
import controller.Message;
import controller.UpdateDeleteMember;
import controller.ViewMembers;

public class Main {

	private static Scanner scanner = new Scanner(System.in);
	@SuppressWarnings("rawtypes")
	private static Class[] args;
	private static String selectedPersonalNumber; 
	private static int selectedBoatId;
	
	public static void main(String[] args) {
		
		//controller.TestClass.initialize();
		Main.args = new Class[2];
		Main.args[0] = String.class;
		Main.args[1] = Message.class;
		
		while (true)
			mainScreen();
		
	}
	
	private static void mainScreen() {
		
		
		String s = "Welcome to Bla yacht club. Please choose an option:\n";
		s += "1) Create a new member\n";
		s += "2) View members\n";
		s += "3) Choose member by personal number";
		s += "\n-------------------------------------------------------";
		System.out.println(s);
		
		int n = 0;
		
		try {
			 n = Integer.parseInt(validatedInput("Choice: ", UtilClass.class.getMethod("checkChoice", args)));
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		switch (n) {
		case 1:
			createMember();
			break;
			
		case 2:
			viewMembers();
			break;
			
		case 3:
			searchMember();
			break;
		}
		
	}
	
	
	public static void createMember()  {
				
		String name = "";
		String personalNumber = "";
		
		try {
			
			name = validatedInput("New member's name (or type back to go back to main menu)", CreateMember.class.getMethod("checkName", args));
			if (name.equalsIgnoreCase("back")) {
				System.out.println("------------------------------------------------");
				return;
			}
				
			
			personalNumber = validatedInput("New member's personal number (or type back to go back to main menu)", CreateMember.class.getMethod("checkPersonalNumber", args));
			if (personalNumber.equalsIgnoreCase("back")) {
				System.out.println("------------------------------------------------");
				return;
			}
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (CreateMember.createMember(name, personalNumber))
			System.out.println("Member successfully created!\n---------------------------------");
		
		mainScreen();
		
		
	}
	
	public static void viewMembers() {
		
		String choice = "";
		System.out.println("\n1) Compact List\n2) Verbose List");
		try {
			choice = validatedInput("Choice (or type back to go back to main menu)", ViewMembers.class.getMethod("checkChoice", args));
			if (choice.equalsIgnoreCase("back"))
				return;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		System.out.println("---------------");
		if (choice.equals("1"))
			System.out.println(ViewMembers.compactList());
		
		else
			System.out.println(ViewMembers.verboseList());
		
		mainScreen();
	}

	public static void searchMember() {
		
		
		try {
			selectedPersonalNumber = validatedInput("Input member's personal number (or type back to go back to main menu)", UpdateDeleteMember.class.getMethod("checkPersonalNumber", args));
			if (selectedPersonalNumber.equalsIgnoreCase("back"))
				return;
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		
		searchMember2();
		
	}
	
	public static void searchMember2() {
		
		while (true) {
			
			boolean shouldGoBack = false;
			System.out.println(UpdateDeleteMember.getMemberInfo(selectedPersonalNumber));
			System.out.println();
			
			System.out.println("1) View Boats List\n2) Update boats\n3) Update member's name.\n4) Delete member.\n5) back\n---------------------------------------------");
			String choice = "";
			
			try {
				choice = validatedInput("Choice: ", UpdateDeleteMember.class.getMethod("checkChoice", args));
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
						
			switch (choice) {
				case "1":
					viewBoatsList();
					break;
				case "2":
					updateBoats();
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
	
	private static void viewBoatsList() {
		//System.out.println(ViewCreateUpdateBoat.viewMemberBoats(selectedPersonalNumber));
		String [] boats = ViewCreateUpdateBoat.getBoats(selectedPersonalNumber);
		for (String s:boats)
			System.out.println(s);
		System.out.println("------------------------------------------------");
	}
	
	private static void updateName() {
		
		String newName = "";
		
		try {
			newName = validatedInput("New member's name (or type back to go back to the previous menu)", UpdateDeleteMember.class.getMethod("checkName", args));
			if (newName.equalsIgnoreCase("back"))
				return;
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (UpdateDeleteMember.updateName(selectedPersonalNumber, newName))
			System.out.println("Member's name successfully updated!\n---------------------------------");
		
		mainScreen();
		
	}
	
	private static void updateBoats() {
		while (true) {
			String s = "1) Add a boat\n";
			s += "2) Update boat's info\n";
			s += "3) Remove Boat\n";
			s += "4) back";
			s += "\n-------------------------------------------------------";
			System.out.println(s);
			
			int n = 0;
			
			try {
				 n = Integer.parseInt(validatedInput("Choice: ", ViewCreateUpdateBoat.class.getMethod("checkChoice", args)));
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
			
			switch (n) {
			case 1:
				addBoat();
				break;
				
			case 2:
				
				try {
					String message = "Please select boat to update (or type back to go back):";
					chooseBoat(Main.class.getMethod("updateBoatInfo"), message);
				} 
				
				catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				break;
				
			case 3:
				
				try {
					String message = "Please select boat to remove (or type back to go back):";
					chooseBoat(Main.class.getMethod("removeBoat"), message);
				} 
				
				catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				break;
				
			case 4:
				return;
			}
			
			
		}
	}
	
	private static void addBoat() {
		
		System.out.println("\n------------------------------------------------\nPlease choose boat's Type or type back to go back:");
		String[] boatTypes = ViewCreateUpdateBoat.getBoatTypes();
		for (int i=0; i<boatTypes.length; i++) {
			String s = "" + (i+1) + ") " + boatTypes[i];
			System.out.println(s);
		}
		System.out.println("------------");
		
		
		String validatedInput = "";
		try {
			validatedInput = validatedInput("Choice: ", ViewCreateUpdateBoat.class.getMethod("checkTypeChoice", args));
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
					
		if (validatedInput.equalsIgnoreCase("back"))
			return;
		
		double length = 0;
		boolean notValidLength = true;
		
		while (notValidLength) {
			try {
				System.out.print("Please Input boat length in meters: ");
				String input = scanner.nextLine();
				length = Double.parseDouble(input);
				notValidLength = false;
			} 
			
			catch (Exception e) {
				System.out.println("Please input a valid number\n-------------------------");
			}
		}
		
		
		int typeChoice = Integer.parseInt(validatedInput);
		ViewCreateUpdateBoat.addBoat(selectedPersonalNumber, typeChoice, length);
		System.out.println("Boat has been successfully added!\n-----------------------------------------------------------");
		
	}
	
	public static void chooseBoat(Method method, String message) {
		
		while (true) {
			System.out.println(message);
			String[] boats = ViewCreateUpdateBoat.getBoats(selectedPersonalNumber);
			for (int i=0; i<boats.length; i++) {
				System.out.print(i+1 + ") ");
				System.out.println(boats[i]);
			}
			System.out.println("------------------------------------------------");
			
			String chosenBoat = "";
			try {
				chosenBoat = validatedInput("Choice: ", ViewCreateUpdateBoat.class.getMethod("checkBoatChoice", args));
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
			
			if (chosenBoat.equalsIgnoreCase("back")) {
				selectedBoatId = -1;
				return;
			}
			
			else {
				chosenBoat = boats[Integer.parseInt(chosenBoat) -1];
				String formattedString = chosenBoat.split(" ")[2];
				formattedString = formattedString.substring(0, formattedString.length()-1);
				selectedBoatId = Integer.parseInt(formattedString);
			}
			
			System.out.println(ViewCreateUpdateBoat.getBoatById(selectedBoatId));
			try {
				method.invoke(null);
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void updateBoatInfo() {
		
			while (true) {
				if (selectedBoatId == -1)
					return;
				
				System.out.println("\nPlease choose one of the following options to update (or type back to go back)\n1) Update boat's length.\n2) Update boat's type\n--------------");
				String choice = "";
				
				try {
					choice = validatedInput("Choice: ", ViewCreateUpdateBoat.class.getMethod("checkUpdateChoice", args));
				} 
				
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
				}
				
				if (choice.equalsIgnoreCase("back"))
					return;
				
				if (choice.equals("1"))
					updateBoatLength();
				
				if (choice.equals("2"))
					updateBoatType();
			}
		
	}
	
	private static void updateBoatLength() {
		
		boolean notValidLength = true;
		double length = 0;
		while (notValidLength) {
			try {
				System.out.print("Please Input boat length in meters (or type back to go back): ");
				String input = scanner.nextLine();
				
				if (input == null || input.equals(""))
					throw new IllegalArgumentException();
				if (input.equalsIgnoreCase("back"))
					return;
				
				length = Double.parseDouble(input);
				notValidLength = false;
			} 
			
			catch (Exception e) {
				System.out.println("Please input a valid number\n-------------------------");
			}
		}
		
		if (ViewCreateUpdateBoat.updateBoatLength(selectedBoatId, length))
			System.out.println("Boat length has been updated successfully !\n---------------------------------------------------");
		
	}
	
	private static void updateBoatType() {
		System.out.println("\n------------------------------------------------\nPlease choose boat's Type or type back to go back:");
		String[] boatTypes = ViewCreateUpdateBoat.getBoatTypes();
		for (int i=0; i<boatTypes.length; i++) {
			String s = "" + (i+1) + ") " + boatTypes[i];
			System.out.println(s);
		}
		System.out.println("------------");
		
		
		String validatedInput = "";
		try {
			validatedInput = validatedInput("Choice: ", ViewCreateUpdateBoat.class.getMethod("checkTypeChoice", args));
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		int index = Integer.parseInt(validatedInput) -1;
		String newType = boatTypes[index];
		
		
		if (ViewCreateUpdateBoat.updateBoatType(selectedBoatId, newType))
			System.out.println("Boat type has been updated successfully !");
	}
	
	public static void removeBoat() {
		if (selectedBoatId == -1)
			return;
		
		System.out.println("Are you sure you want to remove the following boat ?:");
		System.out.println(ViewCreateUpdateBoat.getBoatById(selectedBoatId));
		
		String confirmQuestion = String.format("Confirm with Y for yes, or N for no", UpdateDeleteMember.getMemberName(selectedPersonalNumber), selectedPersonalNumber);
		String validatedInput = "";
		
		try {
			validatedInput = validatedInput(confirmQuestion, UtilClass.class.getMethod("checkYesNoChoice", args));
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (validatedInput.equalsIgnoreCase("y")) {
			boolean b = ViewCreateUpdateBoat.removeBoat(selectedBoatId);
			if (b)
				System.out.println("Boat has been successfully removed !!!\n----------------------------------------------------");
		}
		
	}
	
	/**
	 * Deletes a member
	 * @return true if the user has confirmed to remove the member
	 */
	private static boolean deleteMember() {
		
		String confirmQuestion = String.format("Are you sure you want to delete the membership of %s, %s ? Y for yes, N for no", UpdateDeleteMember.getMemberName(selectedPersonalNumber), selectedPersonalNumber);
		String validatedInput = "";
		
		try {
			validatedInput = validatedInput(confirmQuestion, UtilClass.class.getMethod("checkYesNoChoice", args));
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (validatedInput.equalsIgnoreCase("y")) {
			UpdateDeleteMember.deleteMember(selectedPersonalNumber);
			System.out.println("Memaber has been removed\n--------------------------------------------------------");
			return true;
		}
		
		else {
			return false;
		}
		
		
	}
	
	private static String validatedInput(String s, Method booleanChecker) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String validatedInput = ""; // The user's validated input 
		
		while (true) {
			
			System.out.print(s + ": ");
			validatedInput = scanner.nextLine();
			Message message = new Message();
			booleanChecker.invoke(null, validatedInput, message);
			
			if (message.isValidated())
				return validatedInput;
			
			else 
				System.out.println(message.getMessage());
			
		}
		
	}
	
}

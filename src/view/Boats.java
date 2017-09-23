package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import controller.Message;
import controller.ViewCreateUpdateBoat;


/**
 * The view package class which handles all the user's input/output operations about adding
 * and updating boats.
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class Boats {
	
	/**
	 * Checks if Update Boats choice input is valid (Add a boat, Update boat's info, Remove
	 * Boat, back)
	 * @param s the user's input
	 * @param message the validation message
	 */
	public static void checkChoice(String s, Message message) {
		
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}
	
	/**
	 * Checks if user's input for what to update is valid.
	 * @param s the user's input
	 * @param message the validation message
	 */
	public static void checkUpdateChoice(String s, Message message) {
		
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equalsIgnoreCase("back"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}
	
	/**
	 * Checks if user's input for boat type is valid.
	 * @param s the user's input
	 * @param message the validation message
	 */
	public static void checkTypeChoice(String s, Message message) {
		
		
		if (s == null) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		
		else {
			
			String[] boatTypes = ViewCreateUpdateBoat.getBoatTypes();
			boolean validated=false;;
			
			for (int i=0; i<boatTypes.length; i++) {
				String choice = "" + (i+1);
				if (s.equals(choice)) {
					validated = true;
					break;
				}
			}
			
			if (validated || s.equalsIgnoreCase("back")) {
				message.setValidated(true);
			}
			
			else {
				message.setValidated(false);
				message.setMessage("Please choose a valid option!");
			}
			
		}
		
	}
	
	/**
	 * Checks if user's choice input for the boat is valid.
	 * @param s the user's input
	 * @param message the validation message
	 */
	public static void checkBoatChoice(String s, Message message) {
		
		
		if (s == null) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		
		else {
			
			String[] boats = ViewCreateUpdateBoat.getBoats(Main.selectedPersonalNumber);
			boolean validated=false;;
			
			for (int i=0; i<boats.length; i++) {
				String choice = "" + (i+1);
				if (s.equals(choice)) {
					validated = true;
					break;
				}
			}
			
			if (validated || s.equalsIgnoreCase("back")) {
				message.setValidated(true);
			}
			
			else {
				message.setValidated(false);
				message.setMessage("Please choose a valid option!");
			}
			
		}
		
	}
	
	/**
	 * Screen for showing list of boats
	 */
	static void viewBoatsList() {
		String [] boats = ViewCreateUpdateBoat.getBoats(Main.selectedPersonalNumber);
		if (boats == null || boats.length == 0) {
			System.out.println("Member does not have any boats registered !");
			System.out.println("------------------------------------------------");
			return;
		}
		for (String s:boats)
			System.out.println(s);
		System.out.println("------------------------------------------------");
	}
	
	/**
	 * Update boats screen
	 */
	static void updateBoats() {
		while (true) {
			String s = "1) Add a boat\n";
			s += "2) Update boat's info\n";
			s += "3) Remove Boat\n";
			s += "4) back";
			s += "\n-------------------------------------------------------";
			System.out.println(s);
			
			int n = 0;
			
			try {
				 n = Integer.parseInt(UtilClass.validatedInput("Choice: ", Boats.class.getMethod("checkChoice", Main.args)));
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
					chooseBoat(Boats.class.getMethod("updateBoatInfo"), message);
				} 
				
				catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				break;
				
			case 3:
				
				try {
					String message = "Please select boat to remove (or type back to go back):";
					chooseBoat(Boats.class.getMethod("removeBoat"), message);
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
	
	/**
	 * Add a new boat screen
	 */
	static void addBoat() {
		
		System.out.println("\n------------------------------------------------\nPlease choose boat's Type or type back to go back:");
		String[] boatTypes = ViewCreateUpdateBoat.getBoatTypes();
		for (int i=0; i<boatTypes.length; i++) {
			String s = "" + (i+1) + ") " + boatTypes[i];
			System.out.println(s);
		}
		System.out.println("------------");
		
		
		String validatedInput = "";
		try {
			validatedInput = UtilClass.validatedInput("Choice: ", Boats.class.getMethod("checkTypeChoice", Main.args));
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
				String input = Main.scanner.nextLine();
				length = Double.parseDouble(input);
				notValidLength = false;
			} 
			
			catch (Exception e) {
				System.out.println("Please input a valid number\n-------------------------");
			}
		}
		
		
		int typeChoice = Integer.parseInt(validatedInput);
		ViewCreateUpdateBoat.addBoat(Main.selectedPersonalNumber, typeChoice, length);
		System.out.println("Boat has been successfully added!\n-----------------------------------------------------------");
		
	}
	
	/**
	 * Choose boat to remove or update
	 * @param method Method object to update or remove boat (depends on the user input)
	 * @param message Output message.
	 */
	static void chooseBoat(Method method, String message) {
		
		while (true) {
			String[] boats = ViewCreateUpdateBoat.getBoats(Main.selectedPersonalNumber);
			if (boats == null || boats.length == 0) {
				System.out.println("Member does not have any boats registered !");
				System.out.println("------------------------------------------------");
				return;
			}
			System.out.println(message);
			
			for (int i=0; i<boats.length; i++) {
				System.out.print(i+1 + ") ");
				System.out.println(boats[i]);
			}
			System.out.println("------------------------------------------------");
			
			String chosenBoat = "";
			try {
				chosenBoat = UtilClass.validatedInput("Choice: ", Boats.class.getMethod("checkBoatChoice", Main.args));
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
			
			if (chosenBoat.equalsIgnoreCase("back")) {
				Main.selectedBoatId = -1;
				return;
			}
			
			else {
				chosenBoat = boats[Integer.parseInt(chosenBoat) -1];
				String formattedString = chosenBoat.split(" ")[2];
				formattedString = formattedString.substring(0, formattedString.length()-1);
				Main.selectedBoatId = Integer.parseInt(formattedString);
			}
			
			System.out.println(ViewCreateUpdateBoat.getBoatById(Main.selectedBoatId));
			try {
				method.invoke(null);
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Update boat info screen
	 */
	public static void updateBoatInfo() {
		
		while (true) {
			if (Main.selectedBoatId == -1)
				return;
			
			System.out.println("\nPlease choose one of the following options to update (or type back to go back)\n1) Update boat's length.\n2) Update boat's type\n--------------");
			String choice = "";
			
			try {
				choice = UtilClass.validatedInput("Choice: ", Boats.class.getMethod("checkUpdateChoice", Main.args));
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
	
	/**
	 * Update boat's length screen
	 */
	static void updateBoatLength() {
		
		boolean notValidLength = true;
		double length = 0;
		while (notValidLength) {
			try {
				System.out.print("Please Input boat length in meters (or type back to go back): ");
				String input = Main.scanner.nextLine();
				
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
		
		if (ViewCreateUpdateBoat.updateBoatLength(Main.selectedBoatId, length))
			System.out.println("Boat length has been updated successfully !\n---------------------------------------------------");
		
	}
	
	/**
	 * Update boat's type screen
	 */
	static void updateBoatType() {
		System.out.println("\n------------------------------------------------\nPlease choose boat's Type or type back to go back:");
		String[] boatTypes = ViewCreateUpdateBoat.getBoatTypes();
		for (int i=0; i<boatTypes.length; i++) {
			String s = "" + (i+1) + ") " + boatTypes[i];
			System.out.println(s);
		}
		System.out.println("------------");
		
		
		String validatedInput = "";
		try {
			validatedInput = UtilClass.validatedInput("Choice: ", Boats.class.getMethod("checkTypeChoice", Main.args));
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		int index = Integer.parseInt(validatedInput) -1;
		String newType = boatTypes[index];
		
		
		if (ViewCreateUpdateBoat.updateBoatType(Main.selectedBoatId, newType))
			System.out.println("Boat type has been updated successfully !");
	}
	
	/**
	 * Remove boat screen
	 */
	public static void removeBoat() {
		if (Main.selectedBoatId == -1)
			return;
		
		System.out.println("Are you sure you want to remove the following boat ?:");
		System.out.println(ViewCreateUpdateBoat.getBoatById(Main.selectedBoatId));
		
		String confirmQuestion = "Confirm with Y for yes, or N for no";
		String validatedInput = "";
		
		try {
			validatedInput = UtilClass.validatedInput(confirmQuestion, UtilClass.class.getMethod("checkYesNoChoice", Main.args));
		} 
		
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		if (validatedInput.equalsIgnoreCase("y")) {
			boolean b = ViewCreateUpdateBoat.removeBoat(Main.selectedBoatId);
			if (b)
				System.out.println("Boat has been successfully removed !!!\n----------------------------------------------------");
		}
		
	}
}

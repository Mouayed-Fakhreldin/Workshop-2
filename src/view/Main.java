package view;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import controller.Message;

/**
 * The class which starts the console user interface.
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class Main {

	static Scanner scanner = new Scanner(System.in);
	@SuppressWarnings("rawtypes")
	static Class[] args; 								// Contains the classes arguments for getMethod for all the Message validating methods [chechChoice() for example]
	static String selectedPersonalNumber; 				// The selected member's personal number
	static int selectedBoatId;							// The selected boat's ID 
	
	public static void main(String[] args) {
		
		Main.args = new Class[2];
		Main.args[0] = String.class;
		Main.args[1] = Message.class;
		
		mainScreen();
		
	}
	
	// Main menu
	private static void mainScreen() {
		
		while (true) {
			String s = "Welcome to Bla yacht club. Please choose an option:\n";
			s += "1) Create a new member\n";
			s += "2) View members\n";
			s += "3) Choose member by personal number";
			s += "\n-------------------------------------------------------";
			System.out.println(s);
			
			int n = 0;
			
			// take validated choice input from the user
			try {
				 n = Integer.parseInt(UtilClass.validatedInput("Choice: ", UtilClass.class.getMethod("checkChoice", args)));
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
			
			switch (n) {
			case 1:
				Members.createMember();
				System.out.println("\n\n");
				break;
				
			case 2:
				Members.viewMembers();
				System.out.println("\n\n");
				break;
				
			case 3:
				Members.searchMember();
				System.out.println("\n\n");
				break;
			}
		}
		
	}
	
	
	
}

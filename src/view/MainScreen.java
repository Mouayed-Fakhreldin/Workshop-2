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
public class MainScreen {

	Scanner scanner = new Scanner(System.in);
	@SuppressWarnings("rawtypes")
	final static Class[] args = new Class[] {String.class, Message.class}; 	// Contains the classes arguments for getMethod for all the Message validating methods [chechChoice() for example]
	String selectedPersonalNumber; 											// The selected member's personal number
	int selectedBoatId;														// The selected boat's ID 
	private Boats boats;
	private Members members;
	
	public MainScreen() {
		boats = new Boats(this);
		members = new Members(this, boats);
	}
	
	// Main menu
	public void displayMainScreen() {
		
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
				 n = Integer.parseInt(UtilClass.validatedInput("Choice: ", UtilClass.class.getMethod("checkChoice", args), scanner, null));
			} 
			
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
			
			switch (n) {
			case 1:
				members.createMember();
				System.out.println("\n\n");
				break;
				
			case 2:
				members.viewMembers();
				System.out.println("\n\n");
				break;
				
			case 3:
				members.searchMember();
				System.out.println("\n\n");
				break;
			}
		}
		
	}
	
	
	
}

package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import controller.Message;

public class UtilClass {
	public static void checkChoice(String s, Message message) {
		
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equals("3"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}

	public static void checkYesNoChoice(String s, Message message) {
		if (s == null || !(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}
	
	/**
	 * A method which takes input from the user and makes sure the input is valid.
	 * If the input is invalid, a validation error message will be displayed. This method depends
	 * on and takes as arguments the specialized validation method which follow the structure
	 * checkSth(String input, Message validationMessage).
	 * @param s The output that will be displayed to the user (so the user will know what to input)
	 * @param booleanChecker A method object with the structure checkSth(String input, Message validationMessage)
	 * @return the validated input
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	static String validatedInput(String s, Method booleanChecker) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String validatedInput = ""; // The user's validated input 
		
		while (true) {
			
			System.out.print(s + ": ");
			validatedInput = Main.scanner.nextLine();
			Message message = new Message();
			booleanChecker.invoke(null, validatedInput, message);
			
			if (message.isValidated())
				return validatedInput;
			
			else 
				System.out.println(message.getMessage());
			
		}
		
	}
}

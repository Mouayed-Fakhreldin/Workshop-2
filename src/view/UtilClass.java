package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

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
	 * @param scanner The Scanner.java object from which to read user's input 
	 * @param object The object from which the boolean checker method should be called
	 * @return the validated input
	 * 
	 * @throws IllegalAccessException if this Method object is enforcing Java language access 
	 * control and the underlying method is inaccessible.
	 * 
	 * @throws IllegalArgumentException if the method is an instance method and the specified 
	 * object argument is not an instance of the class or interface declaring the underlying 
	 * method (or of a subclass or implementor thereof); if the number of actual and formal 
	 * parameters differ; if an unwrapping conversion for primitive arguments fails; or if, 
	 * after possible unwrapping, a parameter value cannot be converted to the corresponding 
	 * formal parameter type by a method invocation conversion.
	 * 
	 * @throws InvocationTargetException  if the underlying method throws an exception.
	 */
	static String validatedInput(String s, Method booleanChecker, Scanner scanner, Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String validatedInput = ""; // The user's validated input 
		
		while (true) {
			
			System.out.print(s + ": ");
			validatedInput = scanner.nextLine();
			Message message = new Message();
			booleanChecker.invoke(object, validatedInput, message);
			
			if (message.isValidated())
				return validatedInput;
			
			else 
				System.out.println(message.getMessage());
			
		}
		
	}
	
	
}

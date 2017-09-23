package controller;

/**
 * A data model class representing a message from the system after user input. It stores 
 * if the input is valid or not. If the input is not valid, it will contain a message 
 * explaining why.
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class Message {
	private String message;
	private boolean validated;

	/**
	 * Gets the input error message.
	 * @return the error message 
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the input error message.
	 * @param message the validation message
	 */
	public void setMessage(String message) {
		this.message = message + "\n--------------";
	}

	/**
	 * Checks if the message is validated (i.e the user's input is valid)
	 * @return true if the input is valid, false otherwise.
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * Sets the validation flag according to whether the user's input is validated.
	 * @param validated true if the user input is validated, false otherwise.
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
}

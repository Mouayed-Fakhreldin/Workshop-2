package controller;

public class Message {
	private String message;
	private boolean validated;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message + "\n--------------";
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}
}

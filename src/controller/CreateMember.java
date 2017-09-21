package controller;

import model.Member;
import model.Queries;

public class CreateMember {

	public static void checkName(String name, Message message) {
		
		if (name == null || name.equals("")) {
			message.setMessage("Names cannot be empty");
			message.setValidated(false);
		}
		
		else if(name.equalsIgnoreCase("back")) {
			message.setValidated(true);
		}
		
		else if(!Member.checkName(name)) {
			message.setMessage("Too many spaces and too few characters");
			message.setValidated(false);
		}
		
		else
			message.setValidated(true);
		
	}
	
	public static void checkPersonalNumber(String personalNumber, Message message) {
		
		if (personalNumber != null && personalNumber.equalsIgnoreCase("back")) {
			message.setValidated(true);
			return;
		}
		
		if (personalNumber == null || personalNumber.length() != 10) {
			message.setValidated(false);
			message.setMessage("Please input 10 digit personal number!");
			return;
		}
		
		else {
			for (int i=0; i<personalNumber.length(); i++) {
				if (!Character.isDigit(personalNumber.charAt(i))) {
					message.setValidated(false);
					message.setMessage("Personal number cannot contain non-digit characters!");
					return;
				}
			}
		}
		
		if (!Queries.checkPersonalNumber(personalNumber)) {
			message.setMessage("Personal number is in use!");
			message.setValidated(false);
			return;
		}
		
		message.setValidated(true);
		
	}
	
	public static boolean createMember(String name, String personalNumber) {
		
		return Queries.storeMember(new Member(name, personalNumber));
		
	}
		
}

package controller;

import java.util.NoSuchElementException;

import model.Member;
import model.Queries;

public class UpdateDeleteMember {
	
	public static void checkName(String name, Message message) {
		
		if (name == null || name.equals("")) {
			message.setMessage("Names cannot be empty");
			message.setValidated(false);
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
		
		if (Queries.checkPersonalNumber(personalNumber)) {
			message.setMessage("Member has not been found. Please make sure the personal number is correct");
			message.setValidated(false);
			return;
		}
		
		message.setValidated(true);
	}
	
	public static void checkChoice(String s, Message message) {
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") )) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}
	
	public static boolean updateName(String personalNumber, String newName) {
		
		if (Queries.checkPersonalNumber(personalNumber))
			return false;
		
		Queries.updateMemberName(Queries.getMember(personalNumber), newName);
		return true;
		
	}
	
	public static String getMemberInfo(String personalNumber) {
		
		Member member = Queries.getMember(personalNumber);
		String s = "";
		s += "Name: ";
		s += member.getName();
		
		s += "\nMember ID: ";
		s += member.getMemberId();
		
		s += "\nNumber of owned boats: ";
		s += member.getBoatList().size();
		
		return s;
		
	}
	
	public static String getMemberName(String personalNumber) {
		if (Queries.checkPersonalNumber(personalNumber))
			throw new NoSuchElementException("Member not found");
		Member member = Queries.getMember(personalNumber);
		return member.getName();
	}
	
	public static boolean deleteMember(String personalNumber) {
		
		if (Queries.checkPersonalNumber(personalNumber))
			return false;
		
		
		Member member = Queries.getMember(personalNumber);
		return Queries.deleteMember(member);
	}
	
	
}

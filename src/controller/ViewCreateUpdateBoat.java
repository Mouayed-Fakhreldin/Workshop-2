package controller;

//import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.Queries;
import model.Boat.BoatType;

public class ViewCreateUpdateBoat {
	private static String selectedPersonalNumber;
	/*public static String viewMemberBoats(String personalNumber) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("\nBoats list:\n");
		Member member = Queries.getMember(personalNumber);
		ArrayList<Boat> boats = member.getBoatList();
		for (Boat boat:boats) {

			builder.append("Boat ID: ");
			builder.append(boat.getBoatId());
			builder.append(", ");
			
			builder.append(boat.getType().toString());
			builder.append(": ");
			
			builder.append(boat.getLength());
			builder.append(" meters.\n");
			
		}
		
		builder.append("------------------------------------------------");
		return builder.toString();
		
	}*/
	
	public static void checkChoice(String s, Message message) {
		
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}
	
	public static void checkUpdateChoice(String s, Message message) {
		
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equalsIgnoreCase("back"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}
	
	public static void checkTypeChoice(String s, Message message) {
		
		
		if (s == null) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		
		else {
			
			String[] boatTypes = getBoatTypes();
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
	
	public static void checkBoatChoice(String s, Message message) {
		
		
		if (s == null) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		
		else {
			
			Member member = Queries.getMember(selectedPersonalNumber);
			Boat[] boats = Queries.getMemberBoats(member);
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
	
	public static String[] getBoatTypes() {
		
		BoatType[] types = BoatType.values();
		String[] boatTypes = new String[types.length];
		for (int i=0; i<types.length; i++)
			boatTypes[i] = types[i].toString();
		return boatTypes;
	}
	
	public static void addBoat(String selectedPersonalNumber, int typeChoice, double length) {
		
		Member member = Queries.getMember(selectedPersonalNumber);
		typeChoice--;
		BoatType[] types = BoatType.values();
		BoatType type = null;
		
		for (int i=0; i<types.length; i++) {
			if (typeChoice == i) {
				type = types[i];
			}
		}
		
		Boat boat = new Boat(member, type, length);
		Queries.storeBoat(boat);
	}
	
	public static String[] getBoats(String selectedPersonalNumber) {
		
		ViewCreateUpdateBoat.selectedPersonalNumber = selectedPersonalNumber;
		Member member = Queries.getMember(selectedPersonalNumber);
		Boat[] boats = Queries.getMemberBoats(member);
		if (boats == null)
			return null;
		
		String[] boatsStrings = new String[boats.length];
		for (int i=0; i<boats.length; i++) {
			StringBuilder s = new StringBuilder("Boat ID: ");
			s.append(boats[i].getBoatId());
			s.append(", ");
			s.append(boats[i].getType());
			s.append(": ");
			s.append(boats[i].getLength());
			s.append(" meters.");
			boatsStrings[i] = s.toString();
		}
		
		return boatsStrings;
		
	}
	
	public static String getBoatById(int id) {
		
		Boat boat = Queries.getBoat(id);
		
		String s = "Boat ID: ";
		s += boat.getBoatId();
		s += ", ";
		s += boat.getType().toString();
		s += ": ";
		s += boat.getLength();
		s += " meters";
		return s;
	}
	
	public static boolean removeBoat(int id) {
		
		Boat boat = Queries.getBoat(id);
		return Queries.deleteBoat(boat);
		
	}
	
	public static boolean updateBoatLength(int id, double newLength) {
		
		Boat boat = Queries.getBoat(id);
		return Queries.updateBoatLength(boat, newLength);
	}
	
	public static boolean updateBoatType(int id, String type) {
		Boat boat = Queries.getBoat(id);
		BoatType newType = BoatType.valueOf(type.toUpperCase());
		return Queries.updateBoatType(boat, newType);
	}
}

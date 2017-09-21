package controller;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.Queries;

public class ViewMembers {

	public static void checkChoice(String s, Message message) {

		if (s == null) {
			message.setValidated(false);
			message.setMessage("Please pick a choice!");
		}

		else if (!(s.equals("1") || s.equals("2") || s.equalsIgnoreCase("back"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}

		else
			message.setValidated(true);

	}

	public static String compactList() {

		Member[] members = Queries.getMembers();
		if (members == null)
			return "No members registered\n------------------------------------------------------";
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < members.length; i++) {

			if (i > 0)
				builder.append("\n");
			builder.append("Name: ");
			builder.append(members[i].getName());

			builder.append("\nMember ID: ");
			builder.append(members[i].getMemberId());

			builder.append("\nNumber of owned boats: ");
			builder.append(members[i].getBoatList().size());

			builder.append("\n------------------------------------------");
		}

		return builder.toString();
	}

	public static String verboseList() {

		Member[] members = Queries.getMembers();
		if (members == null)
			return "No members registered\n------------------------------------------------------";
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < members.length; i++) {

			if (i > 0)
				builder.append("\n");
			builder.append("Name: ");
			builder.append(members[i].getName());

			builder.append("\nMember ID: ");
			builder.append(members[i].getMemberId());
			
			builder.append("\nMember's Personal number: ");
			builder.append(members[i].getPersonalNumber());

			builder.append("\nNumber of owned boats: ");
			builder.append(members[i].getBoatList().size());

			if (members[i].getBoatList().size() == 0)
				builder.append("\n----------------------------------------");
			
			else {
				
				builder.append("\n\nBoats list:\n");
				ArrayList<Boat> boats = members[i].getBoatList();
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
				
			}
		}

		return builder.toString();

	}
	
}

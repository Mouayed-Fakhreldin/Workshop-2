package controller;


public class UtilClass {
	
	
	public static void checkChoice(String s, Message message) {
		
		
		if (s == null || !(s.equals("1") || s.equals("2") || s.equals("3"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}

	/*public static Member getSelectedMember() {
		return selectedMember;
	}*/

	/*public static void setSelectedMember(Member selectedMember) {
		UtilClass.selectedMember = selectedMember;
	}*/

	public static void checkYesNoChoice(String s, Message message) {
		if (s == null || !(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n"))) {
			message.setValidated(false);
			message.setMessage("Please choose a valid option!");
		}
		
		else
			message.setValidated(true);
	}
	
}

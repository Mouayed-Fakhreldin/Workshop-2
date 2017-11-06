package model;

import java.util.ArrayList;


/**
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 * A data model class representing a member at the yacht club
 */
public class Member {
	private String name;
	private String personalNumber;
	private int memberId; 
	private ArrayList <Boat> boatList;
	private boolean stored;

	/**
	 * Creates a member object.
	 * @param name Member's name
	 * @param personalNumber Member's personal number
	 * @throws IllegalArgumentException in these cases.<br> - if the name or personal nr are null or empty.<br> - if the name has too many spaces.<br> - If the name contains a character which is not alphabetic and not a space.<br> - if the personal number is not a 10-digit number
	 */
	public Member(String name, String personalNumber) throws IllegalArgumentException{
		
		setName(name);
		setPersonalNumber(personalNumber);
		this.memberId = 0;
		boatList = new  ArrayList<Boat>();
		stored = false;
		
	}

	/**
	 * Gets the member's name
	 * @return Member's name.
	 */
	public String getName() {
		
		return name;
	}

	/**
	 * Set member's name
	 * @param name Member's name
	 * @throws IllegalArgumentException in these cases.<br> - if the name is null or empty.<br> - if the name has too many spaces.<br> - If the name contains a character which is not alphabetic and not a space.
	 */
	public void setName(String name) throws IllegalArgumentException {
		
		if (name == null || name.length() == 0)
			throw new IllegalArgumentException("Member's Name can not be empty");
		
		else {
			int counter=1;
			int anotherCounter = 0;
			for(int i = 0;i<name.length();i++){
				char c = name.charAt(i);
				if (!Character.isAlphabetic(c) && c != ' ')
					throw new IllegalArgumentException("Member's name can only contain alphabetic characters and spaces");
				if(c==' '){
					counter++;
				}
				else{
					anotherCounter++;
				}
			}
			 
			
			if(counter>anotherCounter)// Member names can not have too many space
				throw new IllegalArgumentException("Member names can not have too many spaces");		
		
			else
				this.name = name;
			
		}		
	}
	

	/**
	 * Gets the member's personal number.
	 * @return Member's personal number
	 */
	public String getPersonalNumber() {
		return personalNumber;
	}

	
	// Sets a member's personal number. IllegalArgumentException is thrown if the personal number is not a 10-digit number.
	private void setPersonalNumber(String personalNumber) throws IllegalArgumentException {
		if(personalNumber == null || personalNumber.length() == 0)
			throw new IllegalArgumentException("Personal nr can not be empty");
		
		for (int i=0; i<personalNumber.length(); i++) {
			if (!Character.isDigit(personalNumber.charAt(i)))
				throw new IllegalArgumentException("Personal nr can not contain non-digit characters");
		}
		
		if (personalNumber.length() != 10)
			throw new IllegalArgumentException("Personal number has to be a 10-digit number");
		
		this.personalNumber = personalNumber;
	}

	/**
	 * Gets the member's ID. If the member has not been assigned an ID, the ID will be 0
	 * @return Member's unique ID
	 */
	public int getMemberId() {
		return memberId;
	}
	
	
	/**
	 * Sets member's ID.
	 * @param memberId Member's ID
	 * @throws IllegalArgumentException if memberId is not a positive number
	 */
	void setMemberId(int memberId) {
		
		if (memberId < 1)
			throw new IllegalArgumentException("Member ID should be a positive number.");
		this.memberId = memberId;
		
	}


	/**
	 * A flag denoting whether the member is stored in the database or not. For the member
	 * to be stored in the database, Queries.storeMember() has to be called 
	 * @return true if the member is stored in the database, false otherwise.
	 */
	public boolean isStored() {
		return stored;
	}

	
	/**
	 * A flag denoting whether the member is stored in the database or not.
	 * @param stored true if it's stored, false otherwise.
	 */
	void setStored(boolean stored) {
		this.stored = stored;
	}

	/**
	 * Gets the member's registered boats.
	 * @return an ArrayList of the member's boats
	 */
	public ArrayList<Boat> getBoatList() {
		Member member = new Member(this.name, this.personalNumber);
		member.memberId = this.memberId;
		member.setStored(this.stored);
		
		ArrayList<Boat> boatList = new ArrayList<Boat>();
		for (Boat boat:this.boatList) {
			boatList.add(new Boat(member, boat.getType(), boat.getLength()));
		}
		
		for (Boat boat:boatList)
			member.addBoat(boat);
		return boatList;
	}
	
	
	/**
	 * Adds a new boat to the member object list.
	 * @param newBoat The new boat to be added
	 * @return true if the boat has been successfully added or false if the boat already exists.
	 */
	public boolean addBoat(Boat newBoat){
		for (int i=0;i<boatList.size();i++){
			if(newBoat.isEqual(boatList.get(i))){
				return false;
			}
		}
		boatList.add(newBoat);
		return true;
		
	}
	
	
	/**
	 * Removes a boat. Note that this method will not remove the boat from the Database.
	 * To remove the boat from the Database, Queries.removeBoat(Boat boat) has to be called
	 * @param boatToDel The boat to be deleted
	 * @return true if the boat has been found and removed, or false if the boat hasn't been found.
	 */
	public boolean removeBoat(Boat boatToDel) {
		for (int i=0; i<boatList.size(); i++ ){
			if(boatList.get(i).isEqual(boatToDel)){
				boatList.remove(i);
				return true;
			}
		}
		return false;

	}
	
	public String toString(){
		int counter =1;
		String output = "Members Name = "+this.name + 
				 "\nMembers Personal Number =  "+this.personalNumber +
				 "\nMembers Id = "+this.memberId+
				 "\nBoats:\n";
		for(int  i=0;i<boatList.size();i++){
			output += "\tBoat "+counter+++" :"+ boatList.get(i).toString()+"\n";
		
		}
		
		return output;
	}

	
}
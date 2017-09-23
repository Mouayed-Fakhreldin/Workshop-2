package model;


/**
 * A data model class representing a boat in the yacht club
 * @author Mouayed Fakhreldin
 * @author Abdilrahman Duale
 * @author Genet Shiferaw
 *
 */
public class Boat {

	
	
	private int boatId;
	private BoatType type;
	private double length;
	private Member owner;
	private boolean stored;
	
	public enum BoatType {
		SAILBOAT, MOTORSAILER, KAYAK, CANOE, OTHER
	}
	
	
	/**
	 * Creates a Boat object.
	 * @param owner The boat's owner 
	 * @param type The boat's type
	 * @param length Boat's length
	 * @throws IllegalArgumentException if owner or type is null or if length is less than 1
	 */
	public Boat(Member owner, BoatType type, double length){
		
		this.boatId = 0;
		
		setType(type);
		
		setLength(length);
		
		if (owner == null)
			throw new IllegalArgumentException("owner can not be null");
		this.owner = owner;
		
		setStored(false);
	}
	
	
	/**
	 * Gets the Boat's ID
	 * @return the boat's ID
	 */
	public int getBoatId() {
		return boatId;
	}


	/**
	 * Sets the ID for the boat
	 * @param boatId 
	 */
	void setBoatId(int boatId) {
		this.boatId = boatId;
	}


	/**
	 * Gets the owner of the boat
	 * @return the owner of the boat.
	 */
	public Member getOwner() {
		return owner;
	}

	/**
	 * Gets the type of the boat 
	 * @return The type of the boat.
	 */
	public BoatType getType() {
		return type;
	}
	
	/**
	 * Sets the type of the boat
	 * @param type Type of the boat.
	 * @throws IllegalArgumentException if the type is null.
	 */
	public void setType(BoatType type) {
		if (type == null)
			throw new IllegalArgumentException("type can not be null");
		this.type = type;
	}
	
	/**
	 * Gets the length of the boat.
	 * @return length of the boat
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * Sets the length of the boat.
	 * @param length Length of the boat
	 * @throws IllegalArgumentException if length is less than 1 (not positive).
	 */
	public void setLength(double length) {
		if (length < 1)
			throw new IllegalArgumentException("Length can only be positive.");
		this.length = length;
	}
	
	
	/**
	 * A flag denoting whether the boat is stored in the database or not. For the boat
	 * to be stored in the database, it needs to be added to the member's boat list and
	 * then stored in the SQL database as well.
	 * @return true if the boat is stored in the database, false otherwise.
	 */
	public boolean isStored() {
		return stored;
	}


	/**
	 * A flag denoting whether the boat is stored in the database or not.
	 * @param stored true if it's stored, false otherwise.
	 */
	void setStored(boolean stored) {
		this.stored = stored;
	}


	/**
	 * Checks if two boats are equal
	 * @param boatToCompare The boat being compared to
	 * @return true if the two boats have the same ID, false otherwise.
	 */
	public boolean isEqual(Boat boatToCompare){
		return this.boatId== boatToCompare.boatId;
	}
	
	public String toString(){
		String output="Boat id = "+this.boatId +", Boat Type = "+this.type+", Boats Length = "+ this.length;
		return output;
	}
}
package project5;

import java.util.ArrayList;

/**
 * This class represents a single collision (single valid row from the input file).
 * @author Gayeon_Park
 *
 */
public class Collision implements Comparable<Collision>{
	
	private String zip;
	private Date date;
	private String key;
	private int personsInjured;
	private int pedestriansInjured;
	private int cyclistsInjured;
	private int motoristsInjured;
	private int personsKilled;
	private int pedestriansKilled;
	private int cyclistsKilled;
	private int motoristsKilled;
	
	/**
	 * This constructor creates an ArrayList<String> object that contains all the 
	 * parsed entries from the input file. The constructor also checks for 
	 * following conditions to see if the parameter entries meet certain requirements 
	 * and throws IllegalArgumentException accordingly:
	 *	• the date cannot be empty and has to represent a valid Date object
	 *	• the zip code has to be a five character string with digits as all of its characters
	 *	• the number of persons/pedestrians/cyclists/motorists injured/killed has to be a non-negative integer
	 *	• the unique key has to be a non-empty string
	 * The other entries do not need to be verified and may contain empty strings. 
	 * 
	 * @param entries
	 * @throws IllegalArgumentException
	 */
	public Collision(ArrayList<String> entries) throws IllegalArgumentException{
		if ( entries == null || entries.size() == 0){
			throw new IllegalArgumentException("Error: an empty or null ArrayList is passed into the parameter.");
		}
		
		//checks if date is not empty and is a valid Date object
		if (entries.get(0).length() == 0){
			throw new IllegalArgumentException("Error: The date is empty or is not a valid Date object.");
		} else date = new Date(entries.get(0));
		
		//checks if the zip code is a five character string with digits as all of its characters
		if (entries.get(3).length() != 5){
			throw new IllegalArgumentException("Error: The zip code does not have correct length.");
		} 
		String possibleNums = "0123456789";
		for (int i = 0; i < entries.get(3).length(); i ++){
			if ( !(possibleNums.contains(entries.get(3).substring(i,i+1))) ){
				throw new IllegalArgumentException("Error: The zip code is not a five digit number.");
			}
		}
		zip = entries.get(3);
		
		//checks if the number of persons/pedestrians/cyclists/motorists injured/killed is a non-negative integer 
		for (int i = 10; i < 18; i ++){
			if ( Integer.parseInt(entries.get(i)) < 0){
				throw new IllegalArgumentException("Error: Cannot have a negative number of any persons.");
			}
		} 
		personsInjured = Integer.parseInt(entries.get(10));
		personsKilled = Integer.parseInt(entries.get(11));
		pedestriansInjured = Integer.parseInt(entries.get(12));
		pedestriansKilled = Integer.parseInt(entries.get(13));
		cyclistsInjured = Integer.parseInt(entries.get(14));
		cyclistsKilled = Integer.parseInt(entries.get(15));
		motoristsInjured = Integer.parseInt(entries.get(16));
		motoristsKilled = Integer.parseInt(entries.get(17));
		
		//checks if the unique key is a non-empty string
		if (entries.get(23).length() == 0){
			throw new IllegalArgumentException("");
		} else key = entries.get(23);
	}
	
	/**
	 * Returns the zip code of the Collision object. 
	 * @return
	 */
	public String getZip(){
		return zip;
	}
	
	/**
	 * Returns the date of the Collision object. 
	 * @return
	 */
	public Date getDate(){
		return date;
	}
	
	/**
	 * Returns the unique key of the Collision object. 
	 * @return
	 */
	public String getKey(){
		return key;
	}
	
	/**
	 * Returns the number of persons injured of the Collision object. 
	 * @return
	 */
	public int getPersonsInjured(){
		return personsInjured; 
	}
	
	/**
	 * Returns the number of pedestrians injured of the Collision object. 
	 * @return
	 */
	public int getPedestriansInjured(){
		return pedestriansInjured;
	}
	
	/**
	 * Returns the number of cyclists injured of the Collision object. 
	 * @return
	 */
	public int getCyclistsInjured(){
		return cyclistsInjured;
	}
	
	/**
	 * Returns the number of the motorists injured of the Collision object. 
	 * @return
	 */
	public int getMotoristsInjured(){
		return motoristsInjured;
	}
	
	/**
	 * Returns the number of the persons killed of the Collision object. 
	 * @return
	 */
	public int getPersonsKilled(){
		return personsKilled;
	}
	
	/**
	 * Returns the number of pedestrians killed of the Collision object. 
	 * @return
	 */
	public int getPedestriansKilled(){
		return pedestriansKilled;
	}
	
	/**
	 * Returns the number of cyclists killed of the Collision object. 
	 * @return
	 */
	public int getCyclistsKilled(){
		return cyclistsKilled;
	}
	
	/**
	 * Returns the number of the Collision object. 
	 * @return
	 */
	public int getMotoristsKilled(){
		return motoristsKilled;
	}

	/**
	 * This method compares two Collision objects based on their zip codes, 
	 * dates and unique keys in that order.
	 * If the two zip codes are different, make the comparison on the zip code alone.
	 * If they are the same, make the comparison on the dates alone.
	 * If the date are the same, then make the comparison based on the unique keys. 
	 * 
	 * @param o
	 * @return returns zero if the zip codes, dates and unique keys are the same,
	 * returns a negative value if the zip code/date/unique key of this Collision object
	 * is smaller/earlier than that of the Collision object o being compared to, 
	 * returns a positive value if the zip code/date/unique key of this Collision object
	 * is greater/later than that of the Collision object o that it is being compared to
	 */
	public int compareTo(Collision o) {
		if ( this.getZip().equals(o.getZip()) ){
			if ( this.getDate().equals(o.getDate()) ){
				if ( this.getKey().equals(o.getKey()) ){
					return 0;
				}
				else if ( Integer.parseInt(this.getKey()) < Integer.parseInt(o.getKey())) return -1;
				else return 1;
			}
			return this.getDate().compareTo(o.getDate());
		}
		else if ( Integer.parseInt(this.getZip()) < Integer.parseInt(o.getZip())) return -1;
		else return 1;
	}

	/**
	 * REWRITE BELOW IN MY OWN WORDS
	 * This is a overridden equals method that compares two Collision objects for equality
	 * by checking if their zip codes, dates, and unique keys are equal.  
	 * 
	 * @param obj
	 * @return returns true if the zip codes, dates and unique keys are the same,
	 * returns false if any one of the zip code, date, and unique key of this Collision object
	 * is not equal than that of the Collision object o being compared to, 
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Collision))
			return false;
		Collision other = (Collision) obj;
		if ( !(this.getZip().equals(other.getZip())) ) 
			return false;
		if ( !(this.getDate().equals(other.getDate())) )
			return false;
		if ( !(this.getKey().equals(other.getKey())) )
			return false;
		return true;
	}
	
	/**
	 * Returns a string representation of this tree using an inorder traversal . 
	 * @see java.lang.Object#toString()
	 * @return string representation of this tree 
	 */
	public String toString() {
		return this.getZip() + " " + this.getDate() + " " + this.getKey();
	}
}
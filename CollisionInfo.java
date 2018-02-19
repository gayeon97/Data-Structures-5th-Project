package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the main class for testing the Collision, CollisionsData and Data classes.
 * This class is responsible for parsing the command line argument, reading the input file,
 * creating the CollisionsData object based on the input file, interacting with the user,
 * calling all the methods of the CollisionsData object needed for the output.
 * This class has an additional static method other than the main() method to provide more modularity. 
 *
 * @author Gayeon_Park
 *
 */
public class CollisionInfo {

	public static void main(String[] args) {
		//Checks if there is a file passed in as a command line
		if (args.length < 1) {
			System.err.printf("Error: the program expects file name as an argument.");
			System.exit (0);
		}
		
		Scanner collisionDataFile = null;
		try {
			File inputFile = new File(args[0]);
			collisionDataFile = new Scanner(inputFile);
		} catch (FileNotFoundException e){
			System.out.printf("ERROR: file %s. not found.\n", args[0]);
		} catch (NullPointerException e2){
			System.out.printf("ERROR: File with such name does not exist.", args[0]);
			System.exit(1);
		}
		
		//read the lines of the input file after the first line
		//create an ArrayList<Strings> using the splitCSVLine (code given by Joanna)
		//if and only if the ArrayList<String> created has more than 24 elements,
		//create a Collision object and add it to the CollisionsData object
		collisionDataFile.nextLine();
		CollisionsData collisionStored = new CollisionsData();
		while (collisionDataFile.hasNextLine()){
			try {
				String temp = collisionDataFile.nextLine();
				ArrayList<String> possibleCollision = splitCSVLine(temp);
				if (possibleCollision.size() > 23){
					collisionStored.add(new Collision(possibleCollision));
				}
			} catch (IllegalArgumentException e){

			}	
		}
		//Close the input file
		collisionDataFile.close();
				
		//First ask a user to enter in a zip code
		System.out.print("Enter a zip code ('quit' to exit): ");
		Scanner input = new Scanner(System.in);
		String userInput = input.next();
		
		//The program will continue to ask the user for an input and will only terminate when the user enters "quit"
		while (!userInput.equalsIgnoreCase("quit")){
			String userZip = "";
			boolean isValidZip = false;
			Date userStartDate = null; 
			Date userEndDate = null;

			//Check if the user entry for zip is a valid zip code
			if (userInput.length() != 5){ //check if the user input is 5 in length
				System.out.println("Invalid zip code. Try agian." + '\n');
			} else{
				//Check if the user input of 5 in length contains digits only
				String possibleNums = "0123456789";
				for (int i = 0; i < userInput.length(); i ++){
					if ( possibleNums.contains(userInput.substring(i,i+1)) ){
						userZip += userInput.substring(i,i+1);
						isValidZip = true;
					}
					else{
						isValidZip = false;
						System.out.println("Invalid zip code. Try agian." + '\n');
						break;
					}	
				}	

				//If the user entry for zip is a valid zip code,
				//check if the user entry for start and end dates are valid dates
				if (isValidZip){
					System.out.print("Enter start date (MM/DD/YYYY): ");
					String userStartDateInput = input.next();
					System.out.print("Enter end date (MM/DD/YYYY): ");
					String userEndDateInput = input.next();

					//Prints a report based on the data from the input file and user inputs
					//The report is a summary of the collisions that occured in a given zip code
					//within the specific dates
					try{
						userStartDate = new Date(userStartDateInput); 
						userEndDate = new Date(userEndDateInput);
						String s1 = String.format("Motor Vehicle Collisions for zipcode %s (%s - %s)", 
														userZip,userStartDate,userEndDate);
						String s2 = String.format("%0" + s1.length() + "d", 0).replace("0","=");
						System.out.println('\n' + s1 + '\n' + s2);
						System.out.println(collisionStored.getReport(userZip,userStartDate,userEndDate));
					} catch (IllegalArgumentException e){
						System.out.println("Invalid date format. Try agian." + '\n');
					}	
				}				
			}			
			//Prompt the user again to enter a zip code
			System.out.print("Enter a zip code ('quit' to exit): ");
			userInput = input.next();
		}		
	}
	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author Joanna Klukowska
	 * @param textLine	a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that line
	 */
	public static ArrayList<String> splitCSVLine(String textLine){

		ArrayList<String> entries = new ArrayList<String>(); 
		int lineLength = textLine.length(); 
		StringBuffer nextWord = new StringBuffer(); 
		char nextChar; 
		boolean insideQuotes = false; 
		boolean insideEntry= false;

		//Iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);

			//Handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {

				//Change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false; 
					insideEntry = false;
				}else {
					insideQuotes = true; 
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
					//Add it to the current entry 
					nextWord.append( nextChar );
				}else { //Skip all spaces between entries
					continue; 
				}
			} else if ( nextChar == ',') {
				if (insideQuotes){ //Comma inside an entry
					nextWord.append(nextChar); 
				} else { //End of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				//Add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			} 

		}
		//Add the last word ( assuming not empty ) 
		//Trim the white space before adding to the list 
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}
	

}

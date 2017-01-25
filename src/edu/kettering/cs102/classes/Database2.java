/* James Garza
 * CS-102
 * 23.01.2017
 * 
 * Database2.java
 * 
 */

package edu.kettering.cs102.classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Database2 {
	LinkedList amList = new LinkedList(); // lists that holds FM Stations
	LinkedList fmList = new LinkedList(); // lists that holds FM Stations
	
	/* Constructor
	 * declare an empty array of Stations with MAX_STATIONS elements
	 */
	public Database2() {}
	
	/* addStationsFromFile(fileName)
	 * takes a file name, reads data from file, and adds new Stations to 
	 * database if the data is formatted correctly. '/' is used to separate 
	 * data elements on one line. The correct format of file data is:
	 * callSign/frequencyBand/frequency/location/genre
	 */
	public void addStationsFromFile(String fileName) {
		File myFile = new File(fileName);	// user's input file name
		Scanner fileScanner = null;			// reads lines from file
		
		try {	// attempt to open file
			fileScanner = new Scanner(myFile);
		} 
		catch (FileNotFoundException error) { // if file not found, exit program
			System.err.println("ERROR: File not found.");
			System.exit(0);
		}

		String fileLine;			// holds a line from input file
		String fileData[];			// holds data after delimiter separation
		Station myStation = null;	// holds newly created Station 
		
		while (fileScanner.hasNextLine()) {
			fileLine = fileScanner.nextLine();	// get file line
			fileData = fileLine.split("/");		// separate data using delimiter
			 
			String callSign = fileData[0];		// 1st data: Station call sign
			String freqBand = fileData[1];		// 2nd data: Station freq band
			String location = fileData[3];		// 4th data: location of Station
			String genre = fileData[4];			// 5th data: Station genre
			
			try {	// will throw exception if 3rd data column is not an integer
				// 3rd data: Station frequency converted from String to integer
				int freq = Integer.parseInt(fileData[2]);
				// create new Station 
				myStation = new Station(callSign, freqBand, freq, 
									location, genre);
				addStation(myStation);	// add Station to database
			} catch (NumberFormatException error) {	// if non-int in 3rd column
				System.err.print("ERROR: Could not add Station since file "
							+ "contains non-integer data is the 3rd column.\n");
				error.printStackTrace();	// error information
			}
		}
		fileScanner.close();	// memory cleanup
	}

	/* addStation(newStation)
	 * add a Station object to the end of the array if able
	 */
	public void addStation (Station newStation) {
		if (newStation.getFreqBand().toUpperCase().equals("AM"))
			amList.addNode(newStation);
		else
			fmList.addNode(newStation);
	}
	
	/* printAll()
	 * loop through every Station in the database a print all info
	 */
	public void printAll() {
		Node current = amList.head;
		System.out.print("AM stations:\n");
		while (current != null)
			// print formatted string for each station
			System.out.println(current.getStationInfo());
		current = fmList.head;
		System.out.print("\nFM stations:\n");
		while (current != null)
			// print formatted string for each station
			System.out.println(current.getStationInfo());
	}
	
	/* printFoundCallSign(input)
	 * searches all stations and finds all call signs that match the user's
	 * input string
	 */
	public void printFoundCallSign(String input) {
		int counter = 0;	// used to count number of matches found
		input = input.toUpperCase();	// error checking setup
		Node looper = amList.head;		// first check AM stations
		// loop that checks all AM stations in amList
		while (looper != null) {
			// if call sign found
			if (looper.getStation().getCallSign().equals(input)) {
				System.out.println(looper.getStationInfo()); // print info
				counter++;	// increment number of matches found
			}
			looper = looper.getNext();
		}
		looper = fmList.head;			// first check AM stations
		// loop that checks all FM stations in amList
		while (looper != null) {
			// if call sign found
			if (looper.getStation().getCallSign().equals(input)) {
				System.out.println(looper.getStationInfo()); // print info
				counter++;	// increment number of matches found
			}
			looper = looper.getNext();
		}
		System.out.println("Found matches: " + counter);
	}
	
	/* printFoundFreq(inputFreqBand, inputFreq)
	 * searches all stations and finds all frequencies that contain the user's
	 * input frequency and frequency band
	 */
	public void printFoundFreq(String inputFreqBand, String inputFreq) {
		int counter = 0;		// used to count number of matches found
		
		if (inputFreqBand.equals("AM"))	// if AM, remove last 0
			inputFreq = inputFreq.substring(0, inputFreq.length() - 1);
		else	// if FM, remove dot "."
			inputFreq = inputFreq.replace(".", "");

		try {
			// throws exception for invalid input
			int inputFreqInt = Integer.parseInt(inputFreq);
			// loop thru all stations checking for frequency matches
			for (int index = 0; index < arrayIndex; index++)
				// if freq matches, continue
				if (inputFreqInt == database[index].getFreq())
					// if freqBand also matches, then consider the station found
					if (inputFreqBand.equals(database[index].getFreqBand())) {
						// print formatted station data
						System.out.println(database[index].getStation());
						counter++;	// increment number of found matches
					}
			System.out.println("Found matches: " + counter);
		} catch (NumberFormatException error) { // if frequency is not an usable
			// user can enter an AM and decimal number, resulting in this error
			System.err.print("Invalid input. Frequency must be an integer for "
							 + "AM or a single decimal for FM.\n");
		}
	}
	
	/* printFoundGenre(input)
	 * searches all stations and finds all genres that contain the user's
	 * input string
	 */
	public void printFoundGenre(String input) {
		int counter = 0;	// used to count number of matches found
		input = input.toUpperCase();	// error checking setup
		
		//loop to go thru all stations
		for (int index = 0; index < arrayIndex; index++)
			// check if user's input is contained in station's genre
			if (database[index].getGenre().toUpperCase().contains(input)) {
				// print station's formatted information
				System.out.println(database[index].getStation());
				counter++;		// increment number of found matches
			}
		// display number of found matches
		System.out.println("Found matches: " + counter);
	}
}
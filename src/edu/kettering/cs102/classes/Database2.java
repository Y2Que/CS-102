/* James Garza
 * CS-102
 * 23.01.2017
 * 
 * Database2.java
 * the second database class for the second assignment. This class manages
 * adding, sorting, and removing objects to two lined lists
 */

package edu.kettering.cs102.classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Database2 {
	final static int LIST_COUNT = 2;	// number of lists
	LinkedList amList;					// lists that holds AM Stations
	LinkedList fmList;					// lists that holds FM Stations
	
	/* Constructor
	 * declare an empty array of Stations with MAX_STATIONS elements
	 */
	public Database2() {
		amList = new LinkedList();	// lists that holds FM Stations
		fmList = new LinkedList();	// lists that holds FM Stations
	}
	
	/* addStation(newStation)
	 * add a Station object to the end of the linked list
	 */
	public void addStation (Station newStation) {
		if (newStation.getFreqBand().toUpperCase().equals("AM"))
			amList.addNode(newStation);
		else
			fmList.addNode(newStation);
	}
	
	/* removeStation ()
	 * removes a Station object from linked list by call sign
	 */
	public Station removeStation () {
		Node removedNode = amList.head;
		//
		//*********************************************************
		//
		//
		//		
		return removedNode.getStation();
	}
	
	
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
	
	/* addStationCmdLine ()
	 * user adds a Station object to the database
	 */
	public void addStationCmdLine () {
		
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
		Node loopNode = amList.head;	// first check AM stations
		
		// loop that checks all lists, both AM and FM
		for (int listCount = 0; listCount < LIST_COUNT; listCount++) {
			// loop that walks thru each list's nodes
			while (loopNode != null) {
				// if call sign found
				if (loopNode.getStation().getCallSign().equals(input)) {
					System.out.println(loopNode.getStationInfo()); // print info
					counter++;	// increment number of matches found
				}
			loopNode = loopNode.getNext();	// check next node
			}
			loopNode = fmList.head;		// next check FM stations
		}
		System.out.println("Found matches: " + counter);
	}
	
	/* printFoundFreq(inputFreqBand, inputFreq)
	 * searches all stations and finds all frequencies that contain the user's
	 * input frequency and frequency band
	 */
	public void printFoundFreq(String inputFreqBand, String inputFreq) {
		int counter = 0;		// used to count number of matches found
		inputFreqBand = inputFreqBand.toUpperCase();	// error checking
		
		if (inputFreqBand.equals("AM"))	// if AM, remove last 0
			inputFreq = inputFreq.substring(0, inputFreq.length() - 1);
		else	// if FM, remove dot "."
			inputFreq = inputFreq.replace(".", "");

		try {
			// throws exception for invalid input
			int inputFreqInt = Integer.parseInt(inputFreq);
			Node loopNode = amList.head;	// start with AM list
			
			// loop that checks all lists, both AM and FM
			for (int listCount = 0; listCount < LIST_COUNT; listCount++) {
				// loop thru all stations checking for frequency matches
				while (loopNode != null) {
					// if freq matches, continue
					if (inputFreqInt == loopNode.getStation().getFreq())
						// if freqBand also matches, consider the station found
						if (inputFreqBand.equals(
										loopNode.getStation().getFreqBand())) {
							// print formatted station data
							System.out.println(loopNode.getStationInfo());
							counter++;	// increment number of found matches
						}
					loopNode = loopNode.getNext();	// get next node
				}
				loopNode = fmList.head;		// next check FM station
			}	
			System.out.println("Found matches: " + counter);
		} catch (NumberFormatException error) { // if frequency is not an usable
			// user can enter AM and decimal number, resulting in this error
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
		Node loopNode = amList.head;	// first check AM stations
		
		// loop that checks all lists, both AM and FM
		for (int listCount = 0; listCount < LIST_COUNT; listCount++) {
			// loop thru all stations checking for frequency matches
			while (loopNode != null) {
				// check if user's input is contained in station's genre
				if (loopNode.getStation().getGenre().toUpperCase()
															.contains(input)) {
					// print station's formatted information
					System.out.println(loopNode.getStationInfo());
				}
				loopNode = loopNode.getNext();	// get next node
			}
			loopNode = fmList.head;		// next check FM station
		}
		// display number of found matches
		System.out.println("Found matches: " + counter);
	}
}
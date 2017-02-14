/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 28.01.2017
 * Program Assignment 2
 * Database.java
 * the second database class for the second assignment. This class manages
 * adding, sorting, and removing objects from two doubly liked lists
 */

package edu.kettering.cs102.program3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import edu.kettering.cs102.program2.Node;
import edu.kettering.cs102.program2.Station;

public class Database {
	final static int LIST_COUNT = 2;	// number of lists
	// lists that hold all AM & FM Stations
	LinkedList<Station> amList;
	LinkedList<Station> fmList;
	
	/* Constructor
	 * declare an empty linked list of Stations
	 */
	public Database() {
		amList = new LinkedList<Station>();
		fmList = new LinkedList<Station>();
	}
	
	/* addStation (newStation)
	 * add a Station object to either linked list
	 */
	public void addStation (Station newStation) {
		if (newStation.getFreqBand().toUpperCase().equals("AM"))
			addStation(newStation, amList);
		else
			addStation(newStation, fmList);
	}
	
	private void addStation (Station newStation, LinkedList<Station> list) {
		if (list.isEmpty())				// if empty list
			list.addFirst(newStation);
		else {							// if not empty list
			
			// iterator to walk through list
			ListIterator<Station> interator = list.listIterator();
			Station current = list.getFirst();	// start at head of list
			
			String newCallSign = newStation.getCallSign();	// get new callSign
			int index = 0;	// to keep track of index of linked list
			// when compareTo value is negative, current node has passed the 
			// correct lexicographic spot
			int sortValue = current.getCallSign().compareTo(newCallSign);
	
			while (interator.hasNext() && sortValue < 0) {
				current = interator.next();	// get next node
				index++;					// increment index
				// reevaluate alphabetical sort position
				sortValue = current.getCallSign().compareTo(newCallSign);
			}
			
			// if duplicate, don't add
			if (sortValue == 0)
				System.err.print("A station with that call sign already "
									+ "exists. Unable to add station:\n"
									+ newStation.getStation() + "\n");
			// if adding to front of list
			else if (index == 0 && sortValue < 0)
				list.addFirst(newStation);
			// if at end of the list, append
			else if (!interator.hasNext() && sortValue > 0)
				list.addLast(newStation);
			// newNode is not first, last or duplicate
			else
				if (sortValue < 0)	// passed correct alphabetical spot 
					list.add(index - 1, newStation);
				else 				// correct alphabetical spot is next
					list.add(index, newStation);
		}
	}
	
	
	/* addStationsFromFile (fileName)
	 * takes a string fileName, reads from file, and adds new Stations to 
	 * linked lists if the data is formatted correctly. '/' is used to separate 
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
				// create new Station, capitalize callSign and freqBand
				myStation = new Station(callSign.toUpperCase(), 
										freqBand.toUpperCase(), freq, 
										location, genre);
				addStation(myStation);	// add Station to database
			} catch (NumberFormatException error) {	// if non-int in 3rd column
				System.err.print("ERROR: Could not add Station since file "
							+ "contains non-integer data is the 3rd column.\n");
			}
		}
		fileScanner.close();	// memory cleanup
	}
	
	/* addStationCmdLine ()
	 * user adds a Station object to the database
	 */
	public void addStationCmdLine () {
		// prompt user to enter a Station
		System.out.print("Please enter your station in the following "
				+ "format:\ncall_sign/frequency/frequency_band/location/"
				+ "genre\nEnter your station: ");
		Scanner inputScanner = new Scanner(System.in);	// Scanner to read input
		String inputString = inputScanner.nextLine();	// get user's input
		String inputData[] = inputString.split("/");	// divide user's input
		
		try {	// check for invalid input
			String callSign = inputData[0];		// 1st data: Station call sign
			String freqStr = inputData[1];		// 2nd data: Station frequency
			String freqBand = inputData[2];		// 3rd data: Station freq band
			String location = inputData[3];		// 4th data: location of Station
			String genre = inputData[4];		// 5th data: Station genre
			
			if (freqBand.equals("AM"))	// if AM, remove last 0
				freqStr = freqStr.substring(0, freqStr.length() - 1);
			else	// if FM, remove decimal '.'
				freqStr = freqStr.replace(".", "");
			
			// Station frequency converted from String to integer
			int freq = Integer.parseInt(freqStr);
			// create new Station 
			Station myStation = new Station(callSign.toUpperCase(),
											freqBand.toUpperCase(), freq, 
											location, genre);
			addStation(myStation);	// add Station to database
		} catch (NumberFormatException error) {	// if non-int in 2nd field
			System.err.print("ERROR: Could not add Station since input "
						+ "contains mismatched data formats between frequency "
						+ "and frequency_band.\n");

		} catch (ArrayIndexOutOfBoundsException error) { // invalid input
			System.err.print("ERROR: Invalid input, could not add Station.\n");
		}
	}
	
	
	/* printAll()
	 * loop through every Station in the database a print all info
	 */
	public void printAll() {
		Node current = amList.head;			// start with AM list
		int counter = 0;					// counts number of records
		System.out.print("AM stations:\n");
		while (current != null) {
			// print formatted string for each station
			System.out.println(current.getStationInfo());
			counter++;						// increment number of records
			current = current.getNext();	// get next node
		}
		current = fmList.head;				// next print FM list
		System.out.print("FM stations:\n");
		while (current != null) {
			// print formatted string for each station
			System.out.println(current.getStationInfo());
			counter++;						// increment number of records
			current = current.getNext();	// get next node
		}
		System.out.println("Found records: " + counter);
	}
	
	/* printFoundCallSign (input)
	 * searches all Stations and prints all call signs that match the user's
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
					counter++;				// increment number of matches found
				}
			loopNode = loopNode.getNext();	// check next node
			}
			loopNode = fmList.head;			// next check FM stations
		}
		System.out.println("Found matches: " + counter);
	}
	
	/* printFoundFreq(inputFreqBand, inputFreq)
	 * searches all stations and prints all frequencies that contain the user's
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
	
	/* printFoundGenre (input)
	 * searches all Stations and prints all genres that contain the user's
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
					counter++;	// increment number of found matches
				}
				loopNode = loopNode.getNext();	// get next node
			}
			loopNode = fmList.head;				// next check FM station
		}
		// display number of found matches
		System.out.println("Found matches: " + counter);
	}
}
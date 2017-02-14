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

import edu.kettering.cs102.program3.Station;

public class Database {
	LinkedList<Station> amList;		// list that holds all AM Stations
	LinkedList<Station> fmList;		// list that holds all FM Stations
	
	/* Constructor
	 * declare an empty linked lists of Stations
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
			ListIterator<Station> iterator = list.listIterator();
			Station current = list.getFirst();	// start at head of list
			
			String newCallSign = newStation.getCallSign();	// get new callSign
			
			/* when compareTo value is positive, current node has passed the 
			 * correct lexicographic spot */
			int sortValue = current.getCallSign().compareTo(newCallSign);
	
			while (iterator.hasNext() && sortValue < 0) {
				current = iterator.next();	// get next node
				// reevaluate alphabetical sort position
				sortValue = current.getCallSign().compareTo(newCallSign);
			}
			
			// if duplicate, don't add
			if (sortValue == 0)
				System.err.print("A station with that call sign already "
									+ "exists. Unable to add station:\n"
									+ newStation.getStation() + "\n");
			// if adding to front of list
			else if (iterator.nextIndex() == 0 && sortValue > 0)
				list.addFirst(newStation);
			// if at end of the list, append
			else if (!iterator.hasNext() && sortValue < 0)
				list.addLast(newStation);
			// newNode is not first, last or duplicate
			else
				if (sortValue > 0)	// passed correct alphabetical spot 
					list.add(iterator.nextIndex() - 1, newStation);
				else 				// correct alphabetical spot is next
					list.add(iterator.nextIndex(), newStation);
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
		
		while (fileScanner.hasNextLine()) {
			String fileLine = fileScanner.nextLine();	// get file line
			String fileData[] = fileLine.split("/");	// separate data
			 
			String callSign = fileData[0];		// 1st data: Station call sign
			String freqBand = fileData[1];		// 2nd data: Station freq band
			String location = fileData[3];		// 4th data: location of Station
			String genre = fileData[4];			// 5th data: Station genre
			
			try {	// will throw exception if 3rd data column is not an integer
				// 3rd data: Station frequency converted from String to integer
				int freq = Integer.parseInt(fileData[2]);
				// create new Station, capitalize callSign and freqBand
				Station myStation = new Station(callSign.toUpperCase(), 
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
			String callSign = inputData[0].toUpperCase();	// Station call sign
			String freqStr = inputData[1];					// Station frequency
			String freqBand = inputData[2].toUpperCase(); 	// Station freq band
			String location = inputData[3];					// Station location
			String genre = inputData[4];					// Station genre
			
			if (freqBand.equals("AM"))	// if AM, remove last 0
				freqStr = freqStr.substring(0, freqStr.length() - 1);
			else	// if FM, remove decimal '.'
				freqStr = freqStr.replace(".", "");
			
			// Station frequency converted from String to integer
			int freq = Integer.parseInt(freqStr);
			// create new Station 
			Station myStation = new Station(callSign, freqBand, freq, 
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
	 * loop through every Station in the database a print all Station info.
	 * If the database is empty, returns 0 records found.
	 */
	public void printAll() {
		int counter = 0;					// counts number of records
		System.out.print("AM stations:\n");
		counter = printAll(amList);			// update count of AM Stations 
		System.out.print("FM stations:\n");
		counter = counter + printAll (fmList);	// update count of FM Stations
		System.out.println("Found records: " + counter); // print Station count
	}
	
	/* printAll (list)
	 * prints all Stations in list and returns integer of records printed
	 */
	private int printAll(LinkedList<Station> list) {
		int count = 0;	// count number of records printed
		// iterator to step through nodes
		ListIterator<Station> iterator = list.listIterator();
		while (iterator.hasNext()) { // loop through linked list, print Stations
			System.out.print(iterator.next().getStation() + "\n");
			count++;
		}
		return count;		// return number of records printed
	}
	
	/* printFoundCallSign (input)
	 * searches all Stations and prints all call signs that match the user's
	 * input string
	 */
	public void printFoundCallSign(String input) {
		int count = 0;		// counts number of records found
		count = printFoundCallSign(input, amList);
		count = count + printFoundCallSign(input, fmList);
		System.out.println("Found matches: " + count);
	}
	
	/* findCallSign (input, list)
	 * searches list for the call sign "input" and returns count of the 
	 * number of records found
	 */
	private int printFoundCallSign(String input, LinkedList<Station> list) {
		int count = 0;	// used to count number of matches found
		input = input.toUpperCase();	// error checking setup
		
		boolean found = false;		// indicates if call sign has been found
		// iterator to step through linked list
		ListIterator<Station> iterator = list.listIterator();
		
		// while not at end of list and not found
		while (iterator.hasNext() && !found) {	
			Station current = iterator.next();			// store current Station
			if (current.getCallSign().equals(input)) {	// if match found
				found = true;					// set found variable to true
				count++;						// increment count found
				System.out.println(current.getStation()); // print Station info
			}
		}
		return count;		// return number of matches found
	}

	/* printFoundFreq(inputFreqBand, inputFreq)
	 * searches all stations and prints all frequencies that contain the user's
	 * input frequency and frequency band
	 */
	public void printFoundFreq(String inputFreqBand, String inputFreq) {
/*
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
	
*/
	}
	
	/* printFoundGenre (input)
	 * searches all Stations and prints all genres that contain the user's
	 * input string
	 */
	public void printFoundGenre(String input) {
		int count = 0;	// used to count number of matches found
		input = input.toUpperCase();	// error checking setup
		
		// update count for found matches
		count = printFoundGenre(input, amList);				// check AM list
		count = count + printFoundGenre (input, fmList);	// check FM list
		
		// print number of found matches
		System.out.println("Found matches: " + count);	
	}
		
	private int printFoundGenre(String input, LinkedList<Station> list) {
		
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
*/
	}
}
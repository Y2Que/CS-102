/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 14.02.2017
 * Program Assignment 3
 * Database.java
 * the 3rd database class for the 3rd assignment. This class manages adding, 
 * searching, sorting, and removing objects from two liked lists
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
	 * declare empty linked lists of Stations
	 */
	public Database() {
		amList = new LinkedList<Station>();
		fmList = new LinkedList<Station>();
	}
	
	/* addStation (newStation)
	 * determines which list to add to and calls private method to perform the
	 * adding of a Station object
	 */
	public void addStation (Station newStation) {
		if (newStation.getFreqBand().toUpperCase().equals("AM"))
			addStation(newStation, amList);
		else
			addStation(newStation, fmList);
	}
	
	/* addStation (newStation, list)
	 * adds newStation object to list while maintaining alphabetical sort by 
	 * call sign and prevents duplicate Stations from being added
	 */
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
		System.out.print("- Please enter your station in the following "
				+ "format:\ncall_sign/frequency/frequency_band/location/"
				+ "genre\n- Enter your station: ");
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
	
	/* removeStation(freqBand, callSign)
	 * determines which list should be accessed can calls a private method to
	 * actually remove the Station object if it exists
	 */
	public Station removeStation(String freqBand, String callSign) {
		Station removedStation;				// holds Station to be removed
		callSign = callSign.toUpperCase();	// error-checking set up
		
		if (freqBand.equals("AM"))	// if AM Station, search that list
			removedStation = removeStation(callSign, amList);
		else						// if FM Station, search that list
			removedStation = removeStation(callSign, fmList);
		return removedStation;		// null of Station doens't exist
	}
	
	/* Station removeStation (callSign, list)
	 * searches list for a Station that matches callSign, asks user for 
	 * confirmation of delete, then returns the Station that was removed.
	 * This method returns null if the Station doesn't exist
	 */
	private Station removeStation (String callSign, LinkedList<Station> list) {
		Station removedStation = null;		// holds the Station to be removed
		
		// iterator to step through nodes
		ListIterator<Station> iterator = list.listIterator();
		boolean found = false; 	// used to exit iteration when Station is found
		
		// while not at end of list and Station not found
		while (iterator.hasNext() && !found) {
			Station current = iterator.next();		// step to next node
			if (current.getCallSign().equals(callSign)) {	// if Station found
				removedStation = current;	// get removed Station
				found = true;				// set flag, Station has been found
			}
		}
		
		if (removedStation != null) {	// if station exists
			// ask user for confirmation of delete
			System.out.println("- Are you sure you want to remove this "
								+ "station?\n"+ removedStation.getStation()
								+ "\n- Enter 'yes' to confirm or anything else "
								+ "to cancel deletion:");
			Scanner inputScanner = new Scanner(System.in); // used to read input
			
			// if user confirmed deletion
			if (inputScanner.nextLine().toUpperCase().equals("YES")) {
				list.remove(iterator.previousIndex());
				System.out.println("Staiton successfully removed.");
			} else	// if confirmation is declined
				System.out.println("Staiton remove cancelled.");
		} else {	// Station does not exist
			System.err.print("Station does not exist.\n");
		}
		return removedStation;	// return removed Station
	}
	
	/* printAll()
	 * loop through every Station in the database a print all Station info.
	 * If the database is empty, prints zero records found.
	 */
	public void printAll() {
		int counter = 0;					// counts number of records
		System.out.print("AM stations:\n");
		counter = printAll(amList);			// update count of AM Stations 
		System.out.print("FM stations:\n");
		counter = counter + printAll (fmList);	// update count of FM Stations
		System.out.println("Found records: " + counter); // print Station count
	}
	
	/* int printAll (list)
	 * prints all Stations in list and returns integer of records printed
	 */
	private int printAll(LinkedList<Station> list) {
		int count = 0;	// count number of records printed
		// iterator to step through nodes
		ListIterator<Station> iterator = list.listIterator();
		while (iterator.hasNext()) { // loop through linked list, print Stations
			System.out.print(iterator.next().getStation() + "\n");
			count++;		// increment number of records found
		}
		return count;		// return number of records printed
	}
	
	/* printFoundCallSign (input)
	 * searches all Stations and prints all call signs that match the user's
	 * input string
	 */
	public void printFoundCallSign(String input) {
		int count = 0;		// counts number of records found
		count = printFoundCallSign(input, amList);			// update count
		count = count + printFoundCallSign(input, fmList);	// update count
		System.out.println("Found matches: " + count);		// print count
	}
	
	/* int printFoundCallSign (input, list)
	 * searches list for the call sign "input" and returns count of the 
	 * number of records found
	 */
	private int printFoundCallSign(String input, LinkedList<Station> list) {
		int count = 0;					// used to count number of matches found
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
		int count = 0;
		if (inputFreqBand.equals("AM")) { // if AM, remove last 0
			inputFreq = inputFreq.substring(0, inputFreq.length() - 1);
			count = printFoundFreq(inputFreq, amList);	// get num of matches
		} else { 	// if FM, remove dot "."
			inputFreq = inputFreq.replace(".", "");
			count = printFoundFreq(inputFreq, fmList);	// get num of matches
		}
		System.out.println("Found matches: " + count);	// print matches found
	}
	
	/* int printFoundFreq(inputFreq, list)
	 * searches Stations within list and prints all frequencies that match the 
	 * user's input frequency. Returns an integer of number of matches found
	 */
	private int printFoundFreq(String inputFreq, LinkedList<Station> list) {
		int count = 0;		// used to count number of matches found
		
		try { // throws exception for invalid input
			int inputFreqInt = Integer.parseInt(inputFreq);
			
			// iterator to step through linked list
			ListIterator<Station> iterator = list.listIterator();
			
			while (iterator.hasNext()) {
				Station current = iterator.next();
				// if freq matches, found Station
				if (inputFreqInt == current.getFreq()) {
					// print formatted station data
					System.out.println(current.getStation());
					count++;	// increment number of found matches
				}
			}
		} catch (NumberFormatException error) { // if frequency is not usable
			// user can enter AM and decimal number, resulting in this error
			System.err.print("Invalid input. Frequency must be an integer for "
							 + "AM or a single decimal for FM.\n");
		}
		return count;		// return number of matches found
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
	
	/* int printFoundGenre(input, list)
	 * searches all Stations and prints those which contain user's input String
	 * within the Station's genre. The integer returned is the count of the
	 * number of Stations found which contain the user's input String
	 */
	private int printFoundGenre(String input, LinkedList<Station> list) {
		int count = 0;			// keep count of how many matches were found
		// iterator to walk through list
		ListIterator<Station> iterator = list.listIterator();
		
		while (iterator.hasNext()) {	// loop through list
			Station current = iterator.next();	// get current Station
			// if the Station genre contains the user's input string
			if (current.getGenre().toUpperCase().contains(input)) {
					// print station's formatted information
					System.out.println(current.getStation());
					count++;	// increment number of found matches
			}
		}
		return count;	// return number of records found
	}
}
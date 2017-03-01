/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 28.02.2017
 * Program Assignment 4
 * Database.java
 * the 4th database class for the 4th assignment. This class manages adding, 
 * searching, sorting, and removing objects from two binary search trees
 */

package edu.kettering.cs102.program4;
import java.io.*;
import java.util.*;

public class Database {
	BinaryTree amTree;		// list that holds all AM Stations
	BinaryTree fmTree;		// list that holds all FM Stations
	
	/* Constructor
	 * declare empty binary trees that will hold AM and FM stations
	 */
	public Database() {
		amTree = new BinaryTree();
		fmTree = new BinaryTree();
	}
	
	/* addStation (newStation)
	 * determines which tree to add to and class BinaryTree addNode method
	 */
	public void addStation (Station newStation) {
		if (newStation.getFreqBand().toUpperCase().equals("AM"))
			amTree.addNode(newStation);
		else
			fmTree.addNode(newStation);
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
		} catch (FileNotFoundException error) { // if file not found, return
			System.err.println("ERROR: File not found.");
			return;		// exit method
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
	 * user adds a Station object to the database via command line
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
			String freqStr  = inputData[1];					// Station frequency
			String freqBand = inputData[2].toUpperCase(); 	// Station freq band
			String location = inputData[3];					// Station location
			String genre    = inputData[4];					// Station genre
			
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
	
	/* printToFile (userFile)
	 * calls binary tree method to write entire contents into userFileStr in
	 * pre-order to tree can later be recreated with the same structure
	 */
	public void printToFile(String userFileStr) {
		try {
			PrintWriter writer = new PrintWriter(userFileStr);
			amTree.writeTreeToFile(writer);
			fmTree.writeTreeToFile(writer);
			writer.close();
			System.out.print("Database write susscessful.\n");
		} catch (IOException error) {
			System.err.print("ERROR: Unable to write to output file.\n");
		}
	}
	
	/* buildDatabaseFromFile (userFileStr)
	 * checks if user's file exists, if it does then drops the current database
	 * and creates a new database based on the user's file
	 */
	public void buildDatabaseFromFile (String userFileStr) {
		// declare file to check if it exists 
		File userFile = new File(userFileStr);
		// if file does not exists or is a folder, exit method
		if(!userFile.exists() || userFile.isDirectory()) {
			System.err.print("ERROR: No file found.\n");
			return;		// exit method
		}
		amTree.removeAll();		// remove all Station from AM tree
		fmTree.removeAll();		// remove all Station from FM tree
		
		// initialize database from file
		addStationsFromFile(userFileStr);
		System.out.print("Load susscessful.\n");
	}
	
	/* removeStation(freqBand, callSign)
	 * determines which list should be accessed can calls a private method to
	 * actually remove the Station object if it exists
	 */
	public void removeStation(String freqBand, String callSign) {
		callSign = callSign.toUpperCase();	// error-checking set up
		try { // try to remove Station, if it doesn't exist, throws an exception
			if (freqBand.equals("AM"))	// if AM Station, search that list
				amTree.removeNode(callSign);
			else						// if FM Station, search that list
				fmTree.removeNode(callSign);
		} catch (NoSuchElementException error) { // if Station not found
			System.err.println("ERROR: Station not found.");
		}
	}
	
	/* printAll()
	 * loop through every Station in the database a print all Station info.
	 * If the database is empty, prints zero records found.
	 */
	public void printAll() {
		int counter = 0;					// counts number of records
		System.out.print("AM stations:\n");
		counter = amTree.printInOrder();			// update count of AM Stations 
		System.out.print("FM stations:\n");
		counter += fmTree.printInOrder();		// update count of FM Stations
		System.out.println("Found records: " + counter); // print Station count
	}
	
	/* printFoundCallSign (input)
	 * searches list for the call sign "input", if found it will print the 
	 * Station and finally return the number of records found
	 */
	public void printFoundCallSign(String input) {
		input = input.toUpperCase();					// error checking setup
		
		// update count for found matches
		int count = amTree.printFoundCallSign(input);	// update count
		count += fmTree.printFoundCallSign(input);		// update count
		
		System.out.println("Found matches: " + count);	// print count
	}

	/* printFoundFreq(inputFreqBand, inputFreq)
	 * searches all stations and prints all frequencies that contain the user's
	 * input frequency and frequency band
	 */
	public void printFoundFreq(String inputFreqBand, String inputFreq) {
		int count = 0;
		if (inputFreqBand.equals("AM")) { 				// if AM, remove last 0
			inputFreq = inputFreq.substring(0, inputFreq.length() - 1);
			count = amTree.printFoundFreq(inputFreq);	// get num of matches
		} else { 										// if FM, remove dot "."
			inputFreq = inputFreq.replace(".", "");
			count = fmTree.printFoundFreq(inputFreq);	// get num of matches
		}
		System.out.println("Found matches: " + count);	// print matches found
	}
	
	/* printFoundGenre (input)
	 * searches all Stations and prints all genres that contain the user's
	 * input string
	 */
	public void printFoundGenre(String input) {
		input = input.toUpperCase();	// error checking setup
		
		// update count for found matches
		int count = amTree.printFoundGenre(input);	// check AM list
		count += fmTree.printFoundGenre(input);		// check FM list
		
		// print number of found matches
		System.out.println("Found matches: " + count);	
	}
}
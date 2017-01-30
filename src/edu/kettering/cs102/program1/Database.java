/* James Garza
 * CS-102
 * 17.01.2017
 * 
 * Database.java
 * this class defines a Database object which is used to store Stations
 * and allow the user to perform search and print methods on Stations.
 */

package edu.kettering.cs102.program1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.kettering.cs102.program2.Station;

public class Database {
	// define max number of stations within the database
	public static final int MAX_STATIONS = 10;
	int arrayIndex = 0;		// point to next empty array element
	Station[] database;		// an array that holds multiple Stations
	
	/* Constructor
	 * declare an empty array of Stations with MAX_STATIONS elements
	 */
	public Database() {
		database = new Station[MAX_STATIONS];
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

	/* addStation(newStation)
	 * add a Station object to the end of the array if able
	 */
	public void addStation (Station newStation) {
		try {	// will throw exception if database is full
			database[arrayIndex] = newStation;	// append Station to array
			arrayIndex++;	// increment total number of Stations
		} catch (IndexOutOfBoundsException error) {
			System.err.println("ERROR: Max number of station reached, "
								+ "unable to add another station.");
		}
	}
	
	/* printAll()
	 * loop through every Station in the database a print all info
	 */
	public void printAll() {
		System.out.println("All stations:");
		// loop for going thru all stations in database
		for (int index = 0; index < arrayIndex; index++)
			// print formatted string for each station
			System.out.println(database[index].getStation());
	}
	
	/* printByIndex(index)
	 * print Station at index of database array
	 */
	public void printByIndex(int index) {
		// print formatted string for each station
		System.out.println(database[index].getStation());
	}	
	
	/* printFoundCallSign(input)
	 * searches all stations and finds all call signs that match the user's
	 * input string
	 */
	public void printFoundCallSign(String input) {
		int counter = 0;	// used to count number of matches found
		input = input.toUpperCase();	// error checking setup
		
		// loop that checks all stations in database
		for (int index = 0; index < arrayIndex; index++)
			// if input matches a station's call sign
			if (input.equals(database[index].getCallSign().toUpperCase())) {
				// print formatted station data
				System.out.println(database[index].getStation());
				counter++;	// increment number of matches found
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
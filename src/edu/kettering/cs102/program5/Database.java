/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 21.03.2017
 * Program Assignment 5
 * Database.java
 * the 5th database class for the 5th assignment. This class manages adding, 
 * searching, sorting, and removing objects from two binary search trees
 */

package edu.kettering.cs102.program5;
import java.io.*;
import java.util.*;
import javax.swing.*;

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
			JOptionPane.showMessageDialog(null, "The file was not found, "
							+ " no files added to the database.\n",
							"File Not Found", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null,"Could not add Station since"
					   + " file contains non-integer data is the 3rd column.\n",
								"Invalid Input", JOptionPane.ERROR_MESSAGE);
			}
		}
		fileScanner.close();	// memory cleanup
	}
	
	/* addStationCmdLine ()
	 * user adds a Station object to the database via command line
	 */
	public void addStationFromUser (String input) {
		String inputData[] = input.split("/");	// divide user's input
		
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
			JOptionPane.showMessageDialog(null, "Could not add Station since "
					+ "input contains mismatched data formats between frequency"
					+ " and frequency_band.", "Invalid Input", 
					JOptionPane.ERROR_MESSAGE);
		} catch (ArrayIndexOutOfBoundsException error) { // invalid input
			JOptionPane.showMessageDialog(null, "Invalid input, could not add "
					+ "Station.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/* printToFile (userFile)
	 * calls binary tree method to write entire contents into userFileStr in
	 * pre-order to tree can later be recreated with the same structure
	 */
	public void printToFile(String userFileStr) {
		try {	// try to open and write to file
			PrintWriter writer = new PrintWriter(userFileStr);
			amTree.writeTreeToFile(writer);
			fmTree.writeTreeToFile(writer);
			writer.close();
		} catch (IOException error) {	// on failure, notify user
			JOptionPane.showMessageDialog(null, "Unable to write to output file"
					+ ".", "Write to File Failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/* buildDatabaseFromFile (userFileStr)
	 * checks if user's file exists, if it does then drops the current database
	 * and creates a new database based on the user's file
	 */
	public void loadFromFile (String userFileStr) {
		// declare file to check if it exists 
		File userFile = new File(userFileStr);
		// if file does not exists or is a folder, exit method
		if(!userFile.exists() || userFile.isDirectory()) {
			JOptionPane.showMessageDialog(null, "Unable to open file.", 
					"Fail Open Failed", JOptionPane.ERROR_MESSAGE);
			return;		// exit method
		}
		amTree.removeAll();		// remove all Station from AM tree
		fmTree.removeAll();		// remove all Station from FM tree
		
		// initialize database from file
		addStationsFromFile(userFileStr);
	}
	
	/* boolean removeStation(freqBand, callSign)
	 * determines which list should be accessed can calls a private method to
	 * actually remove the Station object if it exists, reutrn is Station was
	 * found
	 */
	public boolean removeStation(String freqBand, String callSign) {
		callSign = callSign.toUpperCase();	// error-checking set up
		try { // try to remove Station, if it doesn't exist, throws an exception
			if (freqBand.equals("AM"))	// if AM Station, search that list
				amTree.removeNode(callSign);
			else						// if FM Station, search that list
				fmTree.removeNode(callSign);
		} catch (NoSuchElementException error) { // if Station not found
			return false;		// Station not found
		}
		return true;		// Station found
	}
	
	/* String printAll()
	 * loop through every Station in the database a build a string with all
	 * Station info, then return that string
	 */
	public String printAll() {	// html tags for spaces within label text
		String strResults = "<html>AM stations:<br>";
		strResults += amTree.printInOrder();	// loop thru AM Stations
		strResults += "<br><br>FM stations:<br>";
		strResults += fmTree.printInOrder();	// loop thru FM Stations
		strResults += "</html>";
		return strResults;		// return all Stations
	}
	
	/* String searchCallSign (input)
	 * searches list for the call sign "input", if found it will save the 
	 * Station into a string and return the string
	 */
	public String searchCallSign(String input) {
		input = input.toUpperCase();	// error checking setup
		
		//search each tree for call sign matches
		String strResults = "<html>AM stations:<br>";
		strResults += amTree.searchCallSign(input);	// loop thru AM Stations
		strResults += "<br><br>FM stations:<br>";
		strResults += fmTree.searchCallSign(input);	// loop thru FM Stations
		strResults += "</html>";
		
		return strResults;		// return found Stations
	}

	/* String searchFreq(inputFreqBand, inputFreq)
	 * searches all stations and saves all the Station that has the user's
	 * input frequency and frequency band, then returns that string
	 */
	public String searchFreq(String inputFreqBand, String inputFreq) {
		String strResults = "<html>";
		if (inputFreqBand.equals("AM")) { 				// if AM, remove last 0
			inputFreq = inputFreq.substring(0, inputFreq.length() - 1);
			strResults += amTree.searchFreq(inputFreq);	// get num of matches
		} else { 										// if FM, remove dot "."
			inputFreq = inputFreq.replace(".", "");
			strResults += fmTree.searchFreq(inputFreq);	// get num of matches
		}
		strResults += "</html>";
		
		return strResults;		// return found Stations
	}
	
	/* String searchGenre (input)
	 * searches all Stations and saves all genres that contain the user's
	 * input in a string, then returns that string
	 */
	public String searchGenre(String input) {
		input = input.toUpperCase();	// error checking setup
		
		//search each tree for genre matches
		String strResults = "<html>AM stations:<br>";
		strResults += amTree.searchGenre(input);
		strResults += "<br><br>FM stations:<br>";
		strResults += fmTree.searchGenre(input);
		strResults += "</html>";
		
		return strResults;			// return found Stations
	}
}
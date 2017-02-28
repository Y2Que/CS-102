/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 14.02.2017
 * Program Assignment 3
 * Prog3.java
 * this program takes user's input file that contains stations, adds those
 * formatted stations into a database, and performs search and print functions
 * on the database
 */

package edu.kettering.cs102.program4;
import java.util.*;

public class Prog4 {
	final static int SEARCH_CALLSIGN = 1;	// define input command constants
	final static int SEARCH_FREQ = 2;
	final static int SEARCH_GENRE = 3;
	final static int PRINT_ALL = 4;
	final static int ADD_STATION = 5;
	final static int REMOVE_STATION = 6;
	final static int EXIT = 9;
	
	/* main (args[0])
	 * this subroutine takes the an input file (args[0]) from the user and 
	 * adds formatted stations in that file to a database. The user can then
	 * perform search, print, and additional add functions on the database.
	 */
	public static void main(String[] args) {
		Database myDatabase = new Database();	// declare and define a database
		String userFile = null;					// holds user input file name
		
		try {
			userFile = args[0];		// get input file name from user
			// add Stations from file to database
			myDatabase.addStationsFromFile(userFile);
		} catch (ArrayIndexOutOfBoundsException error) {	// if no file given
			System.err.print("No arguments given to program, no files "
					+ "currently in the database.\n");
		}

		Scanner inputScanner = new Scanner(System.in);	// read input from user
		boolean endProgram = false;	// used to determine when to exit program
		
		do { // main loop that asks for input and performs database functions
			System.out.print("+----------COMMANDS-----------+\n");
			System.out.print("| 1 - Search for a call sign  |\n");
			System.out.print("| 2 - Search for a frequency  |\n");
			System.out.print("| 3 - Search for a genre      |\n");
			System.out.print("| 4 - Print all records       |\n");
			System.out.print("| 5 - Add station             |\n");
			System.out.print("| 6 - Remove Station          |\n");
			System.out.print("| 9 - Exit program            |\n");
			System.out.print("+-----------------------------+\n");
			System.out.print("- Choose a command: ");

			while (!inputScanner.hasNextInt()) {   // if input is not an integer
					System.err.print("Invalid input. Please"
									  + " choose a number shown above.\n");
					System.out.print("- Choose a command: ");
					inputScanner.next();	// discard invalid value
			}

			switch (inputScanner.nextInt()) {	// choose action based on input
			case SEARCH_CALLSIGN:	// find station by call sign
				System.out.print("- Enter a call sign: ");
				myDatabase.printFoundCallSign(inputScanner.next());
				break;
			case SEARCH_FREQ:		// find station by frequency
				String userFreqBand, userFreq;
				do {	// ask user for freq band until "AM" or "FM" is entered
					System.out.print("- Enter a frequency band (AM or FM): ");
					userFreqBand = inputScanner.next().toUpperCase();
				} while (!userFreqBand.equals("AM") && 
						 !userFreqBand.equals("FM"));
				
				System.out.print("- Enter a frequency: ");
				while (!inputScanner.hasNextFloat()) {	// if input not numberic
					System.err.print("Invalid input. Please enter a number.\n");
					System.out.print("- Enter a frequency: ");
					inputScanner.next();		// discard invalid input
				}
				userFreq = inputScanner.next();	// store valid freq from user
				myDatabase.printFoundFreq(userFreqBand, userFreq);
				break;
			case SEARCH_GENRE:		// find Stations by genre
				System.out.print("- Enter a genre: ");
				myDatabase.printFoundGenre(inputScanner.next());
				break;
			case PRINT_ALL:			// print all Stations
				myDatabase.printAll();
				break;
			case ADD_STATION:		// add Station form command line
				myDatabase.addStationCmdLine();
				break;
			case REMOVE_STATION:
				do {  // ask user for freq band until "AM" or "FM" is entered
					System.out.print("- Enter a frequency band (AM or FM): ");
					userFreqBand = inputScanner.next().toUpperCase();
				} while (!userFreqBand.equals("AM") && 
						 !userFreqBand.equals("FM"));
				// ask user for call sign to remove
				System.out.print("- Enter a call sign: ");
				
				// get Station removed if it exists, null if it does not
				Station removedStation = myDatabase.removeStation(userFreqBand,
														inputScanner.next());
				break;
			case EXIT:				// exit program
				System.out.print("Thank you for your time. Goodbye.");
				endProgram = true;	// set the endProgam flag
				break;
			default:	// if integer entered that is not on command list
				System.err.println("Invalid input. "
									+ "Please choose a number shown below.");
				break;
			}
		} while (!endProgram);		// repeat until user enters Exit command
		inputScanner.close();		// memory clean up
	}
}
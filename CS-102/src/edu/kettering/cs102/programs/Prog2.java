/* James Garza
 * CS-102
 * 23.01.2017
 * 
 * Prog2.java
 * this program gets user's input identify a file that contains stations, adds those 
 * formatted stations into a database, and performs search and print functions on 
 * the database 
 */

package edu.kettering.cs102.programs;
import edu.kettering.cs102.classes.*;
import java.util.*;

public class Prog2 {
	
	/* main (inputFile)
	 * this subroutine takes the inputFile from the user and adds formatted 
	 * stations within that file to a database. The user can then perform search
	 * and print functions on the database.
	 */
	public static void main(String[] args) {
		
		Database myDatabase = new Database();	// declare and define a database
		String userFile = args[0];				// get input file name from user
		myDatabase.addStationsFromFile(userFile); // add stations to database

		Scanner inputScanner = new Scanner(System.in);	// read input from user
		boolean endProgram = false;	// used to determine when to exit program
		
		do { // main loop that asks for input and performs database functions
			System.out.print("+----------COMMANDS-----------+\n");
			System.out.print("| 1 - Search for a call sign  |\n");
			System.out.print("| 2 - Search for a frequency  |\n");
			System.out.print("| 3 - Search for a genre      |\n");
			System.out.print("| 4 - Print all records       |\n");
			System.out.print("| 9 - Exit program            |\n");
			System.out.print("+-----------------------------+\n");
			System.out.print("- Choose a command: ");

			while (!inputScanner.hasNextInt()) {   // if input is not an integer
					System.err.print("Invalid input. Please"
									  + " choose a number shown above.\n");
					System.out.print("- Choose a command: ");
					inputScanner.next();			// discard invalid value
			}

			switch (inputScanner.nextInt()) {	// choose action based on input
			case 1:								// find station by call sign
				System.out.print("- Enter a call sign: ");
				myDatabase.printFoundCallSign(inputScanner.next());
				break;
			case 2:								// find station by frequency
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
			case 3:					// find stations by genre
				System.out.print("- Enter a genre: ");
				myDatabase.printFoundGenre(inputScanner.next());
				break;
			case 4:					// print all stations
				myDatabase.printAll();
				break;
			case 9:					// exit program
				System.out.print("Thank you for your time. Goodbye.");
				endProgram = true;	// set the endProgam flag
				break;
			default:	// if integer entered that is not on command list
				System.err.println("Invalid input. "
									+ "Please choose a number shown below.");
				break;
			}
		} while (!endProgram);		// repeat until user enters command 9
		inputScanner.close();		// memory clean up
	}
}
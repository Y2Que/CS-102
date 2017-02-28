/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 17.01.2017
 * Program Assignment 2
 * Station.java
 * this class defines a Station object with call sign, frequency band, 
 * frequency, location, and genre attributes. 
 */

package edu.kettering.cs102.program4;

public class Station {
	String callSign;	// call sign of station
	String freqBand;	// either AM or FM band
	String location;	// home of the station
	String genre;		// format of the station
	int freq;			/* AM frequencies are divided by 10
						   FM frequencies are multiplied by 10 */
	/* Constructor
	 * required parameters to create a Station instance: callSign is the 
	 * abbreviation of the station, freqBand is either AM or FM, freq is the 
	 * numeric frequency, location is the home of the station, and genre is what
	 * you can hear about on the Station
	 */	
	public Station (String callSign, String freqBand, int freq, 
					String location, String genre) {
		this.callSign = callSign;
		this.freqBand = freqBand;
		this.freq = freq;
		this.location = location;
		this.genre = genre;
	}

	/* Getter Methods
	 * returns the instance variable values of the object
	 */
	public String getCallSign() { return callSign;	}
	public String getFreqBand() { return freqBand;	}
	public int getFreq() 		{ return freq; 		}
	public String getLocation() { return location;	}
	public String getGenre() 	{ return genre;		}
	
	/* Setter Methods
	 * changes the values of internal variables
	 */
	public void setCallSign(String input)	{ callSign = input;	}
	public void setFreqBand(String input)	{ freqBand = input;	}
	public void setFreq(int input)			{ freq     = input;	}
	public void setLocation(String input)	{ location = input;	}
	public void setGenre(String input)		{ genre    = input;	}
	
	/* Formatted Getter Method
	 * properly formats freq and appends freqBand into a string
	 */
	public String getFormattedFreq() {
		String formattedFreq;				// holds the formatted string
		if (freqBand.equals("AM"))			// if AM, simply append suffix
			formattedFreq = freq + "0 AM";	
		else							// if PM, calculate freq and add suffix
			formattedFreq = String.format("%.1f", (float) freq/10) + " FM";
		return formattedFreq;				// return formatted string
	}
	
	/* Formatted Getter Method
	 * returns all info about the station in a formatted string
	 */
	public String getStation() {
		return callSign + ", " + getFormattedFreq() + ", "
			    + location + ": " + genre;
	}
}
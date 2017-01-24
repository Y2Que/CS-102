/* James Garza
 * CS-102
 * 23.01.2017
 * 
 * Station.java
 * this class defines a Station object with call sign, frequency band, frequency, 
 * location, and genre attributes. 
 */

package edu.kettering.cs102.classes;

public class LinkedList {
	Node head, tail;
	
	/* Constructor
	 * required parameters to create a Station instance: callSign is the 
	 * abbreviation of the station, freqBand is either AM or FM, freq is the 
	 * numeric frequency, location is the home of the station, and genre is what
	 * you can hear about on the Station
	 */	
	public LinkedList () {
		head = null;
		tail = null;
	}

	/* Getter Methods
	 * returns the instance variable values of the object
	 */
	public Node getHead() { return head;	}
	public Node getTail() { return tail;	}
	
	/* Setter Methods
	 * changes the values of internal variables
	 */
	public void setHead(Node input) { head = input;	}
	public void setTail(Node input) { tail = input;	}
	
	/*
	 * 
	 * 
	 */
	public void addStation(Station newStation) {
		Node current = head;						// walk through nodes
		String callSign = newStation.getCallSign();	// get current callSign
		// when negative, current node passed the correct lexicographic spot 
		while (current.getStation().getCallSign().compareTo(callSign) < 0 && 
			   current != null)
			current = current.getNext();
		if (current == null) {
			head = new Node(null, null, newStation);
			tail = head;
		} else {
			
		}
		
	}
}
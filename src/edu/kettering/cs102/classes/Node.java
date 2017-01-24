/* James Garza
 * CS-102
 * 23.01.2017
 * 
 * Node.java
 * this class defines a Node object with two reference variables called next
 * and previous, and the datum variable station.
 */

package edu.kettering.cs102.classes;

public class Node {
	Node previous, next;	// references to indicate next and previous node
	Station station;		// holds the datum, the station
	
	/* Constructor
	 * required data to create new node is the previous node, the next node,
	 * and the station to be held within the node
	 */	
	public Node (Node previous, Node next, Station station) { 
		this.previous = previous;
		this.next = next;
		this.station = station;
	}

	/* Getter Methods
	 * returns the instance variable values of the object
	 */
	public Node getNext() 		{ return next;		}
	public Node getPrevious()	{ return previous;	}
	public Station getStation()	{ return station;	}
	
	/* Setter Methods
	 * changes the values of internal variables
	 */
	public void setPrevious(Node input)		{ previous	= input;}
	public void setNext(Node input)			{ next		= input; }
	public void setStation(Station input)	{ station	= input; }
	
	/* printStation ()
	 * returns all info about the station in a formatted string
	 */
	public String getStationInfo() {
		return station.getStation();
	}
}
/* James Garza
 * CS-102
 * 23.01.2017
 * 
 * LinkedList.java
 * this class defines a Station object with call sign, frequency band,
 * frequency, location, and genre attributes
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
	
	/* addStation(Station newStation)
	 * adds a new node to the linked list and maintains the lexicographic sort
	 * by call sign of the station
	 */
	public void addNode(Station newStation) {
		Node newNode = new Node(null, null, newStation); // create a new node
		if (head == null) {		// if empty list
			head = newNode;
			tail = head;
		} else {				// if not empty list
			Node current = head;		// variable to walk through nodes
			String callSign = newStation.getCallSign();	// get current callSign
			// when negative, current node passed the correct lexicographic spot 
			while (current.getStation().getCallSign().compareTo(callSign) < 0 && 
					current != null) {
				current = current.getNext();	// step to next item in list
			}
			// if newStation a duplicate, do not add
			if (current.getStation().getCallSign().compareTo(callSign) == 0) {
				System.err.print("A station will that call sign already "
						+ "exists. Please enter another station.");
			} else if (current == null) {	// reached the end of the list
				tail.setNext(newNode);		// link tail to new last element
				newNode.setPrevious(tail);	// link last element to old tail
				tail = newNode;				// redefine last node
			} else {				// new node is not last element
				// link previous node to newNode
				current.getPrevious().setNext(newNode); 
				current.setPrevious(newNode);	// link current node to newNode
			}
		}
	}
	
	/* removeNode(callSign)
	 * search for station by call sign, remove it from list, and return it
	 */
	public Node removeNode(String callSign) {
		Node removedNode = head; 	// finds node to be removed
		// look for desired node by call sign, move next until end of list
		while (!removedNode.getStation().getCallSign().equals(callSign) && 
				removedNode != null)
			removedNode = removedNode.getNext();	// move to next node
		
		if (removedNode == null)	// if end of list reached
			System.err.print("A station with" + callSign + "does not exist.");
		else {		// if desired node found, remove it
			// link removedNode's previous and next node to each other
			removedNode.getPrevious().setNext(removedNode.getNext());
			removedNode.getNext().setPrevious(removedNode.getPrevious());
		}
		return removedNode;
	}
}
/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 28.01.2017
 * Program Assignment 2
 * LinkedList.java
 * this class defines a doubly linked list that holds Node objects
 */

package edu.kettering.cs102.program2;

public class LinkedList {
	Node head, tail;		// references to the beginning and end of the list
	
	/* Constructor
	 * initialize an empty doubly linked list
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
	
	/* addStation (newStation)
	 * adds a new node to the AM or FM linked list and maintains the 
	 * lexicographic sort by call sign 
	 */
	public void addNode(Station newStation) {
		Node newNode = new Node(null, null, newStation); // create a new node
		if (head == null) {		// if empty list
			head = newNode;		// newNode is new head
			tail = head;		// newNode is also new tail
		} else {		// if not empty list
			Node current = head;	// variable to walk through nodes
			String callSign = newStation.getCallSign();	// get current callSign
			// when compareTo value is negative, 
			// current node has passed the correct lexicographic spot
			while (current != null &&
			  current.getStation().getCallSign().compareTo(callSign) < 0) {
				current = current.getNext();	// step to next item in list
			}
			if (current == null) {	// reached the end of the list, append
				tail.setNext(newNode);		// link tail to new last element
				newNode.setPrevious(tail);	// link last element to old tail
				tail = newNode;				// redefine last node
			} else if (current.getStation().getCallSign() // if dup, don't add
												.compareTo(callSign) == 0) {
				System.err.print("A station with that call sign already "
						+ "exists. Unable to add station:\n"
						+ newStation.getStation() + "\n");
			} else if (current == head) { // adding to front of list
				head.setPrevious(newNode);
				newNode.setNext(head);
				head = newNode;
			} else {	// newNode is not first or last node
				// link newNode to previous node
				newNode.setPrevious(current.getPrevious());
				// link previous node to newNode
				current.getPrevious().setNext(newNode);
				// link newNode to current node
				newNode.setNext(current);
				// link current node to newNode
				current.setPrevious(newNode);
			}
		}
	}
	
	/* removedNode removeNode(callSign)
	 * search for station by callSign, remove from list, and return removedNode
	 */
	public Node removeNode(String callSign) {
		Node removedNode = head; 	// finds node to be removed
		// look for desired node by call sign, move next until end of list
		while (!removedNode.getStation().getCallSign().equals(callSign) && 
													removedNode != null)
			removedNode = removedNode.getNext();	// move to next node
		
		if (removedNode == null)	// if end of list reached
			System.err.print("The station '" + callSign + "' does not exist.");
		else {		// if desired node found, remove it
			// link removedNode's previous and next node to each other
			removedNode.getPrevious().setNext(removedNode.getNext());
			removedNode.getNext().setPrevious(removedNode.getPrevious());
		}
		return removedNode;		// return removed node
	}
}
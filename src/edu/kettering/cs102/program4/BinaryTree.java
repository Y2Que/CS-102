/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 25.02.2017
 * Program Assignment 4
 * BinaryTree.java
 * This class defines a private class called TreeNode and manages adding, 
 * removing, and searching of TreeNodes from a binary search tree.
 */

package edu.kettering.cs102.program4;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class BinaryTree {
	
	/* TreeNode class
	 * this class defines a TreeNode object in a binary tree with a left child,
	 * a right child, and a Station as the datum
	 */
	private class TreeNode {
		TreeNode left, right;	// references to left and right child nodes
		Station station;	// holds the datum, the station
		
		/* Constructor
		 * required data to create new node is the station to be held within 
		 * the node
		 */	
		public TreeNode (Station station) { 
			left = null;
			right = null;
			this.station = station;
		}

		/* Getter Methods
		 * returns the instance variable values of the object
		 */
		public TreeNode getLeft() 		{ return left;		}
		public TreeNode getRight()		{ return right;		}
		public Station getStation()		{ return station;	}
		public String getStationInfo()	{ return station.getStation(); }
		
		/* Setter Methods
		 * changes the values of internal variables
		 */
		public void setLeft(TreeNode input)		{ left		= input; }
		public void setRight(TreeNode input)	{ right		= input; }
		public void setStation(Station input)	{ station	= input; }
	}
	
	TreeNode root;	// indicates the root of the binary tree
	
	/* Constructor
	 * creates an empty binary tree
	 */	
	public BinaryTree () {
		root = null;
	}
	
	/* Getter Methods
	 * returns the instance variable values of the object
	 */
	public TreeNode getRoot() 		{ return root; }
	
	/* Setter Methods
	 * changes the values of internal variables
	 */
	public void setRoot(TreeNode input)		{ root = input; }
	
	/* addNode (newStation)
	 * calls private method to add newStation to the binary tree and maintains
	 * the structure of the tree
	 *
	 * Node addNode (newStation)
	 * recursively adds newStation to the binary tree and maintains structure
	 * of the tree. If newStation has duplicate callSign, an exception is thrown
	 */
	public void addNode(Station newStation) {
		root = addNode (newStation, root);
	}
	private TreeNode addNode (Station newStation, TreeNode current) {
		
		// if at the edge of the tree
		if (current == null) {
			TreeNode leaf = new TreeNode(newStation);
			return leaf;
		}
		// value to determine lexicographical order of the callSigns
		int lexValue = newStation.getCallSign()
								 .compareTo(current.getStation().getCallSign());
		// when value is negative, newStation is smaller than the current
		if (lexValue < 0)
			current.setLeft(addNode(newStation, current.getLeft()));
		// when value is positive, newStation is larger than the current
		if (0 < lexValue)
			current.setRight(addNode(newStation, current.getRight()));
		// if newStation has a duplicate callSign, throw exception
		if (lexValue == 0)
			throw new RuntimeException("DuplicateElementException");
		// re-attach sub-tree to main binary tree
		return current;
	}
	
	/* removeNode (target)
	 * call private method to remove target from the binary tree and maintain 
	 * the binary tree structure
	 *
	 * TreeNode removeNode (target, current)
	 * recursively removes target from the binary tree and maintains the binary
	 * tree structure. If target does not exist, throws an exception
	 */
	public void removeNode(String target) {
		root = removeNode(target, root);
	}
	private TreeNode removeNode(String target, TreeNode current) {
		// if we fell off the tree
		if (current == null)
			throw new NoSuchElementException();
		// value to determine lexicographical order of the callSigns
		int lexValue = target.compareTo(current.getStation().getCallSign());
		// when value is negative, target is smaller than the current
		if (lexValue < 0) {
			current.setLeft(removeNode(target, current.getLeft()));
			return current;
		}
		// when value is positive, target is larger than the current
		if (0 < lexValue) {
			current.setRight(removeNode(target, current.getRight()));
			return current;
		}
		/* if target is found and has only 1 child, simply replace target
		 * with non-empty child */
		if (current.getLeft() == null)  { return current.getRight(); }
		if (current.getRight() == null) { return current.getLeft();  }
		
		/* if target is found and has 2 children, need an heir, a replacement 
		 * node that preserves the binary tree structure */
		TreeNode heir = current.getLeft();	// arbitrarily assume left is heir
		// replace heir with the largest value on the left sub-tree
		while (heir.getRight() != null)
			heir = heir.getRight();
		// replace current with new heir
		current.setStation(heir.getStation());
		
		// remove duplicate heir (remove the leaf)
		current.setLeft(removeNode(heir.getStation().getCallSign(),
															current.getLeft()));
		// re-attach sub-tree to main tree
		return current;
	}
	
	/* removeAll ()
	 * remove all nodes from the tree
	 */
	public void removeAll() {
		root = null;			// drop entire tree
	}
	
	/* int printInOrder ()
	 * calls a private method to print and returns number of records
	 * 
	 * int printInOrder (current)
	 * recursively prints all records and returns the number of records found
	 * in the sub-tree where current is the root; returns number of records
	 */
	public int printInOrder() {
		int count = printInOrder(root);
		return count;
	}
	private int printInOrder (TreeNode current) {
		int count = 0;			// stores number of elements in the sub-tree
		if (current == null) return 0;					// if null, no count++
		else count++;									// +1 elements found
		count += printInOrder(current.getLeft());		// print nodes smaller
		System.out.println(current.getStationInfo());	// print current node
		count += printInOrder(current.getRight());		// print nodes larger
		return count;								// return printed elements
	}
	
	/* int printFoundCallSign (target)
	 * calls a private method to search for target callSign and return number 
	 * of records found
	 * 
	 * int printFoundCallSign (target, current)
	 * recursively searches for target, prints Station if target is found, and 
	 * returns the number of records found
	 */
	public int printFoundCallSign (String target) {
		int count = printFoundCallSign(target, root);
		return count;
	}
	private int printFoundCallSign (String target, TreeNode current) {
		int count = 0;						// holds number of records found
		if (current == null) return 0;		// if we fell off the tree
		// value to determine lexicographical order of the callSigns
		int lexValue = target.compareTo(current.getStation().getCallSign());
		// if target is found, print and increment count
		if (lexValue == 0) {
			System.out.println(current.getStationInfo());
			count++;
		}
		// when value is negative, target is smaller than the current
		else if (lexValue < 0)
			return printFoundCallSign(target, current.getLeft());
		// when value is positive, target is larger than the current
		else if (0 < lexValue)
			return printFoundCallSign(target, current.getRight());
		// return number of records found
		return count;
	}
	
	/* int printFoundFreq (target)
	 * calls a private method to search for target frequency and return number 
	 * of records found
	 * 
	 * int printFoundFreq (target, current)
	 * recursively searches for target, prints Station if target is found, and 
	 * returns the number of records found
	 */
	public int printFoundFreq (String targetStr) {
		int count = 0;	// holds number of found records
		try {	// attempt to parse String into integer
			int target = Integer.parseInt(targetStr);
			count = printFoundFreq(target, root);
		} catch (NumberFormatException error) {	// if invalid input is entered
			System.err.print("ERROR: Invalid input.\n");
		}
		return count;	// return number of found records
	}
	private int printFoundFreq (int target, TreeNode current) {
		int count = 0;
		if (current == null) return 0;		// if we fell off the tree
		// store current frequency to compare against target
		int currentFreq = current.getStation().getFreq();
		// if target is found, print and return
		if (target == currentFreq) {
			System.out.println(current.getStationInfo());
			count++;
		}
		// search smaller and larger nodes
		count += printFoundFreq(target, current.getLeft());	 // search smaller
		count += printFoundFreq(target, current.getRight()); // search larger
		return count;				// return number of records found
	}
	
	/* int printFoundGenre (target)
	 * calls a private method to search for target genre and return number 
	 * of records found
	 * 
	 * int printFoundGenre (target, current)
	 * recursively searches all Stations, prints all genres that contain the
	 * user's input string, and returns the number of records found
	 */
	public int printFoundGenre (String target) {
		int count = printFoundGenre(target, root);
		return count;	// return number of found records
	}
	private int printFoundGenre (String target, TreeNode current) {
		int count = 0;						// store number of found records
		if (current == null) return 0;		// if we fell off the tree
		
		// value to determine if target is contained within current's genre
		boolean targetFound = current.getStation().getGenre().toUpperCase()
															 .contains(target);
		// if target is found, print and increment count
		if (targetFound) {
			System.out.println(current.getStationInfo());
			count++;
		}
		// search smaller and larger nodes
		count += printFoundGenre(target, current.getLeft());  // search smaller
		count += printFoundGenre(target, current.getRight()); // search larger
		return count;				// return number of records found
	}
	
	/* writeTreeToFile (writer)
	 * calls a private method to write all records to writer
	 * 
	 * writeTreeToFile (writer, current)
	 * write all records to writer in pre-order format 
	 */
	public void writeTreeToFile(PrintWriter writer) {
		writeTreeToFile(writer, root);
	}
	public void writeTreeToFile(PrintWriter writer, TreeNode current) {
		if (current == null) return;			// if null, exit method
		// print formatted output to file
		writer.println(current.getStation().getStationToFile());
		
		// write all nodes smaller and larger than current to file	
		writeTreeToFile(writer, current.getLeft());  // write smaller
		writeTreeToFile(writer, current.getRight()); // write larger
	}
}
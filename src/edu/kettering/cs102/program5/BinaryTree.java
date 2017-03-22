/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 21.03.2017
 * Program Assignment 5
 * BinaryTree.java
 * This class defines a private class called TreeNode and manages adding, 
 * removing, and searching of TreeNodes from a binary search tree.
 */

package edu.kettering.cs102.program5;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

import javax.swing.*;

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
	
	/* String printInOrder ()
	 * calls a private method to build string of records and returns the string
	 * 
	 * String printInOrder (current)
	 * recursively builds all records into a String and returns the string
	 */
	public String printInOrder() {
		String strResults = printInOrder(root, "");		// call recursive method
		return strResults;		// return all records
	}
	private String printInOrder (TreeNode current, String strResults) {
		if (current == null) return "";		// if null, exit method
		// print nodes smaller
		strResults = printInOrder(current.getLeft(), strResults);	
		// save current node
		strResults += "<br>" + current.getStationInfo();
		// print nodes larger
		strResults += printInOrder(current.getRight(), strResults);
		return strResults;		// return modified string
	}
	
	/* String searchCallSign (target)
	 * calls a private method to search for target callSign and return records 
	 * found
	 * 
	 * String searchCallSign (target, current)
	 * recursively searches for target, adds Station to string if target is 
	 * found, then returns the string
	 */
	public String searchCallSign (String target) {
		String strResults = searchCallSign(target, root);//call recursive method
		return strResults;		// return all records
	}
	private String searchCallSign (String target, TreeNode current) {
		if (current == null) return "";		// if we fell off the tree
		
		String strResults = "";		// holds the Station info if a match
		
		// value to determine lexicographical order of the callSigns
		int lexValue = target.compareTo(current.getStation().getCallSign());
		// if target is found, save record
		if (lexValue == 0)
			strResults += "<br>" + current.getStationInfo();
		// when value is negative, target is smaller than the current
		else if (lexValue < 0)
			return searchCallSign(target, current.getLeft());
		// when value is positive, target is larger than the current
		else if (0 < lexValue)
			return searchCallSign(target, current.getRight());
		// return String of of records found
		return strResults;
	}
	
	/* String searchFreq (target)
	 * calls a private method to search for target frequency and returns records
	 * found
	 * 
	 * String searchFreq (target, current)
	 * recursively searches for target, save Station to string if target is 
	 * found, and returns the records found
	 */
	public String searchFreq (String targetStr) {
		String strResults = "";
		try {	// attempt to parse String into integer
			int target = Integer.parseInt(targetStr);
			strResults += searchFreq(target, root);		// call recursive method
		} catch (NumberFormatException error) {	// if invalid input is entered
			JOptionPane.showMessageDialog(null, "Unable to search by frequency."
								, "Invalid Input", JOptionPane.ERROR_MESSAGE);
		}
		return strResults;	// return records found
	}
	private String searchFreq (int target, TreeNode current) {
		if (current == null) return "";		// if we fell off the tree
		
		String strResults = "";		// holds the Station info if a match

		// if target is found, print and return
		if (target == current.getStation().getFreq()) {
			strResults += current.getStationInfo();
		}
		// search smaller and larger nodes
		strResults += searchFreq(target, current.getLeft());  // search smaller
		strResults += searchFreq(target, current.getRight()); // search larger
		return strResults;	// return records found
	}
	
	/* String searchGenre (target)
	 * calls a private method to search for target genre and return records 
	 * found
	 * 
	 * String searchGenre (target, current)
	 * recursively searches all Stations, save all Stations that contain the
	 * user's input string in their genre, and returns the records found
	 */
	public String searchGenre (String target) {
		String strResults = searchGenre(target, root);	// call recursive method
		return strResults;	// return number of found records
	}
	private String searchGenre (String target, TreeNode current) {
		if (current == null) return "";		// if we fell off the tree
		
		String strResults = "";		// holds the Station info if a match

		// if target is found, print and increment count
		if (current.getStation().getGenre().toUpperCase().contains(target)) {
			strResults = "<br>" + current.getStationInfo();
		}
		// search smaller and larger nodes
		strResults += searchGenre(target, current.getLeft());  // search smaller
		strResults += searchGenre(target, current.getRight()); // search larger
		return strResults;		// return records found
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
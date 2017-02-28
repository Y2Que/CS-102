/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 25.02.2017
 * Program Assignment 4
 * BinaryTree.java
 * This class defines a private class called TreeNode and manages adding and 
 * removing of TreeNodes from a binary tree.
 */

package edu.kettering.cs102.program4;
import java.util.NoSuchElementException;

public class BinaryTree {
	
	/* private Node class
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
		public TreeNode getLeft() 	{ return left;		}
		public TreeNode getRight()	{ return right;		}
		public Station getStation()	{ return station;	}
		
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
			return addNode (newStation, current.getLeft());
		// when value is positive, newStation is larger than the current
		if (lexValue > 0)
			return addNode (newStation, current.getRight());
		// if newStation has a duplicate callSign, throw exception
		if (lexValue == 0)
			throw new RuntimeException("DuplicateElementException");
		// re-attach sub-tree to main binary tree
		return current;
	}
	
	/* removeNode(target)
	 * call private method to remove target from the binary tree and maintain 
	 * the binary tree structure
	 *
	 * Node removeNode(target, current)
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
		if (lexValue > 0) {
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
	
	/*
	 * 
	 */
	public int printInOrder () {
		int count = 0;
		count = printInOrder(root);
		return count;
	}
	private int printInOrder (TreeNode current) {
		int count = 0;
		if (current == null) return 0;				// if null 
		count++;									// increment elements found
		count += printInOrder(current.getLeft());	// print all nodes smaller
		System.out.print(current.getStation());		// print current node
		count += printInOrder(current.getRight());	// print all nodes larger
		return count;
	}
	private boolean search (Station target, TreeNode current) {
		if (current == null)
			return false;
		if (current.getStation().equals(target))
			return search(target, current.getRight());
		else 
			return search(target, current.getLeft());
	}
}
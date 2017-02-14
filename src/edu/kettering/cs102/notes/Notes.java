/* James A Garza Jr
 * CS-102
 * 
 */

package edu.kettering.cs102.notes;

public class Notes {

	// 27.01.2017
	public Notes() {
		// CS-102 notes
	}
	
	public boolean binSearch(int[] data, int start, int end, int target) {
		if (start > end) return false;
		//int mid = (start + end) / 2; 	// max size array overflows to neg num
		int mid = start + (end - start) / 2;		// same as (start + end) / 2
		if (data[mid] == target) return true;
		if (data[mid] < target)
			return binSearch(data, mid + 1, end, target);
		else
			return binSearch(data, start, mid - 1, target);
	}
	
	/* Tail Recursion
	 * anything that uses tail recursion can be written as a loop
	 * this is binSearch written in a loop
	 */
	public boolean binSearchLoop(int[] data, int start, int end, int target) {
		while (start <= end ) {
			int mid = (start + end) / 2;
			if (data[mid] == target) return true;
			if (data[mid] < target)
				start = mid - 1;
			else
				end = mid - 1;
		}
		return false;
	}
	
	/* Towers of Hanoi
	 * monks play this game in Hanoi, but the 64 disk version and when they
	 * finish, the end of the world happens! We think..
	 * this is the disk game when you can't move a bigger disk onto a smaller
	 * disk and the must transfer all disks to the opposite pole
	 *       ||         ||         ||
	 *     /_||_\       ||         ||
	 *    /__||__\      ||         ||
	 *   /___||___\     ||         ||
	 */
	
	/* Iterative Algorithm:
	 * On ODD moves, move the smallest disk to the tower least recently visited
	 * On EVEN moves, make only other move possible
	 */
	
	/* hanoi (Tower source Tower target, Tower aux, int num)
	 * source is where we want to move disks to, source is where they start,
	 * aux is the mid tower, num is the number of disks
	 * requires 2^(n) - 1 moves
	 * monks, with 64 disks,  assuming they can move a disk a second,
	 * will take over 50 billion years.
	 */
	/*
	public void hanoi (Tower source Tower target, Tower aux, int num) {
		if (num == 0) return;					// if no disks, exit sub
		hanoi(source, aux, target, num - 1);	// move all top disks on aux
		target.add(source.remove());			// move biggest disk to target
		hanoi(aux, target, source, num - 1);	// move all top disks to target
	}
	*/
}

/*  >>>> Recursion and LinkedLists <<<<
 * A list may be define recursively as:
 * - the empty list
 * - a value followed by a list
 * 
 * 
 */
class LinkedList implements ListInterface {
	Node head;
	public LinkedList() { head = null; }
	public boolean isEmpty() { return (head == null); }
	
	public int size () {
		return size(head);
	}
	
	private int size(Node current) {
		if (current == null) return 0;
		return 1 + size(current.getNext());
	}
	
	public MyObject get(int index) {
		if ((index < 0) || (index >= size()))
			throw new IndexOutOfBoundsException();
		return get(index, head);
	}
	
	private MyObject get(int index, Node current) {
		if (index == 0)		// base case
			return current.getDatum();
		return get(index - 1, current.getNext());	// recursive call
	}
	
	public void add(int index, MyObject datum) {
		head = add(index, datum, head);
	}
	
	/* recursive is much less code, elegant, "beautiful code"
	 * only the base case is where we actually add, all others are calls
	 */
	private Node add(int index, MyObject datum, Node current) {
		if (index == 0) {
			Node splice = new Node();
			splice.setDatum(datum);
			splice.setNext(current);	// create new head of list
			return splice;				// return new head
		}
		if (current == null) { 			// fell off the list
			throw new IndexOutOfBoundsException();
		}
		current.setNext(add(index-1, datum, current.getNext()));
		return current;			// head hasn't changed
	}
	
	public MyObject remove(int index) {
		MyObject ofTheKing = get(index);	// get datum
		head = remove (index, head);		// define head?
		return ofTheKing;					// return datum
	}
	
	/* less lines of code than loops
	 * 
	 */
	private Node remove (int index, Node current) {
		if (current == null)
			throw new IndexOutOfBoundsException();
		if (index == 0) {
			return current.getNext();
		}
		current.setNext(remove(index-1, current.getNext()));
		return current;
	}
	
	public void removeAll() {
		head = null;
	}
}


/* ---Stacks
 * a stack is a list in which all add and remove operations occur on one end
 * of the list
 * Examples: stack of paper, method call stack, cafeteria trays, post script
 * Operations: push(), pop(), peek()
 * 
 * ---Implementations
 * 1) Linked list, head is top of stack
 * 2) Array, end of array is top of stack
 * > Which is better? Depends on application, usage. Arrays have a max size
 * but Linked Lists hace overhead of memory access time
 * 
 * Applications
 * 1) Parenthesis Checker: [3+(5*4)]-(6*8
 * 		while (input.hasNext()) {
 * 			token = input.next();
 * 			if (token.isLeft())
 * 				stack.push(token);
 * 			else if (token.isRight())
 * 				match(token, stack.pop());
 * 		}
 * 		return stack.isEmpty();
 * 2) RPN (Reverse Polish Notation)
 * 	3 + 4 * 5 = 2 4 5 * +
 * (3 + 4)* 5 = 3 4 + 5 *
 * 		while (input.hasNext()) {
 * 			token = input.getNext();
 * 			if (token.isValue())
 * 				stack.push(token);
 * 			else if (token.isOp())
 * 				stack.push(apply(token,stack.pop(),stack()));
 * 		}
 * Polish Notation: 23 = + * 4 5 3
 *                       +(*(4,5),3);
 *                    add(multiply(4,5),3);		// same as method syntax!
 */

// 03.02.2017

/* Queues
 * a list in which add & remove operations occur at opposite ends of the list
 * ex - standing in line
 * 
 * Implementations
 * 1) Linked List
 *    - end reference is helpful here
 * 2) Arrays
 *    - manipulating end of array is easy, front of array is hard
 *    - can just move front of array pointer instead of rolling all elements for
 *      manipulating the front of the array
 *    - can think about an array as circular
 *    	i.e 
 *    		front = (front + 1) % 13;	// assuming 13 array slots
 *    		back = (back + 1 % 13;
 */

// END OF MIDTERM MATERIAL
/******************************************************************************/

/* Generics
 * LinkedList<T>
 * 		LinkedList <Station> mList = ...;
 * 		LinkedList <Apple> basket = ...;
 */

/* Collections - a Java object that groups multiple items together
 * Collection Framework - a unified architecture for representing and 
 * 						  maintaining collections
 * Parts:
 * 	- interfaces
 *  - implementation
 *  - utilities
 *
 * Collections
 * - set		// unordered collection of items that forbids duplicates
 * - list		// ordered collection of items that allows dups, recognizable
 * - queue
 * - map		// collection of key value pairs, maintenance done with key
 * 
 * Collections:
 * - int size();
 * - boolean isEmpty();
 * - boolean contains(Object item);
 * > boolean add(Object item);
 * > boolean remove(Object item);
 * - boolean containsAll(Collection other);
 * > boolean addAll(Collection);
 * > boolean removeAll(Collection other);
 * > boolean retainAll(Collection other);
 * > void clear();
 * - Object[] toArray();
 */

/* Enhanced-For Loop ("For-Each Loop")
 * To print all items in a list:
 * - Typical for loop:
 *		for (int pos=0; pos<myLisy.size(); pos++)
 * 			System.out.println(myList.get(pos));
 * - Enhanced for loop:
 * 		for (Object item: myList) {
 * 			System.out.println(item);
 * 		}
 * - do not alter the list while in the enhanced for loop, bad things happen
 * - arrays support this syntax
 */

/* Iterator
 * Every Collection object has a method "iterator()" which returns a new object
 * from this interface:
 * 		public interface Iterator<T> {
 * 			boolean hasNext();
 * 			T next();
 * 			void remove();	// removes object that hasNext grabbed
 * 		} 
 * if you're in a Iterator and you change the type, discard the Iterator
 * and get another one
 */

/* List
 * - T get(int index);
 * > T set(int index, T item);
 * > void add(int index, T item);
 * > T remove(int inedx);
 * > boolean addAll(int index, Collection other);
 * - int inedxOf(Object target);
 * - int LastIndexOf(Object target);
 */

/* ListIterator()
 * - boolean hasNext();
 * - T next();
 * - boolean hasPrevious();
 * - T previous();
 * - int nextInt();
 * - int previousIndex();
 * - void remove();
 * - void set(T item);
 * - void add(T item);
 * don't modify 2 things simultaneously or bad things happen
 */

/* Implementations
 * - java.util.ArrayList
 * - java.util.LinkedList
 */

/* Debugging
 * - Add code to help you debug (add utility routines)
 * 		~ print methods
 * 		~ top-down design with stubs
 * 		~ bottom-up design with drivers
 * - Plan your test data
 * 		~ check boundry conditions
 * 		~ try "white box" (code coverage) testing
 * 		~ the "cat on the keyboard"
 */

// 14.02.2017
/* Binary Search Trees                                 o      - root
 * - a tree may be define recursively as:            /   \
 *   - the empty tree                               o     o   - (parent)
 *   - the node attached to one or more trees i.e. / \   / \
 * ~ a node may have many children, but AT MOST   o   o o   o - leaves (child)
 *   one parent
 * = a binary tree is a tree where every node has (at most/exactly) 2 children
 * = a binary search tree is a binary tree in which the following properties
 *   hold for all nodes:
 *   - values stores in the left subtree of a node are less than the value
 *     in the node
 *   - values stores in the right subtree of a node are greater than the value
 *     in the node                            (57)
 *                                           /    \
 *                                        (25)    (75)
 *                                       /   \    /   \
 *                                    (12)  (37)(62)  (87)
 *                                       
 */

class TreeNode<T> {
	T datum;
	TreeNode<T> left;
	TreeNode<T> right;
	
	// setter methods
	public void setDatum (T input)           { datum = input; }
	public void setLeft  (TreeNode<T> input) { left  = input; }
	public void setRight (TreeNode<T> input) { right = input; }
	
	// getter methods
	public T getDatum()           { return datum; }
	public TreeNode<T> getLeft()  { return left;  }
	public TreeNode<T> getRight() { return right; }
}

// generic types have ugly syntax
class Tree<T extends Comparable<? super T>> {
	
	TreeNode<T> root;
	public Tree() { root = null; }
	
	public boolean search (T target) {
		TreeNode<T> current = root;
		while (current != null) {
			if (current.getDatum().equals(target))
				return true;
			if (current.getDatum().compareTo(target) < 0)
				current = current.getRight();
			else current = current.getLeft();
		}
		return false;
	}
	
	public boolean searchRecursive (T target) {
		return search(target, root);
	}
	private boolean search (T target, TreeNode<T> current) {
		if (current == null)
			return false;
		if (current.getDatum().equals(target))
			return search(target, current.getRight());
		else 
			return search(target, current.getLeft());
	}
	
	// add duplicates to left node
	public void add (T target) {
		TreeNode<T> current = root;
		TreeNode<T> prev = null;
		
		while (current != null) {
			prev = current;
			if (current.getDatum().compareTo(target) < 0)
				current = current.getRight();
			else
				current = current.getLeft();
		}
		
		// create new leaf
		TreeNode<T> leaf = new TreeNode<T>();
		leaf.setDatum(target);
		leaf.setLeft(null);
		leaf.setRight(null);
		
		if (prev == null)
			root = leaf;
		else if (prev.getDatum().compareTo(target) < 0)
			prev.setRight(leaf);
		else 
			prev.setLeft(leaf);
	}
}







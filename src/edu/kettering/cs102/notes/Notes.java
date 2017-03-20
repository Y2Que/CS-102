/* James A Garza Jr
 * CS-102
 * 
 */

package edu.kettering.cs102.notes;

import java.util.NoSuchElementException;

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
			return true;
		if (current.getDatum().compareTo(target) < 0)
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
	
	/* 
	 * trees can get unbalanced and you can use AVI trees or red/black trees
	 * to re-balance the binary tree
	 */
	public void addRecursive(T target) {
		root = addRecursive(target, root);
	}
	private TreeNode<T> addRecursive(T target, TreeNode<T> current) {
		if (current == null) {		// base case
			TreeNode<T> leaf = new TreeNode<T>();
			leaf.setDatum(target);
			leaf.setLeft(null);
			leaf.setRight(null);
			return leaf;
		} 
		if (current.getDatum().compareTo(target) < 0) // target datum > current
			current.setRight(addRecursive(target, current.getRight()));
		else		// target datum < current datum
			current.setLeft(addRecursive(target,current.getLeft()));
		
		return current;
	}
	
	
	/* Remove from binary tree
	 * 
	 * short-circuiting, if current is null, the 2nd operation in the if
	 * statement does not evaluate, thus does not throw an exception
	 * (a && b) - short circuiting, stop evaluating if a is false
	 * (a & c)  - no short circuiting, always evaluates both a and b
	 */
	public void remove (T target) {
		TreeNode<T> current = root;
		TreeNode<T> previous =  null;
		
		while (current != null && !(current.getDatum().equals(target))) {
			previous = current;
			if (current.getDatum().compareTo(target) < 0)
				current = current.getRight();
			else
				current = current.getLeft();
		}
		if (current == null)		// fell off the list
			throw new NoSuchElementException();
		
		if (current.getRight() == null) {	// no right child
			if (previous == null)	// if deleting the root
				root = current.getLeft();
			else if (previous.getDatum() == current) // if child is left child
				previous.setLeft(current.getLeft());
			else								// if current is right child
				previous.setRight(current.getLeft());
		}
		else if (current.getLeft() == null) {	// no left child
			if (previous == null)	// if deleting the root
				root = current.getRight();
			else if (previous.getDatum() == current) // if child is left child
				previous.setLeft(current.getRight());
			else								// if current is right child
				previous.setRight(current.getRight());
		}
		else {
			previous = current;
			TreeNode<T> heir = current.getLeft();
			while (heir.getRight() != null) {
				previous = heir;
				heir = heir.getRight();
			}
			current.setDatum(heir.getDatum());
			if (previous.getLeft() == heir)
				previous.setLeft(heir.getLeft());
			else previous.setRight(heir.getLeft());
		}
	}
	
	public void removeRecursive(T target) {
		root = removeRecursive(target, root);
	}
	private TreeNode<T> removeRecursive(T target, TreeNode<T> current) {
		// if we have fallen off the list
		if (current == null)
			throw new NoSuchElementException();
		// if target is larger than current
		if (current.getDatum().compareTo(target) < 0) {
			current.setRight(removeRecursive(target,current.getRight()));
			return current;
		}
		// if target is less than current
		if (current.getDatum().compareTo(target) > 0) {
			current.setLeft(removeRecursive(target, current.getLeft()));
			return current;
		}
		// if target is found and has 1 child, replace with non-empty child
		if (current.getLeft() == null) return current.getRight();
		if (current.getRight() == null) return current.getLeft();
		// if current has 2 children, need an heir, someone to replace current
		TreeNode<T> heir = current.getLeft();
		while (heir.getRight() != null)
			heir = heir.getRight();
		// replace current with heir
		current.setDatum(heir.getDatum());
		
		// remove duplicate heir (remove leaf)
		current.setLeft(removeRecursive(heir.getDatum(), current.getLeft()));
		
		// re-attach sub-tree to main tree
		return current;
	}
	/* Array-Based Trees
	 *                    (1)
	 *                    /  \
	 *                  (2)   (3)
	 *                 /  \   /  \
	 *               (4) (5) (6) (7)
	 * - children of node at index n can be found at 2n and 2n + 1
	 */
	
	/* Traversal
	 * Every tree traversal does three things in some order:
	 * 1) process the root           (N)  -NRL- *NLR* - "pre-order"
	 * 2) Traverse the left subtree  (L)  -RNL- *LNR* - "in-order"
	 * 3) Traverse the right subtree (R)  -RLN- *LRN* - "post-order"
	 * 
	 * 
	 */
	
	/* for LNR "in-order" traversal
	 */
	public void print() {
		print(root);
	}
	private void print( TreeNode<T> current) {
		if (current == null) return;			// if null 
		// System.out.print(current.getDatum());// for NLR
		print(current.getLeft());				// print all nodes smaller
		System.out.print(current.getDatum());	// print current node
		print(current.getRight());				// print all nodes larger
	}
	
	/* for NLR "pre-order", you can re-construct the same tree if read from file
	 */
}

/* Analysis of Algorithms
 * 
 * Q: How long does an algorithm take to run?
 * A: Measure time as a function
 * 		f: input size -> # of instructions performed
 * 
 * Selection Sort
 * 
 */
class SortingAlgorithms {
	// dummy constructor
	SortingAlgorithms() {}
	
	public void selctionSort (int[] data, int start, int end) {
		int pass;
		for (pass = start; pass < end; pass++) {
			int minPos = pass;
			// look for smallest element
			for (int comp = pass+1; comp <= end; comp++)
				// if smaller than current smallest element
				if (data[comp] < data[minPos])
					minPos = comp;				// new smallest element
			
			int tmp = data[minPos];
			data[minPos] = data[pass];
			data[pass] = tmp;
		}
	}
	
	/* O-Notation
	 * Def: Let f,g: N -> N be functions.
	 * We say f(n) = O(g(n))("order g(n)") if there exists some c and n_0
	 * such that f(n) <= c*g(n) when n >= n_0
	 * 
	 * Ex: 
	 *     2n^2 + 6n - 6 <= 2n^2 + 6n
	 *                   <= 3n^2 if n is "big"
	 *                           if n^2 >= 6n
	 *                           if   n >= 6
	 * 
	 * Ex: Binary Search
	 *  - want to know how many times can I divide n by 2 to get to 1
	 *        n / (2^x) = 1
	 *                n = 2^x
	 *         log_2(n) = x
	 */

	/* Priority Queue
	 *              \  add  | remove |
	 *               +------+--------+
	 * sorted list   | O(n) |  O(1)  |
	 *               +------+--------+
	 * unsorted list | O(1) |  O(n)  |
	 *               +------+--------+
	 */
	
	/* Heap - a binary tree with 2 properties:
	 *   1) it is complete; every level of the tree is filled, except (perhaps)
	 *      the last which is filled left-to-right
	 *          o
	 *        /   \
	 *       o     o
	 *      / \   /
	 *     o  o  o
	 *   2) Every node is larger than both its children
	 *                        (100)
	 *                        /   \
	 *                      (75) (36)
	 *                      /  \
	 *                    (62) (71)
	 */
	class Heap {
		int[] data;
		int last;
		final int STARTSIZE = 42;
		
		public Heap () {
			data = new int[STARTSIZE];
			last = 0;
		}
		
		// This add method takes O(log_2(n)) time
		public void add (int datum) {
			if (data.length - 1 == last) {
				//resize();	// pretended the array is resized
			}
			data[++last] = datum;
			int current = last;
			// while not at the root and larger than parent
			while ((current > 1) && (data[current] > data[current/2])) {
				int tmp = data[current];
				data[current] = data[current/2];
				data[current/2] = tmp;
				current = current/2;
			}
		}
		
		// This remove method takes O(log_2(n)) time
		public int remove() {
			int answer = data[1];
			data[1] = data[last--];
			int current = 1;
			// while there is exists a left child
			while (current*2 <= last) {
				int largerChild = current*2;
				// if there exists a right child and right > left
				if ((largerChild < last) && 
								(data[largerChild + 1] > data[largerChild]))
					largerChild++;
				// break loop if current is greater than largerChild
				if (data[current] > data[largerChild])
					break;
				//
				int tmp = data[current];
				data[current] = data[largerChild];
				data[largerChild] = tmp;
				current = largerChild;
			}
			return answer;
		}
	}
	/* Min Heap
	 * to change this max heap to a min heap, multiply by -1 when adding and
	 * then when pulling out, mutiply by -1 again
	 */
	
	/* Heap Sort
	 * 1) add to heap
	 * 2) remove from heap
	 * 
	 * How long does it take?
	 *    n*log_2(n) + n*log_2(n) = 2n*log_2(n)
	 *                             O(n*log_2(n))
	 * 
	 */
	
	/* Section Sort \ "priority
	 * Heap sort    /    queues"
	 */
	
	/* Divide and Conquer Sort
	 * 1) "Divide" the list into two pieces
	 * 2) Sort the pieces
	 * 3) "Combine" the (sorted) pieces
	 */
	
	/* Merge Sort
	 * "Divide" == halve; "Combine" == merge;
	 */
	public void mergeSort (int[] data, int start, int end) {
		if (start >= end) return;	// 0 or 1 element in the array
		
		int mid = (start + end) / 2;	// bug! at max int value, I think
		mergeSort(data, start, mid);
		mergeSort(data, mid + 1, end);
		int[] sorted = new int [end - start + 1];
		int left = start;
		int right = mid + 1;
		int copy = 0;
		
		while ((left <= mid) && (right <= end)) {			// \
			if (data[left] < data[right]) {					//  \
				sorted[copy++] = data[left++];				//   \
			} else {										//    \
				sorted[copy++] = data[right++];				//     \
			}												//      \ takes
		} // we know 1 of the arrays is empty				//      / O(n) time
															//     /
		// if left is empty, run this loop					//    /
		while (left <= mid) sorted[copy++] = data[left++];	//   /
		// if right is empty, run this loop					//  /
		while (right <= end) sorted[copy++] = data[right++];// /
		
		for (copy = 0; copy < sorted.length; copy++) {		// \
			data[start + copy] = sorted[copy];				//  | O(n) time
		}													// /
		
		/* T(n) = 2n + 2T(n/2)					(n)				O(n) \
		 * 									   /   \			      \
		 * 								   (n/2)   (n/2)		O(n)   \ log_2 n
		 * 								  /   \     /   \			   / levels
		 * 							   (n/4) (n/4) (n/4) (n/4)	O(n)  /
		 * 								..    ..    ..     ..    ..  /
		 * 														\         /
		 * 														 \       /
		 * 														  \     /
		 * 														O(n*log_2(n))
		 */
	}
	
	/* Quick Sort
	 * "Divide" == "partition"; "Combine" == 0;
	 */
	
	public void quickSort(int[] data, int start, int end) {
		if (start >= end) return;
		int pivot = data[start];
		int front = start;
		int back = end;
		while (true) {	// this loop is O(n)
			while ((front <= end) && (data[front] <= pivot)) front++;
			while ((back >= start) && (data[back] > pivot)) back--;
			if (front > back) break;  // don't cross the beams, break loop
			int temp = data[front];		// swap
			data[front] = data[back];
			data[back] = temp;
		}
		int temp = data[start];		// swap pivot into the middle of the list
		data[start] = data[back];
		data[back] = temp;
		quickSort(data, start, back - 1);	// best case: O(n*log_2(n))
		quickSort(data, back + 1, end);		// worse case: O(n^2)
											//  n + (n-1) + (n-2) + ... + 2 + 1
											//  Gauss Sum: n(n+1)/2 = O(n^2)
	}
	
	/* Never Use This Sort: Bubble Sort
	 * 
	 */
	public void bubbleSort (int[] data, int start, int end) {	// O(n^2)
		for (int pass = end; pass > start; pass--)
			for (int comp = start; comp < pass; comp++)
				if (data[comp] > data [comp + 1]) {
					int temp = data[comp];
					data[comp] = data[comp++];
					data[comp++] = temp;
				}
	}
	
	/* Limits on Sorting
	 * Theorem: Any comparison-based sorting algorithm requires at least 
	 *          O(n*log_2(n)) time to execute.
	 * Proof: idea.. use decision trees			 (a<b ?)
	 * 										yes	/       \ no
	 * 									  (b<c ?)      (a<c ?)
	 * 							      yes /    \ no yes /    \ no
	 * 							   (a<b<c) (a<c ?) (b<a<c) (b<c ?)
	 * 									yes	/   \ no    yes /   \ no
	 * 									(a<c<b)(c<a<b)	(b<c<a)(c<b<a)
	 * n elements to be sorted
	 *     n!     leaves
	 * +   n! - 1 internal nodes
	 * --------------------------
	 *    2n!     nodes
	 *    
	 * min height of tree = log_2(2(n!))
	 *                    = log_2(2) + log_2(n!)
	 *  Stirling's Approx ~ O(n*log_2(n))
	 */
	
	/* Linear-Time Sorting
	 * - Radix Sort: sort cards by rank only, then by suit. If concatenated 
	 *               correctly, the cards will be in sorted order
	 * sort by:  1s    10s   100s
	 *     235 | 610 | 201 | 026
	 *     793 | 201 | 504 | 159
	 *     207 | 793 | 207 | 201
	 *     159 | 504 | 610 | 207
	 *     201 | 235 | 026 | 235
	 *     026 | 026 | 235 | 504
	 *     610 | 207 | 159 | 610
	 *     504 | 159 | 739 | 739
	 * 
	 * time:  O(n)
	 * space: O(n)
	 * - there is a space/time trade off with sorting algorithms
	 */
	
	/* Bin Sort
	 * 
	 */
	
	/* 
	 * A(input)           = {0,6,4,1,2,0,2,5,6}
	 * D (distribution)   = {2,1,2,0,1,1,2}
	 * 		       indices:  0,1,2,3,4,5,6
	 * sum of D elements  = {2,3,5,5,6,7,9}
	 * 
	 * S (sorted): decrement the index of sum then place the number there
	 * 				    A = {0,6,4,1,2,0,2,5,6}
	 * 			   sum(D) = {1,3,5,5,6,7,9}
	 * 					S = { ,0, , , , , , , }
	 * 
	 * 				    A = {0,6,4,1,2,0,2,5,6}
	 * 			   sum(D) = {1,3,5,5,6,7,8}
	 * 					S = { ,0, , , , , , ,6}
	 */
}

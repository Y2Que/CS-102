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
		//int mid = (start + end) / 2; 			// bug, max size array overflows to negative number
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






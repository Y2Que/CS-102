package edu.kettering.cs102.notes;

public class Node {
	MyObject datum;		// should be an object, not Node
	Node next;
	public Node() {
		datum = null;
		next = null;
	}
	public MyObject getDatum() 	{ return datum; }
	public Node getNext() 		{ return next; }
	public void setDatum(MyObject datum)	{ this.datum = datum; }
	public void setNext(Node next) 			{ this.next = next; }
}

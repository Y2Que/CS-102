package edu.kettering.cs102.notes;

public interface ListInterface {
	public boolean isEmpty();
	public int size();
	public MyObject get(int index);
	public void add(int index, MyObject datum);
	public MyObject remove(int index);
	public void removeAll();
}

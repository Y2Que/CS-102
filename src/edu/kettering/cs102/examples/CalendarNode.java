/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
ApptNode class: Creates a node of a binary search tree that contains a Calendar object
*/

package edu.kettering.cs102.examples;

class CalendarNode{
	
   Calendar calendar; //appointment data contained in the node
   CalendarNode left;    //link to the left node in the tree
   CalendarNode right;   //link to the right node in the tree
   
	public CalendarNode()
   {
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: ApptNode constructor                                                    |
   |   Purpose: construct an instance of an appointment node                           |
   |   Parameters:                                                                     |   
   |      void                                                                         |     
   |   Returns:                                                                        |   
   |      void                                                                         |   
   |-----------------------------------------------------------------------------------|
   */
       //initialize the contents of the node as null
       calendar = null;
      
       //initialize the link to the left node as null
       left = null;
       
       //initialize the link to the right node as null
       right = null;
	}
   
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: setAttribute                                                            |
   |   Purpose: set an Attribute belonging to an instance of ApptNode                  |
   |   Parameters:                                                                     |   
   |      type Attribute: attribute you want to modify                                 |     
   |   Returns:                                                                        |   
   |      void                                                                         |   
   |-----------------------------------------------------------------------------------|
   */ 
   
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: getAttribute                                                            |
   |   Purpose: get an Attribute belonging to an instance of ApptNode                  |
   |   Parameters:                                                                     |   
   |      void                                                                         |     
   |   Returns:                                                                        |   
   |      type Attribute: attribute you want to get                                    |   
   |-----------------------------------------------------------------------------------|
   */
   
   
	public Calendar getCalendar() {
		return calendar;
	}
   
   public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
   
	public CalendarNode getLeft() {
		return left;
	}
	
	public void setLeft(CalendarNode left) {
		this.left = left;
   }
   
   public CalendarNode getRight() {
		return right;
	}
	
	public void setRight(CalendarNode right) {
		this.right = right;
   }   
}
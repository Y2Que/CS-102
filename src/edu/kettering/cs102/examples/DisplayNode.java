/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
DisplayNode class: Creates a node of a linked list that contains data for the display
*/

package edu.kettering.cs102.examples;

import java.util.*;

class DisplayNode{
	
   Vector<Vector> table; //table of appointment data contained in the node
   String title;  //name of the calendar containing the appt data
   DisplayNode next;    //link to the left node in the tree
   DisplayNode previous;   //link to the right node in the tree
   
	public DisplayNode()
   {
   /***********************************************************************************
   -   Method: DisplayNode constructor                                                    
   -   Purpose: construct an instance of a display node                           
   -   Parameters:                                                                        
   -      void                                                                           
   -***********************************************************************************/
       //initialize the contents of the apptTable as empty
       table = new Vector<Vector>();
       
       //Initialize title to default msg
       String title = "No calendars to show.";
       
       //initialize the link to the left node as null
       next = null;
       
       //initialize the link to the right node as null
       previous = null;
	}
   
   /***********************************************************************************
   -   Method: setAttribute                                                            
   -   Purpose: set an Attribute belonging to an instance of ApptNode                  
   -   Parameters:                                                                        
   -      type Attribute: attribute you want to modify                                      
   -   Returns:                                                                           
   -      void                                                                            
   -***********************************************************************************/ 
   
   /***********************************************************************************
   -   Method: getAttribute                                                            
   -   Purpose: get an Attribute belonging to an instance of ApptNode                  
   -   Parameters:                                                                        
   -      void                                                                              
   -   Returns:                                                                           
   -      type Attribute: attribute you want to get                                       
   -***********************************************************************************/
   
   
	public Vector<Vector> getTable() {
		return table;
	}
   
   public void setTable(Vector<Vector> table) {
		this.table = table;
	}
   
   public String getTitle() {
		return title;
	}
   
   public void setTitle(String  title) {
		this.title = title;
	}
   
	public DisplayNode getNext() {
		return next;
	}
	
	public void setNext(DisplayNode next) {
		this.next = next;
   }
   
   public DisplayNode getPrevious() {
		return previous;
	}
	
	public void setPrevious(DisplayNode previous) {
		this.previous = previous;
   }   
}
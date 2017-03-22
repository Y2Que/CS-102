/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
ApptNode class: Creates a node of a linked list that contains an Appointment object
*/

package edu.kettering.cs102.examples;

class ApptNode{
	
   Appointment appt; //appointment data contained in the node
   ApptNode next;    //link to the next node in the list
   
	public ApptNode()
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
       appt = null;
      
       //initialize the link to the next node as null
       next = null;
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
   
   
	public Appointment getAppt() {
		return appt;
	}
   
   public void setAppt(Appointment appt) {
		this.appt = appt;
	}
   
	public ApptNode getNext() {
		return next;
	}
	
	public void setNext(ApptNode next) {
		this.next = next;
   }
}
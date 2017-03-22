/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
Calendar class: Creates a linked list of appointment objects sorted by date and time
*/

package edu.kettering.cs102.examples;

import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.*;

public class Calendar{
   
   private String dataFileName;  //name of the text file holding the calendar information
   private String calendarTitle; //name of the Calendar
   private ApptNode head;   //first element in the list of appointments
   
   //constructor
   public Calendar(String dataFileName, String calendarTitle)
   {
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: Calendar constructor                                                    |
   |   Purpose: construct a Calendar object                                            |
   |   Parameters:                                                                     |   
   |      String dataFileName: name of the text file containing the calendar info      |
   |      String calendarTitle: name of the calendar                                   |    
   |   Returns:                                                                        |   
   |      void                                                                         |   
   |-----------------------------------------------------------------------------------|
   */
      this.dataFileName = dataFileName;
      this.calendarTitle = calendarTitle;
      
      //initialize the first element in the list as null
      head = null;
   }
   
   //standard getters and setters
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: setAttribute                                                            |
   |   Purpose: set an Attribute belonging to an instance of Calendar                  |
   |   Parameters:                                                                     |   
   |      type Attribute: attribute you want to modify                                 |     
   |   Returns:                                                                        |   
   |      void                                                                         |   
   |-----------------------------------------------------------------------------------|
   */ 
   
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: getAttribute                                                            |
   |   Purpose: get an Attribute belonging to an instance of Calendar                  |
   |   Parameters:                                                                     |   
   |      void                                                                         |     
   |   Returns:                                                                        |   
   |      type Attribute: attribute you want to get                                    |   
   |-----------------------------------------------------------------------------------|
   */
   
   public void setDataFileName(String dataFileName)
   {
      this.dataFileName = dataFileName;
   }
   
   public String getDataFileName()
   {
      return dataFileName;
   }
   
   public void setCalendarTitle(String calendarTitle)
   {
      this.calendarTitle = calendarTitle;
   }
   
   public String getCalendarTitle()
   {
      return calendarTitle;
   }
   
   //helper functions
   
   
   
   public boolean isEmpty(){
   /*
   |------------------------------------------------------------------------------------|
   |   Method: isEmpty                                                                  |
   |   Purpose: Checks if Calendar is empty                                             |
   |   Parameters:                                                                      |
   |      void                                                                          |
   |   Returns:                                                                         |
   |      boolean: true if Calendar is empty                                            |
   |------------------------------------------------------------------------------------|
   */
      return (head == null); //return true if the first element in the list is empty
   }
   
   public int size(){
   /*
   |------------------------------------------------------------------------------------|
   |   Method: size                                                                     |
   |   Purpose: finds the number of elements in calendar                                |
   |   Parameters:                                                                      |
   |      void                                                                          |
   |   Returns:                                                                         |
   |      int: returns number of elements in calendar                                   |
   |------------------------------------------------------------------------------------|
   */
      int count = 0; //number of elements in the list
   	
      //start counting with the first appointment in the list
      ApptNode current = head;
   	
      //continue counting until the end of the linked list is reached
      while (current != null)
      {
         count++;
         current = current.getNext(); //move down a node and increment counter
      }
   	
      //return the number of elements in the linked list
      return count;		
   }
   
   public void add(Appointment newAppt)
   {
   /*
   |------------------------------------------------------------------------------------|
   |   Method: add                                                                      |
   |   Purpose: add an Appointment to the linked list                                   |
   |   Parameters:                                                                      |
   |      Appointment newAppt: appointment object to add to the calendar                |
   |   Returns:                                                                         |
   |      void                                                                          |
   |------------------------------------------------------------------------------------|
   */
   
      ApptNode previous = null; //node before current position (initialize to null)
      ApptNode current = head; //node at current position (initialize to first position)
      Appointment currentAppt; //appointment at current position
      
      //get the date and time of the appointment that needs to be added
      String newDateStr = newAppt.getDate();
      int newStartTime = newAppt.getStartTime();
      int newEndTime = newAppt.getEndTime();
      
      //Create a formatter to force numbers to be 4 digits
      DecimalFormat fourDigits = new DecimalFormat("0000");
       
      //format the start and end time as 4 digits
      String newStartStr = fourDigits.format(newStartTime);
      String newEndStr = fourDigits.format(newEndTime);
       
      //concatenate date and time and convert to a double
      double newDateAndTime = Double.parseDouble(newDateStr + newStartStr + newEndStr);
      
      boolean positionFound = false; //index of current node
      
      //step through the list to find the position 
      //at which the new appointment object should be added
      while((current != null) && (!positionFound))
      {
         //get appointment in current node
         currentAppt = current.getAppt();
         
         //get the date and time of the current appointment
         String currentDateStr = currentAppt.getDate();
         int currentStartTime = currentAppt.getStartTime();
         int currentEndTime = currentAppt.getEndTime();
         
         //format the start and end time as 4 digits
         String currentStartStr = fourDigits.format(currentStartTime);
         String currentEndStr = fourDigits.format(currentEndTime);
         
         //concatenate date and time and convert to a double
         double currentDateAndTime = Double.parseDouble(currentDateStr + currentStartStr 
            										+ currentEndStr);
         
         //check if the new appointment date and time is greater than the current date
         if (newDateAndTime > currentDateAndTime)
         {
            //If so, step to next position in the list
            previous = current;
            current = current.getNext();
         }
         else
         {
            //If not, exit while loop and add new appointment to calendar
            positionFound = true;
         }
      }
      
      ApptNode splice = new ApptNode(); //create a new node to insert
      splice.setAppt(newAppt); //add the appointment data to the new node
      splice.setNext(current); //insert the new node into the current position,
   							// link it to the rest of the list
   							// and shift all of the other nodes down.
      
      if(previous == null)
      { 
         //if the node was added at index 0
         //make it the first node
         head = splice;
      }   
      else
      {
         //otherwise link the previous node to the inserted node
         previous.setNext(splice);
      }                       
                                  
   }
     
   public boolean delete(String apptDescription)
   {
   /*
   |------------------------------------------------------------------------------------|
   |   Method: delete                                                                   |
   |   Purpose: Removes all appointments matching apptDescription                       |
   |   Parameters:                                                                      |
   |      String apptDescription: substring of the name of the appointment to delete    |
   |   Returns:                                                                         |
   |      boolean: true if appointment was deleted                                      |
   |------------------------------------------------------------------------------------|
   */    
      boolean apptFound = false; //true if calendar name exists in database
      ApptNode currentNode = head; //node at current position in the linked list 
      ApptNode previousNode = null; //node before current position
      
      //check if calendar is empty
      if (currentNode == null){
         return apptFound;
      }
      
      //step through the list to search for matching appointments
      while(currentNode!=null){   
         
         //get appointment at current index
         Appointment currentAppt = currentNode.getAppt();
         
         //get appointment name at the current index
         String currentName = currentAppt.getEventName();
         
         //if matching appointment is found, delete appointment
         if ((currentName.toLowerCase()).contains(apptDescription)) {
            
            apptFound = true;
            
            //confirm deletion
            int response = JOptionPane.showConfirmDialog(null, 
                              "The following appointment matches your description:\n"
                                 + currentAppt.toString()
                                 + "\nAre you sure you want to delete it? ", 
                              "DELETE", JOptionPane.YES_NO_OPTION);
            
            //if the description matches, allow the user to remove it from the list
            if (response == JOptionPane.YES_OPTION){
               //remove current node
               if(previousNode == null){
                  //if the current node is the head, reassign head reference to next node
                  currentNode = currentNode.getNext();
                  head = currentNode;
               }
               else{
                  //Set previousNode's adjacent node to the node after current
                  //(cutting current out of the list)
                  currentNode = currentNode.getNext();
                  previousNode.setNext(currentNode);
               }
               JOptionPane.showMessageDialog(null, 
                     "Appointment \'" + currentName + "\' was removed.",
                      "SUCCESS", JOptionPane.PLAIN_MESSAGE); 
            }
            else{
               JOptionPane.showMessageDialog(null, 
                     "Appointment \'" + currentName + "\' was not removed.",
                      "CANCELLED", JOptionPane.PLAIN_MESSAGE);
               
               //skip to the next appointment if the user does not want to delete the current one.
               previousNode = currentNode;
               currentNode = currentNode.getNext();
            }                     
         } 
         else{
            //if the current appointment did not match, look at the next appointment
            previousNode = currentNode;
            currentNode = currentNode.getNext();
         }  
      }
      //return true if a matching appointment was found, false otherwise
      return apptFound;
   } 
   
   public Appointment get(int index)
   {
   /*
   |------------------------------------------------------------------------------------|
   |   Method: get                                                                      |
   |   Purpose: returns an Appointment object at a given index                          |
   |   Parameters:                                                                      |
   |      int index: index for the element to return                                    |
   |   Returns:                                                                         |
   |      Appointment: Appointment in the list at the given index                       |
   |------------------------------------------------------------------------------------|
   */
      
      ApptNode current = head; //start searching the list with the first appointment
      
      //if index is invalid, set current to null
      if (index <0)
         current = null;
      
      int position = 0; //index of current node
      
      //step through the linked list until the index is found or the list ends
      while((current != null) && (position != index))
      {
         position++;
         current = current.getNext();
      }
        
      if (current == null)
         //throw an exception if the index was not found
         throw new IndexOutOfBoundsException();
      else
         //return appointment at the given index
         return current.getAppt(); 
   }
   
   public Display showAll(Display display)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: showAll                                                        
   -   Purpose: adds a panel showing all appointments to the display        													                   
   -   Parameters:                                                                      
   -      Display display: linked list of display panels for UI                                                                          
   -   Returns:                                                                         
   -      Display: linked list of display panels for UI                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      ApptNode current = head; //node at index (initialize to first item in the list)
      Vector<Vector> dispContent = new Vector<Vector>(); //table of appointment information for UI
      Vector<String> defaultContent = new Vector<String>(); //default table content if calendar is empty
       
      //display a message if the calendar is empty
      if (head == null){
         defaultContent.addElement("No appointments to show.");
         dispContent.addElement(defaultContent);
      }   
      else{
         //loop until the index is reached
         while(current != null){
          
            //get information about the current appt. 
            Vector<String> output = ((Appointment)current.getAppt()).toVector(); 
            dispContent.addElement(output);
            
            //step to the next element in the list
            current = current.getNext(); 
         }
         
      }
      
      display.addPanel(calendarTitle, dispContent);
      return display;
   }
   
   public void save(){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: save                                                              
   - Purpose: saves the calendar by overwriting the file that was used to create it                                         
   - Parameters:                                                                      
   -    void                         
   - Returns:                                                                         
   -    void  
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      //get the name of the file to overwrite
      String dataFileName = getDataFileName();
      
      try{
         //open the file
         FileWriter file = new FileWriter(dataFileName);
         try{
         //create a buffered writer to write to the file
         BufferedWriter buffer = new BufferedWriter(file);
      
         //write the name of the calendar to the file
         buffer.write(getCalendarTitle());
         buffer.newLine();
         buffer.newLine();
         buffer.flush();
      
         ApptNode currentNode = head; //node at the current position in the list
      
         //traverse the calendar linked list
         while(currentNode != null){
            //write the original string used to create the appointment to the file
            buffer.write(((Appointment)currentNode.getAppt()).getApptData());
            buffer.newLine();  
            buffer.flush();
            
            //get the next node in the calendar linked list
            currentNode = currentNode.getNext(); 
         }
      
         //close the buffer and the file
         buffer.close();   
         file.close();
         
         }catch(IOException exc){
            //This error should never occur--it's just a safety check
            JOptionPane.showMessageDialog(null, 
                     "There was a problem with the buffer writer." 
                     + getCalendarTitle() + " was not saved.",
                     "ERROR", JOptionPane.ERROR_MESSAGE);
         }
      } 
      catch (IOException exc){
         //Warn the user that the data file used to create the calendar no longer exists
         JOptionPane.showMessageDialog(null, 
                     "\'"+ dataFileName + "\' no longer exists in the current directory.\n"
                        + getCalendarTitle() + " was not saved.",
                     "ERROR", JOptionPane.ERROR_MESSAGE);                                       
      }
   }

   
   public Display showApptAtDateAndTime(String inputDate, int inputTime, Display display)
   {
   /***********************************************************************************
   -   Method: showApptAtDateAndTime                                                            
   -   Purpose: show all appointments matching                   
   -   Parameters:                                                                        
   -      String inputDate: date to search for
   -      int inputTime: time to search for
   -      Display display: linked list of content for display                                    
   -   Returns:                                                                           
   -      Display : linked list of content for display                                                                           
   -***********************************************************************************/
        
      ApptNode current = head; //node at current position (initialize to head)
      
      Vector<Vector> dispContent = new Vector<Vector>(); //table of appointment information for UI
      Vector<String> defaultContent = new Vector<String>(); //default table content if no matches are found
      
      boolean timeFound = false; // flag is true if matches were found
      
      //step through the list and search each element until the end of the list is reached
      while (current!=null){
         //get the appointment at the current position 
         Appointment currentAppt = current.getAppt(); 
         
         //get the date and time of the appointment
         String dateStr = currentAppt.getDate();
         int startTime = currentAppt.getStartTime();
         int endTime = currentAppt.getEndTime();
      
         //check if the date and time of the appointment matches the requested date and time
         boolean dateMatches = dateStr.equalsIgnoreCase(inputDate);
         boolean timeMatches = ((inputTime >= startTime) && (inputTime <= endTime));
      
         //if an appointment is found matching the criteria, print it to the console
         if (dateMatches && timeMatches)
         {
            timeFound = true;
            //get information about the current appt. 
            Vector<String> output = ((Appointment)current.getAppt()).toVector(); 
            dispContent.addElement(output);
         }
          
         //step to the next position in the list
         current = current.getNext();
      }
      
      if (!timeFound){
         defaultContent.addElement("No matching appointments.");
         dispContent.addElement(defaultContent);   
      }
      
      //add results for this calendar to a display panel
      display.addPanel(calendarTitle, dispContent); 
      return display;
   }
   
   public Display showApptMatchingDesc(String inputStr, Display display)
   {
   /***********************************************************************************
   -   Method: showApptMatchingDesc                                                            
   -   Purpose: show all appointments matching description                  
   -   Parameters:                                                                        
   -      String inputStr: string to search for
   -      Display display: linked list of content for display to add appointment to                                     
   -   Returns:                                                                           
   -      Display : linked list of content for display                                                                           
   -***********************************************************************************/     
      ApptNode current = head; //node at current position (initialize to head)
      
      Vector<Vector> dispContent = new Vector<Vector>(); //table of appointment information for UI
      Vector<String> defaultContent = new Vector<String>(); //default table content if no matches are found
      
      boolean strFound = false; //indicates if an appointment 
   								//matching the input string was found
      
      //search each element in the list until the end of the list is reached
      while (current!=null){
         
       //get the appointment at the current position	
         Appointment currentAppt = current.getAppt(); 
         
         //get the description of the appointment
         String description = currentAppt.getEventName();
         
         //check if the description contains the requested string
         boolean descMatches = description.contains(inputStr);
      
         //if an appointment is found matching the criteria, print it to the console
         if (descMatches)
         {
            strFound = true;
            //get information about the current appt. 
            Vector<String> output = ((Appointment)current.getAppt()).toVector(); 
            dispContent.addElement(output);
         }
          
         //step to the next position in the list
         current = current.getNext();
      }
      if (!strFound){
         defaultContent.addElement("No matching appointments.");
         dispContent.addElement(defaultContent);   
      }
      //add results for this calendar to a display panel
      display.addPanel(calendarTitle, dispContent); 
      
      return display;
   }

   public void removeAll(){
   /*
   |------------------------------------------------------------------------------------|
   |   Method: removeAll                                                                |
   |   Purpose: deletes all elements in a calendar                                      |
   |   Parameters:                                                                      |
   |      void                                                                          |
   |   Returns:                                                                         |
   |      void                                                                          |
   |------------------------------------------------------------------------------------|
   */
      head = null;
   }
}
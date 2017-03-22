
/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
Database class: Allows the user to create a database of calendars (structured as a binary search tree) 
                and search and output the contents of the database. The calendars are sorted by their titles.
*/

package edu.kettering.cs102.examples;

import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Database{

   private CalendarNode root; //base of the binary search tree
   private Display display; //linked list with content for UI
   
   public Database(){
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    -   Method: Database constructor
    -   Purpose: constructs a database object
    -   Parameters:
    -      void
    -    Returns:
    -      void
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
       
      root = null; //intialize root to null
   }
   
   public Calendar get(String calendarName)
   {
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    -   Method: get
    -   Purpose: returns a Calendar with the given name
    -   Parameters:
    -      String calendarName: the name of the calendar to get
    -    Returns:
    -       Calendar: Calendar in the list that matches the given name
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
       
      CalendarNode currentNode = root; //node at current position
      Calendar currentCal; //calendar in contained in current node
      String currentCalName; //name of the current calendar
      int compareNames=-1; //result of comparison between the calender name to be found 
                        //and the current name (initialize to random non-zero value) 
      
      while(compareNames!=0 & currentNode!=null){
         
         currentCal = currentNode.getCalendar(); 
         currentCalName = currentCal.getCalendarTitle();
         
         // compareNames >0 if calendarName > currentCalName,
         // compareNames <0 if calendarName < currentCalName
         compareNames = calendarName.compareToIgnoreCase(currentCalName);  
      
          //if calendarName > currentCalName search right in the tree
         if(compareNames>0)
            currentNode = currentNode.getRight();
         //if newCalName <= currentCalName search left in the tree
         else if(compareNames<0)
            currentNode = currentNode.getLeft();
      }
      
      if (currentNode==null){
         throw new NoSuchElementException();
      }
      
      return currentNode.getCalendar();    
   }
   
   
   public void addCalendar(String dataFileName, String calendarName)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: addCalendar                                                              
   - Purpose: add a Calendar to the database                                          
   - Parameters:                                                                      
   -    String dataFileName: name of the data file which contained the calendar data  
   -    String calendarName: name of the calendar to be added                         
   - Returns:                                                                         
   -    void  
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      //construct a new calendar to add
      Calendar newCalendar = new Calendar(dataFileName, calendarName);
      
      //call private method to add calendar and update root node
      CalendarNode newRoot = addCalendar(newCalendar, root);
       
      if (newRoot != null){
         //if the add was successful, update the root and notify the user
         root = newRoot;
         JOptionPane.showMessageDialog(null, 
                     calendarName + " was added to the database.",
                      "SUCCESS", JOptionPane.PLAIN_MESSAGE);
      }
   } 
   
   private CalendarNode addCalendar(Calendar newCalendar, CalendarNode current)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: addCalendar                                                              
   - Purpose: add a Calendar to the database                                          
   - Parameters:                                                                      
   -    Calendar newCalendar: calendar object to add to the database  
   -    CalendarNode current: node at current position in search                         
   - Returns:                                                                         
   -    CalendarNode: new root node  
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      
      //if the position has been found, add the calendar to the tree
      if (current == null){
         CalendarNode leaf = new CalendarNode(); //create a new node
         leaf.setCalendar(newCalendar); //set content of the node
         leaf.setRight(null); //intialize left and right pointers
         leaf.setLeft(null);
         return leaf; //return current node (in a recursive call, this links to the previous node)
      }
      
      Calendar currentCal = current.getCalendar(); //calendar at current node
      String currentCalName = currentCal.getCalendarTitle(); //name of calendar at current node
      String newCalName = newCalendar.getCalendarTitle(); //name of calendar to be added
      
      // compareNames > 0 if newCalName > currentCalName,
      // compareNames < 0 if newCalName < currentCalName
      int compareNames = newCalName.compareToIgnoreCase(currentCalName);  
      
      if(compareNames>0){ //if newCalName > currentCalName search right in the tree
         current.setRight(addCalendar(newCalendar, current.getRight()));
      }
      else if(compareNames<0){ //if newCalName <= currentCalName search left in the tree
         current.setLeft(addCalendar(newCalendar, current.getLeft()));
      }
      else{
         JOptionPane.showMessageDialog(null, 
                     newCalName + " already exists in the database.",
                      "ERROR", JOptionPane.ERROR_MESSAGE); 
         return null;
      }
      
      return current;           
   }
   
   public void deleteCalendar(String calendarName)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: deleteCalendar                                                           
   - Purpose: Removes the calendar called calendarName                               
   - Parameters:                                                                      
   -     String calendarName:  name of the calendar to remove                         
   - Returns:                                                                         
   -     void                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
       
      try{ 
         //call recursive method to traverse the tree and delete the calendar
         root = deleteCalendar(calendarName, root);
         
         //if the deletion was successful, update the root and notify the user
         
         JOptionPane.showMessageDialog(null, 
                     calendarName + " was deleted.",
                      "SUCCESS", JOptionPane.PLAIN_MESSAGE);
      
      }catch(NoSuchElementException exc){
         JOptionPane.showMessageDialog(null, 
                     calendarName + " was not found.",
                      "ERROR", JOptionPane.ERROR_MESSAGE); 
      }                   
   } 
   
   private CalendarNode deleteCalendar(String calendarName, CalendarNode current)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: deleteCalendar                                                           
   - Purpose: Removes the calendar called calendarName                               
   - Parameters:                                                                      
   -     String calendarName:  name of the calendar to remove 
   -     CalendarNode current: node at current position in search                         
   - Returns:                                                                         
   -     CalendarNode: new root node                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      if(current == null){
         //if calendar was not found (possibly because the database is empty) display error
         throw new NoSuchElementException();
      }
      
      Calendar currentCal = current.getCalendar(); //get calendar in current node
      String currentName = currentCal.getCalendarTitle(); //get the name of the current calendar
      
      //if calendarName > currentName, search right branch
      if(calendarName.compareTo(currentName) > 0){ 
         current.setRight(deleteCalendar(calendarName, current.getRight()));
         return current;
      }
      
      //if calendarName < currentName, search left branch
      if(calendarName.compareTo(currentName) < 0){ 
         current.setLeft(deleteCalendar(calendarName, current.getLeft()));
         return current;
      }
      
      //If the node has no left child, link the right node (or null) to the removed node's parent
      if(current.getLeft() == null) 
         return current.getRight();
      
      //If the node has no right child, link the left node to the removed node's parent   
      if(current.getRight() == null) 
         return current.getLeft();
      
      //If the node has a left + right child, 
      //replace the content of the node instead of cutting it out of the tree  
      
      //Create a new node that will replace the old node
      CalendarNode heir = current.getLeft();
      
      //get the right-most node in the left branch (this is a middle value of the subtree)
      while(heir.getRight() != null){
         heir = heir.getRight();
      }
      
      //replace the contents of the old node with the contents of the new node
      current.setCalendar(heir.getCalendar());
      
      //Delete the heir's original location (its contents have already been copied)
      current.setLeft(deleteCalendar(((Calendar)heir.getCalendar()).getCalendarTitle(), current.getLeft()));
      
      return current;
   }  
   
   public void addAppointment(String calendarName, String apptData)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: addAppointment                                                            
   - Purpose: constructs an Appointment object and adds it to the database             
   - Parameters:                                                                       
   -   String calendarName: name of the calendar to which the Appointment belongs     
   -   String apptData: a String containing information about the Appointment         
   - Returns:                                                                          
   -   void                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      
      try{
        //Get the calendar to add the appointment to 
         Calendar currentCalendar = get(calendarName); 
       
         //Decode appointment data:
         try{     
            //replace all / with spaces to use scannerOject.next method
            apptData = apptData.replace('/', ' ');
         
            //create scanner object for appointment data string
            Scanner scanApptData = new Scanner(apptData);
         
            //get a string that contains the date: YYYYMMDD
            String date = scanApptData.next();
         
            //set year, month, and day to default strings
            int year = 0000;
            int month = 00;
            int day = 00;
         
            //if the date string is the correct length, 
            //split it into year, month, and day values
            if (date.length() == 8)
            {
               //get year
               String yearStr = date.substring(0,4);
               if (stringIsInt(yearStr)) //if year is all integers
                  year = Integer.parseInt(yearStr); //convert yearStr to int
            
               //get month
               String monthStr = date.substring(4,6);
               if (stringIsInt(monthStr))
                  month = Integer.parseInt(monthStr);
            
               //get day
               String dayStr = date.substring(6);
               if (stringIsInt(dayStr))
                  day = Integer.parseInt(dayStr);
            }
         
            //get start time
            int startTime = 0000; //startTime default int value
            String startTimeStr = scanApptData.next();//startTime as a string
         
            if (startTimeStr.length() == 4)
            {
               if(stringIsInt(startTimeStr))
                  startTime = Integer.parseInt(startTimeStr);
            }
         
            //get end time
            int endTime = 0000; //startTime default int value
            String endTimeStr = scanApptData.next();//endTime as string
         
            if (endTimeStr.length() == 4)
            {
               if(stringIsInt(endTimeStr))
               {
                  endTime = Integer.parseInt(endTimeStr);
                  if (startTime > endTime)
                  {
                     //endTime is invalid, so reset to default
                     endTime = 0000;
                  }
               }
            }
         
            //get name
            String eventName = scanApptData.nextLine();
            eventName = eventName.trim();
         
            //create new appointment and add it to the calendar
            Appointment newAppt = new Appointment(calendarName, year, month, day, 
               								startTime, endTime, eventName, apptData);
            currentCalendar.add(newAppt);
         
         }
         catch(NoSuchElementException exc){
            //If you get this exception, there was an empty line in the file.
            //Just skip this line.
         }
      }
      catch(NoSuchElementException exc){
         //Thrown if requested calendar does not exist.
         JOptionPane.showMessageDialog(null, 
                "Error: " + calendarName 
                 + " does not exist in the database.",
                 "ERROR", JOptionPane.ERROR_MESSAGE);               
      }  
   }
   
   public void deleteAppt(String apptDescription){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: deleteAppt                                                            
   - Purpose: deletes all appointments matching apptDescription from database            
   - Parameters:                                                                       
   -   String apptDescription: substring of description of appointment to delete        
   - Returns:                                                                          
   -   void                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      
      if(root==null){//if the database is empty, print a warning and exit.
         JOptionPane.showMessageDialog(null, 
                "The database is empty.",
                 "ERROR", JOptionPane.ERROR_MESSAGE);
         return;
      }
      
      //call private method to delete appointments
      boolean apptFound = deleteAppt(apptDescription, false, root);
      
      //if no appointments were found, notify user
      if (!apptFound){
         JOptionPane.showMessageDialog(null, 
                     "There are no appointments with descriptions containing " +
                      apptDescription + " in the database.", 
                      "ERROR", JOptionPane.ERROR_MESSAGE);
      }
   }
   
   private boolean deleteAppt(String apptDescription, boolean apptFound, CalendarNode currentNode){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: deleteAppt                                                            
   - Purpose: deletes all appointments matching apptDescription from database            
   - Parameters:                                                                       
   -   String apptDescription: substring of description of appointment to delete
   -   boolean apptFound: indicates if a matching appointment was found (since this is a 
   -                      recursive call,the value must be passed and updated with every call)
   -   CalendarNode currentNode: node at current position in the tree        
   - Returns:                                                                          
   -   boolean: true if a matching appt was found, false otherwise                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/   
      
      if (currentNode != null){
        
         //Go to the end of the left branch first
         apptFound = deleteAppt(apptDescription, apptFound, currentNode.getLeft());
      
         //Move from left-most branch to right-most branch, and at each calendar node along the way, delete matching appointments
         boolean calendarContainsAppt = ((Calendar)currentNode.getCalendar()).delete(apptDescription);
      
         //apptFound is false by default. 
         //Update value until a matching appointment is found, then quit updating.
         if (!apptFound){ 
            apptFound = calendarContainsAppt;
         }
      
         //search right branches after left branches
         apptFound = deleteAppt(apptDescription, apptFound, currentNode.getRight());
      }
      
      //return if a null leaf was found
      return apptFound;
   }
   
   public void save(){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: save                                                              
   - Purpose: saves the database by overwriting the input files that were used.                                          
   - Parameters:                                                                      
   -    void                         
   - Returns:                                                                         
   -    void  
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      save(root);
      JOptionPane.showMessageDialog(null, 
                     "The database was saved.",
                      "SUCCESS", JOptionPane.PLAIN_MESSAGE);      
   }
   
   private void save(CalendarNode currentNode){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: save                                                              
   - Purpose: saves the database by overwriting the input files that were used.                                          
   - Parameters:                                                                      
   -    CalendarNode currentNode: node at the current position in the tree                         
   - Returns:                                                                         
   -    void  
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      //if a null leaf is reached, return to the calling method
      if(currentNode==null){
         return;
      }
      
      //save the calendar at the currentNode
      ((Calendar)currentNode.getCalendar()).save();
      
      //save all the calendars in the left subtree
      save(currentNode.getLeft());
      
      //save all the calendars in the right subtree
      save(currentNode.getRight());
   }
   
   public void restore(){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: restore                                                              
   - Purpose: restores the database from the files in the current directory                                          
   - Parameters:                                                                      
   -    void                         
   - Returns:                                                                         
   -    void  
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      restore(root);
      JOptionPane.showMessageDialog(null, "The database was restored based on the files "
                                       + "\nin the current directory.", 
                                       "RESTORED", JOptionPane.PLAIN_MESSAGE);      
   }
   
   private void restore(CalendarNode currentNode){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: restore                                                              
   - Purpose: restores the database from the files in the current directory                                          
   - Parameters:                                                                      
   -    CalendarNode currentNode: node at the current position in the tree                         
   - Returns:                                                                         
   -    void  
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
         
      //if a null leaf is reached, return to the calling method
      if(currentNode==null){
         return;
      }
      
      //restore the calendar at the current node
      restoreCalendarFromFile(currentNode.getCalendar());
      
      //restore all the calendars in the left subtree
      restore(currentNode.getLeft());
      
      //restore all the calendars in the right subtree
      restore(currentNode.getRight());
   }
   
   public void restoreCalendarFromFile(Calendar calendar){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: restoreCalendarFromFile                                                              
   - Purpose: restores the calendar from its files in the current directory                                          
   - Parameters:                                                                      
   -    Calendar calendar: calendar object to restore                         
   - Returns:                                                                         
   -    void  
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      calendar.removeAll(); //remove all of the appointments in the calendar
      
      //get the name of the file used to restore the calendar
      String fileName = calendar.getDataFileName();
      
      try{
         File file = new File(fileName); //make a new File object to open file
         Scanner scanInput = new Scanner(file); //Scanner object to scan file
         String calendarName = scanInput.nextLine(); // title of calendar
         
         //read all of the lines in the file
         while(scanInput.hasNextLine())
         {
         //get line in file that describes the appointment	
            String nextAppt = scanInput.nextLine(); 
            addAppointment(calendarName, nextAppt);
         } 
      } 
      catch(FileNotFoundException exc) {
         JOptionPane.showMessageDialog(null, 
                     fileName + " does not exist in current directory.",
                     "WARNING", JOptionPane.WARNING_MESSAGE);
         deleteCalendar(calendar.getCalendarTitle());
      } 
      catch(NoSuchElementException exc){
         JOptionPane.showMessageDialog(null, 
                     fileName + " is empty.",
                     "WARNING", JOptionPane.WARNING_MESSAGE);
      }
   }      
 
   /******************************************************************************/
   //Auxillary methods to print information about the database to the command line
   
   
   public Display showAllRecords(){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: showAllRecords                                                            
   - Purpose: prints every appointment in the database in order of calendar name and time            
   - Parameters:                                                                       
   -   void       
   - Returns:                                                                          
   -   Display: linked list with content for the display                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      display = new Display(); //assign display to a new, empty list
      
      
      if(root==null){//if the database is empty, print a warning and exit.
         JOptionPane.showMessageDialog(null, 
                     "The database is empty.",
                      "ERROR", JOptionPane.ERROR_MESSAGE);
         
         return display;
      }
      
      //call a recursive method to traverse the list and add every calendar to the display
      showAllRecords(root);
      return display;
   }
    
   private void showAllRecords(CalendarNode current){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   - Method: showAllRecords                                                            
   - Purpose: private method to recursively display all appointments in the database            
   - Parameters:                                                                       
   -   CalendarNode current: calendar node at current position in the tree       
   - Returns:                                                                          
   -   void                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      if (current == null){
         return;
         
      }
      
      //go to left-most branch first
      showAllRecords(current.getLeft());
       
      //traverse the tree from left to right
      // and print the calendar at each node along the way
      display = ((Calendar)current.getCalendar()).showAll(display);
      
      //search right branches after left branches
      showAllRecords(current.getRight());
   }
   

   public Display showOneCalendar()
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: showOneCalendar                                                         
   -   Purpose: prints the appointments contained in the specified calendar to the 		
   -				console        													                   
   -   Parameters:                                                                      
   -      void                                                                          
   -   Returns:                                                                         
   -      Display: linked list with content for UI                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      display = new Display(); //create a new instance of Display linked list
      
      //check if database is empty
      if (root == null){
         JOptionPane.showMessageDialog(null, 
                     "The database is empty. No calendars to show",
                      "ERROR", JOptionPane.ERROR_MESSAGE);
         return display;
      }
      
      //get users request
      String requestedCalendarStr = JOptionPane.showInputDialog(
                    null,
                    "Enter the name of the calendar you want to view: ",
                    "Waiting for user input...",
                    JOptionPane.PLAIN_MESSAGE);
   
      //If a string was given, search the database.
      if ((requestedCalendarStr != null) && (requestedCalendarStr.length() > 0)) {
         //trim leading and trailing white spaces
         requestedCalendarStr = requestedCalendarStr.trim(); 
         try{
            //update display with the requested calendar
            Calendar requestedCalendar = get(requestedCalendarStr);
            display = requestedCalendar.showAll(display);
         } 
         catch(NoSuchElementException exc){
            //if the calendar does not exist, warn the user
            JOptionPane.showMessageDialog(null, 
                     "Sorry, no calendars by that name were found.",
                      "ERROR", JOptionPane.ERROR_MESSAGE);
         }
      }
      
      return display; //if no calendar was found, the display will be empty
     
   }

   public Display searchForTime()
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: searchForTime                                                         
   -   Purpose: Adds appointments containing the specified day and time 
   -              to display content list            
   -   Parameters:                                                                      
   -      void                                                                          
   -   Returns:                                                                         
   -      Display: linked list with content for UI                                                                          
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      display = new Display(); //create a new instance of Display linked list
      
      //check if database is empty
      if (root == null){
         JOptionPane.showMessageDialog(null, 
                     "The database is empty. No appointments to show.",
                      "ERROR", JOptionPane.ERROR_MESSAGE);
         return display;
      }    
   
      //get the date of the appointment the user wants to output
           //get users request
      String requestedDate = JOptionPane.showInputDialog(
                    null,
                    "Enter the date(YYYYMMDD): ",
                    "Waiting for user input...",
                    JOptionPane.PLAIN_MESSAGE);
      
      //If a string was given, get time.
      if ((requestedDate != null) && (requestedDate.length() > 0)) {
         
         //get the time of the appointment the user wants to output
         String requestedTimeStr = JOptionPane.showInputDialog(
                    null,
                    "Enter a time(HHMM): ",
                    "Waiting for user input...",
                    JOptionPane.PLAIN_MESSAGE);
      
         int requestedTime = -1; //intialize requestedTime to invalid number

         //If a string was given, search the database.
         if ((requestedTimeStr != null) && (requestedTimeStr.length() > 0))
         {
            //if input string is all ints, convert input to integer
            if(stringIsInt(requestedTimeStr))
               requestedTime = Integer.parseInt(requestedTimeStr);
            else
               JOptionPane.showMessageDialog(null, 
                     "Input must be an integer.",
                      "ERROR", JOptionPane.ERROR_MESSAGE);
                  
         }
      
         //use a recursive method to search all appointments for the requested date 
         // and time
         display = searchForTime(requestedDate, requestedTime,display, root);
                     
      }               
      return display; //display linked list holds no content if no matches were found
   }
   
   private Display searchForTime(String requestedDate, int requestedTime,
                                  Display display, CalendarNode currentNode)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: searchForTime                                                         
   -   Purpose: Prints the appointments containing the specified day and time.            
   -   Parameters:                                                                      
   -      String requestedDate: date of the appointment to print
   -      int requestedTime:  time of the appointment to print
   -      Display display: Linked list of display panels for UI
   -      CalendarNode currentNode: node at current position in the tree                                                                        
   -   Returns:                                                                         
   -      Display: linked list of display panels for UI                                                                       
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      //check if database is empty
      if (root == null){
         JOptionPane.showMessageDialog(null, 
                     "The database is empty. No appointments to show.",
                      "ERROR", JOptionPane.ERROR_MESSAGE);
         return display;
      }    
   
      
      if (currentNode != null){
        
         //Go to the end of the left branch first
         display = searchForTime(requestedDate, requestedTime, display, currentNode.getLeft());
      
         //Move from left-most branch to right-most branch, 
         //and at each calendar node along the way, search for matching appointments
         display = ((Calendar)currentNode.getCalendar())
                        .showApptAtDateAndTime(requestedDate, requestedTime, display);
      
         //search right branches after left branches
         display = searchForTime(requestedDate, requestedTime, display, currentNode.getRight());;
      }
      
      //return if a null leaf was found
      return display;
   } 

   public Display searchForDescription()
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: searchForDescription                                                         
   -   Purpose: Prints the appointments containing the specified description            
   -   Parameters:                                                                      
   -      void                                                                        
   -   Returns:                                                                         
   -      Display: linked list of content for display                                                                        
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      display = new Display(); //create a new instance of Display linked list
   
      //check if database is empty
      if (root == null){
         JOptionPane.showMessageDialog(null, 
                     "The database is empty. No appointments to show.",
                      "ERROR", JOptionPane.ERROR_MESSAGE);
         return display;
      } 
   
      //get the description of the appointment the user wants to search for
      String requestedString = JOptionPane.showInputDialog(
                    null,
                    "Enter a string to search for: ",
                    "Waiting for user input...",
                    JOptionPane.PLAIN_MESSAGE);
                    
      requestedString = requestedString.trim();
   
      //use a recursive method to search all appointments for the requested string
      display = searchForDescription(requestedString, display, root);
   
      return display; //display linked list holds no content if no matches were found
   }
   
   private Display searchForDescription(String requestedString, Display display, CalendarNode currentNode)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: searchForDescription                                                         
   -   Purpose: Prints the appointments containing the specified string            
   -   Parameters:                                                                      
   -      String requestedString: substring of the appointment description to find and print
   -      Display display: linked list of display content for UI
   -                          (since this is a recursive method, it must be passed 
   -                           and returned with every call)
   -      CalendarNode currentNode: node at current position in the tree                                                                        
   -   Returns:                                                                         
   -      Display: display content for UI                                                                        
   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      if (currentNode != null){
        
         //Go to the end of the left branch first
         display = searchForDescription(requestedString, display, currentNode.getLeft());
      
         //Move from left-most branch to right-most branch, 
         //and at each calendar node along the way, delete matching appointments
         display = ((Calendar)currentNode.getCalendar()).showApptMatchingDesc(requestedString, display);
         
         //search right branches after left branches
         display = searchForDescription(requestedString, display, currentNode.getRight());
      }
      
      //return if a null leaf was found
      return display;
   } 

   public boolean stringIsInt(String stringToCheck)
   {
   /*
   |------------------------------------------------------------------------------------|
   |   Method: stringIsInt                                                              |
   |   Purpose: Checks if a string contains all integer values                          |
   |   Parameters:                                                                      |
   |      String stringToCheck:  string to check if it contains all integers            |
   |   Returns:                                                                         |
   |      boolean: true if string contains all integers                                 |
   |------------------------------------------------------------------------------------|
   */
      int strLength = stringToCheck.length(); //length of string to check 
   											          //(number of characters)
      int strIndex = 0; //current character to check
      boolean stringIsAllInts = true; //value to return. True if the string is all ints
   
      //check all characters of the string until a non-integer value is found
      while((strIndex < strLength) && Character.isDigit(stringToCheck.charAt(strIndex)))
      {
         strIndex ++;
      }
   
      //if the loop ended before reaching the end of the string,
     //a non-integer value was found
      //otherwise, allInts is true
      if (strIndex != strLength)
      {
         stringIsAllInts = false;
      }
      return stringIsAllInts;
   }
   
}

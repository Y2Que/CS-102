/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
CalendarProgram class: Creates a database of appointments and contains methods 
                        to search and display contents of the database.
*/

package edu.kettering.cs102.examples;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.swing.*;

public class CalendarProgram extends JFrame
{
   private static Database myCalendars; //binary search tree containing calendars
   private static Display display; //linked list of display panel content
   
   public CalendarProgram(String[] txtFiles)
   {
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    -   Method: Prog5 constructor
    -   Purpose: constructs a client for program 5
    -   Parameters:
    -      String [] txtFiles: an array of text file names to process
    -    Returns:
    -      void
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      //Create a calendar database
      myCalendars = new Database();
      initDatabase(txtFiles); 
           
    }
    
    public Database getDatabase(){
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    -   Method: getDatabase
    -   Purpose: return myCalendars database
    -   Parameters:
    -      void
    -    Returns:
    -      Database: calendar database object
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      return myCalendars;
    }
    
    public void setDatabase(Database newDatabase){
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    -   Method: setDatabase
    -   Purpose: set myCalendars database
    -   Parameters:
    -      Database newDatabase: calendar database object
    -    Returns:
    -      void
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      myCalendars = newDatabase;
    }
    
    public void initDatabase(String[] txtFiles){
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    -   Method: initDatabase
    -   Purpose: initialize the database with text files given as run-time arguments
    -   Parameters:
    -      String[] txtFiles: file names to process
    -    Returns:
    -      void
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
       
    
      //get number of files to process
      int numFiles = txtFiles.length;
      
      //if no calendar files are given, display a warning and exit the program
      if (txtFiles.length == 0)
      {
         JOptionPane.showMessageDialog(null, "No input files were given. The database is empty.", 
                                       "WARNING", JOptionPane.WARNING_MESSAGE);
      }
      else{
         //loop through and process each file in the args array
         for(int currentFileIdx=0; currentFileIdx<numFiles; currentFileIdx++)
         {    
            processFile(txtFiles[currentFileIdx]);            
         }
      }
   }
    
   public static void processFile(String fileName)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: processFile                                                               
   -   Purpose: read a file and add the calendar to the Database                         
   -   Parameters:                                                                          
   -      String filename: name of the file to be processed                              
   -   Returns:                                                                             
   -      void                                                                              
   -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      try{
         File currentFile = new File(fileName); //make a new File object to open file
         Scanner scanInput = new Scanner(currentFile); //Scanner object to scan file
         String calendarName = scanInput.nextLine(); //get title of calendar
         calendarName =	calendarName.trim();	 //get rid of leading and trailing whitespace
      	
         //add a new calendar to the database
         myCalendars.addCalendar(fileName, calendarName);
         
         //read all of the lines in the file
         while(scanInput.hasNextLine())
         {
             //get line in file that describes the appointment
            String nextAppt = scanInput.nextLine(); 
            myCalendars.addAppointment(calendarName, nextAppt);
         } 
      } 
      catch(FileNotFoundException args) {
         JOptionPane.showMessageDialog(null, fileName + " does not exist in current directory.",
                                       "WARNING", JOptionPane.WARNING_MESSAGE);
      } 
      catch(NoSuchElementException args){
         JOptionPane.showMessageDialog(null, fileName + " is empty.",
                                       "WARNING", JOptionPane.WARNING_MESSAGE);
      }
   }
   
   
   public static Display outputSelection(int userChoice)
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: outputSelection                                                           
   -   Purpose: receive a users request and outputs the desired data                     
   -   Parameters:                                                                          
   -      int userChoice: number (1-12) corresponding to the users menu selection        
   -   Returns:                                                                             
   -      Display: linked list with content for UI                                                                           |   
   -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
     
      final int PRINT_ALL = 1;    //print all appointments in the database
      final int PRINT_CAL = 2;    //print a single calendar in the database
      final int FIND_TIME = 3;    //print all appointments that occur
                                    // during the requested time
      final int FIND_DESC = 4;    //print all appoinments with event names 
                                    // that contain the requested string
      final int ADD_APPT = 5;     //add an appointment to a calendar in the database
      final int ADD_CAL = 6;      //add a calendar to the database via a text file
      final int REMOVE_CAL = 7;   //remove a calendar from the database
      final int REMOVE_APPT = 8;  //remove appointment from the database
      final int SAVE = 9;         //save current database
      final int RESTORE = 10;     //restore last database 
      final int PREV_PANEL = 11;  //move to the previous display panel
      final int NEXT_PANEL = 12;  //move to the next display panel
       
      switch(userChoice){
         case PRINT_ALL: 
            display = myCalendars.showAllRecords();
            break;
         case PRINT_CAL: 
            display = myCalendars.showOneCalendar();
            break;
         case FIND_TIME: 
            display = myCalendars.searchForTime();
            break;
         case FIND_DESC: 
            display = myCalendars.searchForDescription();
            break;
         case ADD_APPT: 
            display = addAppointment();
            break;
         case ADD_CAL: 
            display = addCalendarFromFile();
            break;
         case REMOVE_CAL: 
            display = removeCalendar();
            break;
         case REMOVE_APPT: 
            display = removeAppointment();
            break;
          case SAVE: 
            myCalendars.save();
            display = new Display();
            break;
         case RESTORE: 
            myCalendars.restore();
            display = new Display();
            break;  
         case PREV_PANEL:
            display.previousPanel();
            break;
         case NEXT_PANEL:
            display.nextPanel();
            break;   
                                  
      }
      return display;
   
   }
   
   public static Display addAppointment()
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    -   Method: addAppointment
    -   Purpose: adds an appointment based on the users input
    -   Parameters:
    -      void
    -    Returns:
    -      Display: empty list that is not displayed
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
     
      
      String apptData;  //data used to construct a new appointment object
      display = new Display();
      
      //get the calendar name
      String calendarName = (String)JOptionPane.showInputDialog(null, "Enter the name of a calendar "
                              + "to which you want to add an appointment: ", 
                              "Waiting for user input...", JOptionPane.PLAIN_MESSAGE);
      try{
        //Try to get the requested calendar to make sure it is a valid name
         myCalendars.get(calendarName);
      }catch(NoSuchElementException exc){
         //Thrown if requested calendar does not exist.
         JOptionPane.showMessageDialog(null, 
                     "Input does not match any calendar in the database.",
                      "ERROR", JOptionPane.ERROR_MESSAGE);
         return display;               
      }
        
      //get the date
      String userInput = (String)JOptionPane.showInputDialog(null, 
                              "Enter the date of the appointment (YYYYMMDD): ", 
                              "Waiting for user input...", JOptionPane.PLAIN_MESSAGE);
      apptData = userInput + "/";
         
      //get the start time
      userInput = (String)JOptionPane.showInputDialog(null, 
                              "Enter the start time of the appointment "
                              + "in military time (HHMM): ", 
                              "Waiting for user input...", JOptionPane.PLAIN_MESSAGE);
      apptData = apptData + userInput + "/";
         
      //get the end time
       userInput = (String)JOptionPane.showInputDialog(null, 
                              "Enter the end time of the appointment "
                              + "in military time (HHMM): ", 
                              "Waiting for user input...", JOptionPane.PLAIN_MESSAGE);
      apptData = apptData + userInput + "/";
         
      //get the event name
      userInput = (String)JOptionPane.showInputDialog(null, 
                              "Enter the description for the appointment: ", 
                              "Input", JOptionPane.PLAIN_MESSAGE);
      apptData = apptData + userInput + "/";
        
      //add an appointment to the calendar
      myCalendars.addAppointment(calendarName, apptData); 
      
      JOptionPane.showMessageDialog(null, 
                     "A new event was added to " + calendarName + ".",
                      "SUCCESS", JOptionPane.PLAIN_MESSAGE); 
      
      return display;
      
   }
   
   public static Display addCalendarFromFile()
   {
   /************************************************************************************
   -   Method: addCalendarFromFile                                                      
   -   Purpose: add a new calendar to the database                                      
   -   Parameters:                                                                      
   -      void          
   -   Returns:                                                                         
   -      Display: linked list of display panels for the UI                                                                          
   *************************************************************************************/

      display = new Display();
      
      //get the name of the file to add
      String fileName = (String)JOptionPane.showInputDialog(null, "Enter the name of a calendar "
                              + "file in your current directory: ", 
                              "Waiting for user input...", JOptionPane.PLAIN_MESSAGE);
                              
      //process the file given by the user
      fileName = fileName.trim();//get rid of leading and trailing whitespace
      processFile(fileName);
      return display;
      
   }
   
   public static Display removeCalendar(){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: removeCalendar                                                           
   -   Purpose: remove a calendar from the database                                     
   -   Parameters:                                                                      
   -      void          
   -   Returns:                                                                         
   -      Display: empty linked list for the display                                    
   -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      display = new Display();
      //get the users input
      String calendarToRemove = (String)JOptionPane.showInputDialog(null, 
                              "Enter the name of a calendar "
                              + "you want to remove: ", 
                              "Waiting for user input...", JOptionPane.PLAIN_MESSAGE);
      myCalendars.deleteCalendar(calendarToRemove.trim());
      return display;
   }
   
   public static Display removeAppointment(){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: removeAppointment                                                        
   -   Purpose: remove an appointment from the database                                 
   -   Parameters:                                                                      
   -      void     
   -   Returns:                                                                         
   -      Display: empty linked list for the display                                    
   -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
      //get the users input
      String apptName = (String)JOptionPane.showInputDialog(null, 
                              "Enter the name/description of the appointment "
                              + "\nyou want to remove (or a substring of the name): ", 
                              "Waiting for user input...", JOptionPane.PLAIN_MESSAGE);
      
      //delete all apointments containing the users input string
      myCalendars.deleteAppt((apptName.trim()).toLowerCase());
      Display display = new Display();
      return display;     
   }
   
   public static void exitProgram(){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: exitProgram                                                               
   -   Purpose: exit the current program                                                 
   -   Parameters:                                                                          
   -      void                                                                                
   -   Returns:                                                                             
   -      void                                                                              
   -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/  
      JOptionPane.showMessageDialog(null, "Exiting calendar program...",
                                       "Message", JOptionPane.INFORMATION_MESSAGE);
      System.exit(0);
   }
  
}
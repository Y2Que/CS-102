/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
Prog5 class: Creates a user interface for a calendar program
*/

package edu.kettering.cs102.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Prog5 extends JFrame
{
   private Container contents; //main container for the frame

   private Display display; //linked list of display panels  
   private static JPanel menuPanel;               //panel of button options for user
   private static JScrollPane menuScrollPane;     //pane that holds menuPanel and allows scrolling
   private static JScrollPane calendarScrollPane; //pane that holds calendarListView and allows scrolling
   private static JButton [] menuButtons;         //array of buttons for menu
   private static String [] menuOptions = {       //labels for menu buttons
            "Show all",
            "Find calendar",
            "Search for time",
            "Search for description",
            "Add an appointment",
            "Add a calendar from file",
            "Remove calendar",
            "Remove appointment",
            "Save database",
            "Restore database" };

   private static JPanel displayPanel;            //panel containing calendar table and a navigation bar
   private static JPanel calendarPanel;           //contains a calendar table
   private static JTable calendarTable;           //table that display appointments           
   private static JButton previousButton;         //button to navigate to the previous node
   private static JButton nextButton;             //button to navigate to the next node
   private static JLabel calendarName;            //name of the current calendar
   private static BorderLayout navigationBar;     //layout for the navigation bar
   private static JPanel navigationPanel;         //panel containg navigation components
 
   private static CalendarProgram prog;           //instance of CalendarProgram
 
   public Prog5(){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: Prog5 constructor                                                               
   -   Purpose: construct the user interface                                                
   -   Parameters:                                                                          
   -      void                                                                              
   -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
   
      super( "Colette's CS-102 Calendar Program" );
   
      contents = getContentPane( ); //get content pane for current frame
   
      //set main layout to a border layout
      contents.setLayout( new BorderLayout( 1, 1 ) );
   
      //create menu panel with grid layout
      menuPanel = new JPanel( );
      menuPanel.setLayout( new GridLayout( 11, 0 ) );
   
      ButtonHandler bh = new ButtonHandler( ); //create a button handler for the menu buttons
      menuButtons = new JButton[menuOptions.length];
   
      //fill menuPanel with menuButtons and assign appropriate menu option labels
      for ( int buttonNum = 0; buttonNum < menuOptions.length; buttonNum ++ )
      {
         menuButtons[buttonNum] = new JButton( menuOptions[buttonNum] );
         menuButtons[buttonNum].addActionListener( bh );
         menuPanel.add( menuButtons[buttonNum] );
      }
   
      //create a scroll pane to hold the menu panel
      menuScrollPane = new JScrollPane(menuPanel);
   
      //instantiate display panel that holds navigation bar and calendar table
      displayPanel = new JPanel( );
      displayPanel.setLayout( new BorderLayout( 3, 3 ) );
   
      //construct navigation components and add action listeners
      navigationPanel = new JPanel();
      navigationPanel.setLayout(new BorderLayout(5,5));
      previousButton = new JButton("<");
      previousButton.addActionListener(bh);
      nextButton = new JButton(">");
      nextButton.addActionListener(bh);
      calendarName = new JLabel( "Calendar Name", SwingConstants.CENTER );
      navigationPanel.add( previousButton, BorderLayout.WEST );
      navigationPanel.add( calendarName, BorderLayout.CENTER );
      navigationPanel.add( nextButton, BorderLayout.EAST );
      
      //create a calendar scroll pane to hold the table 
      calendarScrollPane = new JScrollPane(); 
      
      //add a default label to the calendar scroll pane 
      JLabel defaultMsg = new JLabel("Select a menu option to update the display", 
                           SwingConstants.CENTER);
      calendarScrollPane.setViewportView(defaultMsg);
   
      //add navigation and table components to display panel
      displayPanel.add( navigationPanel, BorderLayout.NORTH );
      displayPanel.add( calendarScrollPane, BorderLayout.CENTER );
   
      // add menu and calendar to content pane
      contents.add( menuScrollPane,  BorderLayout.WEST);
      contents.add( displayPanel, BorderLayout.CENTER );
   
      //set a default size for the Prog5
      setSize( 600, 300 );
      setVisible( true );
   }
 
   private static void updateDisplay(Display display){
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: updateDisplay                                                               
   -   Purpose: add the contents of the linked list to the display                                                
   -   Parameters:                                                                          
   -      Display display: linked list of content for the display
   -   Returns:
   -      void                                                                              
   -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

   
      //set the calendar name at the top of the panel
      calendarName.setText(display.getTitle());
   
      //create the table for the calendar pane
   
      //make a dummy header that won't be shown
      Vector<String> header = new Vector<String>();
      header.add(""); 
      
      //make a structure to hold appointments
      Vector<Vector> table = new Vector<Vector>(); 
      table = display.getTable();
      
      if (table.isEmpty()){
         //if it is empty, display default message 
         JLabel defaultMsg = new JLabel("Select a menu option to update the display", 
                           SwingConstants.CENTER);
         calendarScrollPane.setViewportView(defaultMsg);  
      }
      else{
         //if its not empty, construct a JTable 
         //and add the contents from the current position in the linked list
         calendarTable = new JTable(display.getTable(), header);
         calendarTable.setTableHeader(null);
         calendarTable.setGridColor(Color.GRAY);
      
         //add the table to a scroll pane
         calendarScrollPane.setViewportView(calendarTable);
      }
      //add the scroll pane to the displayPanel
      displayPanel.add( calendarScrollPane, BorderLayout.CENTER );
       
   }

   private class ButtonHandler implements ActionListener
   {
      public void actionPerformed( ActionEvent ae )
      /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      -   Method: actionPerformed                                                               
      -   Purpose: handle menu button presses                                                
      -   Parameters:                                                                          
      -      ActionEvent ae: action event that triggered this method
      -   Returns:
      -      void                                                                              
      -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      {
         final int PREV = 11; // move to the previous node
         final int NEXT = 12; // move to the next node
         
         //check if the button that triggered the event was the "<" button
         if (ae.getSource( ) == previousButton){
            //call outputSelection to get previous display panel
            display = prog.outputSelection(PREV);   
         
         }
         
         //check if the button that triggered the event was the ">" button
         else if (ae.getSource( ) == nextButton){
            //call outputSelection to get next display panel
            display = prog.outputSelection(NEXT);
         } 
         
         //check if the button that triggered the event was one of the menu buttons
         else{
            int buttonNum = 0;
            while((buttonNum<menuOptions.length)&&(ae.getSource( ) != menuButtons[buttonNum])){
               buttonNum++;
            } 
            //call outputSelection to update the display content  
            display = prog.outputSelection(buttonNum+1);
         }
         //used the new linked list to update the display
         updateDisplay(display);
      }
   }

   public static void main( String [] args )
   {
   /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   -   Method: main                                                               
   -   Purpose: start user interface for calendar program                                               
   -   Parameters:                                                                          
   -      String [] args: text files to initialize the database with
   -   Returns:
   -      void                                                                              
   -+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/    
      
      //instantiate calendar program
      prog = new CalendarProgram(args);
      Prog5 myNestedLayout = new Prog5( );
      myNestedLayout.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
   }
}
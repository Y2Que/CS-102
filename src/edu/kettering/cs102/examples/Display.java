/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
Display class: creates a linked list of display nodes for the UI
*/

package edu.kettering.cs102.examples;

import java.text.*;
import java.util.*;

public class Display{
   
   private DisplayNode head;
   private DisplayNode tail;
   private DisplayNode current;
   int numPanels;
   
   public Display(){
   /***********************************************************************************
   -   Method: Display constructor                                                            
   -   Purpose: create a new display linked list that contains content for calendar 
   -            table in the UI                 
   -   Parameters:                                                                        
   -      void                                    
   -   Returns:                                                                           
   -      void                                                                            
   -***********************************************************************************/   
      //make an empty node and assign all references to it
      head = new DisplayNode(); //beginning of the list
      current = head; //position in the list (node that is displayed)
      tail = current; //end of the list
      numPanels = 0; //number of panels in the list
   
   }
   
   public void addPanel(String title, Vector<Vector> table){
   /***********************************************************************************
   -   Method: addPanel                                                            
   -   Purpose: add a new panel at the end of the list                 
   -   Parameters:                                                                        
   -      String title: title of the panel
   -      Vector<Vector> table: data for the panel                                     
   -   Returns:                                                                           
   -      void                                                                            
   -***********************************************************************************/   
      if (numPanels == 0){
         //if the list is currently empty,
         //update the content of the tail
         head.setTitle(title);
         head.setTable(table);
         numPanels = 1;
               
      }
      else{
         //if the list has other items in it already,
         //make a new node, link it to the end,
         //and reassign tail reference 
         DisplayNode newNode = new DisplayNode(); //create a new node
         newNode.setTitle(title);
         newNode.setTable(table);
         tail.setNext(newNode);
         newNode.setPrevious(tail);
         tail = newNode;
         numPanels++;
      
      }      
      
   }
   
   public void nextPanel(){
   /***********************************************************************************
   -   Method: nextPanel                                                            
   -   Purpose: moves current pointer to the next node in the list                  
   -   Parameters:                                                                        
   -      void                                     
   -   Returns:                                                                           
   -      void                                                                           
   -***********************************************************************************/
      if(current != tail){
         current = current.getNext();
      }
   }
   
   public void previousPanel(){
   /***********************************************************************************
   -   Method: previousPanel                                                            
   -   Purpose: move current pointer to the previous node in the list                  
   -   Parameters:                                                                        
   -      void                                     
   -   Returns:                                                                           
   -      void                                                                            
   -***********************************************************************************/
      if(current != head){
         current = current.getPrevious();
      }
   }
   
   public Vector<Vector> getTable(){
   /***********************************************************************************
   -   Method: getTable                                                            
   -   Purpose: get the table data for the current panel                  
   -   Parameters:                                                                        
   -      void                                     
   -   Returns:                                                                           
   -      Vector<Vector>: table data for the calendar table in the UI                                                                           
   -***********************************************************************************/ 
      return current.getTable();  
   }
   
   public String getTitle(){
   /***********************************************************************************
   -   Method: getTitle                                                            
   -   Purpose: get the name of the current calendar to display               
   -   Parameters:                                                                        
   -      void                                     
   -   Returns:                                                                           
   -      String : name of the current calendar                                                                            
   -***********************************************************************************/
      return current.getTitle();  
   }
}
/*
Colette Umbach
Login ID: umba5897
CS-102, Winter 2016
Programming Assignment 5
Appointment class: Allows the user to set and get appointment information.
*/

package edu.kettering.cs102.examples;

import java.text.*;
import java.util.Vector;

public class Appointment{
   
   private String calendarName; //name of the calendar containing the appointment
   private String date;         //date of the appointment
   private int year;            //year of the date
   private int month;           //month of the date
   private int day;             //day of the date
   private int startTime;       //start time of the appointment
   private int endTime;         //end time of the appointment
   private String eventName;    //event name describing the appointment
   private String apptData;     //string from file used to construct the appointment
   
   
   public Appointment(String calendarName, int year, int month, int day, 
                        int startTime, int endTime, String eventName, String apptData)
   {
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: Appointment constructor                                                 |
   |   Purpose: construct an appointment object                                        |
   |   Parameters:                                                                     |   
   |      String calendarName: name of the calendar to whicn an appointment belongs    |
   |      int year: year of the appointment                                            |
   |      int month: month of the appointment                                          |
   |      int day: day of the appointment                                              |  
   |      int startTime: start time of the appointment                                 |
   |      int endTime: end time of the appointment                                     |
   |      String eventName: name of the appointment event                              |
   |      String apptData: string used to construct the appointment                    |     
   |   Returns:                                                                        |   
   |      void                                                                         |   
   |-----------------------------------------------------------------------------------|
   */ 
      this.calendarName = calendarName;
      this.year = year;
      this.month = month; 
      this.day = day; 
      this.startTime = startTime;
      this.endTime = endTime;
      this.eventName = eventName;
      this.apptData = apptData;
      
      setDate(year, month, day);
   }
   
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: setAttribute                                                            |
   |   Purpose: set an Attribute belonging to an instance of Appointment               |
   |   Parameters:                                                                     |   
   |      type Attribute: attribute you want to modify                                 |     
   |   Returns:                                                                        |   
   |      void                                                                         |   
   |-----------------------------------------------------------------------------------|
   */ 
   
   /*
   |-----------------------------------------------------------------------------------|
   |   Method: getAttribute                                                            |
   |   Purpose: get an Attribute belonging to an instance of Appointment               |
   |   Parameters:                                                                     |   
   |      void                                                                         |     
   |   Returns:                                                                        |   
   |      type Attribute: attribute you want to get                                    |   
   |-----------------------------------------------------------------------------------|
   */
   
   public void setCalendarName(String calendarName)
   {
      this.calendarName = calendarName;
   }
   
   public String getCalendarName()
   {
      return calendarName;
   }
   
   public void setDate(int year, int month, int day)
   {
   /*
   |------------------------------------------------------------------------------------|
   |   Method: setDate                                                                  |
   |   Purpose: takes integer values for year, month, and day and converts them into a  |
   |             date string                                                             |
   |   Parameters:                                                                      |   
   |      int year: year of the appointment date                                        |
   |      int month: month of the appointment date                                      |
   |      int day: day of the appointment date                                          |
   |   Returns:                                                                         |   
   |      void                                                                          |   
   |------------------------------------------------------------------------------------|
   */
      //create formats for the year, month, and day
      DecimalFormat fourDigits = new DecimalFormat("0000");
      DecimalFormat twoDigits = new DecimalFormat("00");
      
      //apply formats to year, month, and day in order to concatenate them into a date
      String yearStr = fourDigits.format(year);
      String monthStr = twoDigits.format(month);
      String dayStr = twoDigits.format(day);
      
      date = yearStr + monthStr + dayStr;
   }
   
   public String getDate()
   {
      return date;
   }
   
   public void setYear(int year)
   {
      this.year = year;
   }
   
   public int getYear()
   {
      return year;
   }
   
   public void setMonth(int month)
   {
      this.month = month;
   }
   
   public int getMonth()
   {
      return month;
   }
   
   public void setDay(int day)
   {
      this.day = day;
   }
   
   public int getDay()
   {
      return day;   
   }
   
   public void setStartTime(int startTime)
   {
      this.startTime = startTime;
   }
   
   public int getStartTime()
   {
      return startTime;
   }
   
   public void setEndTime(int endTime)
   {
      this.endTime = endTime;
   }
   
   public int getEndTime()
   {
      return endTime;
   }
   
   public void setEventName(String eventName)
   {
      this.eventName = eventName;
   }
   
   public String getEventName()
   {
      return eventName;
   }
   
   public void setApptData(String apptData)
   {
      this.apptData = apptData;
   }
   
   public String getApptData()
   {
      return apptData;
   }
   
   public String toString()
   {
   /*
   |------------------------------------------------------------------------------------|
   |   Method: toString                                                                 |
   |   Purpose: Outputs information about an instance of Appointment to the console     |
   |   Parameters:                                                                      |   
   |      void                                                                          |
   |   Returns:                                                                         |   
   |      String: Information about the appointment                                     |   
   |------------------------------------------------------------------------------------|
   */
      DecimalFormat timeFormat = new DecimalFormat("0000");
      
      //format time with preceding 0s
      String formattedStartTime = timeFormat.format(startTime);
      String formattedEndTime = timeFormat.format(endTime);
      
      
      
      return year + "-" + month + "-" + day
             + " (" + formattedStartTime + "-" + formattedEndTime + ") "
             + eventName;
   } 
   
   public Vector<String> toVector()
   {
   /*
   |------------------------------------------------------------------------------------|
   |   Method: toVector                                                                 |
   |   Purpose: Outputs information about an appointment to a vector for JTable         |
   |   Parameters:                                                                      |   
   |      void                                                                          |
   |   Returns:                                                                         |   
   |      Vector<String>: a vector to be used as a row of a JTable                      |   
   |------------------------------------------------------------------------------------|
   */
      DecimalFormat fourDig = new DecimalFormat("0000");
      DecimalFormat twoDig = new DecimalFormat("00");
      
      //format time with preceding 0s
      String formattedStartTime = fourDig.format(startTime);
      String formattedEndTime = fourDig.format(endTime);
      
      String formattedYear = fourDig.format(year);
      String formattedMonth = twoDig.format(month);
      String formattedDay = twoDig.format(day);
      
      //construct a string with appointment data
      String appt =  formattedYear + "-" + formattedMonth + "-" + formattedDay
             + " (" + formattedStartTime + "-" + formattedEndTime + ") "
             + eventName;
             
      //create a vector that will be a row of the table
      Vector<String> tblRow = new Vector<String>();
      
      //add the appt string to the row
      tblRow.addElement(appt);  
      
      return tblRow;     
   }

}
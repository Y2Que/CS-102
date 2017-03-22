/* James Garza
 * Login ID: garz6275
 * CS-102, Winter 21.03.2017
 * Program Assignment 5
 * Prog5.java
 * this program takes user's input file that contains stations, adds those
 * formatted stations into a database, and performs search and print functions
 * on the database with a GUI
 */

package edu.kettering.cs102.program5;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Prog5 extends JFrame {
	
	private static Database myDatabase;		// declare and define a database
	private Container contents;				// main container for the frame
	private static JPanel panelMenu, panelResults, panelIO, panelCards, 
						  panelModify, panelSearch;		// holds buttons
	// buttons for each action performable on the database
	private static JButton btnPrint, btnSearchCallSign, btnSearchFreq, btnIO,
						btnSearchGenre, btnAdd, btnRemove, btnMenuRemove,
						btnWrite, btnLoad, btnSearch;
	// allows for scrolling through many results
	private static JScrollPane scrollPaneResults;
	// labels for conveying info to user
	private static JLabel lblResultHeader, lblResults, lblIO, 
						  lblHeader, lblHeaderSearch;
	// textboxes to get user's input
	private static JTextField txtInput, txtInputSearch, txtFileInput;
	// combobox to get either AM or FM
	private static JComboBox<String> cmbFreqBand, cmbFreqBandSearch;
	
	/* Constructor
	 * builds the user interface  
	 */
	public Prog5() {
		// call the constuctor's super to create the window title
		super("Station Database");
		
		contents = getContentPane();	// get content pane for current frame
	    contents.setLayout(new BorderLayout());		// set to border layout
	    
	    // construct panelIO for reading and writing from file
	    panelIO = new JPanel();
	    panelIO.setLayout(new GridBagLayout());
	    // constants to place objects within GridBagLayout
	    GridBagConstraints gridBagConst = new GridBagConstraints();
	    gridBagConst.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConst.anchor = GridBagConstraints.CENTER;
	    lblIO = new JLabel("Enter a file name:");	// create label
		gridBagConst.gridwidth = 2;					// 2 cells wide
		gridBagConst.gridy = 0;						// 1st row
		panelIO.add(lblIO, gridBagConst);			// add to panel
		txtFileInput 		= new JTextField();		// create textbox
		gridBagConst.gridy = 1;						// 2nd row
		panelIO.add(txtFileInput, gridBagConst);	// add to panel
		btnWrite = new JButton("Write to File");	// create button
		gridBagConst.gridwidth = 1;					// 1 cell wide
		gridBagConst.gridy = 2;						// 3rd row
		panelIO.add(btnWrite, gridBagConst);		// add to panel
		btnLoad = new JButton("Load from File");	// create button
		gridBagConst.gridx = 1;						// 2nd column
		panelIO .add(btnLoad, gridBagConst);		// add to panel
	    
		// create menu panel with grid layout for each button 
	    panelMenu = new JPanel();
	    panelMenu.setLayout(new GridLayout(7, 0));
		// create menu buttons
		btnPrint 			= new JButton("Print All");
		btnSearch			= new JButton("Search by Frequency");
		btnSearchCallSign 	= new JButton("Search by Call Sign");
		btnSearchGenre 		= new JButton("Search by Genre");
		btnAdd 				= new JButton("Add Station");
		btnMenuRemove		= new JButton("Remove Station");
		btnIO				= new JButton("Save / Load Database");
		// add buttons to panels
		panelMenu.add(btnPrint);
		panelMenu.add(btnSearchCallSign);
		panelMenu.add(btnSearch);
		panelMenu.add(btnSearchGenre);
		panelMenu.add(btnAdd);
		panelMenu.add(btnMenuRemove);
		panelMenu.add(btnIO);

		// define default messages to user
		lblResultHeader = new JLabel("", SwingConstants.CENTER);
	    lblResults = new JLabel("Press a button to update the display.", 
	    												SwingConstants.CENTER);
		// allows user to scroll through multiple results
		scrollPaneResults = new JScrollPane();
		//add a default label to the calendar scroll pane 
	    scrollPaneResults.setViewportView(lblResults);
		// display results of button presses
	    panelResults = new JPanel( );
	    panelResults.setLayout(new BorderLayout());
	    panelResults.add(lblResultHeader, BorderLayout.NORTH);
	    panelResults.add(scrollPaneResults, BorderLayout.CENTER);
	    
	    // allows changing of database
	    panelModify = new JPanel();
	    panelModify.setLayout(new GridBagLayout());
	    lblHeader = new JLabel("Remove station by call sign:");
		gridBagConst.gridwidth = 2;							// 2 cells wide
		gridBagConst.gridy = 0;								// 1st row
		gridBagConst.gridx = 0;				 				// 1st column
		panelModify.add(lblHeader, gridBagConst);		// add to panel
		txtInput = new JTextField("Enter info here");		// create textbox
		gridBagConst.gridwidth = 1;							// 1 cell wide
		gridBagConst.gridy = 1;								// 2nd row
		panelModify.add(txtInput, gridBagConst);			// add to panel
		cmbFreqBand = new JComboBox<String>();		// create combobox
		cmbFreqBand.addItem("AM");					// add item to combobox
		cmbFreqBand.addItem("FM");					// add item to combobox
		gridBagConst.gridx = 1;				 		// 2nd column
		panelModify.add(cmbFreqBand, gridBagConst);	// add to panel
		btnRemove = new JButton("Remove Station");	// create button
		gridBagConst.gridwidth = 2;					// 2 cells wide
		gridBagConst.gridx = 0;				 		// 1st column
		gridBagConst.gridy = 2;						// 3rd row
		panelModify .add(btnRemove, gridBagConst);	// add to panel

		// panel for searching the database
		panelSearch = new JPanel();
		panelSearch.setLayout(new GridBagLayout());
		lblHeaderSearch = new JLabel("Enter a frequency:");
		gridBagConst.gridwidth = 2;							// 2 cells wide
		gridBagConst.gridy = 0;								// 1st row
		panelSearch.add(lblHeaderSearch, gridBagConst);		// add to panel
		txtInputSearch = new JTextField("Enter info here");
		gridBagConst.gridwidth = 1;							// 1 cell wide
		gridBagConst.gridy = 1;								// 2nd row
		panelSearch.add(txtInputSearch, gridBagConst);		// add to panel
		cmbFreqBandSearch = new JComboBox<String>();	// create combobox
		cmbFreqBandSearch.addItem("AM");				// add item to combobox
		cmbFreqBandSearch.addItem("FM");				// add item to combobox
		gridBagConst.gridx = 1;				 			// 2nd column
		panelSearch.add(cmbFreqBandSearch, gridBagConst);// add to panel
		btnSearchFreq = new JButton("Search for Frequency");	// create button
		gridBagConst.gridwidth = 2;								// 2 cells wide
		gridBagConst.gridx = 0;				 					// 1st column
		gridBagConst.gridy = 2;									// 3rd row
		panelSearch.add(btnSearchFreq, gridBagConst);			// add to panel
		
	    // allows switching between panels 
	    panelCards = new JPanel(new CardLayout());
	    panelCards.add(panelResults, "Results");
	    panelCards.add(panelIO, "IO");
	    panelCards.add(panelModify, "Modify");
	    panelCards.add(panelSearch, "Search");
	    
	    //create a button handler for the menu buttons
	  	ButtonHandler btnHandler = new ButtonHandler( ); 
	  		
  		// add buttons to handler
  		btnPrint			.addActionListener(btnHandler);
  		btnSearch			.addActionListener(btnHandler);
  		btnSearchCallSign	.addActionListener(btnHandler);
  		btnSearchFreq		.addActionListener(btnHandler);
  		btnSearchGenre		.addActionListener(btnHandler);
  		btnAdd				.addActionListener(btnHandler);
  		btnMenuRemove		.addActionListener(btnHandler);
  		btnIO				.addActionListener(btnHandler);
  		btnWrite			.addActionListener(btnHandler);
  		btnLoad				.addActionListener(btnHandler);
  		btnRemove			.addActionListener(btnHandler);
	    
	    // add main panels to container
	    contents.add(panelMenu, BorderLayout.WEST);
		contents.add(panelCards, BorderLayout.CENTER);
	    
	    setSize(600, 300);  	// set a default size for the Prog5
	    setVisible(true);		// enable visibility
	}
	
	/* ButtonHandler
	 * handles button presses
	 */
	private class ButtonHandler implements ActionListener {
		/* actionPerformed (event)
		 * accepts event as an input parameter, locates the source and acts
		 * appropriately depending on which button was pushed
		 */
		public void actionPerformed(ActionEvent event) {
			
			String strResults = "";
			
			//check the source of the listener
			// if print all is selected
			if (event.getSource() == btnPrint) {
				strResults = myDatabase.printAll();
				updateResults("Print out of all Stations", strResults);

	        } else if (event.getSource() == btnSearchCallSign) {
	        	String input = JOptionPane.showInputDialog("Enter a call sign:");
	        	if (input != null) {	// if there is input
	        		strResults = myDatabase.searchCallSign(input);
	        		updateResults("Search by Call Sign: " + input, strResults);
	        	}

	        } else if (event.getSource() == btnSearch) {
	        	CardLayout cardLayout = (CardLayout) panelCards.getLayout();
	        	cardLayout.show(panelCards, "Search");	// switch panel

	        } else if (event.getSource() == btnSearchFreq) {
	        	String input = txtInputSearch.getText();
	        	if (input != null) {	// if there is input
	        		strResults = myDatabase.searchFreq(cmbFreqBandSearch
	        							.getSelectedItem().toString(), input);
	        		updateResults("Search by Frequency: " + input, strResults);
	        	}
	        	updateResults("Search by Frequency", strResults);
	     
	        } else if (event.getSource() == btnSearchGenre) {
	        	String input = JOptionPane.showInputDialog("Enter a genre:");
	        	if (input != null) {	// if there is input
		            strResults = myDatabase.searchGenre(input);
		        	updateResults("Search by Genre: " + input, strResults);
	        	}

	        } else if (event.getSource() == btnAdd) {
	        	String input = JOptionPane.showInputDialog("Enter a station with"
	        			+ " the format disgegarding the spaces:\n(call_sign / "
	        			+ "frequency / frequency_band / location / genre)");
	        	if (input != null) { 	// if there is input
	        		myDatabase.addStationFromUser(input);
	        		updateResults("Success! ", "\nSuccessfully added station.");
	        	}
	        	
	        } else if (event.getSource() == btnRemove) {
	        	String input = txtInput.getText();
	        	if (input != null) { 	// if there is input
	        		boolean found = myDatabase.removeStation(cmbFreqBand
	        							.getSelectedItem().toString(), input);
	        		if (found) {	// if station is found
	        			updateResults("Success! ", "\nSuccessfully removed "
	        							+ "station.");
	        		} else {		// if no station found
	        			updateResults("Not Found", "\nStation not found.");
	        		}
	        	}
	        	
	        } else if (event.getSource() == btnMenuRemove) {
	        	// show panelModify to user
	        	CardLayout cardLayout = (CardLayout) panelCards.getLayout();
	        	cardLayout.show(panelCards, "Modify");	// switch panel
	        	
	        } else if (event.getSource() == btnIO) {
	        	// show panelIO to user
	        	CardLayout cardLayout = (CardLayout) panelCards.getLayout();
	        	cardLayout.show(panelCards, "IO");	// switch panel
	        	
	        } else if (event.getSource() == btnWrite) {
	        	String strInput = txtFileInput.getText();
	        	if (strInput != null) {	// if there is input
	        		int reply = JOptionPane.showConfirmDialog(null, "Are you "
	        			+ "sure you want to write database to file?", "Write to"
	        					+ " file?", JOptionPane.YES_NO_OPTION);
	        		if (reply == JOptionPane.YES_OPTION) {	// if confirmed
	        			myDatabase.printToFile(strInput);
	        		}
	        	}
	        	
	        } else if (event.getSource() == btnLoad) {
	        	String strInput = txtFileInput.getText();
	        	if (strInput != null) {	// if there is input
	        		int reply = JOptionPane.showConfirmDialog(null, "Are you "
	        			+ "sure you want to load database from file?", "Load "
	        					+ "from file?", JOptionPane.YES_NO_OPTION);
	        		if (reply == JOptionPane.YES_OPTION) {	// if confirmed
	        			myDatabase.loadFromFile(strInput);
	        		}
	        	}
	        }
		}
	}
	
	/* updateResults (strHeader, strResults)
	 * updates the list of Stations found depending on the action performed
	 * with strHeader at the top of the panel and strResults in the body
	 */
	public static void updateResults (String strHeader, String strResults) {

		// show panelResults to user
    	CardLayout cardLayout = (CardLayout) panelCards.getLayout();
    	cardLayout.show(panelCards, "Results");
    	
		lblResultHeader.setText(strHeader);	// set informative header
		lblResults.setText(strResults);		// update results
		  
		// add the table to the scroll pane
		scrollPaneResults.setViewportView(lblResults);
	}
	
	/* main (args[0])
	 * opens file in args[0] is any and adds the Stations in that file to the
	 * database, also starts the user interface
	 */
	public static void main(String[] args) {
		myDatabase = new Database();	// define database of stations
		
		try {
			// get input file from user and add Stations from file to database
			myDatabase.addStationsFromFile(args[0]);
		} catch (ArrayIndexOutOfBoundsException error) {	// if no file given
			JOptionPane.showMessageDialog(null, "No arguments given to program,"
									+ " no files currently in the database.\n",
									"No Input File", JOptionPane.ERROR_MESSAGE);
		}

		Prog5 stationsProgram = new Prog5();	// start program
		stationsProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
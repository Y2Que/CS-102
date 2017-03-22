/* Select a Color using JRadioButtons
   Anderson, Franceschi
*/

package edu.kettering.cs102.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChangingColors extends JFrame
{
 private Container contents;
 private JRadioButton red, green, blue;
 private ButtonGroup colorGroup;
 private JLabel label;
 private Color selectedColor = Color.RED;

 public ChangingColors( )
 {
  super( "Selecting a color" );
  contents = getContentPane( );
  contents.setLayout( new FlowLayout( ) );

  red = new JRadioButton( "red", true );
  green = new JRadioButton( "green" );
  blue = new JRadioButton( "blue" );

  label = new JLabel( "Watch my background" );
  label.setForeground( Color.GRAY );
  label.setOpaque( true );
  label.setBackground( selectedColor );

  contents.add( red );
  contents.add( green );
  contents.add( blue );
  contents.add( label );

  // create button group
  colorGroup = new ButtonGroup( );
  colorGroup.add( red );
  colorGroup.add( green );
  colorGroup.add( blue );

  // create RadioButtonHandler event handler
  // and register it on the radio buttons
  RadioButtonHandler rbh = new RadioButtonHandler( );
  red.addItemListener( rbh );
  green.addItemListener( rbh );
  blue.addItemListener( rbh );

  setSize( 225, 200 );
  setVisible( true );
 }

 private class RadioButtonHandler implements ItemListener
 {
  public void itemStateChanged( ItemEvent ie )
  {
   if ( ie.getSource( ) == red )
      selectedColor = Color.RED;
   else if ( ie.getSource( ) == green )
      selectedColor = Color.GREEN;
   else if ( ie.getSource( ) == blue )
      selectedColor = Color.BLUE;

   label.setBackground( selectedColor );
  }
 }

 public static void main( String [] args )
 {
  ChangingColors cc = new ChangingColors( );
  cc.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
 }
}

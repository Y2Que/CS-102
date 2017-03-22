/*  Using JCheckBoxes
    Anderson, Franceschi
*/

package edu.kettering.cs102.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MixingColors extends JFrame
{
 private Container contents;
 private JCheckBox red, green, blue;
 private int redValue, greenValue, blueValue;
 private JLabel label;

 public MixingColors( )
 {
  super( "Selecting a color" );
  contents = getContentPane( );
  contents.setLayout( new FlowLayout( ) );

  red = new JCheckBox( "red" );
  green = new JCheckBox( "green" );
  blue = new JCheckBox( "blue" );

  label = new JLabel( "Watch my background" );
  label.setOpaque( true );
  label.setForeground( Color.GRAY );
  label.setBackground( new Color ( 0, 0, 0 ) );

  contents.add( red );
  contents.add( green );
  contents.add( blue );
  contents.add( label );

  // create CheckBoxHandler event handler
  // and register it on the checkboxes
  CheckBoxHandler cbh = new CheckBoxHandler( );
  red.addItemListener( cbh );
  green.addItemListener( cbh );
  blue.addItemListener( cbh );

  setSize( 225,200 );
  setVisible( true );
 }

 private class CheckBoxHandler implements ItemListener
 {
  public void itemStateChanged( ItemEvent ie )
  {
   if ( ie.getSource( ) == red )
   {
    if ( ie.getStateChange( ) == ItemEvent.SELECTED )
      redValue = 255;
    else
      redValue = 0;
   }
   else if ( ie.getSource( ) == green )
   {
    if ( ie.getStateChange( ) == ItemEvent.SELECTED )
      greenValue = 255;
    else
      greenValue = 0;
   }
   else if ( ie.getSource( ) == blue )
   {
    if ( ie.getStateChange( ) == ItemEvent.SELECTED )
      blueValue = 255;
    else
      blueValue = 0;
   }

   label.setBackground(
        new Color( redValue, greenValue, blueValue ) );
  }
 }

 public static void main( String [] args )
 {
  MixingColors mc = new MixingColors( );
  mc.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
 }
}

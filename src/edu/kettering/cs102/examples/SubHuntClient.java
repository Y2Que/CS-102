/* Using MouseListener
   Anderson, Franceschi
*/

package edu.kettering.cs102.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SubHuntClient extends JFrame
{
 private int x; // current x mouse position
 private int y; // current y mouse position
 private SubHunt sub; // submarine
 private boolean gameStarted = false;

 private MouseHandler mh; // mouse event handler

 public SubHuntClient( )
 {
  super( "Click in the window to sink the sub" );

  sub = new SubHunt( SubHunt.DEFAULT_GAME_SIZE );
  // instantiate event handler and register listener on window
  mh = new MouseHandler( );
  addMouseListener( mh );

  setSize( sub.getGameSize( ), sub.getGameSize( ) );
  setVisible( true );
 }

 private class MouseHandler extends MouseAdapter
 {
  public void mouseClicked( MouseEvent me )
  {
   x = me.getX( );
   y = me.getY( );
   sub.play( x, y );
   setTitle( sub.getStatus( ) );
   repaint( );
  }
 }

 public void paint( Graphics g )
 {
  if ( !gameStarted )
  {
   super.paint( g );
   gameStarted = true;
  }
  sub.draw( g, x, y );
  if ( sub.isHit( ) )
   removeMouseListener( mh );
 }

 public static void main( String [] args )
 {
  SubHuntClient subH = new SubHuntClient( );
  subH.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
 }
}

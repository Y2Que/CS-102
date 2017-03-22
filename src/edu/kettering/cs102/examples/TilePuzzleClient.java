/** Using GridLayout dynamically
*   Anderson, Franceschi
*/

package edu.kettering.cs102.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TilePuzzleClient extends JFrame
{
 private Container contents;

 private JButton [][] squares;
 private TilePuzzle game; // the tile puzzle game

 public TilePuzzleClient( int newSide )
 {
  super( "The Tile Puzzle Game" );
  game = new TilePuzzle( newSide );
  setUpGameGUI( );
 }

 private void setUpGameGUI( )
 {
  contents = getContentPane( );
  contents.removeAll( ); // remove all components
  contents.setLayout( new GridLayout( game.getSide( ),
           game.getSide( ) ) );
  squares = new JButton [game.getSide( )][game.getSide( )];

  ButtonHandler bh = new ButtonHandler( );

  // for each button: instantiate button with appropriate button label,
  // add to container, and register listener
  for ( int i = 0; i < game.getSide( ); i++ )
  {
   for ( int j = 0; j < game.getSide( ); j++ )
   {
    squares[i][j] = new JButton( game.getTiles( )[i][j] );
    contents.add( squares[i][j] );
    squares[i][j].addActionListener( bh );
   }
  }

  setSize( 300, 300 );
  setVisible( true );
 }

 private void update( int row, int col )
 {
  for ( int i = 0; i < game.getSide( ); i++ )
  {
   for ( int j = 0; j < game.getSide( ); j++ )
   {
    squares[i][j].setText( game.getTiles( )[i][j] );
   }
  }

  if ( game.won( ) )
  {
   JOptionPane.showMessageDialog( TilePuzzleClient.this,
         "Congratulations! You won!\nSetting up new game" );
   Random random = new Random( );
   int sideOfPuzzle = 3 + random.nextInt( 4 );
   game.setUpGame( sideOfPuzzle );
   setUpGameGUI( );
  }
 }

 private class ButtonHandler implements ActionListener
 {
  public void actionPerformed( ActionEvent ae )
  {
   for ( int i = 0; i < game.getSide( ); i++ )
   {
    for ( int j = 0; j < game.getSide( ); j++ )
    {
     if ( ae.getSource( ) == squares[i][j] )
     {
      if ( game.tryToPlay( i, j ) )
       update( i, j );
      return;
     }
    }
   }
  }
 }

 public static void main( String [] args )
 {
  TilePuzzleClient myGame = new TilePuzzleClient( 3 );
  myGame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
 }
}

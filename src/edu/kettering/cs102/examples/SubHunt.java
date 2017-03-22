 /* SubHunt class
 Anderson, Franceschi
 */

package edu.kettering.cs102.examples;

 import java.awt.Graphics;
 import java.awt.Color;
 import java.util.Random;

 public class SubHunt
 {
  public static int DEFAULT_GAME_SIZE = 300;
  public static int SIDE = 35; // size of submarine

  private int gameSize;
  private int xCtr; // x coordinate of center of submarine
  private int yCtr; // y coordinate of center of submarine
  private String status = "";
  private boolean hit;

 /** Constructor
 * @param newGameSize gameSize
 */
 public SubHunt( int newGameSize )
 {
  if ( newGameSize > SIDE )
    gameSize = newGameSize;
  else
    gameSize = DEFAULT_GAME_SIZE;
  // generate submarine center
  Random random = new Random( );
  xCtr = SIDE / 2 + random.nextInt( gameSize - SIDE );
  yCtr = SIDE / 2 + random.nextInt( gameSize - SIDE );
  hit = false;
 }

 /** getStatus
 * @return status
 */
 public String getStatus( )
 {
  return status;
 }

 /** getGameSize
 * @return gameSize
 */
 public int getGameSize( )
 {
  return gameSize;
 }

 /** isHit
 * @return hit
 */
 public boolean isHit( )
 {
  return hit;
 }

 /** play
 * @param x the x coordinate of the play
 * @param y the y coordinate of the play
 */
 public void play( int x, int y )
 {
  // is click within the submarine?
  if ( Math.abs( x - xCtr ) < SIDE / 2
      && Math.abs( y - yCtr ) < SIDE / 2 )
  {
    status = "Sunk!";
    hit = true;
  }
  // is click close?
  else if ( Math.abs( x - xCtr ) < 2 * SIDE
            && Math.abs( y - yCtr ) < 2 * SIDE )
    status = "Close ...";
  // click is too far from submarine
  else
    status = "In the water";
 }

 /** draw
 * @param g a Graphics object
 * @param x the x coordinate of the play
 * @param y the y coordinate of the play
 */
 public void draw( Graphics g, int x, int y )
 {
  if ( status.equals( "Sunk!" ) )
  {
    // draw sunken submarine
    g.setColor( Color.BLACK );
    g.fillRoundRect( xCtr - SIDE/2, yCtr - SIDE/2, SIDE/2, SIDE, 15, 15 );

    g.fillRoundRect( xCtr - SIDE/4, yCtr - SIDE/3, SIDE/2, SIDE/2, 7, 7 );

    g.drawLine( xCtr + SIDE/4, yCtr - SIDE/12,
                xCtr + SIDE/2, yCtr - SIDE/12 );

    // draw red depth charge
    g.setColor( Color.RED );
    g.fillOval( x - SIDE/4, y - SIDE/4, SIDE/2, SIDE/2 );
  }
  else if ( status.equals( "In the water" ) ) // draw blue circle
  {
    g.setColor( Color.BLUE );
    g.fillOval( x - SIDE/2, y - SIDE/2, SIDE, SIDE );
  }
  // else Close ... , do not draw
 }
}

/** TilePuzzle class
*   Anderson, Franceschi
*/

package edu.kettering.cs102.examples;

public class TilePuzzle
{
 private String [][] tiles;
 private int side; // grid size
 private int emptyRow;
 private int emptyCol;

 /** constructor
 * @param newSide grid size
 */
 public TilePuzzle( int newSide )
 {
  setUpGame( newSide );
 }

 /** setUpGame
 * @param newSide grid size
 */
 public void setUpGame( int newSide )
 {
  if ( newSide < 1 )
   side = 3;
  else
   side = newSide;
  emptyRow = side - 1;
  emptyCol = side - 1;
  tiles = new String[side][side];

  // initialize tiles
  for ( int i = 0; i < side; i++ )
  {
   for ( int j = 0; j < side; j++ )
   {
    tiles[i][j] = String.valueOf( ( side * side )
                                - ( side * i + j + 1 ) );
   }
  }
  // set empty cell label to blank
  tiles[side - 1][side - 1] = "";
 }

 /** getSide
 * @return side
 */
 public int getSide( )
 {
   return side;
 }

 /** getTiles
 * @return a copy of tiles
 */
 public String[][] getTiles( )
 {
  String[][] copyOfTiles = new String[side][side];

  for ( int i = 0; i < side; i++ )
  {
   for ( int j = 0; j < side; j++ )
   {
    copyOfTiles[i][j] = tiles[i][j];
   }
  }
  return copyOfTiles;
 }

 /** tryToPlay
 * enable play if play is legal
 * @return true if the play is legal, false otherwise
 */
 public boolean tryToPlay( int row, int col )
 {
  if ( possibleToPlay( row, col ) )
  {
   // play: switch empty String and tile label at row, col
   tiles[emptyRow][emptyCol] = tiles[row][col];
   tiles[row][col] = "";
   // update emptyRow and emptyCol
   emptyRow = row;
   emptyCol = col;
   return true;
  }
  else
   return false;
 }

 /** possibleToPlay
 * @return true if the play is legal, false otherwise
 */
 public boolean possibleToPlay( int row, int col )
 {
  if ( ( col == emptyCol && Math.abs( row - emptyRow ) == 1 )
         || ( row == emptyRow && Math.abs( col - emptyCol ) == 1 ) )
   return true;
  else
   return false;
 }

 /** won4
 * @return true if the tiles are all at the right place, false otherwise
 */
 public boolean won( )
 {
  for ( int i = 0; i < side ; i++ )
  {
   for ( int j = 0; j < side; j++ )
   {
    if ( !( tiles[i][j].equals(
            String.valueOf( i * side + j + 1 ) ) )
       && ( i != side - 1 || j != side - 1 ) )
     return false;
   }
  }
  return true;
 }
}

/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  M.Tolliver
 * Date       :  2018-04-19
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2018-04-19  M.Tolliver  Initial writing and begin coding
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.math.BigInteger;

public class BrobInt {

  // CONSTANTS
  private static final int MAX_NUM_CHARS = 8;
  private static final int BASE = 10;

  // instance variables
  private int[] intArray;
  private String strValue;


  public BrobInt( String value ) {

    // set string value to input
    strValue = value;

    // create array to hold int values of the string
    intArray = new int[ (int)(value.length() / MAX_NUM_CHARS ) + 1 ];

    // variable to iterate through the array
    int arrayIndex = 0;

    // add characters from back to front until there are less than 8 left
    for ( int stringIndex = value.length(); stringIndex > value.length() % MAX_NUM_CHARS; stringIndex -= MAX_NUM_CHARS ) {
      intArray[ arrayIndex ] = Integer.parseInt( value.substring( stringIndex - MAX_NUM_CHARS , stringIndex ) );
      arrayIndex++;
    }

    // add remaining characters to the array if length of value is not divisible by 8
    if ( value.length() % 8 != 0 ) {
      intArray[ intArray.length - 1 ] = Integer.parseInt( value.substring( 0 , value.length() % MAX_NUM_CHARS ) );
    }
  }

  public BrobInt add( BrobInt value ) {
    String newBrobIntString = "";   // string to be input for the return BrobInt
    int[] shortArray = ( value.getArrayRep().length < intArray.length ) ? value.getArrayRep() : intArray;   // variable for shorter array
    int[] longArray = ( value.getArrayRep().length < intArray.length ) ? intArray : value.getArrayRep();   // variable for shorter array
    int carry = 0;    // holds the carry amount
    int index = 0;    // holds current index
    int sum = 0;      // holds current sum digit

    for ( index = 0; index < longArray.length; index++ ) {

      if ( index < shortArray.length ) {
        sum = longArray[ index ] + shortArray[ index ] + carry;
        carry = ( String.valueOf( sum ).length() > String.valueOf( longArray[ index ] ).length() ) ? 1 : 0;
      }
      else {
        sum = longArray[ index ] + carry;
        carry = ( String.valueOf( sum ).length() > String.valueOf( longArray[ index ] ).length() && index < longArray.length ) ? 1 : 0;
      }

      newBrobIntString = sum + newBrobIntString;  // add sum to the string

      // add 0 padding if needed
      if ( index < longArray.length - 1 ) {
        String strSum = String.valueOf( sum );
        while ( strSum.length() < MAX_NUM_CHARS ) {
          newBrobIntString = "0" + newBrobIntString;
          strSum += "0";
        }
      }
    }

    return new BrobInt( newBrobIntString ); // return the sum as a BrobInt

  }

  public int[] getArrayRep() {
    return intArray;
  }

  public boolean equals( BrobInt bI ) {
    return ( this.toString().equals( bI.toString() ) ) ? true : false;
  }

  public String toString() {
    return strValue;
  }

  public static void main( String args[] ) {
    BrobInt b = new BrobInt("144127909719710664015092431502440849849506284148982076191826176553");
    BigInteger bI = new BigInteger( "144127909719710664015092431502440849849506284148982076191826176553" );
    BrobInt c = new BrobInt("14412790971971066401509243150244084984950628410898207");
    BigInteger cI = new BigInteger("14412790971971066401509243150244084984950628410898207");

    System.out.println( "My val: " + b.add( c ).toString() );
    System.out.println( "Bg int: " +  bI.add( cI ) );

  }

}


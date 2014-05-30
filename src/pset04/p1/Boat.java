package pset04.p1;

/**
 * A Boat is a simple vessel
 * 
 * @author Mathias Menninghaus
 */

public class Boat {
   public String typ = "boat";

   public String getTyp() {
      return typ;
   }

   public String getTyp2() {
      return getTyp();
   }
}
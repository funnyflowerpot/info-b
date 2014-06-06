package util;

/**
 * A Test class that does not implement <code>Comparable</code> and wraps
 * Integer and int.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class NotComparable {

   private int s;

   public NotComparable(Integer s) {
      this.s = s;
   }

   public String toString() {
      return "" + this.s;
   }

}

package util;

import java.util.Comparator;

/**
 * Compares Integer instances in reverse order.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class IntegerReverseComparator implements Comparator<Integer> {

   @Override
   public int compare(Integer o1, Integer o2) {
      return o2.compareTo(o1);
   }

}

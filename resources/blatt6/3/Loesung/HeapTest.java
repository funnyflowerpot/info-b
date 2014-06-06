package util;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Test the class Heap if it functions as it should.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class HeapTest {

   public static void main(String[] args) {

      final Integer[] integers = { 1, 5, 7, 2, 3, -1, 0 };

      final NotComparable[] notComparables = new NotComparable[integers.length];
      for (int i = 0; i < integers.length; i++) {
         notComparables[i] = new NotComparable(integers[i]);
      }

      Heap<Integer> comparableHeap = new Heap<Integer>();
      final Integer[] sortedIntegers = { -1, 0, 1, 2, 3, 5, 7 };

      System.out.println("Test Heap functions...");
      System.out.println("...do HeapSort on a Heap and test outcome...");
      arrayEquals(sortedIntegers, HeapSort.heapSort(comparableHeap, integers));

      System.out.println("...do HeapSort with a specific comparator...");
      Comparator<Integer> c = new IntegerReverseComparator();

      Heap<Integer> comparatorHeap = new Heap<Integer>(c);
      Integer[] reverseSortedIntegers = { 7, 5, 3, 2, 1, 0, -1 };
      arrayEquals(reverseSortedIntegers,
            HeapSort.heapSort(comparatorHeap, integers));

      System.out.println("...test NoSuchElementExcpetion in deleteFirst...");
      try {
         comparableHeap.deleteFirst();
         System.out.print("FAIL: did not throw NoSuchElementException");
      } catch (NoSuchElementException ex) {
      }

      System.out.println("...test NoSucheElementExcpetion in getFirst...");
      try {
         comparableHeap.getFirst();
         System.out.print("FAIL: did not throw NoSuchElementException");
      } catch (NoSuchElementException ex) {
      }

      Heap<NotComparable> failureHeap = new Heap<NotComparable>();

      System.out
            .println("...test ClassCastExcpetion when inserting non-Comparable elements...");
      try {
         HeapSort.heapSort(failureHeap, notComparables);
         System.out.print("FAIL: did not throw ClassCastException");
      } catch (ClassCastException ex) {
      }

      System.out.println("...test NullPointerExcpetion when inserting...");
      try {
         comparableHeap.insert(null);
         System.out.print("FAIL: did not throw NullPointerException");
      } catch (NullPointerException e) {
      }
      System.out.println("...finished.");
   }

   /**
    * Compares two arrays with the same length element by element if they are
    * equal to each other.
    * 
    * @param expected
    *           the expected array of elements
    * @param actual
    *           the array of elements which should be tested on equality
    */
   private static <E> void arrayEquals(E[] expected, E[] actual) {
      if (expected.length != actual.length) {
         System.out.println("FAIL length expected: " + expected.length
               + " but was " + actual.length);
      } else {
         for (int i = 0; i < actual.length; i++) {
            if (expected[i] != actual[i]) {
               System.out.println("FAIL at pos " + i + " expected "
                     + expected[i] + " but was " + actual[i]);
               break;
            }
         }
      }
   }
}

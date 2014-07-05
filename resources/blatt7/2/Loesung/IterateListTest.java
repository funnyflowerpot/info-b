package util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tests the Iterable implementation in MyList
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class IterateListTest {

   /**
    * Test two {@link util.MyList} Objects on equality element by element.
    * 
    * @param expected
    * @param actual
    * @return true, if every element in MyList <code>expected</code> is equal to
    *         the element at the same position in MyList <code>actual</code>
    */
   private static <E> void assertEquals(MyList<E> expected, MyList<E> actual) {
      expected.reset();
      actual.reset();
      while (!expected.endpos() && !actual.endpos()) {
         assertEquals(expected.elem(), actual.elem());
         expected.advance();
         actual.advance();
      }
      if (expected.endpos() != actual.endpos()) {
         System.out.println("FAIL lists do not have the same length");
      }
   }

   private static <E> void assertEquals(E expected, E actual) {
      if (!expected.equals(actual)) {
         System.out.println("FAIL expected " + expected + " but was " + actual);
      }
   }

   private static <E> void assertEquals(E[] expected, Iterable<E> a) {
      int i = 0;
      Iterator<E> actual = a.iterator();
      while (i < expected.length && actual.hasNext()) {
         assertEquals(expected[i], actual.next());
         i++;
      }
      if ((i != expected.length) != actual.hasNext()) {
         System.out.println("FAIL expected for hasNext "
               + (i != expected.length) + " but was " + actual.hasNext());
      }
   }

   public static void main(String[] args) {

      System.out.println("Test the Iterable implementation of MyList...");

      Integer[] integers = { 1, 5, 7, 2, 3, -1, 0 };
      MyList<Integer> original = new MyList<Integer>();
      for (Integer i : integers) {
         original.add(i);
      }

      Integer[] inserted = { 0, -1, 3, 2, 7, 5, 1 };
      System.out.println("...test iteration through every element...");
      assertEquals(inserted, original);

      System.out.println("...test iteration and remove...");
      Iterator<Integer> iter = original.iterator();
      iter.next();
      iter.next();
      iter.remove();
      integers = new Integer[] { 1, 5, 7, 2, 3, 0 };
      MyList<Integer> withoutLast = new MyList<Integer>();
      for (Integer i : integers) {
         withoutLast.add(i);
      }
      assertEquals(withoutLast, original);

      System.out.println("...test for IllegalStateException in remove...");
      try {
         iter = original.iterator();
         iter.remove();
         System.out.print("FAIL Exception not thrown ");
      } catch (IllegalStateException e) {
      }

      System.out.println("...test Fail-Fast in next and add...");
      try {
         iter = original.iterator();
         original.add(3);
         iter.next();
         System.out.print("FAIL Exception not thrown ");
      } catch (ConcurrentModificationException e) {
      }

      System.out.println("...test Fail-Fast in delete and remove...");
      try {
         iter = original.iterator();
         original.delete();
         iter.remove();
         System.out.print("FAIL Exception not thrown ");
      } catch (ConcurrentModificationException e) {
      }

      System.out.println("...test NoSuchElementException in next...");
      try {
         iter = original.iterator();
         while (iter.hasNext()) {
            iter.next();
         }
         iter.next();
         System.out.print("FAIL Exception not thrown ");
      } catch (NoSuchElementException e) {
      }
      System.out.println("...finished.");
   }
}

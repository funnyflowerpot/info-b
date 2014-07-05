package util;

/**
 * 
 * Tests the Implementation of Visitable in MyList
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class VisitableTest {

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
         if (!expected.elem().equals(actual.elem())) {
            System.out.println("FAIL expected: " + expected.elem()
                  + " but was " + actual.elem());
         }
         expected.advance();
         actual.advance();
      }
      if (expected.endpos() != actual.endpos()) {
         System.out.println("FAIL lists do not have the same length");
      }
   }

   private static void assertEquals(Object expected, Object actual) {
      if (!expected.equals(actual)) {
         System.out.println("FAIL expected " + expected + " but was " + actual);
      }
   }

   public static void main(String[] args) {
      System.out.println("Test implementation of Visitable in MyList...");

      Integer[] integers = { 1, 5, 7, 2, 3, -1, 0 };
      MyList<Integer> original = new MyList<Integer>();

      for (Integer i : integers) {
         original.add(i);
      }

      final MyList<Integer> copy = new MyList<Integer>();

      Visitor<Integer> visitAll = new Visitor<Integer>() {

         @Override
         public boolean visit(Integer o) {
            copy.add(o);
            copy.advance();
            return true;
         }
      };

      original.accept(visitAll);

      System.out
            .println("...test with Visitor implementation which visits the complete list...");
      assertEquals(original, copy);

      /*
       * Create a Visitor which only view puts the second element into
       */

      final MyList<Integer> onlySecond = new MyList<Integer>();

      Visitor<Integer> visitSecond = new Visitor<Integer>() {

         private int i = 0;

         @Override
         public boolean visit(Integer o) {
            if (i++ == 1) {
               onlySecond.add(o);
               return false;
            } else {
               return true;
            }
         }
      };

      original.accept(visitSecond);

      System.out
            .println("...test with Visitor implementation which visits only one element...");
      assertEquals(-1, onlySecond.elem());

      System.out.println("...finished");

   }
}

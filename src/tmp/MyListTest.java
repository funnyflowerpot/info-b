package tmp;

public class MyListTest {

   public static void main(String[] args) {

      /*
       * not absolute requirements
       */
      MyList<String> l = new MyList<>();
      System.out.println("Test clone() in MyList...");
      System.out.println("...test not absolute requirements...");
      assertTrue(l.clone() != l, "clone has the same reference as original");
      assertTrue(l.clone().getClass() == l.getClass(),
            "clone has not the same class as original");
      assertTrue(l.clone().equals(l), "clone should be equal to original");
      /*
       * is anything copied at all?
       */
      l.add("A");
      MyList<String> clone = l.clone();
      System.out.println("...test if inner structure is copied/clones");
      assertEquals("A", clone.elem());
      /*
       * test for independence
       */
      System.out.println("...test independence...");
      l.add("B");
      clone.advance();
      assertTrue(clone.endpos(), "clone should be at endpos");
      clone.add("C");
      clone.add("D");
      l.advance();
      l.advance();
      assertTrue(l.endpos(), "original should be at endpos");
      /* l now has A B, clone has A C D */
      l.reset();
      l.delete();
      clone.reset();
      assertEquals("A", clone.elem());
      System.out.println("...finished.");

   }

   private static void assertEquals(Object expected, Object actual) {
      if (!expected.equals(actual)) {
         System.out
               .println("FAIL: expected " + expected + " but was " + actual);
      }
   }

   private static void assertTrue(boolean expected, String message) {
      if (!expected) {
         System.out.println("FAIL " + message);
      }
   }
}

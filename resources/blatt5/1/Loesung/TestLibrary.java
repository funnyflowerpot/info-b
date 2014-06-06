package library;

import util.List;
import library.Book;
import library.BluRay;
import library.Library;
import library.LibraryItem;

/**
 * Class to test the library implementation.
 * 
 */
public class TestLibrary {

   /**
    * Main routine to start the program.
    * 
    * @param args
    *           the command line arguments
    */
   public static void main(String[] args) {

      System.out.println("Testing library operations...");
      Library lib = new Library();

      LibraryItem li1 = new BluRay("A Game of Thrones Season 2",
            "David Benioff");
      LibraryItem li2 = new Book("A Storm of Swords", "G.R.R. M.");
      li2.setBorrowed(true);

      System.out.println("...test for empty library at the beginning...");

      List result = lib.search("A");
      assertTrue(result.empty(), "had to be empty");

      lib.addItem(li1);
      lib.addItem(li2);

      System.out.println("...test query for all inserted elements...");
      result = lib.search("A");
      assertEquals("A Game of Thrones Season 2 David Benioff",
            ((LibraryItem) result.elem()).getDescription());
      result.advance();
      assertEquals("A Storm of Swords by G.R.R. M.",
            ((LibraryItem) result.elem()).getDescription());
      result.advance();
      assertTrue(result.endpos(), "more than two results in list");

      System.out.println("...test query for one inserted element...");
      result = lib.search("A Game of Thrones");
      li1 = (LibraryItem) result.elem();
      assertEquals("A Game of Thrones Season 2 David Benioff",
            li1.getDescription());
      result.advance();
      assertTrue(result.endpos(), "more than one result in list");

      System.out.println("...test deletion of one element...");
      lib.deleteItem(li1);
      result = lib.search("A");
      li2 = (LibraryItem) result.elem();
      assertEquals("A Storm of Swords by G.R.R. M.", li2.getDescription());
      result.advance();
      assertTrue(result.endpos(), "more than one result in library");

      System.out.println("...test borrowed status unaffected by library...");
      assertTrue(li2.isBorrowed(), "is not borrowed, but should be");
      assertFalse(li1.isBorrowed(),"is borrowed, but should not be");
      System.out.println("...finished");
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

   private static void assertFalse(boolean expected, String message) {
      if (expected) {
         System.out.println("FAIL " + message);
      }
   }

}

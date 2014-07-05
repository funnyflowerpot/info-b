package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Tests FileArray
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class FileArrayTest {

   public static void main(String[] args) {

      System.out.println("Test FileArray implementation...");

      System.out.println("...test creation...");

      try (FileArray file = new FileArray("array.dat");) {
         System.out.println("FAIL did not throw FileNotFoundException");
      } catch (FileNotFoundException e1) {

      } catch (IOException e1) {
         e1.printStackTrace();
      }

      Integer[] array = { 1, 2, 3, 4, 5 };

      try (FileArray file = new FileArray(array, "array.dat");) {
      } catch (IOException e1) {
         e1.printStackTrace();
      }

      try (FileArray file = new FileArray("array.dat");) {
         Integer[] copy = new Integer[5];

         System.out.println("...test get() and length()...");

         for (int i = 0; i < file.length(); i++) {
            copy[i] = file.get(i);
         }
         arrayEquals(array, copy);

         System.out.println("...test set() and length()...");
         for (int i = 0; i < file.length(); i++) {
            file.set(i, i);
            array[i] = i;
            copy[i] = file.get(i);
         }
         arrayEquals(array, copy);

         System.out.println("...test Exception in set()...");
         try {
            file.set(-1, 0);
            System.out
                  .println("FAIL did not throw ArrayIndexOutOfBoundsException");
         } catch (ArrayIndexOutOfBoundsException e) {

         }

         System.out.println("...test Exception in get()...");
         try {
            file.get(-1);
            System.out
                  .println("FAIL did not throw ArrayIndexOutOfBoundsException");
         } catch (ArrayIndexOutOfBoundsException e) {

         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      System.out.println("...clean up...");

      File f = new File("array.dat");
      f.delete();

      System.out.println("...finished");

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

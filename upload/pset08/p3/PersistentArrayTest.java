package pset08.p3;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import testing.TestingLibrary;

/**
 * @author pwicke, sriegl
 *
 * Test PersistentArray on Herz and Nieren.
 */
public class PersistentArrayTest {
  
  
  /**
   * Helper method to print file contents. This method is quick and dirty, as
   * it serves only for files smaller than 1kb, which is sufficient for our
   * tests.
   * 
   * @param file file to be printed
   * @return byte array containing file contents
   */
  private static byte[] getFileContent(File file) {
    try (FileInputStream fis = new FileInputStream(file)) {
      byte[] buffer = new byte[1024]; // will be large enough for our tests      
      int readBytes = fis.read(buffer, 0, 1024);
      return Arrays.copyOf(buffer, readBytes); // crop unnecessary elements
    } catch (IOException e) {
      System.err.println("Warning: Could not print file content. Continueing.");
      return new byte[0];
    }
  }
  
  /**
   * Helper method that does, what its name claims. In hex code.
   * 
   * @param data data that is to be converted
   * @return stringified version of data
   */
  private static String byteArrayToString(byte[] data) {
    StringBuilder builder = new StringBuilder();
    for(byte b : data)
      builder.append(String.format("%02x", b));
    return builder.toString();
  }
  
  /**
   * This is where the magic happens.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    try {
      
      System.out.println("Now testing PersistentArray on Herz and Nieren.");
      
      String fileContent1;
      String fileContent2;
      String arrayString1;
      String arrayString2;
      Integer[] data = {3, 1, 3, 3, 7, 3};
      
      // we are only interested in the path for a temporary file, so let's
      // get it via a VM method instead of an own implementation
      File tempFile1 = File.createTempFile("info-b.pset08.p3.", null);
      File tempFile2 = File.createTempFile("info-b.pset08.p3.", null);


      /*
       * test: fail on illegal file path and illegal file format
       */
      
      TestingLibrary.printTestException(new Runnable() {
        @Override
        public void run() {
          try {
            new PersistentArray<Integer>("r2d2_c3po+-*/<§$%&>");
          } catch (IOException e) {
            throw new RuntimeException("(IOException) " + e.getMessage());
          }
        }
      }, "illegal file name specified");
      
      TestingLibrary.printTestException(new Runnable() {
        @Override
        public void run() {
          try {
            new PersistentArray<Integer>("src/pset08/p1/thehobbit.txt");
          } catch (IOException e) {
            throw new RuntimeException("(IOException) " + e.getMessage());
          }
        }
      }, "file not containing array specified");

      
      /*
       * test: value changes in array cause changes in file content
       */
      
      final PersistentArray<Integer> pa1 = new PersistentArray<Integer>(data, tempFile1.getAbsolutePath());
      
      fileContent1 = byteArrayToString(getFileContent(tempFile1));
      arrayString1 = pa1.toString();
      pa1.setElement(2, 4);
      pa1.setElement(3, 5);
      fileContent2 = byteArrayToString(getFileContent(tempFile1));
      arrayString2 = pa1.toString();
      
      TestingLibrary.printTest(!fileContent1.equals(fileContent2), 
          "changing array changes file content",
          arrayString1, arrayString2);

      
      /*
       * test: value changes in array cause changes in file content
       */

      fileContent1 = byteArrayToString(getFileContent(tempFile1));
      arrayString1 = pa1.toString();
      // following change already happened
      pa1.setElement(3, 5);
      fileContent2 = byteArrayToString(getFileContent(tempFile1));
      arrayString2 = pa1.toString();
      
      TestingLibrary.printTest(fileContent1.equals(fileContent2), 
          "modifying array with same values does not change file content",
          arrayString1, arrayString2);
      
      
      /*
       * test: invalid indexes specified
       */

      TestingLibrary.printTestException(new Runnable() {
        @Override
        public void run() {
          pa1.getElement(-2);
        }
      }, "error on illegal index for getElement(int)");
      
      TestingLibrary.printTestException(new Runnable() {
        @Override
        public void run() {
          try {
            pa1.setElement(999, 42);
          } catch (IOException e) {
            // before this line, IndexOutOfBoundsException will be thrown
            System.err.println("This line should never have been reached.");
          }
        }
      }, "error on illegal index for setElement(int, Integer)");
      
      
      /*
       * test: access fails after underlying file closed
       */
      
      pa1.close();
      
      TestingLibrary.printTestException(new Runnable() {
        @Override
        public void run() {
          try {
            pa1.setElement(0, 42);
          } catch (IOException e) {
            throw new RuntimeException("(IOException) " + e.getMessage());
          }
        }
      }, "array not longer writable after close()");

      
      /*
       * test: read existing file
       */
      
      PersistentArray<Integer> pa2 = new PersistentArray<Integer>(tempFile1.getAbsolutePath());
      
      fileContent1 = byteArrayToString(getFileContent(tempFile1));
      arrayString1 = pa2.toString();
      // we take values of following lines from before
      //fileContent2 = byteArrayToString(getFileContent(tempFile1));
      //arrayString2 = persistentArrayToString(pa1);
      
      TestingLibrary.printTest(fileContent1.equals(fileContent2), 
          "restored array equals last array we had",
          arrayString1, arrayString2);
      
      pa2.close();
      
      
      /*
       * clean up
       */
      
      tempFile1.delete();
      tempFile2.delete();
      
      
    } catch (IOException e) {
      System.err.println("Unexpected IO exception occured. Program execution aborted.");
      e.printStackTrace(); 
    }
  }

}

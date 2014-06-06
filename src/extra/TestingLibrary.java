package extra;

import java.util.Arrays;

public class TestingLibrary {


  /**
   * Execute dangerous code and print test results nicely. The test will only
   * succeed iff the dangerous code will fail.
   * 
   * @param dangerousCode runnable interface enclosing code that ought to fail
   * @param message some nice description for the user
   * @param args optional debug information
   */
  public static void printTestException(
      Runnable dangerousCode, String message, Object... args) {

    boolean testSuccessful = false;
    
    try {
      dangerousCode.run();
    } catch(Exception e) {
      testSuccessful = true;
    }

    printTest(testSuccessful, message, args);
  }
  
  /**
   * Print test results nicely.
   * 
   * @param testResult determines success of test
   * @param message description of test
   * @param args optional additional information
   */
  public static void printTest(boolean testResult, String message, Object... args) {
    System.out.printf("[%s]  %s", testResult ? " OK " : "FAIL", message);
    if(args.length > 0)
      System.out.printf(" (debug info: %s)", Arrays.toString(args));
    System.out.println();
  }
  
}

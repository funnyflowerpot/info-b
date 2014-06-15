package testing;

import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class TestingLibrary {
  
  public static Scanner scanner = null;


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
  
  
  // TODO (sr): DOKU
  public static String promptInput(String message, String[] validAnswers) {
    String input;
    if(scanner == null)
      scanner = new Scanner(System.in);
    while(true) {
      System.out.print(message);
      input = scanner.nextLine();
      for(String validAnswer : validAnswers)
        if(validAnswer.equals(input))
          return input;
    }
  }
  
  /**
   * Query for program arguments graphically, if given array is empty. Add a
   * call of this method at the beginning of main to allow users to provide
   * command line arguments via a GUI, if there were not already arguments
   * give (i.e. <code>args</code> is non-empty). This is nice for not having
   * to change run configurations in Eclipse for each new test.
   *
   * @return array with prompted command line arguments
   */
  public static String[] populateProgramArguments(String[] args) {
    if(args.length > 0)
      return args;
    String input = JOptionPane.showInputDialog("Please provide some command line arguments:");
    return input.split(" ");
  }
}

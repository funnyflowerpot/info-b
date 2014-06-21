package testing;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Testing library with very exquisitely chosen testing methods
 * to provide maximal testing comfort
 * @author sriegl, pwicke
 * @date 16.06.2014
 */
public class TestingLibrary {
  
  public static Scanner scanner = null;
  
  // Could be useful: REGEX that matches valid Windows path or valid Linux path
  @SuppressWarnings("unused")
  private final static String pathREGEXWin 	 = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?"; 
  @SuppressWarnings("unused")
  private final static String pathREGEXLinux 	 = "^(/)?([^/\0]+(/)?)+$";


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
  
  
  /**
   * Prompt the user via console to enter one of a set of given input strings.
   * Note that this method should be used exclusively and a separate usage of
   * console input reading should be avoided, since a persistent Scanner-
   * instance will be created after first call.
   * 
   * @param message a short description of the input that is to be expected
   *                by the user
   * @param validAnswers the set of valid answers, of which one the user has
   *                     to input
   * @return one element of the set of valid answers
   * @throws NoSuchElementException if no input can be read
   * @throws IllegalStateException if input stream is closed
   */
  public static String promptInput(String message, String[] validAnswers) {
    // create scanner, if not used already
    if(scanner == null)
      scanner = new Scanner(System.in);
    String input;
    // prompt until we have valid input
    while(true) {
      System.out.print(message);
      input = scanner.nextLine();
      // exit as soon as user entered one of valid answers
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
    if(input.equals(""))
      return new String[0];
    return input.split(" ");
  }
}

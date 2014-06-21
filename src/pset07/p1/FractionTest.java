package pset07.p1;

import java.util.Arrays;

/**
 * @author sriegl
 *
 * Test the updated Fraction class on correctly implemented instance pool.
 */
public class FractionTest {

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
   * This is where the magic happens.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    
    Fraction frac1 = Fraction.parseFraction("3/4");
    Fraction frac2 = Fraction.getInstance(3, 4);
    
    Fraction frac3 = Fraction.getInstance(3, 2);
    Fraction frac4 = Fraction.parseFraction("3/2");
   
    System.out.println("Now testing... FRACTIONS");
        
    
    // different ways of instantiation, checking on different instances
    
    printTest(frac1 == frac2, "first pair of fractions are identical");
    printTest(frac3 == frac4, "second pair of fractions are identical");
    printTest(frac1 != frac3, "fractions from different pairs are not identical (1)");
    printTest(frac1 != frac4, "fractions from different pairs are not identical (2)");
    printTest(frac2 != frac3, "fractions from different pairs are not identical (3)");
    printTest(frac2 != frac4, "fractions from different pairs are not identical (4)");
    
    
    // mathematical operations
    
    printTest(frac1.add(frac2) == frac3,
        "sum of fractions are identical to fraction from pool");
    
    printTest(frac3.substract(frac2) == frac1,
        "difference of fractions are identical to fraction from pool");

    
    // most important test of all
    
    printTest(Fraction.parseFraction("1/2") == Fraction.parseFraction("2/4"), 
        "Fraction.parseFraction(\"1/2\") == Fraction.parseFraction(\"2/4\")");

    
    System.out.println();
    System.out.println("As additional service we present new math:");
    System.out.println("http://www.youtube.com/watch?v=DfCJgC2zezw");
  }

}

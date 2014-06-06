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
    
    // frac1 != frac2, since "new" forces a new instance, not grabbed from
    // pool. Furthermore, it will not get inserted into pool, since there is
    // an instance already present.
    Fraction frac1 = Fraction.parseFraction("3/4");
    Fraction frac2 = new Fraction(3, 4);
    
    // frac3 == frac4, since frac3 is in pool and instance for frac4 will be
    // grabbed from pool by parseFraction()
    Fraction frac3 = new Fraction(3, 2);
    Fraction frac4 = Fraction.parseFraction("3/2");
   
    System.out.println("Now testing... FRACTIONS");
        
    
    
    // different ways of instantiation
    
    printTest(frac1 != frac2, 
        "fractions are not identical (parsing before instantiation)");
    
    printTest(frac3 == frac4,
        "fractions are identical (parsing after instantiation)");
    
    
    
    // mathematical operations
    
    printTest(frac1.add(frac2) == frac3,
        "sum of fractions are identical to fraction from pool");
    
    printTest(frac3.substract(frac2) == frac1,
        "difference of fractions are identical to fraction from pool");

    printTest(frac3.substract(frac1) != frac2,
        "difference of fractions are not identical to fraction outside pool");

    
    
    // the most important test of all
    
    printTest(Fraction.parseFraction("1/2") == Fraction.parseFraction("2/4"), 
        "Fraction.parseFraction(\"1/2\") == Fraction.parseFraction(\"2/4\")");

    
    
    System.out.println();
    System.out.println("As extra service we present how to do algebra correctly:");
    System.out.println("http://www.youtube.com/watch?v=DfCJgC2zezw");
  }

}

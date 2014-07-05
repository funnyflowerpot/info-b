package math;

public class FractionTest {

   public static void main(String[] args) {

      Fraction a = Fraction.createFraction(1, 2);
      Fraction b = Fraction.parseFraction("2/4");
      Fraction c = Fraction.createFraction(4);

      System.out.println("Testing Fraction-ObjectPool...");
      if (a != b) {
         System.out.println("FAIL: Fractions " + a + " and " + b
               + " should have the same reference.");
      }
      if (b == c) {
         System.out.println("FAIL: Fractions " + b + " and " + c
               + " should not have the same reference.");
      }
      System.out.println("...finished.");

   }

}

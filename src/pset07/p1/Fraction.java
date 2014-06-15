package pset07.p1;

import java.util.HashMap;
import java.util.Map;

/**
 * Every instance of Fraction represents a fraction with numerator and
 * decorator.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
@SuppressWarnings("serial")
public class Fraction extends Number {

   /**
    * The regular expression that defines the String representation of a
    * Fraction.
    */
   public static final String REGEX = "-?\\d+/[1-9]\\d*";

   /**
    * A internal pool like in Integer to cache created instances. The pool
    * follows the singleton design pattern internally, so leaving out a
    * getInstance()-method.
    */
   private static Map<Double, Fraction> instPool;
   
   /**
    * Creates greatest common divisor for a and b.
    * 
    * @param a
    * @param b
    * @return the greatest common divisor for a and b.
    */
   public static int gcd(int a, int b) {
      return b == 0 ? a : gcd(b, a % b);

   }

   /**
    * Parses a Fraction from a String by using REGEX. Throws RuntimeException if
    * String s does not contain valid Fraction.
    * 
    * @param s
    *           String to be parsed
    * @return parsed String as Fraction
    * @throws RuntimeException
    *            if String s is not a valid Fraction
    */
   public static Fraction parseFraction(String s) {
      if (!s.matches(REGEX)) {
         throw new RuntimeException("Parsing error");
      }
      String[] splitted = s.split("/");
      // Formerly:
      //return new Fraction(Integer.parseInt(splitted[0]),
      //    Integer.parseInt(splitted[1]));
      return getInstance(Integer.parseInt(splitted[0]),
          Integer.parseInt(splitted[1]));
   }

   private int numerator;

   private int denominator;

   /**
    * Creates a Fraction object with numerator and denominator 1, cancels the
    * fraction with creation.
    * 
    * @param numerator
    */
   private Fraction(int numerator) {
      this(numerator, 1);
   }

   /**
    * Creates a Fraction object with numerator and denominator, cancels the
    * fraction by creation. Denominator == 0 is not allowed.
    * 
    * @param numerator
    * @param denominator
    */
   private Fraction(int numerator, int denominator) {
      if (denominator == 0) {
         throw new RuntimeException("denominator == 0 is not possible");
      }

      /*
       * creates greatest common divisior.
       */
      int gcd = Fraction.gcd(numerator, denominator);
      /*
       * check sign, make denominator positive
       */
      if (denominator / gcd < 0) {
         gcd *= -1;
      }

      this.numerator = numerator / gcd;
      this.denominator = denominator / gcd;
   }
   
   /**
    * Get a fraction instance specified by numerator and denominator. Insert
    * instance into pool, if a new Fraction instance has to be generated.
    * Create the instance pool, if necessary.
    * 
    * @param num the numerator
    * @param denom the denominator
    * @return a fraction contained in to the pool, newly created if necessary
    */
   public static Fraction getInstance(int num, int denom) {
      // create the instance pool, if not already present
      if(instPool == null)
        instPool = new HashMap<Double, Fraction>();
      // get instance from pool or create a new instance and insert it into
      // pool
      Fraction fraction;
      double key = (double) num / (double) denom;
      if(instPool.containsKey(key)) {
        fraction = instPool.get(key);
      } else {
        fraction = new Fraction(num, denom);
        instPool.put(key, fraction);
      }
      return fraction;
    }
    
   /**
    * Adds addend to this Fraction and return the result as a new Fraction.
    * 
    * @param addend
    *           Fraction to be added
    * @return the sum as a new Fraction
    */
   public Fraction add(Fraction addend) {
     // Formerly:
     //return new Fraction(this.numerator * addend.denominator
     //    + this.denominator * addend.numerator, this.denominator
     //    * addend.denominator);
     return getInstance(this.numerator * addend.denominator
         + this.denominator * addend.numerator, this.denominator
         * addend.denominator);
   }

   /**
    * Divides this Fraction with another Fraction
    * 
    * @param another
    *           Fraction to divide this Fraction by
    * @return quotient as a new Fraction
    */
   public Fraction divide(Fraction another) {
     // Formerly:
     //return new Fraction(this.numerator * another.denominator,
     //    this.denominator * another.numerator);
     return getInstance(this.numerator * another.denominator,
         this.denominator * another.numerator);
   }

   /**
    * 
    * @return denominator of this Fraction
    */
   public int getDenominator() {
      return this.denominator;
   }

   /**
    * 
    * @return numerator of this Fraction
    */
   public int getNumerator() {
      return this.numerator;
   }

   /**
    * Multiplies this Fraction with another Fraction
    * 
    * @param another
    *           Fraction to multiply this Fraction with
    * @return product as a new Fraction
    */
   public Fraction multiply(Fraction another) {
      // Formerly: 
      //return new Fraction(this.numerator * another.numerator, this.denominator
      //     * another.denominator);
      return getInstance(this.numerator * another.numerator, this.denominator
            * another.denominator);
   }

   /**
    * Multiplies this Fraction with n.
    * 
    * @param n
    *           factor to multiply with
    * @return product as a new Fraction
    */
   public Fraction multiply(int n) {
     // Formerly:
     //return new Fraction(this.numerator * n, this.denominator);
     return getInstance(this.numerator * n, this.denominator);
   }

   /**
    * Multiplies this Fraction with all other Fraction instances given by
    * factors
    * 
    * @param factors
    *           Fraction instances to multiply this Fraction with
    * @return product as a new Fraction
    */
   public Fraction multiply(Fraction... factors) {
      Fraction result = this;
      for (int i = 0; i < factors.length; i++) {
         result = result.multiply(factors[i]);
      }
      return result;
   }

   /**
    * Subtracts subtrahend from this Fraction an returns the result as a new
    * Fraction.
    * 
    * @param subtrahend
    *           to be substracted Fraction
    * @return the difference as a new Fraction
    */
   public Fraction substract(Fraction subtrahend) {
     // Formerly:
     //return new Fraction(this.numerator * subtrahend.denominator
     //    - this.denominator * subtrahend.numerator, this.denominator
     //    * subtrahend.denominator);
     return getInstance(this.numerator * subtrahend.denominator
         - this.denominator * subtrahend.numerator, this.denominator
         * subtrahend.denominator);
   }

   /**
    * Returns a string representation of this Fraction such as
    * numerator/denominator.
    * 
    * @return This Fraction as a String
    */
   public String toString() {
      return numerator + "/" + denominator;
   }

   @Override
   public int intValue() {
      return (int) (doubleValue() + 0.5);
   }

   @Override
   public long longValue() {
      return (long) (doubleValue() + 0.5);
   }

   @Override
   public float floatValue() {
      return (float) doubleValue();
   }

   @Override
   public double doubleValue() {
      return (double) this.getNumerator() / (double) this.getDenominator();
   }
}

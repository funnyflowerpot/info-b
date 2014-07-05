package math;

import java.util.HashMap;

/**
 * Every instance of Fraction represents a fraction with numerator and
 * decorator. Fraction instances may be instantiated via
 * {@link #createFraction(int, int)}, {@link #createFraction(int)} or
 * {@link #parseFraction(String)}. Fraction instances are cancelled within
 * instantiation. Equal Fraction instances will have the same reference, e.g.
 * </br>
 * <code>Fraction.parseFraction("1/2") == Fraction.parseFraction("2/4")</code>
 * </br> delivers <code>true</code>
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Fraction extends Number {

   private static HashMap<Fraction, Fraction> fractions;

   /**
    * The regular expression that defines the String representation of a
    * Fraction.
    */
   public static final String REGEX = "-?\\d+/[1-9]\\d*";

   static {
      fractions = new HashMap<>();
   }

   /**
    * Creates a Fraction object with numerator and denominator 1.
    * 
    * @param numerator
    * 
    * @return the created Fraction, may have the same reference as an already
    *         created but equal Fraction
    */
   public static Fraction createFraction(int numerator) {
      return createFraction(numerator, 1);
   }

   /**
    * Creates a Fraction object with numerator and denominator, cancels the
    * fraction by creation. Denominator == 0 is not allowed. Throws Exception if
    * <code>denominator</code> is 0.
    * 
    * @param numerator
    * @param denominator
    * 
    * @throws NumberFormatExcpetion
    *            if <code>denominator</code> ist 0
    * 
    * @return the created Fraction, may have the same reference as an already
    *         created but equal Fraction
    */
   public static Fraction createFraction(int numerator, int denominator) {
      Fraction f = new Fraction(numerator, denominator);
      if (fractions.containsKey(f)) {
         return fractions.get(f);
      } else {
         fractions.put(f, f);
         return f;
      }

   }

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
    * Parses a Fraction from a String by using REGEX. Throws
    * NumberFormatException if String s does not contain valid Fraction.
    * 
    * @param s
    *           String to be parsed
    * @return parsed String as Fraction,, may have the same reference as an
    *         already created but equal Fraction
    * @throws NumberFormatException
    *            if String s is not a valid Fraction
    */
   public static Fraction parseFraction(String s) {
      if (!s.matches(REGEX)) {
         throw new NumberFormatException("Parsing error");
      }
      String[] splitted = s.split("/");
      return createFraction(Integer.parseInt(splitted[0]),
            Integer.parseInt(splitted[1]));
   }

   private int denominator;

   private int numerator;

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
    * Adds addend to this Fraction and return the result as a new Fraction.
    * 
    * @param addend
    *           Fraction to be added
    * @return the sum as a new Fraction
    */
   public Fraction add(Fraction addend) {
      return createFraction(this.numerator * addend.denominator
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
      return createFraction(this.numerator * another.denominator,
            this.denominator * another.numerator);
   }

   @Override
   public double doubleValue() {
      return (double) this.getNumerator() / (double) this.getDenominator();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Fraction other = (Fraction) obj;
      if (denominator != other.denominator)
         return false;
      if (numerator != other.numerator)
         return false;
      return true;
   }

   @Override
   public float floatValue() {
      return (float) doubleValue();
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

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + denominator;
      result = prime * result + numerator;
      return result;
   }

   @Override
   public int intValue() {
      return (int) (doubleValue() + 0.5);
   }

   @Override
   public long longValue() {
      return (long) (doubleValue() + 0.5);
   }

   /**
    * Multiplies this Fraction with another Fraction
    * 
    * @param another
    *           Fraction to multiply this Fraction with
    * @return product as a new Fraction
    */
   public Fraction multiply(Fraction another) {
      return createFraction(this.numerator * another.numerator, this.denominator
            * another.denominator);
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
    * Multiplies this Fraction with n.
    * 
    * @param n
    *           factor to multiply with
    * @return product as a new Fraction
    */
   public Fraction multiply(int n) {
      return createFraction(this.numerator * n, this.denominator);
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
      return createFraction(this.numerator * subtrahend.denominator
            - this.denominator * subtrahend.numerator, this.denominator
            * subtrahend.denominator);
   }

   /**
    * Returns a string representation of this Fraction such as
    * numerator/denominator.
    * 
    * @return This Fraction as a String
    */
   @Override
   public String toString() {
      return numerator + "/" + denominator;
   }
}

package math;

/**
 * Contains a main class to execute a Calculate instance, which calculates an
 * operation on two Number (Fraction or Double) instances.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Calculator {

   /*
    * The Operators
    */
   public static final String ADD = "+";
   public static final String SUBSTRACT = "-";
   public static final String MULTIPLY = "*";
   public static final String DIVIDE = "/";

   /**
    * @param args
    */
   public static void main(String[] args) {

      if (args.length != 3) {
         System.err.println("invalid number of arguments (must be three)");
         printUsage();
         System.exit(1);
      } else {
         Calculator calc = new Calculator();
         String result = calc.calc(args[0], args[1], args[2]);
         if (result == null) {
            System.err.println(calc.getErrorMessage());
            printUsage();
            System.exit(1);
         } else {
            System.out.println("= " + result);
         }
      }
   }

   /**
    * Prints a short description of the usage on the standard console.
    */
   private static void printUsage() {
      System.out.println("Usage: java Calculator operand operator operand");
      System.out.println("operand may be a floating point value or integer or fraction");
      System.out.println("a fraction is defined by " + Fraction.REGEX);
      System.out.println("valid operators are +,-, *, /");
   }

   /**
    * Holds the error message of the last call of
    * {@link #calc(String, String, String)} which went wrong.
    */
   private String errorMessage;

   /**
    * Calculates the formular given by <code>a operator b</code>. If a, operator
    * or b are not valid, null will be returned and errorMessage will hold a
    * description of the error that occurred.
    * 
    * @param a
    *           the first Number argument
    * @param operator
    *           operator to connect the arguments with
    * @param b
    *           the second Number argument
    * @return The result of the operation as Fraction or Double, or null if an
    *         error occured.
    */

   private Number calc(Number a, String operator, Number b) {

      if (a instanceof Fraction && b instanceof Fraction) {
         return calc((Fraction) a, operator, (Fraction) b);
      }

      Number result;

      switch (operator) {

      case ADD:
         result = a.doubleValue() + b.doubleValue();
         break;
      case SUBSTRACT:
         result = a.doubleValue() - b.doubleValue();
         break;
      case MULTIPLY:
         result = a.doubleValue() * b.doubleValue();
         break;
      case DIVIDE:
         result = a.doubleValue() / b.doubleValue();
         break;
      default:
         this.errorMessage = "Operation " + operator + " unknown";
         return null;
      }

      return result;
   }

   /**
    * Calculates the formular given by <code>a operator b</code>. If a, operator
    * or b are not valid, null will be returned and errorMessage will hold a
    * description of the error that occurred.
    * 
    * @param a
    *           the first Fraction argument
    * @param operator
    *           operator to connect the arguments with
    * @param b
    *           the second Fraction argument
    * @return The result of the operation as Fraction or null.
    */
   private Fraction calc(Fraction a, String operator, Fraction b) {

      Fraction result;
      /*
       * differentiate between operators and compute regarding operation.
       */
      switch (operator) {

      case ADD:
         result = a.add(b);
         break;
      case SUBSTRACT:
         result = a.substract(b);
         break;
      case MULTIPLY:
         result = a.multiply(b);
         break;
      case DIVIDE:
         result = a.divide(b);
         break;
      default:
         this.errorMessage = "Operation " + operator + " unknown";
         return null;
      }

      return result;

   }

   /**
    * Calculates the formular given by <code>a operator b</code>. If a, operator
    * or b are not valid, null will be returned and errorMessage will hold a
    * description of the error that occured.
    * 
    * @param a
    *           String representation of the first argument
    * @param operator
    *           operator to connect the arguments with
    * @param b
    *           String representation of the second argument
    * @return The result of the operation as String or null.
    */
   public String calc(String a, String operator, String b) {

      Number numberA = parseNumber(a);
      Number numberB = parseNumber(b);

      if (numberA == null || numberB == null) {
         return null;
      }

      return calc(numberA, operator, numberB).toString();
   }

   /**
    * Return the error message of the last call of
    * {@link #calc(String, String, String)} which went wrong.
    * 
    * @return the last error message
    */
   public String getErrorMessage() {
      return this.errorMessage;
   }

   /**
    * Parses the given String to a Double or a Fraction. If it cannot be parsed,
    * null will be returned and errorMessage will hold a description of the
    * error that occurred.
    * 
    * @param number
    *           String to be parsed to a Fraction or a Double
    * @return A Fraction or a Double, representing the given String or null.
    */
   private Number parseNumber(String number) {
      if (number.contains("/"))
         return parseFraction(number);
      return parseDouble(number);
   }

   /**
    * Parses the given String to a Double and returns it. If it cannot be
    * parsed, null will be returned and errorMessage will hold a description of
    * the error that occurred.
    * 
    * @param number
    *           String to be parsed to a double
    * @return A Double representing the given String or null.
    */
   private Double parseDouble(String number) {
      if (!number.matches("-?(\\d+\\.?\\d*)|(\\d*\\.\\d+)")) {
         errorMessage = number + " is not a valid Double";
         return null;
      }
      return Double.parseDouble(number);
   }

   /**
    * Parses the given String to a Fraction and returns it. If it cannot be
    * parsed, null will be returned and errorMessage will hold a description of
    * the error that occurred.
    * 
    * @param fraction
    *           String to be parsed to a fraction
    * @return A Fraction representing the given String or null.
    */
   private Fraction parseFraction(String fraction) {
      if (!fraction.matches(Fraction.REGEX)) {
         errorMessage = fraction + " is not a valid Fraction";
         return null;
      }
      return Fraction.parseFraction(fraction);

   }

}

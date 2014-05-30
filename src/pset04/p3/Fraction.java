package pset04.p3;

/**
 * This class is able to represent a fraction.
 * Therefore it takes a numerator and a denominator.
 * fraction = numerator / denominator
 * 
 * @author Wicke
 * @date 04/30/2014
 */

public class Fraction extends Number {
	
	/**										
	 * Number extends Object implements Serializable
	 * A serializable class must declare its own serialVersionUID
	 * by declaring a field named "serialVersionUID" that must be
	 * static, final, and of type long
	 */
	private static final long serialVersionUID = -8226543895810377536L;
	
	// data 
	private int numerator;
	private int denominator;
	
	// defining the regular expression (regex) that matches with a fraction string
	private static final String REGEX = "^-?(0|[1-9][0-9]*)/[1-9][0-9]*$";
	// -?  -> optional algebraic sign
	// (0|[1-9][0-9]+)  ->  either choose '0' or choose a number between 1 and 9 
	//					  ->  followed by optional numbers between 0 and 9 - defining numerator
	// /[1-9][0-9]+	  ->  fraction symbol followed by already expl. expression - denominator
	
	// constructors (chained)
	Fraction(int numerator, int denominator){	
		if(denominator == 0) 
			throw new RuntimeException("Denominator can't be 0."); 
		// produce greatest common divisor of numerator and denominator
		int commonDivisor = Fraction.commonDivisor(numerator, denominator);
		// check sign, make denominator positive
		if(denominator / commonDivisor < 0){
			commonDivisor *= -1;
		}
		setNumerator(numerator/commonDivisor);
		setDenominator(denominator/commonDivisor);
	}
	Fraction(int integer){					
		this(integer, 1);
	}
	Fraction(){
		this(0,1);
	}
	
	// Getter and Setter
	/**
	 * Getter method for numerator
	 * @return numerator
	 */
	public int getNumerator() {
		return numerator;
	}

	/**
	 * Setter method for numerator
	 * @param numerator of integer type
	 */
	public void setNumerator(int numerator) {
		// '0' as numerator is a 0 fraction
		if(numerator == 0) setDenominator(0);
		this.numerator = numerator;
	}
	
	/**
	 * Getter method for denominator
	 * @return denominator of integer type
	 */
	public int getDenominator() {
		return denominator;
	}

	/**
	 * Setter method for denominator
	 * @param denominator
	 */
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
	// class method
	/**
	 * Checks if the given String is in Fraction format
	 * @param s
	 * @return false if not a string format
	 */
	public static boolean checkIfFraction(String s){
	  // trim will cut the space at end or beginning of string
		if(s.trim().matches(REGEX))
			return true;
		else
			return false;
	}
	/**
	 * This class method is able to check whether a string is in an appropriate 
	 * fraction format and then returns the fraction as a <code>Fraction</code>
	 * @param fraction_string
	 * @return Fraction fraction that once was a string
	 * @throws RuntimeException if input string does not match REGEX format
	 */
	public static Fraction parseFraction(String fraction_string){
		// Check if the string for correct fraction format with matches-method
		if(!checkIfFraction(fraction_string))
			throw new RuntimeException("Invalid FRACTION representation.\n Use following expression: (-)*(any real number)(/)(any real number, not 0)\n*optional");
		// Use String.split(String regex, int limit) to split the String 
		// at the '/' symbol and allow 2 new Strings to save numerator
		// and denominator which will be stored in String[2] splitResult
		// finally return the built fraction
		else{
			String[] splitResult = fraction_string.split("/",2); 
			int numerator 	= Integer.parseInt(splitResult[0]);
			int denominator	= Integer.parseInt(splitResult[1]);
			return new Fraction(numerator, denominator);
		}
	}
	// methods	
	/**
	 * This method multiplies the fraction with an integer
	 * @param factor of <code>Fraction</code> type
	 * @return Fraction that is the product 
	 */
	public Fraction multiply(int factor){
		// numerator/denominator * factor = numerator*factor / denominator
		return new Fraction(this.numerator*factor,this.denominator);
	}
	/**
	 * This method multiplies two fractions with each other
	 * by multiplying each numerator and each denominator.
	 * @param factor of <code>Integer</code> type
	 * @return Fraction that is the product
	 */
	public Fraction multiply(Fraction factor){
		// num/denom * num/denom = num*num/denom*denom
		return new Fraction(this.numerator*factor.numerator,this.denominator*factor.denominator);
	}
	/**
	 * This method divides two fractions
	 * @param divisor of <code>Fraction</code> type
	 * @return Fraction that is the quotient 
 	 */
	public Fraction divide(Fraction divisor){
		// (num01/denom01) / (num02/denom02) = (num01*denom02) / (denom01*num02)  
		return new Fraction(this.numerator*divisor.denominator, this.denominator*divisor.numerator);
	}
	/**
	 * This method multiplies multiple Fractions with one another
	 * @param multiple <code>Fraction</code> factors
	 * @return Fraction that is the product
	 */
	public Fraction multiply(Fraction... factors){
		// Create reference (product) that is this the Fraction of 'this.' instance  
		Fraction product = new Fraction(this.numerator,this.denominator);
		// Iterate through all factors and recursively multiply each factor with product
		for(Fraction factor : factors){
			product = product.multiply(factor);	
		}
		return product;									
	}
	/**
	 * This method adds the addend to the current Fraction
	 * and returns the sum as Fraction
	 * @param addend
	 * @return the sum of type <code>Fraction</code>	
	 */
	public Fraction add(Fraction addend){
		// E.g: a/b + c/d = (a*d+c*d)/b*d
		int sum_numerator = (this.numerator*addend.denominator)+(addend.numerator*this.denominator);
		int sum_denominator = (this.denominator*addend.denominator);
		return new Fraction(sum_numerator,sum_denominator);
	}
	/**
	 * This method subtracts a Fraction from another Fraction and returns the outcoming Fraction.
	 * @param subtrahend 
	 * @return outcoming <code>Fraction</code>
	 */
	public Fraction substract(Fraction subtrahend){
		// E.g: a/b + c/d = (a*d-c*d)/b*d
		int out_numerator = (this.numerator*subtrahend.denominator)-(subtrahend.numerator*this.denominator);
		int out_denominator = (this.denominator*subtrahend.denominator);
		return new Fraction(out_numerator,out_denominator);
	}
	/**
	 * This method calculates the greatest common divisor of the two parameters
	 * @param a of type <code>int</code>
	 * @param b of type <code>int</code>
	 * @return the greatest common divisor of type <code>int</code>
	 */
	private static int commonDivisor(int a, int b){
		// As long as b is not 0, recursion with b=a and a%b=b
		// Otherwise return a, which then is the greatest common divisor
		return b == 0 ? a : commonDivisor(b, a % b);
	}
	
	// toString-Methode
	public String toString(){
		return numerator+"/"+denominator;
	}
	/**
	 *Abstract method of Number Implementation:
	 *Returns the value of the specified number as a double. This may involve rounding.
	 *@return Returns the value of the specified number as a double. This may involve rounding.
	 */
	@Override
	public double doubleValue() {
		// type-casting due to division - requires at least one float point value
		return (double) this.numerator/ (double) this.denominator; 
	}
	/**
	 * Abstract method of Number Implementation:
	 * Returns the value of the specified number as a float. This may involve rounding.
	 *@return the numeric value represented by this object after conversion to type float.
	 */
	@Override
	public float floatValue() {
		// type-casting due to division - requires at least one float point value
		return (float) this.numerator/ (float) this.denominator;
	}
	/**
	 * Abstract method of Number Implementation:
	 * Returns the value of the specified number as an int.
	 * This may involve rounding or truncation.
	 * @return the numeric value represented by this object after conversion to type int.
	 */
	@Override
	public int intValue() {
		return (int) this.numerator / (int) this.denominator;
	}
	/**
	 * Abstract method of Number Implementation:
	 * Returns the value of the specified number as a long.
	 * This may involve rounding or truncation.
	 * @return the numeric value represented by this object after conversion to type long.
	 */
	@Override
	public long longValue() {
		return (long) this.numerator / (long) this.denominator;
	}
}
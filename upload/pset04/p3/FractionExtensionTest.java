package pset04.p3;
import javax.swing.JOptionPane;

/**
 * This program is a simple fraction calculator which is able
 * to substract, divide, multiply and add two fractions with one another.
 * It will ask the user for input, correct the input and provides the result
 * of the calculation in a formatted manner.
 * @author Wicke
 * @date 09.05.2014 
 *
 */

public class FractionExtensionTest {
	

	public static void main(String[] args) {
		// Provide a more comfortable input mechanism for testing in eclipse.
		// This will emulate application start with command line arguments.
		if(args.length == 0) {
		  String input = JOptionPane.showInputDialog("Please provide some command line arguments:");
		  main(input.split(" ",3));
		  // main.args[] now contains all split parts of arguments, that were separated
		  // by a white space and puts them recursively in the main method
		  return;
		}
		// Check argument for parts, if not 3 ask for new arguments
		if(args.length !=3 ){
			String input = JOptionPane.showInputDialog("Wrong format (enter NUM OP NUM):");
		  	main(input.split(" ",3));
		  	return;
		}	
		// Checks if one of the following applies:
		// Valid OPERANDS
		// FRACTION op. OPERAND ^ OPERAND op. FRACTION ^ OPERAND op. OPERAND (whereas OPERAND is no FRACTION)									
		if( (Fraction.checkIfFraction(args[0]) && NumCheck.checkAllAny(args[2]))||(NumCheck.checkAllAny(args[0]) && Fraction.checkIfFraction(args[2]))||(NumCheck.checkAllAny(args[0])&&NumCheck.checkAllAny(args[2]))){
			// allocate memory for the 2 operands of type float
			float first, second;
			// check if first argument is Fraction and assign operands
			if(Fraction.checkIfFraction(args[0])){
				Fraction argument1 = Fraction.parseFraction(args[0]);
				first = argument1.floatValue();
				second = Float.parseFloat(args[2]);
			}
			// check if second argument is Fraction and assign operands
			else if(Fraction.checkIfFraction(args[2])){
				Fraction argument2 = Fraction.parseFraction(args[2]);
				first = Float.parseFloat(args[0]);
				second= argument2.floatValue();
			}
			// check if no argument is a Fraction and assign operands
			else {
				if(Fraction.checkIfFraction(args[0]))System.out.println("args[0] is a Fraction = "+args[0]+"\n");
				first = Float.parseFloat(args[0]);
				second = Float.parseFloat(args[2]);
			}
				// Do maths according to OPERATOR which is stored in args[1]
				switch(args[1]){
				case "+":	System.out.println(" = "+(float) (first+second));
							break;
				case "-":	System.out.print(" = "+(float) (first-second));
							break;
				case "*":	System.out.println(" = "+(float) (first*second));
							break;
				case "/":	System.out.println(" = "+(float) (first/second));
							break;			
				// In the default case, a wrong operator was used
				default: String input = JOptionPane.showInputDialog("Invalid operator (+,-,*,/):");
			  	main(input.split(" ",3));
			  	return;
				}
			}						
		else if(Fraction.checkIfFraction(args[0]) && Fraction.checkIfFraction(args[2])){	
			Fraction firstFrac 	= new Fraction(); 
			Fraction secondFrac = new Fraction();
			firstFrac  =  Fraction.parseFraction(args[0]);
			secondFrac =  Fraction.parseFraction(args[2]);	
			// Do maths according to OPERATOR which is stored in args[1]
			switch(args[1]){
			case "+":	System.out.println(" = "+firstFrac.add(secondFrac));
						break;
			case "-":	System.out.print(" = "+firstFrac.substract(secondFrac));
						break;
			case "*":	System.out.println(" = "+firstFrac.multiply(secondFrac));
						break;
			case "/":	System.out.println(" = "+firstFrac.divide(secondFrac));
						break;
			// In the default case, a wrong operator was used
			default: String input = JOptionPane.showInputDialog("Invalid operator (+,-,*,/):");
		  	main(input.split(" ",3));
		  	return;
			}
		}
		else {
			String input = JOptionPane.showInputDialog("Wrong format (enter NUM OP NUM):");
		  	main(input.split(" ",3));
		  	return;
		}
	}
}



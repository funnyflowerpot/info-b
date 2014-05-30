package pset04.p3;

/**
 * This class is a compilation of several check-methods
 * in order to evaluate which datatype a given string represents
 * long, int, double or float
 * @author Wicke
 * @date 15.05.2014 
 *
 */

public class NumCheck {

	/**
	 * Checks whether the given String is of any data type Integer, Long, Double or Float
	 * @param s
	 * @return true if of any type (Integer, Long, Double or Float)
	 */
	public static boolean checkAllAny(String s){
		if(NumCheck.checkInt(s) || NumCheck.checkFloat(s) || NumCheck.checkLong(s) || NumCheck.checkDouble(s))
			return true;
	return false;
	}
	public static boolean checkInt(String s){
		try  {  
			Integer.parseInt(s);  
		}  
		catch(NumberFormatException nfe)  {  
			return false;  
		}  
		return true;  
	}
	/**
	 * Checks if String is of type Float
	 * @param s
	 * @return true if String is Integer
	 */
	public static boolean checkFloat(String s){
		try  {  
			Float.parseFloat(s);  
		}  
		catch(NumberFormatException nfe)  {  
			return false;  
		}  
		return true;  
	}
	/**
	 * Checks if String is of type Double
	 * @param s
	 * @return true if String is type Double
	 */
	public static boolean checkDouble(String s){
		try  {  
			Double.parseDouble(s);  
		}  
		catch(NumberFormatException nfe)  {  
			return false;  
		}  
		return true;  
	}
	/**
	 * Checks if String is of type Long
	 * @param s
	 * @return true if String is tyoe Long
	 */
	public static boolean checkLong(String s){
		try  {  
			Long.parseLong(s);  
		}  
		catch(NumberFormatException nfe)  {  
			return false;  
		}  
		return true;  
	}
}

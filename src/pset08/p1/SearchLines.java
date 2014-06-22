package pset08.p1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * This command line program is a able to list all the matches of a given regex within one file
 * @author sriegl, pwicke
 * @date 21-06-2014
 */
public class SearchLines {
	/*
	 * Data fields for the regex, the target file and the total matches
	 */
	private static String regex 	= null;
	private static int totalMatches = 0;

	public static void main(String[] args) {
		// parse all arguments
		parseArgs(args);
		
		// initialise streams: file->FileInputStream->InputStreamReader->MatchReader
		try(	InputStreamReader inread 	= new InputStreamReader(System.in);
				MatchReader matchread 		= new MatchReader(inread, regex);
				){
				// as long as there is a next line
				while (matchread.readLine()!=null){
					// save amount of matches
					int matches = matchread.getAmountofMatches();
					// if there is a match print out the appropriate information and line
					if( matches !=0 ){
						setTotalMatches(getTotalMatches() + 1);
						System.out.println(matches+"x match in line "+matchread.getLineNumber()+
								" found:\""+matchread.getLastLine()+"\"");
					}
				}
			} catch (IOException e){
				throw new InternalError("Something went terribly wrong! "+e.getMessage());
		}
		// Give the total number of matches
		System.out.println("The pattern \""+regex+"\" could be found "+totalMatches+" times.");
	}	

	/** 
	 * This method will parse the arguments provided by the user:
	 * It will check if the argument is a valid regular expression
	 * @param <code>args</code> of the main method 
	 * @throws <code>IllegalArgumentException</code> if provided arguments are invalid
	 */
	private static void parseArgs(String[] args) {
		// Check if argument provided
		if(args.length !=1 )
			throw new IllegalArgumentException("Arguments must be: REGEX");
		
		// Check first argument: must be valid regex and assign it to static variable
		try{ 
			Pattern.compile(args[0]);
		} catch (PatternSyntaxException pse) {
			throw new IllegalArgumentException("First argument must be a valid regular expression!");
		}
		setRegex(args[0]);
	}

	
	/**
	 * This is the getter method for the regular expression
	 * @return regex of type <code>String</code> that you want to search for in the file
	 */
	@SuppressWarnings("unused")
	private static String getRegex() {
		return regex;
	}

	/**
	 * This is the setter method of the regular expression
	 * @param regex the regex to set of type <code>String</code> 
	 */
	private static void setRegex(String regex) {
		SearchLines.regex = regex;
	}

	/**
	 * This is the getter method for the number of total matches
	 * @return number of totalMatches type <code>int</code>
	 */
	private static int getTotalMatches() {
		return totalMatches;
	}

	/** 
	 * This is the setter method for the number of total matches
	 * @param totalMatches the totalMatches of the regex
	 */
	private static void setTotalMatches(int totalMatches) {
		SearchLines.totalMatches = totalMatches;
	}

}

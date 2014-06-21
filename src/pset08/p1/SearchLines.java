package pset08.p1;

import java.io.File;
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
	 * Data fields will 
	 */
	private static String regex 	= null;
	private static File target 		= null;

	public static void main(String[] args) {
		// parse all arguments
		parseArgs(args);
		
		// initialise streams: file->FileInputStream->InputStreamReader->MatchReader
		try(	InputStreamReader inread 	= new InputStreamReader(System.in);
				MatchReader matchread 		= new MatchReader(inread, regex);
				){
				while (matchread.readLine()!=null){
					// check if matches occur and return result
					int matches = matchread.getAmountofMatches();
					if( matches !=0 ){
						System.out.println(matches+"x match in line "+matchread.getLineNumber()+
								" found:\""+matchread.getLastLine()+"\"");
					}
				}
			} catch (IOException e){
				throw new InternalError("Something went terribly wrong! "+e.getMessage());
		}
	}	

	private static void parseArgs(String[] args) {
		if(args.length !=3 )
			throw new IllegalArgumentException("Arguments must be: REGEX < FILE");
		
		// Check first argument: must be valid regex and assign it to static variable
		try{ 
			Pattern.compile(args[0]);
		} catch (PatternSyntaxException pse) {
			throw new IllegalArgumentException("First argument must be a valid regular expression!");
		}
	}

	
	/**
	 * @return the regex
	 */
	public static String getRegex() {
		return regex;
	}

	/**
	 * @param regex the regex to set
	 */
	public static void setRegex(String regex) {
		SearchLines.regex = regex;
	}

	/**
	 * @return the target
	 */
	public static File getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public static void setTarget(File target) {
		SearchLines.target = target;
	}

}

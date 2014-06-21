package pset07.p4;
import testing.TestingLibrary;
import java.io.File;

/**
 * This is the command line program Ls which allows to show a directory of your choice.
 * You can provide [-r] [-s] {Path or Directory}.
 * Optional:
 * [-r]: Will recursively iterate through all directories of the provided path
 * [-s]: Will show the size of the current files in the visited directory
 * {Path} : If this path is not provided, the current working directory will be listed
 * 			else, the path will be listed with its directories and files.
 * 
 * @author Wicke, Riegl
 * @date 15.06.2014 
 *
 */
public class Ls {
	
	/*
	 * These are flags that will help to parse the input
	 */
	private static boolean hasRecursionArgument		= false;
	private static boolean hasSizeArgument			= false;
	
	private final static String recursionCommand = "-r";
	private final static String sizeCommand 	 = "-s";
	private		  static String path			 = null;
	
	/**
	 * See class description
	 * @param args Arguments provided by command line input (see class description)
	 */
	public static void main(String[] args){
	  // User-friendly input panel
	  args = TestingLibrary.populateProgramArguments(args);
		
	  // Set all flags according to arguments
	  parseArguments(args);
	
	  // Creates visitable file with appropriate path
	  VisitableFile f = new VisitableFile(path);
		
	  if(f.isDirectory())
	    System.out.println(f.getPath() + " (interactive directory listing)");
	  f.accept(new FileVisitor(hasRecursionArgument, hasSizeArgument));
	
	  // Be polite.
	  System.out.println("Thank you for travelling with Ls Airlines.");
	}
	 
	/**
	 * This method is will parse the input of the command-line and adjust the operations 
	 * and appropriate flags to support the main method of the Ls command line program
	 * @param <code>String[]</code> arguments that will be entered in command line
	 * @throws IllegalArgumentException if arguments are not valid
	 */
	private static void parseArguments(String[] arguments){	
		
		// Check for number of arguments
		switch(arguments.length){	
		
			// No path provided, use current directory
			case 0:
				path = ".";
				break;
			// One command line argument provided [-r], [-s] or path
			case 1:
				if(new File(arguments[0]).exists()){
					path = arguments[0];
				}
				// recursion demanded [-r]: list all files in all directories of current dir.
				else if(arguments[0].matches(recursionCommand)){
					hasRecursionArgument=true;
					path = ".";
				}
				// size demanded [-s]: list all files and their size in current dir.
				else if(arguments[0].matches(sizeCommand)){
					hasSizeArgument=true;
					path = ".";
				}
				else{
					throw new IllegalArgumentException("Invalid arguments!");
				}
				break;
			// Two command line arguments provided 
			case 2:
				// recursion and size demanded [-r] [-s] in current directory
				if (arguments[0].matches(recursionCommand) && arguments[1].matches(sizeCommand)){
					hasSizeArgument = true;
					hasRecursionArgument = true;
					path = ".";
				}
				// size and recursion demanded [-s] [-r] in current directory
				else if(arguments[0].matches(sizeCommand) && arguments[1].matches(recursionCommand)){
					hasSizeArgument = true;
					hasRecursionArgument=true;
					path = ".";
				}
				// size and path demanded  [-s] {Path}
				else if(arguments[0].matches(sizeCommand) && new File(arguments[1]).exists()){
					hasSizeArgument = true;
					path = arguments[1];
				}
				// recursion and path demanded  [-r] {Path}
				else if(arguments[0].matches(recursionCommand) && new File(arguments[1]).exists()){
					hasRecursionArgument = true;
					path = arguments[1];
				}
				else{
					throw new IllegalArgumentException("Invalid arguments!");
				}
				break;
			case 3:
				// recursion, size and path demanded [-r] [-s] {path}
				if (arguments[0].matches(recursionCommand) && arguments[1].matches(sizeCommand) && new File(arguments[2]).exists()){
					hasSizeArgument = true;
					hasRecursionArgument=true;
					path = arguments[2];
				}
				// size, recursion and path demanded [-s] [-r] {path}
				else if (arguments[0].matches(sizeCommand) && arguments[1].matches(recursionCommand) && new File(arguments[2]).exists()){
					hasSizeArgument = true;
					hasRecursionArgument=true;
					path = arguments[2];
				}
				else{
					throw new IllegalArgumentException("Invalid arguments!");
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid arguments!");
		}		
	}
}

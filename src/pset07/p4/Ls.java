package pset07.p4;



public class Ls {
	
	/*
	 * These are flags that will help to parse the input
	 */
	private static boolean hasArgs		= false;
	private static boolean recursion	= false;
	private static boolean size			= false;
	private static boolean otherDir		= false;
	
	private static String OS			= null;
	private static String path			= null;
	
	private final static String recursionCommand = "-r";
	private final static String sizeCommand 	 = "-s";
	
	//FIXME: Some regex pattern for windows paths
	/* Maybe this shit is far too complicated, as there is:
	 * static String 	pathSeparator
     * The system-dependent path-separator character, represented as a string for convenience.
	 * and further data fields in the file API
	 */ 
	 
	private final static String pathREGEXWin 	 = "/*[/*]"; 
	//FIXME: Some regex pattern for linux paths
	private final static String pathREGEXLinux 	 = "\\*[\\*]";
	
	 /**
	  * This method adjusts the parsing for the path, regarding the operating system
	  * @return <code>String</code> name of the OP 
	  * @throws <code>InternalError</code> if program does not support the operating system
	  */
	 private static void setOsParsing(){
		 String OS = null;
		 // ask OS for its properties
	     if(OS == null) 
	    	  OS = System.getProperty("os.name");
	     // check if OS is Windows and adjust parsing
	     if (OS.startsWith("Windows")){
			  path = pathREGEXWin;
		 }
	     // check if OS is Linux and adjust parsing
		 else if(OS.startsWith("Linux")){
			  path = pathREGEXLinux; 
		 }
		 else throw new InternalError("Operating system not supported!");
	   }
	 
	/**
	 * This method is will parse the input of the command-line and adjust the operations 
	 * and appropriate flags to support the main method of the Ls command line program
	 * @param <code>String[]</code> arguments that will be entered in command line
	 */
	private static void parseArguments(String[] arguments){
		//FIXME:Parsing problem 
		//Works fine in identifying cases but for some reason the .matches does not work!
		
		// adjust parsing according to OS
		setOsParsing();
		// Check for number of arguments
		switch(arguments.length){
			case 0:	
				// no arguments provided: set argumentFlag to false and path to current path
				hasArgs=true;
				path = System.getProperty("user.dir");
				break;
			case 1:
				// recursion demanded [-r]: list all files in all directories of current dir.
				if(arguments[0].matches(recursionCommand)){
					recursion=true;
					path = System.getProperty("user.dir");
					System.out.println("RecursionCommand detected!"); //FIXME: Not reachable!
				}
				// size demanded [-s]: list all files and their size in current dir.
				else if(arguments[0].matches(sizeCommand)){
					size=true;
					path = System.getProperty("user.dir");
				}
				break;
			case 2:
				// recursion and size demanded [-r] [-s] in current directory
				if (arguments[0].matches(recursionCommand) && arguments[1].matches(sizeCommand)){
					size = true;
					recursion = true;
					path = System.getProperty("user.dir");
				}
				// size and recursion demanded [-s] [-r] in current directory
				else if(arguments[0].matches(sizeCommand) && arguments[1].matches(recursionCommand)){
					size = true;
					recursion=true;
					path = System.getProperty("user.dir");
				}
				// size and path demanded  [-s] {Path}
				else if(arguments[0].matches(sizeCommand) && arguments[1].matches(path)){
					size = true;
					path = arguments[1];
				}
				// recursion and path demanded  [-r] {Path}
				else if(arguments[0].matches(recursionCommand) && arguments[1].matches(path)){
					recursion = true;
					path = arguments[1];
				}
				break;
			case 3:
				// recursion, size and path demanded [-r] [-s] {path}
				if (arguments[0].matches(recursionCommand) && arguments[1].matches(sizeCommand) && arguments[2].matches(path)){
					size = true;
					recursion=true;
					path = arguments[2];
				}
				// size, recursion and path demanded [-s] [-r] {path}
				else if (arguments[0].matches(sizeCommand) && arguments[1].matches(recursionCommand) && arguments[2].matches(path)){
					size = true;
					recursion=true;
					path = arguments[2];
				}
				break;
			default:
				throw new RuntimeException("Invalid command line input");
		}	
		// FIXME: static String problem
		// It does identify the OS and adjust the path but at this point it has
		// forgotten which OS it was. The static String OS is reset? How to avoid this?
		
		System.out.println("Parsing done for: "+OS);
		
	}
	
	public static void main(String[] args){
		
		// Set all flags according to arguments
		parseArguments(args);
		
		// Creates visitable file with appropriate path
		VisitableFile f = new VisitableFile(path);
		
		
				
		
	}
	
	
}

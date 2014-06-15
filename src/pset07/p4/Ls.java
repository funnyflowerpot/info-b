package pset07.p4;

import java.io.File;

import javax.swing.JOptionPane;



public class Ls {
	
	/*
	 * These are flags that will help to parse the input
	 */
  // TODO (pw): could these variables names, that describe their meaning or purpose more precisely?
	private static boolean recursion	= false;
	private static boolean size			= false;
	
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
		
		// adjust parsing according to OS
		setOsParsing();
		// Check for number of arguments
		switch(arguments.length){
// TODO ANPASSEN!
			case 1:
				// recursion demanded [-r]: list all files in all directories of current dir.
				if(arguments[0].matches(recursionCommand)){
					recursion=true;
					path = ".";
					System.out.println("RecursionCommand detected!"); 
				}
				// size demanded [-s]: list all files and their size in current dir.
				else if(arguments[0].matches(sizeCommand)){
					size=true;
					path = ".";
				}
				break;
			case 2:
				// recursion and size demanded [-r] [-s] in current directory
				if (arguments[0].matches(recursionCommand) && arguments[1].matches(sizeCommand)){
					size = true;
					recursion = true;
					path = ".";
				}
				// size and recursion demanded [-s] [-r] in current directory
				else if(arguments[0].matches(sizeCommand) && arguments[1].matches(recursionCommand)){
					size = true;
					recursion=true;
					path = ".";
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
    // Provide a more comfortable input mechanism for testing in eclipse.
    // This will emulate application start with command line arguments.
    if(args.length == 0) {

      String input = JOptionPane.showInputDialog("Please provide some command line arguments:");
      // main.args[] now contains all split parts of arguments, that were separated
      // by a white space and puts them recursively in the main method
      main(input.split(" "));
      
      return;
    }
    
    // FIXME (pw): please review. i suggest a helper method in our library
    // that allows following call. the following line is meant to replace the
    // whole "if"-block above. please let me know, if you think the following
    // line is more readable and understandable than block above. note: use 
    // ctrl+click to open implementation after hovering curser above method name.
    //args = TestingLibrary.populateProgramArguments(args);

    // Set all flags according to arguments
		parseArguments(args);

    // FIXME: next lines only for temporary testing
    path = ".";
    recursion = true;
    size = true;

		// Creates visitable file with appropriate path
    // FIXME (pw): in parseArguments(), a regex pattern gets assigned to "path"
		VisitableFile f = new VisitableFile(path);

		// TODO: check if "path" is actually a valid path (-> exists(), isDirectory()), handle exception
		
		// TODO (sr): next line should be included in accept(), which means accept() will change slightly
		System.out.println(f.getPath());
		f.accept(new FileVisitor(recursion, size));
				
		
	}
	
	
}

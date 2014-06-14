package pset07.p4;

import java.io.File;

import testing.TestingLibrary;

public class VisitableFile extends File implements Visitable<VisitableFile> {


  
	public VisitableFile(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}



	/**
	 * SerialVersionUID as necessity of file extension
	 */
	private static final long serialVersionUID = -1064693652865606286L;

	

	@Override
	public void accept(Visitor<VisitableFile> v) {

	  String input1;
	  String input2;
	  
	  // accept will only be called for directories
	  if(!isDirectory())
	    throw new InternalError("Ugh! Implementation only handles directories!");

	  FileVisitor visitor = (FileVisitor) v;
	  visitor.increaseLevelOfRecursion();
	  
	  // list files, since we are a directory and want our content
	  for(File file : listFiles()) {

	    // TODO (pw): please read this comment. FYI and correct me if this is Potentially Fucking Up the Testat (PFT)
	    // Handling return value is a requirement of the visitor pattern, 
	    // although FileVisitor always returns true.
	    // Note: Usually, this narrowing cast is unsafe. The cast here is not 
	    // dangerous, since VisitableFile extends File only in terms of methods
	    // (i.e. functionality), but not in additional fields. Trying to access
	    // non-existing fields is harmful and we are not doing that.
	    if(!visitor.visit((VisitableFile) file))
	      return;
	    
	    if(file.isDirectory() && visitor.isRecursing()) {
	      
	      // ask for user input until we have a valid answer (return == "y")
	      input1 = TestingLibrary.promptInput("Recurse into directory? ([y],n,q) ", 
	          new String[]{"", "y", "n", "q"});
	      
	      
	      switch(input1) {
	      
	      // oh yeah, let's do this directory listing
	      case "":
	      case "y":
	        // see big comment above for a note about this cast
	        ((VisitableFile) file).accept(visitor);

	        input2 = TestingLibrary.promptInput("Continue directory listing of \""
	            + getName() + "\"? ([y],q) ", new String[]{"", "y", "q"});

	        if(input2.equals("q"))
	          visitor.quitVisitation();
	        
	        // done with recursion
	        break;
	        
	      // do not recurse into this directory (next file in current directory
	      // will be handled next)
	      case "n":
	        break;
	      
	      // like "n", but additionally set flag, so we can escape the whole
	      // visiting process quickly
	      case "q":
	        visitor.quitVisitation();
	        break;
	        
	      // "something, somewhere went terribly wrong"
	      default:
	        throw new InternalError("Very invalid user input. This should never have happened.");

	      } // end of "switch" (user input before recursion)
	 
	      
	      // abort directory listing, if requested by input "q"
	      if(visitor.isQuitRequested())
	        break;
	      
	    } // end of "if" (file is directory and recursion is requested)
	  
	  } // end of "for" (file iteration, directory traversion)
    
    visitor.decreaseLevelOfRecursion();
	  
		/*
		 * TODO (sr): place these comments at a good position
		 * 
		 * Taken from "Design Patterns", section of visitor pattern:
		 * Represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates.
		 * 
		 * Why not exception handling? Because having a way to quit directory 
		 * listing is not a bug, but a feature.
		 */
	}

}

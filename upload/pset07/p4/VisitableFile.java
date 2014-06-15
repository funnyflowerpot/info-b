/*
 * Stylistic remarks for the sake of the 'Testat':
 * Taken from "Design Patterns", section of visitor pattern:
 *Represent an operation to be performed on the elements of
 * an object structure. Visitor lets you define a new operation
 * without changing the classes of the elements on which it operates.
 * 
 * Why not exception handling? Because having a way to quit directory 
 * listing is not a bug, but a feature.
 */

package pset07.p4;
import java.io.File;
import java.util.Arrays;
import testing.TestingLibrary;

/**
 * A thin wrapper for class File to allow instances get visited, according to
 * The visitor pattern.
 * 
 * @date 16.06.2014
 * @author sriegl, pwicke
 */
public class VisitableFile extends File implements Visitable<VisitableFile> {
  
  /**
   * Wrapper method for constructor File(String).
   * 
   * @param pathname the pathname specifying this file
   */
  public VisitableFile(String pathname) {
    super(pathname);
  }

  /**
   * SerialVersionUID as necessity of file extension
   */
  private static final long serialVersionUID = -1064693652865606286L;

  
  @Override
  public void accept(Visitor<VisitableFile> v) {

    VisitableFile file;
    String[] fileNames;
    String input1;
    String input2;

    FileVisitor visitor = (FileVisitor) v;

    // this will only be called from main
    if(!isDirectory()) {
      visitor.visit(this);
      return;
    }

    visitor.increaseLevelOfRecursion();
    
    // guarantee alphabetical order for directory listings
    fileNames = list();
    Arrays.sort(fileNames);
    
    // list files, since we are a directory and want our content
    for(String fileName : fileNames) {

      file = new VisitableFile(getPath() + File.separatorChar + fileName);
      
      // Handling return value is a requirement of the visitor pattern, 
      // although FileVisitor always returns true.
      if(!visitor.visit(file))
        return;
      
      if(file.isDirectory() && visitor.isRecursing()) {
        
        // ask for user input until we have a valid answer (return == "y")
        input1 = TestingLibrary.promptInput("Recurse into directory \""
              + file.getName() + "\"? ([y],n,q) ", new String[]{"", "y", "n", "q"});
        
        
        switch(input1) {
        
        // oh yeah, let's do this directory listing
        case "":
        case "y":
          // see big comment above for a note about this cast
          file.accept(visitor);

          // in accept() sometime, exit of visitation was requested
          if(visitor.isQuitRequested())
            break;

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
  }

}

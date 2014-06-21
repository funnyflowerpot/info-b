package pset07.p5;
import testing.TestingLibrary;

/**
 * @author sriegl, pwicke
 * @date 16.04.2014
 *
 * A class that allows to search for files within a given directory. See doc
 * of main() for details.
 */
public class Search {

  /** command line argument: recurse into directories */
  private boolean recursiveSearch;
  /** command line argument: pattern to filter found files */
  private String searchPattern;
  /** command line argument: directory in which search should be performed */
  private String searchPath;
  
  /**
   * Provide sane default variables.
   */
  public Search() {
    recursiveSearch = false;
    searchPattern = null;
    searchPath = null;
  }
  
  /**
   * Run a search of files through a certain directory. Search can be modified
   * by command line arguments:
   *   -r              recursive search
   *   -p \<pattern\>  only list files that match a certain pattern
   *   \<directory\>   search in a certain directory
   * A directory must only be specified once.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {

    // User-friendly input panel
    args = TestingLibrary.populateProgramArguments(args);
    Search search = new Search();
    
    // parse arguments, handle invalid input with output
    try {
      search.parseArguments(args);
    } catch(IllegalArgumentException e) {
      System.out.println("Program execution aborted: " + e.getMessage());
      System.out.println("Usage: Search [-r] [-p <pattern>] [<directory>]");
      System.out.println("Remember that patter must be a regex that matches"
          + " the name of the file completely, e.g. \".+\\.java\".");
      return;
    }
    
    search.doThatCrazySearchThing();
  }
  			
  /**
   * Does that crazy search thing.
   */
  private void doThatCrazySearchThing() {
    VisitableFile dir = new VisitableFile(searchPath);
    FileVisitor visitor = new FileVisitor(recursiveSearch, searchPattern);
    dir.accept(visitor);
  }

  /**
   * Parse arguments, usually forwarded from command line.
   * 
   * @param <code>args</code> arguments to be parsed
   * @throws <code>IllegalArgumentException</code> if search path given multiple times
   */
  public void parseArguments(String[] args) {
    
    // iterate through all arguments successively
    for(int i = 0; i < args.length; i++) {
      
      switch(args[i]) {
      
      // if current argument is "-r", set flag
      case "-r":
        recursiveSearch = true;
        break;
        
      // if current argument is "-p", take next argument as pattern string,
      // or throw exception if not given
      case "-p":
        if(i == args.length - 1)
          throw new IllegalArgumentException("pattern specification missing");
        searchPattern = args[++i];
        break;
        
      // no special argument preceding, handle as being search path
      default:
        if(searchPath != null)
          throw new IllegalArgumentException("search path given multiple times");
        searchPath = args[i];
      }
    }
    
    // if no directory was given, use current directory as default
    if(searchPath == null)
      searchPath = ".";
  }
  
}

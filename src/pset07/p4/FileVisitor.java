package pset07.p4;

/**
 * This class implements the Visitor Interface for visitable files
 * The visitor can be called with the boolean parameters for recursion and size
 * Recursion will 'allow' the visitor to recursively visit all directories of a file
 * Size will print the size of a visited file
 * @author Wicke, Riegl
 * @date 14.06.2014 
 *
 */
public class FileVisitor implements Visitor<VisitableFile> {


  /*
   * Flags for appropriate visitor specifications
   */
  private boolean recurse;
  private boolean printSize;
  private boolean quit;
  private int levelOfRecursion;

  	/**
  	 * This is the FileVisitor constructor:
  	 * It will take the information about the visitor specification and set
  	 * the flags accordingly
  	 * @param <code>boolean</code> recurse is true if Visitor has to visit all directories recursively 
  	 * @param <code>boolean</code> printSize is true if Visitor shall print the size of each visited file
  	 */
	public FileVisitor(boolean recurse, boolean printSize) {
		this.recurse = recurse;
		this.printSize = printSize;
		levelOfRecursion = 0;
	}

	@Override
	public boolean visit(VisitableFile o) {
	  // indentation
	  for(int i = 0; i < levelOfRecursion; i++)
	    System.out.print(i == levelOfRecursion - 1 ? "+-> " : "|   ");
	  // file name
	  System.out.printf("%s", o.getName());
	  // size, if file
	  if(!o.isDirectory() && printSize)
	    System.out.printf(" (%d bytes)", o.length());
	  System.out.println();	  
		return true;
	}

  /**
   * This is a getter method for the quit request
   * if a quit of current listing is set, it will return true
   * @return <code>true</code> if quit if listing requested
   */
  public boolean isQuitRequested() {
    return quit;
  }
  /**
   * This is a setter method for visits.
   * If called it will set the quit flag false, thus ending visits
   */
  public void quitVisitation() {
    this.quit = true;
  }
  /**
   * This is the getter method for recursion
   * @return <code>true</code> if recursion flag is set and recursion required
   */
  public boolean isRecursing() {
    return recurse;
  }
  /**
   * This is the setter method to increase the level of recursion
   * One call will increment the level of recursion by one
   */
  public void increaseLevelOfRecursion() {
    levelOfRecursion++;
  }
  /**
   * This is the setter method to decrease the level of recursion
   * One call will decrement the level of recursion by one
   */
  public void decreaseLevelOfRecursion() {
    if(levelOfRecursion > 0)
      levelOfRecursion--;
  }

}

package pset07.p5;

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


  /** recurse into subdirectories */
  private boolean recurse;
  /** filter that must apply for files to be printed. "null" for all files. */
  private String pattern;

	/**
	 * Initialize FileVisitor instance.
	 * 
	 * @param recurse recurse into sub-directories
	 * @param pattern filter for file names to be printed
	 */
	public FileVisitor(boolean recurse, String pattern) {
		this.recurse = recurse;
		this.pattern = pattern;
	}

	@Override
	public boolean visit(VisitableFile o) {
	  if(pattern == null || o.getName().matches(pattern))
	    System.out.println(o.getAbsolutePath());
		return true;
	}

  /**
   * @return true if visitor should recurse into sub-directories
   */
  public boolean isRecursing() {
    return recurse;
  }

}

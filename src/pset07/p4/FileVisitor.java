package pset07.p4;

/**
 * @author Wicke
 * @date 14.06.2014 
 *
 */
public class FileVisitor implements Visitor<VisitableFile> {


  // TODO (sr): DOKU
  private boolean recurse;
  private boolean printSize;
  private boolean quit;
  private int levelOfRecursion;

  /**
	 * 
	 */
	public FileVisitor(boolean recurse, boolean printSize) {
		this.recurse = recurse;
		this.printSize = printSize;
		levelOfRecursion = 0;
	}

	@Override
	public boolean visit(VisitableFile o) {
		// TODO check for the flags and do the following:
		// recursion set: recursively call all files and directories in every sub-directory
		// 				  this is probably 
		// size set:
	  
	  for(int i = 0; i < levelOfRecursion - 1; i++)
	    System.out.print("|   ");
	  System.out.printf("+-> %s", o.getName());
	  if(!o.isDirectory() && printSize)
	    System.out.printf(" (%d bytes)", o.length());
	  System.out.println();
	  
		return true;
	}

  public boolean isQuitRequested() {
    return quit;
  }

  public void quitVisitation() {
    this.quit = true;
  }

  public boolean isRecursing() {
    return recurse;
  }

  public void increaseLevelOfRecursion() {
    levelOfRecursion++;
  }

  public void decreaseLevelOfRecursion() {
    if(levelOfRecursion > 0)
      levelOfRecursion--;
  }

}

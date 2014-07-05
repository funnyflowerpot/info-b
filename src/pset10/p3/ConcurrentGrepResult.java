package pset10.p3;

import java.io.File;
import java.util.LinkedList;


/**
 * @author pwicke, sriegl
 *
 * Simple data structure that represents a search result of ConcurrentGrep. A
 * search result consists of a link to the file (for the file name) and the
 * lines in which the search pattern was found, including the respective line
 * number, the amount of matches and the line itself.
 * 
 * This is a open data structure, without providing operational methods on
 * its own. Note that the internal data structure that is representing lines,
 * that match the pattern, can be "getted" and also modified.
 */
public class ConcurrentGrepResult {

  /** The file in which the search pattern occurs. */
  private File file;
  
  /** A data structure that holds all matching lines with their properties. */
  private LinkedList<ConcurrentGrepResultLine> lines;
  
  
  /**
   * Create a representation of a matching file. Matching lines should be
   * added later on.
   * 
   * @param file file in which pattern was found
   */
  public ConcurrentGrepResult(File file) {
    lines = new LinkedList<ConcurrentGrepResultLine>();
    this.file = file;
  }
  
  /**
   * Getter.
   * 
   * @return the file in which pattern occurs
   */
  public File getFile() {
    return file;
  }

  /**
   * Getter. Note: This is a open data structure, so the underlying data set
   * is returned directly. This data set can and should sometimes be modified.
   * 
   * @return a data structure with all the files that match for this file.
   */
  public LinkedList<ConcurrentGrepResultLine> getLines() {
    return lines;
  }
  
  
  
  /**
   * @author pwicke, sriegl
   *
   * Data structure that represents a line that matches a pattern in a 
   * certain file. Instances of this object should always be owned by an
   * instance of <code>ConcurrentGrepResult</code>.
   */
  public static class ConcurrentGrepResultLine {

    /** The line itself for which pattern matched. */
    private String line;
    
    /** The line number of this line. */
    private int lineNumber;
    
    /** How often pattern matched in this line. */
    private int amountOfMatches;
    
    
    /**
     * Build a new representation of a line in which a pattern matched.
     * 
     * @param line the line itself
     * @param lineNumber number of line in file
     * @param amountOfMatches how often pattern matched in line
     */
    public ConcurrentGrepResultLine(String line, int lineNumber, int amountOfMatches) {
      this.line = line;
      this.lineNumber = lineNumber;
      this.amountOfMatches = amountOfMatches;
    }

    /**
     * Getter.
     * 
     * @return the line number within the corresponding file.
     */
    public int getLineNumber() {
      return lineNumber;
    }

    /**
     * Getter.
     * 
     * @return how often pattern matched in this line.
     */
    public int getAmountOfMatches() {
      return amountOfMatches;
    }

    /**
     * Getter.
     * 
     * @return the line in which pattern matched at least once.
     */
    public String getLine() {
      return line;
    }
    
  }

}

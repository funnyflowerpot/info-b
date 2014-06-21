package pset06.p4;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * @author sriegl
 * 
 * Helper class for HeapTest. Compares two patterns of regular expressions
 * based on the comparableness of the underlying String-instances.
 */
public class PatternComparator implements Comparator<Pattern> {

  /* (non-Javadoc)
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(Pattern o1, Pattern o2) {
    return o1.pattern().compareTo(o2.pattern());
  }

}

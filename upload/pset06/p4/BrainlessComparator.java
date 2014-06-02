package pset06.p4;

import java.util.Comparator;

/**
 * @author sriegl
 * 
 * Helper class to allow only one algorithm for the implementation of Heap by
 * handling classes that implement Comparable via a Comparator.
 * 
 * Since this class is not safe for general purposes, it is only available
 * within this package. See also:
 * 
 * "Wurde kein Comparator mit angegeben, soll die Heap-Implementation davon
 * ausgehen, dass alle eingefügten Instanzen vom Typ Comparable sind."
 * 
 * @param <T> a type that must implement interface Comparable
 */
class BrainlessComparator<T> implements Comparator<T> {

  /* (non-Javadoc)
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public int compare(T o1, T o2) {
    return ((Comparable<T>) o1).compareTo(o2);
  }

}

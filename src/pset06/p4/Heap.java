package pset06.p4;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author sriegl
 *
 * This class is a implementation of the ADT heap, supporting generic data
 * types. This Heap can get fed via an arbitrary class T and a Comparator\<T\>,
 * otherwise T must implement the interface Comparable\<T\>. Some typical
 * methods for data manipulation are available.
 *
 * @param <T> the type our heap should be in charge of
 */
public class Heap<T> {

  /**
   * weapon of choice for comparison, even if T implements Comparable
   */
  private Comparator<T> comparator;
  
  /**
   * underlying container for heap's data structure
   */
  private T[] elements;
  
  /**
   * number of meaningful elements in heap
   */
  private int size;
  
  /**
   * Since we have no parameter of Comparator we can (must) assume, that T
   * implements Comparable, therefore we use a wrapper for the call of
   * compareTo().
   */
  public Heap() {
    this(new BrainlessComparator<T>());
  }
  
  /**
   * The caller of our constructor provided a comparator for our type T. Since
   * it is not allowed to instantiate a generic array, we workaround with an
   * Object array. This is okay, since we can expect only objects of type T
   * entering the array via insert() and we are interpreting (casting) elements 
   * of this array at no time as another type than T.
   * 
   * @param comparator the comparator that compares the elements that need to
   *                   be compared in a comparing fashion
   */
  @SuppressWarnings("unchecked")
  public Heap(Comparator<T> comparator) {
    this.comparator = comparator;
    size = 0;
    elements = (T[]) new Object[10];
  }
  
  /**
   * Insert an element into the heap, maintaining a correct inner structure.
   * Extend the underlying array, if necessary.
   * 
   * @param element the element to be inserted into the heap
   */
  public void insert(T element) {
    if(elements.length == size)
      elements = Arrays.copyOf(elements, (int) (elements.length * 1.5));
    elements[size] = element;
    size++;
    sort(elements);
  }
  
  /**
   * Delete first element in heap. From second element on, shift the underlying
   * array to the left by 1 and decrement heap size.
   * 
   * @return element, that gets removed from heap
   */
  public T deleteFirst() {
    T first = elements[0];
    for(int i = 1; i < size; i++)
      elements[i - 1] = elements[i];
    size--;
    sort(elements);
    return first;
  }
  
  /**
   * Repair an array, maintaining a heap-organization. 
   * 
   * This implementation was shamelessly stolen from Informatik A and slightly
   * modified to allow generic data types.
   * 
   * @param a array to be sorted
   * @param l lower bound of array
   * @param r upper bound of array
   */
  private void sift(T[] a, int l, int r) {  // repariere Array a in den Grenzen von l bis r
    int i, j;                               // Indizes
    T x;                                    // Array-Element
    i = l; x = a[l];                        // i und x initialisieren
    j = 2 * i + 1;                          // linker Sohn
    // if ((j < r) && (a[j + 1] < a[j])) j++;  // jetzt ist j der kleinere Sohn
    if ((j < r) && comparator.compare(a[j + 1], a[j]) < 0) j++;
    // while((j <= r) && (a[j] < x)) {         // falls kleinerer Sohn existiert
    while((j <= r) && comparator.compare(a[j], x) < 0) {
      a[i] = a[j];
      i = j;
      j = 2 * i + 1;
      // if((j < r) && (a[j + 1] < a[j])) j++; // jetzt ist j der kleinere Sohn
      if((j < r) && comparator.compare(a[j + 1], a[j]) < 0) j++;
    }
    a[i] = x;
  }
  
  /**
   * Reorganize an array to maintain a internal heap element order.
   * 
   * This implementation was shamelessly stolen from Informatik A and slightly
   * modified to allow generic data types.
   * 
   * @param a the array to be sorted
   */
  private void sort(T[] a) {          // statische Methode sort
    int l, r, n;                      // links, rechts, Anzahl
    T tmp;
    n = size;                         // Zahl der Heap-Eintraege
    for(l = (n - 2) / 2; l >= 0; l--) // beginnend beim letzten Vater
      sift(a, l, n - 1);              // jeweils Heap reparieren
    for(r = n - 1; r > 0; r--) {      // rechte Grenze fallen lassen
      tmp = a[0];                     // kleinstes Element holen
      a[0] = a[r];                    // letztes nach vorne
      a[r] = tmp;                     // kleinstes nach hinten
      sift(a, 0, r-1);                // Heap korrigieren
    }
  }

  /**
   * Check if the heap has no elements.
   * 
   * @return true, if heap has no elements
   */
  public boolean empty() {
    return size == 0;
  }
  
  /**
   * Return the first element in the heap.
   * 
   * @return first element in heap
   */
  public T getFirst() {
    return elements[0];
  }
}

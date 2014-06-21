package pset06.p4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * @author sriegl
 *
 * Test the Heap class on heart and kidneys. =)
 */
public class HeapTest {

  /**
   * Print test results nicely.
   * 
   * @param testResult determines success of test
   * @param message description of test
   * @param args optional additional information
   */
  public static void printTest(boolean testResult, String message, Object... args) {
    System.out.printf("[%s]  %s", testResult ? " OK " : "FAIL", message);
    if(args.length > 0)
      System.out.printf(" (debug info: %s)", Arrays.toString(args));
    System.out.println();
  }
  
  /**
   * Run tests on class heap, grouped in two groups. First group will test
   * the Comparator-implementation of heap and all methods on a general
   * basis. The second group will test the Comparable-implementation and will
   * utilize the provided class HeapSort.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {

    System.out.println("NOW TESTING: BASIC METHODS, CONSTRUCTOR 1.");
    
    // There is no point in a PatternComparator, except its implementation is
    // pretty simple and easily understandable, which is desired for a 
    // Testat.
    Comparator<Pattern> comparator = new PatternComparator();    
    Heap<Pattern> patHeap = new Heap<Pattern>(comparator);

    // something to fill into the heap
    // bunched into an array to simplify variable access
    Pattern[] patterns = {
        Pattern.compile("2b|!2b=?"),
        Pattern.compile("c(omputer|\\.) s(science|\\.)"),
        Pattern.compile("bla+h"),
    };
    
    printTest(patHeap.empty(), "heap empty");
    
    
    // exception on accessing non-existing elements
    
    String message = null;
    try {
      patHeap.getFirst();
    } catch(NoSuchElementException e) {
      message = e.getMessage();
    }
    printTest(message != null, "error on getting first element", message);
    
    
    // insertion
    
    patHeap.insert(patterns[0]);
    printTest(patHeap.getFirst() == patterns[0], "inserted element is first element");
    patHeap.insert(patterns[1]);
    printTest(patHeap.getFirst() == patterns[1], "inserted element is first element");
    patHeap.insert(patterns[2]);
    printTest(patHeap.getFirst() != patterns[2], "inserted element is not first element");
    
    printTest(!patHeap.empty(), "heap not empty");

    
    // getting and deletion
    
    Pattern element;
    element = patHeap.getFirst();
    printTest(element == patHeap.deleteFirst(), "deleted element was first element", element);
    element = patHeap.getFirst();
    printTest(element == patHeap.deleteFirst(), "deleted element was first element", element);
    element = patHeap.getFirst();
    printTest(element == patHeap.deleteFirst(), "deleted element was first element", element);

    printTest(patHeap.empty(), "heap empty");

    
    
    // part 2
    
    System.out.println();
    System.out.println("NOW TESTING: HEAP STRUCTURE, CONSTRUCTOR 2.");

    Heap<Integer> intHeap = new Heap<Integer>();

    printTest(intHeap.empty(), "heap empty (before sort)");
    
    Integer[] source = {5, 8, 2, 7, 8, 4, 6, 2000, 1};
    Integer[] sorted = {2000, 8, 8, 7, 6, 5, 4, 2, 1};
    Integer[] result = HeapSort.heapSort(intHeap, source);
    
    printTest(intHeap.empty(), "heap empty (after sort)");
    printTest(Arrays.equals(result, sorted), "array correctly sorted", Arrays.toString(result));
        
  }

}

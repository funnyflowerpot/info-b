package pset08.p3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Arrays;

// TODO doc
/**
 * @author pwicke, sriegl
 *
 * Create an array that is synchronized with the content of an underlying
 * array. Support reading the underlying file via a second constructor.
 * 
 * To avoid uneccessary instances of ObjectOutputStream-classes, the internal
 * file pointer marking the current position in the underlying file is reset 
 * before every write process, as well as the (only) instance of 
 * ObjectOutputStream gets reset before every write process.
 * 
 * This class features an instance of RandomAccessFile to provide random
 * "seeking" of positions within the file.
 *
 * @param <T> the type the elements of the array should be of
 */
public class PersistentArray<T extends Serializable> implements AutoCloseable {

  /** the underlying file, acting as data grave */
  private RandomAccessFile raf;

  /** weapon of choice to universally putting any Serializable into a file. */
  private ObjectOutputStream oos;
  
  /** container for user data */
  private T[] elements;
  
  
  /**
   * Initialize internal data array, set up output stream and create or
   * overwrite underlying file with current data.
   * 
   * @param data initial array data
   * @param filepath path of underlying file to be created
   * @throws IOException if file could not get created or initialized
   */
  public PersistentArray(T[] data, String filepath) throws IOException {
    elements = Arrays.copyOf(data, data.length);
    setupOutputStream(filepath);
    updateFile();
  }
  
  
  /**
   * Initialize array based on present underlying file and set up output 
   * stream.
   * 
   * @param filepath path of underlying file to be read from
   * @throws IOException if file is not readable, has wrong format, could not
   *   get created or initialized
   */
  public PersistentArray(String filepath) throws IOException {
    elements = loadArrayFromFile(filepath);
    setupOutputStream(filepath);
  }
  
  
  /**
   * Update the underlying file and avoid creating new ObjectOutputStream
   * instances. This will be achieved by putting the internal "file pointer"
   * of the RandomAccessFile instance back to a position where it will
   * override the lastly written array.
   * 
   * For more details of the Object Serialization Stream Protocol, see here:
   * 
   * http://docs.oracle.com/javase/7/docs/platform/serialization/spec/protocol.html
   * 
   * @throws IOException if file could is not writable anymore
   */
  private void updateFile() throws IOException {
    try {
      // At the beginning of each ObjectOutputStream, there is a preamble of
      // two shorts (magic value and version), that gets inserted with the
      // creation of ObjectOutputStream. When re-writing the array, skip past
      // preamble (Short.SIZE is length of a "short" in bits).
      raf.seek(Short.SIZE / 8 * 2);
      oos.writeObject(elements);
      // force writing to file, to have data save even if stream could not be
      // closed (and flushed before) properly
      oos.flush();
      // reset internal references list, which causes ObjectOutputStream to
      // actually writes a full representation of the array and not just a
      // reference to a formerly printed one
      oos.reset();
    } catch (IOException e) {
      throw new IOException("Could not update array file: " + e.getMessage());
    }
  }
  

  /**
   * Initialize internal file representation and ObjectOutputStream instance.
   * This method will only be called from constructors (avoid recurring code).
   * 
   * @param filepath path for the file the representation should be built for
   * @throws IOException if file representation could not be created
   */
  private void setupOutputStream(String filepath) throws IOException {
    try {
      raf = new RandomAccessFile(filepath, "rw");
      // overwrite without care
      // no exception handling; if this fails, constructor should be aborted
      oos = new ObjectOutputStream(new FileOutputStream(raf.getFD()));
    } catch (IOException e) {
      throw new IOException("Could not open \"" + filepath + "\" for saving array: " + e.getMessage(), e);
    }
  }
  
  
  /**
   * Load and return an array of data from a persistent array that was saved
   * in a file.
   * 
   * @param filepath path of the file data should be loaded from
   * @return array containing data of file
   * @throws IOException if file could not be read or has invalid content
   */
  @SuppressWarnings("unchecked")
  private T[] loadArrayFromFile(String filepath) throws IOException {
    try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
      return (T[]) ois.readObject();
    } catch (IOException e) {
      throw new IOException("Could not open file for initializing array: " + e.getMessage(), e);
    } catch (ClassNotFoundException e) {
      throw new IOException("Unsupported array element type read from file: " + e.getMessage(), e);
    }
  }
  
  
  /**
   * Getter for an array element. Might throw IndexOutOfBoundsException.
   * 
   * @param index index of the element to be returned
   * @return the element denoted by the index
   */
  public T getElement(int index) {
    if(index < 0 || index >= elements.length)
      throw new IndexOutOfBoundsException("Invalid index specified: " + index);
    return elements[index];
  }
  
  
  /**
   * Setter for an array element. Might throw IndexOutOfBoundsException. 
   * Changes will be written immediately to the underlying file. If a change
   * could not be written to the file, do not change internal array.
   * 
   * @param index index of the element to be changed
   * @param element new value
   * @throws IOException if underlying array could not be updated
   */
  public void setElement(int index, T element) throws IOException {
    if(index < 0 || index >= elements.length)
      throw new IndexOutOfBoundsException("Invalid index specified: " + index);
    T oldValue = elements[index];
    elements[index] = element;
    // if we can't update underlying file, reset changes
    try {
      updateFile();
    } catch(IOException e) {
      elements[index] = oldValue;
      throw e;
    }
  }
  
  
  /**
   * Size of internal array.
   * 
   * @return size of internal array
   */
  public int size() {
    return elements.length;
  }
  
  
  @Override
  public void close() throws IOException {
    // no exception handling, IO error might be interesting for caller
    oos.close();
  }
  

  /**
   * Turn this array into a string.
   * 
   * @return stringified PersistentArray
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for(int i = 0; i < elements.length; i++)
      builder.append(i == 0 ? "[" : ", ").append(elements[i]);
    return builder.append("]").toString();
  }
  
}

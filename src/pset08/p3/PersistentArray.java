package pset08.p3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

// TODO doc
public class PersistentArray<T> implements AutoCloseable {

  private RandomAccessFile raf;
  private ObjectOutputStream oos;
  private T[] elements;
  
  
  public PersistentArray(T[] data, String filepath) throws IOException {
    elements = Arrays.copyOf(data, data.length);
    setupOutputStream(filepath);
    updateFile();
  }
  
  
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
   * @throws IOException
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
  
  
  private T[] loadArrayFromFile(String filepath) throws IOException {
    try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
      return (T[]) ois.readObject();
    } catch (IOException e) {
      throw new IOException("Could not open file for initializing array: " + e.getMessage(), e);
    } catch (ClassNotFoundException e) {
      throw new IOException("Unsupported array element type read from file: " + e.getMessage(), e);
    }
  }
  
  
  public T getElement(int index) {
    if(index < 0 || index >= elements.length)
      throw new IndexOutOfBoundsException("Invalid index specified: " + index);
    return elements[index];
  }
  
  
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
  
  public int size() {
    return elements.length;
  }
  
  
  @Override
  public void close() throws IOException {
    // no exception handling, IO error might be interesting for caller
    oos.close();
  }
  
}

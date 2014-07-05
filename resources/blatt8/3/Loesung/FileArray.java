package io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Holds an array as a RandomAccessFile.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class FileArray implements Closeable {

   private final RandomAccessFile file;

   private static final int ELEM_BYTE_SIZE = 4;

   private static final String MODE = "rws";

   /**
    * Creates a new FileArray, therefore creates a new file with the name
    * <code>name</code>. If that file already existed, it will be overridden.
    * Writes every element from <code>original</code> into that file.
    * 
    * @param original
    *           the array which should be stored in the file with the name
    *           <code>name</code>
    * @param name
    *           the name of the file where <code>original</code> should be
    *           stored
    * @throws IOException
    *            if an I/O error occurs
    */
   public FileArray(Integer[] original, String name) throws IOException {
      File f = new File(name);
      if (!f.exists()) {
         f.createNewFile();
      }
      file = new RandomAccessFile(f, MODE);
      file.setLength(0);
      for (Integer i : original) {
         file.writeInt(i);
      }
   }

   /**
    * Creates a FileArray from an already existing array. It the given
    * <code>name</code> exists but is not a FileArray, no exception will be
    * thrown, but the return values of {@link #get(int)} will be unpredictable.
    * 
    * @param name
    *           the name of the file which contains the array to be read.
    * @throws FileNotFoundException
    *            if <code>name</code> does not exist
    * @throws IOException
    *            if an I/O error occurs
    */
   public FileArray(String name) throws IOException {
      File f = new File(name);
      if (!f.exists()) {
         throw new FileNotFoundException(name + " does not exist");
      }
      file = new RandomAccessFile(f, MODE);
   }

   /**
    * Returns the element that is stored at position <code>i</code>.
    * 
    * @param i
    *           position from which the element should be read
    * @return the element at position <code>i</code>
    * @throws IOException
    *            if an I/O error occurs
    */
   public Integer get(int i) throws IOException {
      if (i < 0 || i >= length()) {
         throw new ArrayIndexOutOfBoundsException(i);
      }
      file.seek(i * ELEM_BYTE_SIZE);
      return file.readInt();
   }

   /**
    * Updates the element at position <code>i</code> with <code>elem</code>.
    * 
    * @param i
    *           position to update
    * @param elem
    *           the value to which the element at position i should be replaced
    * @throws IOException
    *            if an I/O error occurs
    */
   public void set(int i, Integer elem) throws IOException {
      if (i < 0 || i >= length()) {
         throw new ArrayIndexOutOfBoundsException(i);
      }
      file.seek(i * ELEM_BYTE_SIZE);
      file.writeInt(elem);
   }

   /**
    * Returns the number of elements in this FileArray.
    * 
    * @return number of elements in this FileArray
    * @throws IOException
    *            if an I/O error occurs
    */
   public int length() throws IOException {
      return (int) (file.length() / ELEM_BYTE_SIZE);
   }

   /**
    * Closes this FileArray
    */
   public void close() throws IOException {
      this.file.close();
   }
}

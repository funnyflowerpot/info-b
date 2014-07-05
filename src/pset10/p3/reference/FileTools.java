package pset10.p3.reference;

import java.io.File;

/**
 * Small tool set for work with <code>java.io.File</code>
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */

public class FileTools {

   /**
    * Calculates the byte-size of a File. For directories it recursively sums up
    * the size of every file and every directory in the given root directory.
    * 
    * @param file
    *           the file or directory to calculate the size of.
    * @return the size in bytes
    */
   public static long getSize(File file) {
      if (!file.isDirectory()) {
         return file.length();
      } else {
         long size = 0;

         File[] files = file.listFiles();

         if (files != null) {
            for (File f : files) {
               size += getSize(f);
            }
         }
         return size;
      }
   }

   /**
    * Transforms the given byte size into a string with a magnitude. E.g. kb,
    * mb, gb and so on. May be used for converting the result of
    * {@link #getSize(File)} into a better readable version.
    * 
    * @param size
    *           an amount of bytes.
    * @return the size converted into a string with magnitude
    */
   public static String sizeToString(long size) {

      double number = size;
      String unit;

      if (size == 0) {
         unit = "-";
      } else if (number / 1000000000000L > 1) {
         number = (size) / 1000000000000.0;
         unit = "tb";
      } else if (number / 1000000000L > 1) {
         number = (size) / 1000000000.0;
         unit = "gb";
      } else if (number / 1000000L > 1) {
         number = (size) / 1000000.0;
         unit = "mb";
      } else if (number / 1000L > 1) {
         number = (size) / 1000.0;
         unit = "kb";
      } else {
         unit = " b";
      }

      return String.format("%8s",
            String.format("%03.2f %2s", new Double(number), unit));

   }

}

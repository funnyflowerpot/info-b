package extra;

import java.io.FileInputStream;
import java.io.IOException;

public class FileAccess {

  private static String filename = "resources/blatt5/2/Loesung/Library_sequenzdiagramm.eps";
  
  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    
    FileInputStream fis;
    byte[] buffer1 = new byte[1024*1024];
    int[] buffer2 = new int[1024*1024];
    int funnyByte;
    long readStart;
    long readEnd;
    
    // chunk size: big
    // use multi-byte read()

    fis = new FileInputStream(filename);
    readStart = System.currentTimeMillis();
    fis.read(buffer1, 0, 1024*1024);
    readEnd = System.currentTimeMillis();
    System.out.println("multi-byte read(byte[], ...), chunk size=1024*1024, " + (readEnd - readStart) + "ms.");
    fis.close();
    
    // chunk size: 1
    // use single-byte read()

    fis = new FileInputStream(filename);
    readStart = System.currentTimeMillis();
    for(int i = 0; true; i++) {
      funnyByte = fis.read();
      if(funnyByte == -1)
        break;
      buffer2[i] = funnyByte;
    }
    readEnd = System.currentTimeMillis();
    System.out.println("single-byte read(),           chunk size=1,         " + (readEnd - readStart) + "ms.");
    fis.close();

    // chunk size: 1
    // use multi-byte read()

    fis = new FileInputStream(filename);
    readStart = System.currentTimeMillis();
    for(int i = 0; true; i++) {
      funnyByte = fis.read(buffer1, i, 1);
      if(funnyByte == -1)
        break;
      buffer2[i] = funnyByte;
    }
    readEnd = System.currentTimeMillis();
    System.out.println("multi-byte read(byte[],...),  chunk size=1,         " + (readEnd - readStart) + "ms.");
    fis.close();
    
  }

}

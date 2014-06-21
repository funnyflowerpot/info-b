package extra;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

public class BufferedVsUnbuffered {

  private static final String FILENAME = "README.md";
  
  @SuppressWarnings("unused")
  public static void main(String[] args) throws IOException {

    // class InputStream is abstract. To use, it would be necessary to
    // implement read().
    //InputStream is = new InputStream() { ... read() ... };
    
    int value;
    byte[] byteBuffer = new byte[1000];
    char[] charBuffer = new char[1000];
    
    
    /*
     * Comparison: FileInputStream and BufferedInputStream
     */
        
    FileInputStream fis = new FileInputStream(FILENAME);
    BufferedInputStream bis = new BufferedInputStream(fis);
    
    // check out following
    // fis.<ctrl><space>
    // bis.<ctrl><space>
    
    value = fis.read();
    fis.read(byteBuffer, 0, 1000);   // read(buffer-array, start-at, chunk-size)

    // a internal buffer allows using the methods mark(int) and reset()
    
    value = bis.read();
    bis.read(byteBuffer, 0, 10);     // read(buffer-array, start-at, chunk-size)
    // 10 bytes read, now at position 10 in file
    bis.mark(5); // "mark" current position, which means allow jumping back
                 // to this position later, if we read less than (here) 5 bytes
    value = bis.read();
    value = bis.read();
    value = bis.read();
    // 3 bytes read, now at position 13 in file
    bis.reset();
    // back at position 10 again
    value = bis.read();
    // note: we are reading this character for the second time
    value = bis.read();
    value = bis.read();
    value = bis.read();
    value = bis.read();
    value = bis.read();
    // 6 bytes read, now at position 16 in file
    // marked position was valid until position 10+5=15 (marked position plus
    // specified read limit) and current position 16>15
    
    fis.close();
    bis.close();
    
    
    /*
     * Comparison: FileReader and BufferedReader
     * this is similar to comparison above
     */
        
    FileReader fr = new FileReader(FILENAME);
    BufferedReader br = new BufferedReader(fr);
    
    // check out following
    // fr.<ctrl><space>
    // br.<ctrl><space>

    // note: XxxReader is working with char[] instead of byte[].

    // big advantage of BufferedReader: readLine()
    // note: this is _very_ handy

    value = fr.read();
    fr.read(charBuffer);
    
    String read = br.readLine();
    
    fr.close();
    br.close();
    
    
    
    // prior to using the Scanner class, this was the preferred way:
    
    BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    String input = console.readLine();
       
    
  }

}

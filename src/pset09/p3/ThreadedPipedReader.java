package pset09.p3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PipedReader;
import java.io.Writer;

public class ThreadedPipedReader extends PipedReader implements Runnable {

  private Thread thread;
  private BufferedReader br;
  private Writer writer;


  public ThreadedPipedReader(Writer writer) {
    super();
    br = new BufferedReader(this);
    this.writer = writer;
    thread = new Thread(this);
  }
  
  @Override
  public void run() {

    String line;
    
    try {
      // we must get interrupted to exit this loop
      // the condition is for defensive programming
      // replacing condition with "true" would not affect program flow
      while( (line = br.readLine()) != null) {
        writer.write(line + System.lineSeparator());
        writer.flush();
      }
    
    } catch(InterruptedIOException e) {
      // everything is okay
    } catch(IOException e) {
      e.printStackTrace();
      System.err.println("User output thread was interrupted. That's unusual.");
    }
    
    try {
      super.close();
      br.close();
    } catch (IOException e) {
      System.err.println("Could not free resources for user output thread. :-(");
    }
  }
  
  public void start() {
    thread.start();
  }

  public void join() throws InterruptedException {
    thread.join();
  }

  public void finish() {
    thread.interrupt();
  }
  
}

package pset09.p3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PipedReader;
import java.io.Writer;

/**
 * @author pwicke, sriegl
 *
 * End point of a pipe, that writes data it receives from the pipe to a 
 * <code>Writer</code>, that gets decorated by this class using the decorator
 * pattern.
 */
public class ThreadedPipedReader extends PipedReader implements Runnable {

  /** underlying thread to allow for recurrence */
  private Thread thread;
  
  /** allow for readLine() */
  private BufferedReader br;
  
  /** where to write received data to */
  private Writer writer;


  /**
   * Setup an end point for a pipe, with <code>writer</code> being a sink
   * that gets decorated by this class.
   * 
   * @param writer output for received data
   */
  public ThreadedPipedReader(Writer writer) {
    super();
    br = new BufferedReader(this);
    this.writer = writer;
    thread = new Thread(this);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
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
      // everything is okay, we wanted to arrive here to escape while(true)
    	
    } catch(IOException e) {
      e.printStackTrace();
      System.err.println("User output thread was interrupted. That's unusual.");
    }
    
    // for a proper "shutdown" of this thread, we need to close all open streams
    try {
      super.close();
      br.close();
    } catch (IOException e) {
      System.err.println("Could not free resources for user output thread. :-(");
    }
  }
  
  /**
   * Start underlying thread. 
   */
  public void start() {
    thread.start();
  }

  /**
   * Join underlying thread.
   * 
   * @throws InterruptedException if joining threads causes trouble
   */
  public void join() throws InterruptedException {
    thread.join();
  }

  /**
   * Request shutdown of thread.
   */
  public void finish() {
    thread.interrupt();
  }
  
}

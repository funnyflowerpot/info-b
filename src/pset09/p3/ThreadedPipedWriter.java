package pset09.p3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PipedWriter;


/**
 * @author pwicke, sriegl
 *
 * Read concurrently from an InputStream and pass data on through a pipe to
 * some other stream. If a file path could be detected as a line, read the
 * complete file and write file's content into the pipe.
 */
public class ThreadedPipedWriter extends PipedWriter implements Runnable {

  /** underlying thread to allow for recurrence */
  private Thread thread;
  
  /** allow for readLine() */
  private BufferedReader br;
  
  
  /**
   * Build a pipe end point, with <code>sink</code> being the other end point.
   * Decorate <code>input</code> based on decorator pattern.
   * 
   * @param input source stream from which data should be read
   * @param sink destination stream to which data should be written
   * @throws IOException if decoration failed.
   */
  public ThreadedPipedWriter(InputStream input, PipedReader sink) throws IOException {
    super(sink);
    br = new BufferedReader(new InputStreamReader(input));
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
      while(true) {
        // InputStream does not react on Thread.interrupt(). This is a serious
    	// problem (actually a Java bug that was filed nine years ago). As a
    	// workaround we utilize "busy checking", which means we poll every
    	// few milliseconds whether there is something to read from the stream
    	// or not. If yes, read, else wait.
        while(!br.ready()) {
          Thread.sleep(MarklarTranslator.IO_THREAD_SLEEP_DELAY);
        }
        line = br.readLine();
        // check if file is readable, be robust against EOF
        if(line != null && !importFile(line.trim())) {
          write(line + System.lineSeparator());
          flush();
        }
      }
    
    } catch(InterruptedException e) {
      // everything is okay, we wanted to arrive here to escape while(true)
    	
    } catch(IOException e) {
      System.err.println("User input thread was interrupted. That's unusual.");
    }
    
    // for a proper "shutdown" of this thread, we need to close all open streams
    try {
      super.close();
      br.close();
    } catch (IOException e) {
      System.err.println("Could not free resources for user input thread. :-(");
    }
  }
  
  /**
   * Check path for being a readable file, open it and fully write it into
   * the pipe. This method will not cause an exception.
   * 
   * @param path path specifying file that should be read from
   * @return true, if path denoted a valid file that was written into the
   *         pipe; false, otherwise 
   */
  private boolean importFile(String path) {
    File file = new File(path);
    String line;
    // continue only if file exists and is readable
    if(!file.exists() || !file.canRead())
      return false;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      // until we reach end of file, write file line by line into pipe
      while( (line = br.readLine()) != null) {
        write(line + System.lineSeparator());
        flush();
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find specified file. Proceeding normally: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Caught IOException. Proceeding normally: " + e.getMessage());
    }
    return true;
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

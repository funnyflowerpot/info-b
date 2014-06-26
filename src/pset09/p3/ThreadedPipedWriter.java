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


// mention interruption problems
public class ThreadedPipedWriter extends PipedWriter implements Runnable {

  private Thread thread;
  private BufferedReader br;
  
  
  public ThreadedPipedWriter(InputStream input, PipedReader sink) throws IOException {
    super(sink);
    br = new BufferedReader(new InputStreamReader(input));
    thread = new Thread(this);
  }

  @Override
  public void run() {
    
    String line;
     
    try {
      // we must get interrupted to exit this loop
      while(true) {
        // only move on as soon as we have something to read
        while(!br.ready()) {
          Thread.sleep(MarklarTranslator.IO_THREAD_SLEEP_DELAY);
        }
        line = br.readLine();
        // check if file is readable
        if(!importFile(line.trim())) {
          write(line + System.lineSeparator());
          flush();
        }
      }
    
    } catch(InterruptedException e) {
      // everything is okay
    } catch(IOException e) {
      System.err.println("User input thread was interrupted. That's unusual.");
    }
    
    try {
      super.close();
      br.close();
    } catch (IOException e) {
      System.err.println("Could not free resources for user input thread. :-(");
    }
  }
  
  private boolean importFile(String path) {
    File file = new File(path);
    String line;
    if(!file.exists() || !file.canRead())
      return false;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      while( (line = br.readLine()) != null) {
        write(line + System.lineSeparator());
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find specified file. Proceeding normally: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Caught IOException. Proceeding normally: " + e.getMessage());
    }
    return true;
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

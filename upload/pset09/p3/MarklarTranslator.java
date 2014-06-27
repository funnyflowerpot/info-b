package pset09.p3;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author pwicke, sriegl
 *
 * Showcase for both thread classes and the Maklar filter class.
 */
public class MarklarTranslator {
  
  /** Delay for threads after which there should be a new poll for new input
   *  from the respective input stream (if necessary). */
  public static final int IO_THREAD_SLEEP_DELAY = 100;

  /**
   * This is where the magic happens.
   * 
   * Note: A warning gets suppressed, saying that we are not closing one of
   * the two thread classes. Closing is handled by the thread classes itself,
   * so it will not be visible here, so Java should shut up at this point.
   * 
   * @param args command line arguments
   */
  @SuppressWarnings("resource")
  public static void main(String[] args) {
    
    final ThreadedPipedWriter tpw;
    final ThreadedPipedReader tpr;

    try {
      
      // create those funny thread things
      tpr = new ThreadedPipedReader(new MarklarFilterWriter(new PrintWriter(System.out)));
      tpw = new ThreadedPipedWriter(System.in, tpr);
      
    } catch (IOException e) {
      System.err.println("Could not create user input thread. Aborting.");
      System.exit(1);
      
      // this return is to avoid syntactical error messages and will never be reached
      return;
    }
    
    // let the games begin
    tpr.start();
    tpw.start();
    
    // put process termination handling somewhere, where it is practically
    // assured to be executed (i.e. not outside a shutdown hook)
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          
          System.out.println("Shutdown requested. Finishing threads...");

          // initiate shutdown
          tpw.finish();
          tpr.finish();
          
          // wait for shutdown of threads to be completed
          tpw.join();
          tpr.join();
          
          System.out.println("Everything went smoooooth. See ya.");
          
        } catch (InterruptedException e) {
          System.err.println("Could not wait for user input and user output thread to close: " + e.getMessage());
          System.err.println("That's pretty weird.");
        }
        
      }
    }));
    
    // since created threads are not daemons, the JVM will wait for their
    // termination
  }

}

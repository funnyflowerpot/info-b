package pset10.p3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import pset10.p3.ConcurrentGrepResult.ConcurrentGrepResultLine;

import testing.TestingLibrary;


public class ConcurrentGrep extends FileVisitorAdapter {
  
  /** Recurse into directories. */
  private boolean recursive;
  
  /** Pattern that needs to be matched in a file to be found. */
  private Pattern regex;
  
  /** File or directory that should get searched. */
  private File file;
  
  /** All started worker threads. */
  private LinkedList<Thread> threads;
  
  /** A container for search results. */
  private final ConcurrentLinkedQueue<ConcurrentGrepResult> results;
  
  
  /**
   * A ConcurrentGrep-instance allows for searching through a file or 
   * directory and prints all lines that match a certain regex. Recursive
   * search is supported.
   * 
   * @param recursive
   * @param regex
   * @param file
   */
  public ConcurrentGrep(boolean recursive, Pattern regex, File file) {
    threads = new LinkedList<Thread>();
    results = new ConcurrentLinkedQueue<ConcurrentGrepResult>();
    this.recursive = recursive;
    this.regex = regex;
    this.file = file;
  }
  
  /**
   * This is where the magic happens.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    
    // lazy argument acquisition for Eclipse users
    // testing suggestion: use "-r [vV]isitor src" as arguments
    args = TestingLibrary.populateProgramArguments(args);
    
    // get a new instance by given arguments
    ConcurrentGrep cgrep;
    try {
      cgrep = ConcurrentGrep.parseArguments(args);
    } catch (IllegalArgumentException e) {
      System.err.println("Could not execute program. " + e.getMessage());
      System.err.println("Usage: java ConcurrentGrep [-r] <regex> <file-or-directory>");
      return;
    }
    
    // convert internal File instance into FileSystem
    // no need to expect an exception here, because we already made sure,
    // that the file base of cgrep does exist
    FileSystem fs = new FileSystem(cgrep.getFile());
    
    // start searching
    fs.accept(cgrep);
    
    try {
      // wait for worker threads to end
      cgrep.waitForThreads();
    } catch (InterruptedException e) {
      System.err.println("Could not successfully wait for all threads to finish.");
      System.err.println("We have no idea how this actually can happen.");
      System.err.println("I will proceed normally, though not all search results might get shown.");
    }
    
    // present results to user
    cgrep.printResults();
  }

  
  /**
   * Wait for all internal worker threads to finish.
   * 
   * @throws InterruptedException if waiting for a thread ended unexpectedly
   */
  private void waitForThreads() throws InterruptedException {
    for(Thread thread : threads) {
      thread.join();
    }
  }

  /**
   * Print found results to STDOUT. For complete results, make sure 
   * <code>waitForThreads()</code> was called before.
   */
  public void printResults() {
    
    // for allowing fancy output and still keeping this code readable, we
    // introduce some helper variables as an extra to the "compulsory" code
    final String LINE = hiddenSecretBonusOption ? "\u001B[0;32m" : "";
    final String INFO = hiddenSecretBonusOption ? "\u001B[1;32m" : "";
    final String HEADER = hiddenSecretBonusOption ? "\u001B[1;34m" : "";
    final String FOOTER = hiddenSecretBonusOption ? "\u001B[0;34m" : "";
    final String NORMAL = hiddenSecretBonusOption ? "\u001B[0m" : "";
    
    // do not die silently when not having results
    if(results.size() == 0) {
      System.out.println("Sorry, no files found.");
      return;
    }
    
    // print all results
    for(ConcurrentGrepResult result : results) {
      
      // header for a file
      System.out.printf("%s,--<%s%s%s>%s%n", LINE, HEADER, 
          result.getFile().getPath(), LINE, NORMAL);
      
      // line matches
      for(ConcurrentGrepResultLine line : result.getLines()) {
        System.out.printf("%s+--%s(l:%3d; m:%2d)%s-->%s %s%n", LINE, INFO, 
            line.getLineNumber(), line.getAmountOfMatches(), LINE, NORMAL, line.getLine());
      }
      
      // footer for a file
      System.out.printf("%s`--<%s%s%s>%s%n", LINE, FOOTER,
          result.getFile().getPath(), LINE, NORMAL);
    }
  }
  
  /** What might this just be useful for...? :) */
  private static boolean hiddenSecretBonusOption = false;

  /**
   * Create a new ConcurrentGrep instance that is initialized with the 
   * arguments given in args.
   * 
   * @param args command line arguments
   * @return new ConcurrentGrep instance that is configured like args define
   * @throws IllegalArgumentException if supplied arguments are invalid
   */
  public static ConcurrentGrep parseArguments(String[] args) throws IllegalArgumentException {
    boolean recursive = false;
    Pattern regex = null;
    File file = null;
    
    // valid arg count
    if(args.length < 2 || args.length > 3) {
      throw new IllegalArgumentException("Argument count must be two or three.");
    }
    
    // check on recursive-flag
    if(args.length == 3) {
      if(args[0].equals("-r")) {
        recursive = true;
      } else if(args[0].equals("-R")) {
        recursive = true;
        hiddenSecretBonusOption = true;
      } else {
        throw new IllegalArgumentException("Too many non-flag arguments.");
      }
    }
    
    // convert second last argument to regex
    try {
      regex = Pattern.compile(args[args.length - 2]);
    } catch(PatternSyntaxException pse) {
      throw new IllegalArgumentException("Cannot understand specified regex: " + pse.getMessage());
    }
    
    // convert last argument to file, check validity
    file = new File(args[args.length - 1]);
    if(!file.canRead()) {
      throw new IllegalArgumentException("Cannot read specified file: " + args[args.length - 1]);
    }
    
    // yay, args were correct, we can create a new object
    return new ConcurrentGrep(recursive, regex, file);
  }

  
  /**
   * Returns the file or directory, that serves as the search base.
   * 
   * @return an existing file or directory
   */
  public File getFile() {
    return file;
  }

  
  /* (non-Javadoc)
   * @see pset10.p3.FileVisitorAdapter#preVisitDirectory(java.io.File)
   */
  @Override
  public FileVisitResult preVisitDirectory(File dir) {
    // if we were told to recurse, then really do it
    return recursive ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
  }

  
  /* (non-Javadoc)
   * @see pset10.p3.FileVisitorAdapter#visitFile(java.io.File)
   */
  @Override
  public FileVisitResult visitFile(final File file) {
    
    // create a new thread to search within this file
    // add thread to thread list and start thread afterwards
    Thread thread = new Thread(new Runnable() {
      
      @Override
      public void run() {
        
        ConcurrentGrepResult result = new ConcurrentGrepResult(file);
        String line;

        // we do not have to expect PatternSyntaxException, because we 
        // already verified, that regex is valid
        try (SearchLineReader slr = new SearchLineReader(new FileReader(file), regex.pattern())) {
          
          // until there are lines to read in the file, read lines
          for(line = slr.readLine(); line != null; line = slr.readLine()) {
            // does the pattern match at least once in our file?
            if(slr.getAmountOfMatches() > 0) {
              // we have a match, that means we have a result!
              result.getLines().add(new ConcurrentGrepResultLine(line, slr.getLineNumber(), slr.getAmountOfMatches()));
            }
          }
          
        } catch (FileNotFoundException e) {
          System.err.printf("Could not read file \"%s\": %s%n", file.toString(), e.getMessage());
          System.err.printf("Continueing normally.");
          return;
        } catch (IOException e) {
          System.err.printf("Could not close file \"%s\": %s%n", file.toString(), e.getMessage());
          System.err.printf("Continueing normally.");
          return;
        }
        
        // we found at least one line, that means we have a serious result to present
        if(result.getLines().size() > 0) {
          results.add(result);
        }
      }
      
    });
    
    threads.add(thread);
    thread.start();
    
    return FileVisitResult.CONTINUE;
  }
  
}

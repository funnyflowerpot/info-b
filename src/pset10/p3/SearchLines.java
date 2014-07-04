package pset10.p3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.PatternSyntaxException;

/**
 * Command line program to search for matches with a given regular expression in
 * a given file.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class SearchLines {

   public static void main(String[] args) {

      if (args.length != 1) {
         handleError("there must be one argument");
      }

      String regex = args[0];

      SearchLineReader slr = null;

      try {
         try {
            slr = new SearchLineReader(new InputStreamReader(System.in), regex);
         } catch (PatternSyntaxException ex) {
            handleError(regex + " is not a valid regular expression "
                  + ex.getMessage());
         }

         String line;
         while ((line = slr.readLine()) != null) {
            if (slr.getAmountOfMatches() > 0) {
               System.out.printf("%d matches in line %d: %s %n",
                     slr.getAmountOfMatches(), slr.getLineNumber(), line);
            }
         }

      } catch (IOException ex) {
         ex.printStackTrace();
      } finally {
         try {
            slr.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   /**
    * Handles an error in the {@code SearchLines} program by printing the error
    * message and a description of the usage of the program on the standard
    * output. It then terminates the program with error code {@code 1}.
    * 
    * @param message
    *           the message to be printed before the program terminates
    */
   private static void handleError(String message) {
      System.out.println(message);
      printUsage();
      System.exit(1);
   }

   /**
    * Print a short description of the usage of the {@code SearchLines} program
    * on the standard output.
    */
   private static void printUsage() {
      System.out.println("Usage: java io/SearchLines RegularExpression < File");
   }

}

package pset08.p2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;

/**
 * Command-Line Program which is able to (recursively) go through given
 * directories and output the names of those files that match a given pattern.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Search {

   /**
    * Handles an error in this program by printing the error message and a
    * description of the usage of the program on the standard output. It then
    * terminates the program with error code {@code 1}.
    * 
    * @param message
    *           the message to be printed before the program terminates
    */
   private static void handleError(String message) {
      System.out.println(message);
      printUsage();
      System.exit(1);
   }

   public static void main(String[] args) {
      /*
       * flags for the options
       */
      boolean recursive = false;
      String regex = null;
      String search = null;
      String replacement = null;
      final FileFilter filter;

      /*
       * determine whether all arguments are read or not
       */
      boolean argumentsRead = false;

      int i = 0;
      while (!argumentsRead && i < args.length) {

         /*
          * read out the arguments
          */
         switch (args[i]) {
         case "-r":
            recursive = true;
            i++;
            break;
         case "-p":
            i++;
            /*
             * the pattern option must be followed by an regex
             */
            if (i >= args.length) {
               handleError("Option flag for a file pattern set, but no pattern found");
            }
            regex = args[i++];

            break;
         default:
            /*
             * arguments must be set at the beginning. Every String which isn't
             * an argument, must be Search and Replacement followed by a path
             */
        	 
        	search = args[i++]; 
        	
        	if (i >= args.length) {
                handleError("Search is set, but no Replacement found");
             }
        	replacement = args[i++];
        	        	
            argumentsRead = true;
            break;
         }
      }

      /*
       * make a new array with the paths
       */
      String[] dirs = Arrays.copyOfRange(args, i, args.length);

      /*
       * if no paths where given, use the current directory
       */
      if (dirs.length == 0) {
         dirs = new String[] { "." };
      }

      /*
       * create the filter, and filter all files with the same Visitor.
       */
      try {

         filter = new PatternFileFilter(regex);

         FileVisitor fileVisit = new FileSRVisitor(filter, recursive, search, replacement);

         for (String dir : dirs) {
            File f = new File(dir);
            if (f.exists()) {
               new FileSystem(f).accept(fileVisit);
            }
         }

      } catch (PatternSyntaxException e) {
         handleError("Pattern Syntax is illegal: \n" + e.getMessage());
      }
   }

   /**
    * Print a short description of the usage of this program on the standard
    * output.
    */
   private static void printUsage() {
      System.out
            .println("Usage: java io.Search [-r] [-p FilePattern] Search Replacement {FilesAndDirectories}");
   }

   private static class FileSRVisitor extends FileVisitorAdapter {
      private final FileFilter filter;
      private final boolean recursive;
      private final String search;
      private final String replacement;

      private FileSRVisitor(final FileFilter filter, final boolean recursive, final String search, final String replacement) {
         this.filter = filter;
         this.recursive = recursive;
         this.search = search;
         this.replacement = replacement;
      }

      @Override
      public FileVisitResult preVisitDirectory(File dir) {
         if (recursive) {
            return FileVisitResult.CONTINUE;
         } else {
            return FileVisitResult.SKIP_SUBTREE;
         }
      }

      @Override
      public FileVisitResult visitFile(File file) {

    	 /* FIXME: file pattern
    	  * 	   the program does not recognize *.txt neither in console nor in eclipse 
    	  * This is where the replacing takes place.
    	  * Sample input: -r -p thehobbit.txt Gollum Smeagol
    	  */
         if (filter.accept(file)) {
        	 
        	 // Initialise a String that will later be the new text
        	 String inputString = "";
        	 final String SEP = System.getProperty("line.separator");
        	 
        	 // Create input string: file->FileStream->InputStream->BufferedStream
        	 try(
        		FileInputStream filein = new FileInputStream(file);	
        		InputStreamReader inread = new InputStreamReader(filein);
        		BufferedReader buffread = new BufferedReader(inread);
        		)
        	{

        		// currentLine: The current line in the file        		
        		String currentLine = "";
        		// as long as there is a line, append line separator and line to inputString 
        		do{
        			currentLine = buffread.readLine();
        			
        			if(currentLine != null){
        				// The first line is empty paste corrected string
        				if(inputString == "")
        					inputString = currentLine;
        				else	// From the second line paste after separator
        					inputString = inputString + SEP + currentLine;
        			}
           		}while(currentLine != null);	
        		
        		// Replace all occurences of regex 'search' with 'replacement' string   		
        		inputString = inputString.replaceAll(search, replacement);
        		// The input string is now corrected with all replacements and line separators

        	} catch (IOException e){
        		e.printStackTrace();
        	}
        	 
        	 //Create Output
        	 try(
        			 FileOutputStream fileout = new FileOutputStream(new File(file.getAbsolutePath()));
        			 OutputStreamWriter streamout	= new OutputStreamWriter(fileout);
             		 BufferedWriter bufout = new BufferedWriter(streamout);       			 
        			 )
        	{
        		 // write new String in output stream
        		 bufout.write(inputString);
                 
        	}catch (IOException e){
        		e.printStackTrace();
        	}

         }
         return FileVisitResult.CONTINUE;
      }
   }

}

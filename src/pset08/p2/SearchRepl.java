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
 * directories and find all files that match a pattern
 * it will replace all occurrences of a given regex in those files.
 * 
 * @author mmenninghaus, pwicke, sriegl
 * 
 */
public class SearchRepl {

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

    	 /*
    	  * This is where the replacing takes place.
    	  * Feasible input: -r -p thehobbit.txt Gollum Smeagol
    	  * 				-r -p .*\.txt Smeagol Gollum
    	  */
    	  
         if (filter.accept(file)) {
        	 
        	 // Initialise a String that will later be the new text
        	 String inputString = "";
        	 // Define appropriate line separator
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
        		
        		do{ // read current line
        			currentLine = buffread.readLine();
        			// as long as it is not empty
        			if(currentLine != null){
        				// if adding the FIRST line, do not append line separator
        				if(inputString == "")
        					inputString = currentLine;
        				// after the first line, always add separator before appending next line
        				else
        					inputString = inputString + SEP + currentLine;
        			}
        		// do so, until you reach the end of the file
           		}while(currentLine != null);	
        		
        		// Replace all occurrences of regex 'search' with 'replacement' string   		
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
   
   /**
    * Print a short description of the usage of this program on the standard
    * output.
    */
   private static void printUsage() {
      System.out
            .println("Usage: java io.Search [-r] [-p FilePattern] Search Replacement {FilesAndDirectories}");
   }   
   
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
}

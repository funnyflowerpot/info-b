package io;

import java.io.File;
import java.io.FileFilter;
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
             * an argument, must be a path
             */
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

         FileVisitor fileVisit = new FileSearchVisitor(filter, recursive);

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
            .println("Usage: java io.Search [-r] [-p FilePattern] {FilesAndDirectories}");
   }

   private static class FileSearchVisitor extends FileVisitorAdapter {
      private final FileFilter filter;
      private final boolean recursive;

      private FileSearchVisitor(final FileFilter filter, final boolean recursive) {
         this.filter = filter;
         this.recursive = recursive;
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
         if (filter.accept(file)) {
            System.out.println(file.getAbsolutePath());
         }
         return FileVisitResult.CONTINUE;
      }
   }

}

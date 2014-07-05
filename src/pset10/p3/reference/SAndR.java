package pset10.p3.reference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import pset10.p3.FileSystem;
import pset10.p3.FileVisitResult;
import pset10.p3.FileVisitorAdapter;

/**
 * An {@code SandR} is a command-line program which is able to search and
 * replace Strings in several files.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class SAndR {

   /**
    * Concatenate every line from the given {@code File} with the standard
    * line-separator to a single {@code String}.
    * 
    * @param f
    *           {@code File} that should be returned as one {@code String}
    * @return {@code f} as one {@code String}
    * @throws FileNotFoundException
    *            if {@code f} is a directory
    * @throws FileNotFoundException
    *            if {@code f} does not exist
    * @throws IOException
    *            if an IO error occurs
    */
   private static String buildStringFromFile(File f) throws IOException {

      if (!f.exists()) {
         throw new FileNotFoundException(f + " does not exist");
      }
      if (f.isDirectory()) {
         throw new FileNotFoundException(f + " is a directory");
      }

      FileReader fileIn = new FileReader(f);
      BufferedReader bufRead = new BufferedReader(fileIn);

      StringBuilder wholeFile = new StringBuilder();
      String lineSeparator = System.getProperty("line.separator");

      try {
         String line;
         while ((line = bufRead.readLine()) != null) {

            wholeFile.append(line);
            wholeFile.append(lineSeparator);
         }
      } catch (IOException ex) {
         throw ex;
      } finally {
         bufRead.close();
      }
      return wholeFile.toString();
   }

   /**
    * Handles an error in the {@code SandR} program by printing the error
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

   public static void main(String[] args) throws IOException {
      /*
       * options and arguments
       */
      boolean recursive = false;
      String regex = null;
      FileFilter filter = null;
      String search = null;
      String replacement = null;

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
             * an argument must be a path
             */
            argumentsRead = true;
            break;
         }
      }
      /*
       * next, two arguments must be search string and the replacement
       */
      if (i >= args.length) {
         handleError("No expression to search for found");
      } else {
         search = args[i++];
      }

      if (i >= args.length) {
         handleError("No expression to search for found");
      } else {
         replacement = args[i++];
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

      try {
         filter = new PatternFileFilter(regex);
      } catch (PatternSyntaxException ex) {
         handleError(regex + " is not a valid regular expression: "
               + ex.getMessage());
      }

      Pattern searchPattern = null;

      try {
         searchPattern = Pattern.compile(search);
      } catch (PatternSyntaxException ex) {
         handleError(search + " is not a valid regular expression: "
               + ex.getMessage());
      }

      SearchAndReplaceVisitor sAndR = null;
      try {
         sAndR = new SearchAndReplaceVisitor(filter, recursive, searchPattern,
               replacement);
      } catch (PatternSyntaxException ex) {
         handleError(search + " is not a valid regular expression: "
               + ex.getMessage());
      }

      for (String dir : dirs) {

         File f = new File(dir);
         if (!f.exists()) {
            System.out.println(dir + " does not exist");
         } else {
            FileSystem w = new FileSystem(f);
            w.accept(sAndR);
         }
      }

   }

   /**
    * Print a short description of the usage of the {@code SandR} program on the
    * standard output.
    */
   private static void printUsage() {
      System.out
            .println("Usage: java io/SandR [-r] [-p FilePattern] Search Replacement {FilesAndDirectories}");
   }

   private static class SearchAndReplaceVisitor extends FileVisitorAdapter {

      /**
       * {@code Pattern} that should be searched for in every {@code File} that
       * is accepted by {@code filter} and will be replaced by
       * {@code replacement}
       */
      private final Pattern search;

      /**
       * {@code String} which should replace every match of {@code search} in
       * every {@code File} that will be accepted by {@code filter}
       */
      private final String replacement;

      /**
       * {@code FileFilter} which accepts every file in which every match of
       * {@code search} should be replaced by {@code replacement}
       */
      private final FileFilter filter;

      /**
       * denotes, whether this visitor should search in the sub-directories.
       */
      private final boolean recursive;

      /**
       * 
       * @param filter
       *           {@code FileFilter which accepts every file in which every match of
       *           {@code search} should be replaced by {@code replacement}
       * @param recursive
       *           denotes, whether this visitor should search in the
       *           sub-directories.
       * @param search
       *           a regular expression that should be searched for in every
       *           {@code File} that is accepted by {@code filter} and will be
       *           replaced by {@code replacement}
       * @param replacement
       *           {@code String} which should replace every match of
       *           {@code search} in every {@code File} that will be accepted by
       *           {@code filter}
       * 
       * 
       */
      public SearchAndReplaceVisitor(FileFilter filter, boolean recursive,
            Pattern search, String replacement) {
         this.search = search;
         this.replacement = replacement;
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

      /**
       * Searches for matches with {@link #search} and replaces them with
       * {@link #replacement}.
       * 
       * @param f
       *           the {@code File} to be searched and replaced in
       * @throws FileNotFoundException
       *            if {@code f} is a directory
       * @throws FileNotFoundException
       *            if {@code f} does not exist
       * @throws IOException
       *            if an IO error occurs
       */
      private void searchAndReplace(File f) throws IOException {

         if (!f.exists()) {
            throw new FileNotFoundException(f + " does not exist");
         }
         if (f.isDirectory()) {
            throw new FileNotFoundException(f + " is a directory");
         }

         String wholeFile = buildStringFromFile(f);

         Matcher matcher = search.matcher(wholeFile);

         File tmp = File.createTempFile(f.getName(), null);
         try (FileWriter fileWrite = new FileWriter(tmp);
               BufferedWriter bufwrite = new BufferedWriter(fileWrite)) {

            wholeFile = matcher.replaceAll(replacement);

            bufwrite.write(wholeFile);
            bufwrite.flush();

            tmp.renameTo(f);

         } catch (IOException ex) {
            throw ex;
         }
      }

      /**
       * For every {@code file} visited, the method
       * {@link #searchAndReplace(File)} will be called if the
       * {@code FileFilter} of this visitor accepts {@code file}.
       * 
       * @param file
       *           the File that will be searched for the specific regular
       *           expression of this {@code SandR}
       * 
       * @return {@link FileVisitResult#CONTINUE}
       */
      public FileVisitResult visitFile(File file) {
         try {
            if (filter.accept(file)) {
               searchAndReplace(file);
            }
         } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
         }
         return FileVisitResult.CONTINUE;
      }

   }
}

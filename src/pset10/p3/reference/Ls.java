package pset10.p3.reference;

import java.io.File;
import java.util.Arrays;

import pset10.p3.FileSystem;
import pset10.p3.FileVisitResult;
import pset10.p3.FileVisitor;

/**
 * Lists the content of the given directories on the standard console. Command
 * line parameters are '-r' for recursive listing of sub-directories and '-s'
 * for also displaying the byte-size of every listed element.
 */
public class Ls {

   public static void main(String[] args) {
      /*
       * flags for the options
       */
      boolean recursive = false;
      boolean size = false;
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
         case "-s":
            size = true;
            i++;
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
       * if no paths where given, list the current directory
       */
      if (dirs.length == 0) {
         dirs = new String[] { "." };
      }

      /*
       * list all files.
       */
      DoLs ls = new DoLs(recursive, size);

      for (String dir : dirs) {
         System.out.println("Listing " + dir);
         File f = new File(dir);
         if (!f.exists()) {
            System.out.println("...does not exist");
         } else {
            new FileSystem(f).accept(ls);
         }
      }
   }

   private static class DoLs implements FileVisitor {
      private StringBuffer indent = new StringBuffer();

      private boolean recursive;

      private boolean size;

      private DoLs(boolean recursive, boolean size) {
         this.recursive = recursive;
         this.size = size;
      }

      @Override
      public FileVisitResult postVisitDirectory(File dir) {
         indent = indent.delete(indent.length() - 2, indent.length());
         return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult preVisitDirectory(File dir) {
         System.out.print(indent + "+ " + dir.getName());
         if (size) {
            System.out.println(" "
                  + FileTools.sizeToString(FileTools.getSize(dir)));
         } else {
            System.out.println();
         }
         indent.append("| ");

         return this.recursive ? FileVisitResult.CONTINUE
               : FileVisitResult.SKIP_SUBTREE;
      }

      @Override
      public FileVisitResult visitFailed(File file) {
         System.out.print(indent);
         System.out.println(file.getName() + " (unreadable) ");
         return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(File file) {

         System.out.print(indent);

         System.out.print(file.getName());
         if (size) {
            System.out.print(" "
                  + FileTools.sizeToString(FileTools.getSize(file)));

         }
         System.out.println();

         return FileVisitResult.CONTINUE;
      }
   }

}

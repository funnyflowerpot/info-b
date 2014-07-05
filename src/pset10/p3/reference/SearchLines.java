package pset10.p3.reference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.PatternSyntaxException;

import pset10.p3.SearchLineReader;

import testing.TestingLibrary;


/* TODO: this package "reference" can be deleted now

Implementieren Sie ein Programm, das auf der Kommandozeile mit einem regulären Ausdruck und
einer Pfadangabe zu einer Datei oder einem Verzeichnis aufgerufen wird und die Datei bzw. alle
Dateien in dem Verzeichnis zeilenweise nach Zeichenketten absucht, die dem regulären Ausdruck
genügen.

Von jeder Datei, in der mindestens eine Zeile gefunden wurde, die eine Zeichenkette enthält, die dem
regulären Ausdruck genügt, soll der Dateiname auf der Kommandozeile ausgegeben werden. Unter
dem Dateinamen soll jede Zeile der Datei mit mindestens einem Treffer ausgegeben werden und zwar
zusammen mit der Zeilennummer und der Anzahl der Treffer. Wenn das Programm mit der Option
-r aufgerufen wird, sollen auch alle Unterverzeichnisse rekursiv durchlaufen werden.

 * Jede Suche nach Treffern in einer Datei durch Benutzung des SearchLineReader
 * soll mit einem eigenen Thread erfolgen.
 * Da die Ausgabe gruppiert nach den durchsuchten Dateien erfolgen soll,
 * sollte kein Thread die Ergebnisse direkt auf die Kommandozeile schreiben.
 * 
 * Stattdessen sollten die Threads ihre Ergebnisse in eine gemeinsame Ergebnis-Collection
 * schreiben, die, nachdem alle Thread-Instanzen fertig sind, ausgeben wird. Achten Sie dafür
 * darauf, dass die ErgebnisCollection thread-sicher ist, also dass unterschiedliche
 * Thread-Instanzen Elemente einfügen und abfragen können. Das Paket
 * java.io.concurrent enthält solche Collections. Erst nach Beenden aller
 * nebenläufigen Such-Threads kann die Ausgabe erfolgen. Deswegen sollte Ihr
 * Hauptprogramm auf jede gestartete Thread-Instanz warten.
 * 
 */



public class SearchLines {

   public static void main(String[] args) {
     args = TestingLibrary.populateProgramArguments(args);

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

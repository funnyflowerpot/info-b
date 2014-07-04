/*TODO:
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

package pset10.p3;


import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// FIXME: Supposed that ConcurrentHashMap is the Collection of our choice
import java.util.concurrent.ConcurrentHashMap;

/**
 * Extends the LineNumberReader by searching for matches with a given regular
 * expression and counting them while reading.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class SearchLineReader extends LineNumberReader {
	
   /**
    * {@code Pattern} to search matches for
    */
   private final Pattern pattern;

   /**
    * total amount of currently found matches with {@code pattern}
    */
   private int count;

   /**
    * 
    * @param in
    *           {@code Reader} to be wrapped by this {@code SearchLineReader}
    * @param search
    *           regular expression to search for
    * @throws PatternSyntaxException
    *            if {@code search} is not a valid regular expression.
    */
   public SearchLineReader(Reader in, String search) {
      super(in);
      this.pattern = Pattern.compile(search);
      this.count = 0;
   }

   @Override
   public String readLine() throws IOException {

      String line = super.readLine();
      count = 0;

      if (line != null) {
         Matcher m = this.pattern.matcher(line);
         while (m.find()) {
            count++;
         }
      }
      return line;
   }

   /**
    * Get the number of matches with the given regular expression in the latest
    * read line via {@link #readLine()}.
    * 
    * @return number of already found matches in the last call of
    *         {@link #readLine()} with the given regular expression of this
    *         {@code SearchLineReader}
    */
   public int getAmountOfMatches() {
      return this.count;
   }

}

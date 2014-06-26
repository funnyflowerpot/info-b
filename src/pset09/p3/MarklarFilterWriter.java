package pset09.p3;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;


/**
 * @author pwicke, sriegl
 *
 * Maklar Maklar Maklar MaklarFilterWriter Maklar Maklar
 * 
 * Filter text that gets written to this Writer before handing it over to a
 * specified other Writer. The "filtering" is replacing every word starting
 * with a capital letter by a given string ("Maklar").
 */
public class MarklarFilterWriter extends FilterWriter {

  /** regex of Maklar Maklar Maklar words Maklar Maklar to be replaced */
  private static final String MAKLAR_MAKLAR_MAKLAR_REGEX_MAKLAR_MAKLAR = "\\b[A-Z][A-Za-z]*\\b";  
  /** Maklar Maklar Maklar Maklar Maklar Maklar 
   *  Maklar Maklar Maklar Maklar Maklar Maklar 
   *  Maklar Maklar Maklar Maklar Maklar Maklar */
  private static final String MAKLAR_MAKLAR_MAKLAR_MAKLAR_MAKLAR_MAKLAR = "Maklar";  
  
  
  /**
   * Set up filter. Nothing special here. Maklar Maklar.
   * 
   * @param maklarMaklarMaklarwriterMaklarMaklar the writer all lines should 
   *                                             get handed over to
   */
  protected MarklarFilterWriter(Writer maklarMaklarMaklarwriterMaklarMaklar) {
    super(maklarMaklarMaklarwriterMaklarMaklar);
  }
  
  /* (non-Javadoc)
   * @see java.io.FilterWriter#write(java.lang.String, int, int)
   * Maklar Maklar Maklar Maklar Maklar Maklar
   */
  public void write(
      String maklarMaklarMaklarStringMaklarMaklar, 
      int maklarMaklarMaklarOffsetMaklarMaklar, 
      int maklarMaklarMaklarLengthMaklarMaklar) throws IOException {

    // Since calls of write(String) will refer to this method, we can only
    // call the overridden method for handing input string over. And since
    // we will (probably) modify the input string, we need to specify the new
    // length of the string.
    
    // Replace every occurrence of Maklar in the given string and save result.
    String MaklarMaklarMaklarMaklarStringMaklarMaklar = maklarMaklarMaklarStringMaklarMaklar.substring(
        maklarMaklarMaklarOffsetMaklarMaklar, 
        maklarMaklarMaklarOffsetMaklarMaklar + maklarMaklarMaklarLengthMaklarMaklar
      ).replaceAll(
        MAKLAR_MAKLAR_MAKLAR_REGEX_MAKLAR_MAKLAR, 
        MAKLAR_MAKLAR_MAKLAR_MAKLAR_MAKLAR_MAKLAR
      );

    // Hand modified input string over to super method with its respective
    // length.
    super.write(
      MaklarMaklarMaklarMaklarStringMaklarMaklar,
      0,
      MaklarMaklarMaklarMaklarStringMaklarMaklar.length()
    );
  }   

}

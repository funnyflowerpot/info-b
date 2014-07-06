package pset10.p2;

import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * This program simulates an AntRace
 * whilst using multiple threads 
 * @author pwicke, sriegl
 *
 */
public class AntRace implements AntFields {

	public static void main(String[] args) throws InterruptedException {
		
		// create an AntField by using one of the AntFields
		AntField field = new AntField(FIELD4);
		
		// Set the first and manually on one of the fields
		// suppose it is one of the free fields
		Ant ant = new Ant(field, 2, 4, 1);
		
		// Now create a new antThread (Ant implements Runnable)
		Thread antThread = new Thread(ant);		
		// start the first AntThread
		antThread.start();
		// this method waits for the first ant(thread) to die
		antThread.join();
		
		// print header
		System.out.println(getHiddenSecretCoolBonusStuff());
		
		// print the field with ant steps on it
		System.out.println(field.toString());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
  /**
   * Hidden secret cool bonus stuff.
   * 
   * @return hidden secret cool bonus stuff
   */
  private static String getHiddenSecretCoolBonusStuff() {
    
    int count;
    byte[] uncompressedData = new byte[10000];
    String compressedData = 
        "\170\234\215\223\61\316\303\40\14\205\367\236\302\273\55\37\300" +
        "\336\330\275\60\40\70\202\167\337\137\277\201\250\115\332\264" +
        "\372\55\105\41\344\313\173\346\101\0\76\213\57\117\217\33\2\350" +
        "\237\10\377\104\304\133\61\353\362\3\201\100\324\260\370\206" +
        "\244\201\226\71\30\125\125\105\344\205\340\354\100\335\133\122" +
        "\143\50\200\337\250\10\323\174\223\230\173\351\315\277\266\213" +
        "\126\147\237\376\234\370\104\310\206\253\150\273\107\60\102\40" +
        "\214\200\273\25\167\305\167\4\143\166\342\315\102\163\61\360\64" +
        "\233\10\263\220\260\314\45\62\153\161\352\155\345\373\122\341" +
        "\235\242\222\50\52\321\60\153\61\120\45\103\276\32\11\316\313" +
        "\275\132\241\140\20\102\5\275\40\74\200\143\50\61\141\356\215" +
        "\323\352\104\370\204\244\46\25\333\216\124\222\111\201\70\166" +
        "\173\43\252\200\265\324\235\70\125\337\63\247\134\324\263\65\163" +
        "\140\367\225\353\32\302\321\111\42\271\324\171\217\276\345\60" +
        "\362\244\320\112\104\117\52\263\152\175\156\112\53\40\363\265" +
        "\134\221\364\351\143\345\45\75\175\226\300\207\312\40\155\145" +
        "\314\134\142\37\136\176\123\311\36\255\71\216\142\313\360\165" +
        "\22\316\351\346\347\131\165\273\341\55\222\351\215\320\343\317" +
        "\320\173\344\134\172\146\36\177\251\166\242\141";
    
    try {

      Inflater inf = new Inflater();
      inf.setInput(compressedData.getBytes());
      inf.finished();
      count = inf.inflate(uncompressedData);      
      
    } catch (DataFormatException e) {
      return "Didn't work, what a shame.";
    }
    
    return new String(Arrays.copyOf(uncompressedData, count));
  }
}

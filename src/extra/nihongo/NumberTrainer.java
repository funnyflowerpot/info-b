package extra.nihongo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class NumberTrainer {

  public static final String VERSION = "v1.0";
  
  public static final String[][] NUMBERS = {
    { "zero", "ichi", "ni", "san", "yon", "go", "roku", "nana", "hachi", "kyuu" },
    { "ぜろ", "いち", "に", "さん", "よん", "ご", "ろく", "なな", "はち", "きゅう" },
    { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" },
  };
  
  private static final String[][] POTENCIES = {
    { "", "juu", "hyaku", "sen", "man" },
    { "", "じゅう", "ひゃく", "せん", "まん" },
    { "", "十", "百", "千", "万" },
  };
  
  private static final int UPPER_LIMIT = 100000;
  
  private static Map<String, String> irregularForms;
  
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    String input = "";
    int number = 0;
    boolean printSpaces = false;
    
    setupIrregularForms();
    
    System.out.println("Welcome to number trainer " + VERSION + ".");
    System.out.println("Now querying にほんご numbers.");
    System.out.println("Enter \"q\" to leave.");
    
    while(!input.equals("q")) {
      
      number = random.nextInt(UPPER_LIMIT);
      
      System.out.println();
      System.out.print("What is " + number + " in Hiragana? ");
      System.out.flush();
      
      input = scanner.nextLine().trim();
      
      System.out.println(getCharactersFor(number, 1, printSpaces));
    }
    
    scanner.close();
    
    System.out.println();
    System.out.println("Thank you for flying with Japan Airlines.");
  }

  private static void setupIrregularForms() {
    irregularForms = new HashMap<String, String>();
    irregularForms.put("ichijuu", "juu");
    irregularForms.put("ichihyaku", "hyaku");
    irregularForms.put("sanhyaku", "sanbyaku");
    irregularForms.put("rokuhyaku", "roppyaku");
    irregularForms.put("hachihyaku", "happyaku");
    irregularForms.put("ichisen", "sen");
    irregularForms.put("sansen", "sanzen");
    irregularForms.put("hachisen", "hassen");
    irregularForms.put("いちじゅう", "じゅう");
    irregularForms.put("いちひゃく", "ひゃく");
    irregularForms.put("さんひゃく", "さんびゃく");
    irregularForms.put("ろくひゃく", "ろっぴゃく");
    irregularForms.put("はちひゃく", "はっぴゃく");
    irregularForms.put("いちせん", "せん");
    irregularForms.put("さんせん", "さんぜん");
    irregularForms.put("はちせん", "はっせん");
  }

  private static String getCharactersFor(int number, int alphabet, boolean printSpaces) {
    
    if(number >= UPPER_LIMIT)
      throw new IllegalArgumentException();
    
    if(number == 0) {
      return NUMBERS[alphabet][0];
    }
    
    StringBuilder result = new StringBuilder();
    String part;
    int exp;
    int digit;
    
    while(number > 0) {
      exp = (int) Math.log10(number);
      digit = (int) (number / Math.pow(10, exp));
      if(digit > 0) {
        part = NUMBERS[alphabet][digit] + POTENCIES[alphabet][exp];
        if(irregularForms.containsKey(part))
          part = irregularForms.get(part);
        if(printSpaces && result.length() > 0)
          result.append(' ');
        result.append(part);
      }
      number = number - digit * (int) Math.pow(10, exp);
    }
    
    return result.toString();
  }

}

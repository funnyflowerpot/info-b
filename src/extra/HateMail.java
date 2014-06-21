package extra;


public class HateMail {

  public static final boolean CHALLENGE_ACCEPTED = true;
  private static final String ERROR_STRING = 
      "Deshalb ist es wichtig für uns zu wissen, "
      + "was an unserem Ls-Program.{1,3}auszusetzten ist, "
      + "bevor wir.{1,3}uns der neuen Aufgaben zuwenden.";

  
  public static void main(String[] args) {
    
    HateMail hm = new HateMail();
    String messageText = hm.sendHateMail();

    if(messageText.matches(ERROR_STRING)) {
      messageText = hm.getOtherSuggestion();
    }
    
  }
  
  private String getOtherSuggestion() {
    return "";
  }

  private String sendHateMail() {
    return "";    
  }

}

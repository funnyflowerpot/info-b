package pset04.p1;

/**
 * Represents boats driven by a motor engine.
 * 
 * @author Mathias Menninghaus
 */
public class MotorBoat extends Boat {
   public String typ = "motorboat";

   public String getTyp() {
      return typ;
   }

   public String getTyp2() {
      return super.getTyp2();
   }
}
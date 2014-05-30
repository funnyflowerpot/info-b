package pset04.p1;

/**
 * A Class, representing the boat rental of knut hansen.
 * 
 * @author Mathias Menninghaus
 */

public class BoatRental {

   public static void main(String[] args) {
      MotorBoat myMotorBoat = new MotorBoat();

      System.out.println("myMotorBoat.typ         : " + myMotorBoat.typ);
      System.out.println("((Boot)myMotorBoat).typ : "
            + ((Boat) myMotorBoat).typ);
      System.out.println("myMotorBoat.getTyp()    : " + myMotorBoat.getTyp());
      System.out.println("myMotorBoat.getTyp2()   : " + myMotorBoat.getTyp2());

      Canoe myCanoe = new Canoe();
      System.out.println("myCanoe.typ             : " + myCanoe.typ);
      System.out.println("myCanoe.getTyp()        : " + myCanoe.getTyp());
      System.out.println("myCanoe.getTyp2()       : " + myCanoe.getTyp2());

      Boat myBoat = myCanoe;
      System.out.println("myBoat.typ              : " + myBoat.typ);
      System.out.println("myBoat.getTyp()         : " + myBoat.getTyp());
      System.out.println("((Canoe)myBoat).typ     : " + ((Canoe) myBoat).typ);
   }
}
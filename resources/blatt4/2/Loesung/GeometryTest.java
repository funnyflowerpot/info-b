package geometry;

/**
 * Make some tests on classes of the type Geometry.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class GeometryTest {

   public static void main(String[] args) {

      /*
       * Only needs to test Point2D and Rectangle, because they are subtypes of
       * Point and Volume.
       */
      Point2D inner = new Point2D(1, 1);
      Point2D outer = new Point2D(-2, 1);

      Rectangle r1 = new Rectangle(new Point2D(-1, 4), new Point2D(4, -1));
      Rectangle r2 = new Rectangle(new Point2D(2, 7), new Point2D(6, 1));

      Rectangle r3 = new Rectangle(new Point2D(6, 1), new Point2D(2, 7));

      System.out.println("Test Geometry package...");

      System.out.println("Test volume() in Point / Point2D");
      assertEquals(0, inner.volume(), 0.000001);
      assertEquals(0, outer.volume(), 0.000001);

      System.out.println("Test intersect() in Point / Point2D");
      assertEquals(null, inner.intersect(outer));
      assertEquals(inner, inner.intersect(r1));
      assertEquals(null, inner.intersect(r2));

      System.out.println("Test compareTo() in Point / Point2D");
      assertTrue(inner.compareTo(outer) == 0);
      assertTrue(inner.compareTo(r1) < 0);
      assertTrue(r2.compareTo(inner) > 0);

      System.out.println("Test init of Volume / Rectangle");
      assertEquals(r2, r3);

      System.out.println("Test volume() in Volume / Rectangle");
      assertEquals(25.0, r1.volume());
      assertEquals(24.0, r2.volume());

      System.out.println("Test intersect() in Volume / Rectangle");
      assertEquals(inner, r1.intersect(inner));
      assertEquals(null, r2.intersect(inner));
      assertEquals(new Rectangle(new Point2D(2, 1), new Point2D(4, 4)),
            r1.intersect(r2));

      System.out.println("Test compareTo() in Volume / Rectangle");
      assertTrue(r1.compareTo(r2) > 0);
      assertTrue(r2.compareTo(r1) < 0);
      assertTrue(r1.compareTo(r1) == 0);
      
      System.out.println("...finished.");

   }

   private static void assertEquals(Object expected, Object actual) {
      if (expected == null && actual == null) {
         return;
      }
      if (expected == null || !expected.equals(actual)) {
         System.out.println("FAILED expected " + expected + " but was "
               + actual);
      }
   }

   private static void assertEquals(double expected, double actual,
         double epsilon) {
      if (Math.abs(expected - actual) > epsilon) {
         System.out.println("FAILED expected " + expected + " but was "
               + actual);
      }
   }

   private static void assertTrue(boolean expected) {
      if (!expected) {
         System.out.println("FAILED true expected");
      }
   }
}

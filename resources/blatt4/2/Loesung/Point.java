package geometry;

import java.util.Arrays;

/**
 * Represents a point with n dimensions.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Point extends ComparableGeometry {

   /**
    * Holds the value of this Point in every dimension
    */
   private double[] values;

   /**
    * Create a new Point. Will copy all of the given values.
    * 
    * @param values
    */
   public Point(double... values) {
      super(values.length);
      this.values = new double[values.length];
      System.arraycopy(values, 0, this.values, 0, values.length);
   }

   /**
    * The volume of a Point is always 0.
    * 
    * @return 0
    */
   public double volume() {
      return 0;
   }

   /**
    * Returns the value at a given dimension.
    * 
    * @param dim
    *           position of the value
    * @return value at dimension <code>dim</code>
    */
   public double getValueAt(int dim) {
      return values[dim];
   }

   /**
    * If <code>other</code> is a <code>Volume</code> it intersects that
    * <code>Volume</code> with this Point, else returns null.
    * 
    * @param other
    *           the Geometry to be intersected with
    * 
    * @return The intersection if <code>other</code> is of type
    *         <code>Volume</code>, or null.
    */
   public Geometry intersect(Geometry other) {
      if (other instanceof Volume) {
         return ((Volume) other).intersect(this);
      }
      return null;
   }

   public String toString() {

      StringBuffer buf = new StringBuffer();
      buf.append("(");
      for (int dim = 0; dim < this.dimensions(); dim++) {
         buf.append(values[dim] + ((dim < this.dimensions() - 1) ? "," : ""));
      }
      buf.append(")");
      return buf.toString();
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(values);
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (!(obj instanceof Point))
         return false;
      Point other = (Point) obj;
      if (!Arrays.equals(values, other.values))
         return false;
      return true;
   }

}

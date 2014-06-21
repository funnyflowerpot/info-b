package geometry;

import java.util.Arrays;

/**
 * A Volume represents a n-dimensional rectangular and paraxial dataspace.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Volume extends ComparableGeometry {

   /**
    * Holds the borders of this Volume as Intervals.
    */
   private Interval[] intervals;

   /**
    * Create a new Volume which is defined by the minimum and maximum values in
    * every dimension by <code>Point</code> <code>min</code> and
    * <code>max</code>. Both Points must have the same number of
    * {@link #dimensions()}.
    * 
    * @param min
    *           first Point
    * @param max
    *           second Point
    * 
    * @throws RuntimeExcpetion
    *            if the dimension of <code>min</code> and <code>max</code> are
    *            not equal
    */
   public Volume(Point min, Point max) {
      super(min.dimensions());
      /*
       * Check for dimension
       */
      if (min.dimensions() != max.dimensions()) {
         throw new RuntimeException();
      }
      /*
       * transform the points into intervals
       */
      intervals = new Interval[this.dimensions()];
      for (int dim = 0; dim < intervals.length; dim++) {
         intervals[dim] = new Interval(min.getValueAt(dim), max.getValueAt(dim));
      }
   }

   /**
    * Create new Volume by using an array of Intervals, where every Interval
    * represents the borders in one dimension.
    * 
    * @param intervals
    */
   private Volume(Interval[] intervals) {
      super(intervals.length);
      this.intervals = new Interval[intervals.length];
      System.arraycopy(intervals, 0, this.intervals, 0, intervals.length);
   }

   /**
    * Compute the volume of this Volume, maybe zero if this Volume has no extent
    * in one dimension.
    * 
    * @return the volume of this Volume
    */
   public double volume() {
      int vol = 1;
      /*
       * multiply the length of this Volume in every dimension
       */
      for (Interval i : intervals) {
         vol *= i.length();
      }
      return vol;
   }

   /**
    * Intersect this Volume with another <code>Geometry</code>. Returns
    * <code>null</code> if there is no intersection. Returns a Volume if
    * <code>other</code> is also a Volume which intersects with this value.
    * Returns <code>other</code> if <code>other</code> is a <code>Point</code>
    * and intersects with this Volume. Returns <code>null</code> if the
    * {@link #dimensions()} of this Volume and <code>other</code> are not the
    * same.
    * 
    * @param other
    *           the Geometry to be intersected with.
    * 
    * @return the intersected Geometry or null.
    */
   public Geometry intersect(Geometry other) {
      if (this.dimensions() != other.dimensions()) {
         return null;
      }

      if (other instanceof Point) {
         /*
          * if other is a Point, test if this Volume contains other
          */
         Point p = (Point) other;
         for (int dim = 0; dim < this.intervals.length; dim++) {
            /*
             * test by every dimension.
             */
            if (!intervals[dim].contains(p.getValueAt(dim))) {
               return null;
            }
         }
         return p;
      }

      if (other instanceof Volume) {
         /*
          * if other is a Volume, intersect it with this Volume
          */
         Volume another = (Volume) other;
         Interval[] newone = new Interval[this.dimensions()];
         for (int dim = 0; dim < this.intervals.length; dim++) {
            /*
             * intersect by every dimension an create a new Volume by the
             * resulting Intervals.
             */
            newone[dim] = this.intervals[dim].intersect(another.intervals[dim]);
            if (newone[dim] == null) {
               return null;
            }
         }
         return new Volume(newone);
      }
      return null;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("(");
      for (int dim = 0; dim < this.dimensions(); dim++) {
         buf.append(intervals[dim].getBegin()
               + ((dim < this.dimensions() - 1) ? "," : ""));
      }
      buf.append("),(");

      for (int dim = 0; dim < this.dimensions(); dim++) {
         buf.append(intervals[dim].getEnd()
               + ((dim < this.dimensions() - 1) ? "," : ""));
      }
      buf.append(")");
      return buf.toString();

   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(intervals);
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (!(obj instanceof Volume))
         return false;
      Volume other = (Volume) obj;
      if (!Arrays.equals(intervals, other.intervals))
         return false;
      return true;
   }

}

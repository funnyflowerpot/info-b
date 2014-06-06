package geometry;


/**
 * Represents a closed interval with a {@link #begin} and an {@link #end}.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
class Interval {

   private double begin;
   private double end;

   /**
    * Copy Constructor
    * 
    * @param i
    */
   Interval(Interval i) {
      this(i.begin, i.end);
   }

   /**
    * With instantiation, begin and end are swapped if not begin < end.
    * 
    * @param begin
    *           beginning of this interval
    * @param end
    *           end of this interval
    */
   Interval(double begin, double end) {
      this.begin = begin < end ? begin : end;
      this.end = begin < end ? end : begin;
   }

   double getBegin() {
      return begin;
   }

   double getEnd() {
      return end;
   }

   /**
    * Intersect this interval with another. Returns a new Interval of
    * <code>null</code> if the two Intervals do not intersect.
    * 
    * @param i
    *           another Interval which should be intersected with this
    * @return the intersection or null
    */
   Interval intersect(Interval i) {
      if (this.end < i.begin || this.begin > i.end) {
         return null;
      } else {
         return new Interval(Math.max(this.begin, i.begin), Math.min(this.end,
               i.end));
      }
   }

   /**
    * Compute whether this interval contains another Interval <code>i</code>.
    * 
    * @param i
    *           another Interval which may be contained by this Interval
    * @return true if this contains <code>i</code>
    */
   boolean contains(Interval i) {
      if (this.begin < i.begin && this.end > i.end) {
         return true;
      } else {
         return false;
      }
   }

   /**
    * Return the absolute length of this Interval.
    * 
    * @return the length of this Interval.
    */
   double length() {
      return Math.abs(end - begin);
   }

   /**
    * Compute whether this Interval contains <code>d</code>.
    * 
    * @param d
    *           double value which may be contained by this Interval
    * @return true if <code>d</code> is contained by this Interval
    */
   boolean contains(double d) {
      return this.begin < d && this.end > d;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      long temp;
      temp = Double.doubleToLongBits(begin);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(end);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Interval other = (Interval) obj;
      if (Double.doubleToLongBits(begin) != Double
            .doubleToLongBits(other.begin))
         return false;
      if (Double.doubleToLongBits(end) != Double.doubleToLongBits(other.end))
         return false;
      return true;
   }
   
   
}

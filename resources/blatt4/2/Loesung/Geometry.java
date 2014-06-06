package geometry;

/**
 * Every Geometry represents a body in a data-space with {@link #dimensions()}.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public abstract class Geometry {

   /**
    * Holds the number of dimensions for this Geometry
    */
   private int dimension;

   /**
    * Create a new Geometry. Every Geometry must have a <code>dimension</code>
    * of at least 2.
    * 
    * @param dimension
    *           number of dimensions of the data space of this Geometry
    * 
    * @throws RuntimeException
    *            if the number of dimensions <code>dimension</code> is lesser
    *            than 2.
    */
   public Geometry(int dimension) {
      if (dimension < 2) {
         throw new RuntimeException("dimension is < 2");
      }
      this.dimension = dimension;
   }

   /**
    * Returns the number of dimensions of the data space of this Geometry.
    * 
    * @return number of dimensions of this Geometry
    */
   public int dimensions() {
      return this.dimension;
   }

   /**
    * Returns the volume of this Geometry. If {@link #dimensions()} is
    * <code>2</code>, the volume is the area.
    * 
    * @return volume of this Geometry
    */
   public abstract double volume();

   /**
    * Estimates if this Geometry intersects with another Geometry, and returns
    * it as a new Geometry. Returns <code>null</code> if this Geometry has
    * another {@link #dimensions()} as <code>other</code>, or if there is no
    * intersection.
    * 
    * @param other
    *           the Geometry to intersect this Geometry with
    * 
    * @return a new Geometry or <code>null</code>
    */
   public abstract Geometry intersect(Geometry other);

}

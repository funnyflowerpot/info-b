package pset04.p2;

import java.util.Arrays;

import geometry.Geometry;

/**
 * @author sriegl
 * Generalized form of a point to allow n dimensions.
 */
@SuppressWarnings("rawtypes")
public class Point extends Geometry implements Comparable {
	
  // variable parameter list of coordinates to define
  // position of point in n-dimensions.
  private double[] coords;
 
  /**
   * This method takes any amount of coordinates (<0) according
   * to their dimension (coords.length) and constructs a geometry
   * with the appropriate dimensions
   * @param <code>double</code> which contains the list of coordinates of the point
   * @throws RuntimeException if list of coordinates is empty: zero dimensions
   */
  public Point(double... coords) {
	// call geometry constructor with number of dimensions 
    super(coords.length);
    
    // Sanity check.
    if(coords.length == 0)
      throw new RuntimeException("Zero dimensions are not allowed.");
    
    // Copy to be independent of argument. 
    this.coords = Arrays.copyOf(coords, coords.length);
  }
 
  /* (non-Javadoc)
   * @see geometry.Geometry#intersect(geometry.Geometry)
   */
  @Override
  public Geometry intersect(Geometry other) {

    // Sanity check of dimensions.
    if(dimensions() != other.dimensions())
      throw new RuntimeException("Geometries must have equal dimensions.");
    
    if(other instanceof Point) {

      // Only cast once, outside the following loop.
      Point otherAsPoint = (Point) other;
      
      // Check if both points are equal by checking on equality of each
      // Coordinate.
      for(int i = 0; i < dimensions(); i++)
        if(otherAsPoint.coords[i] != this.coords[i])
          return null;
      
      // Return a new instance to protect ourself.
      return this.copy();
      
    } else 
      // Intersections between a Point and a more complex geometry should get
      // handled by the latter one, which must implement intersect as well
      return other.intersect(this);
  }
  /* (non-Javadoc)
   * @see geometry.Geometry#volume()
   */
  @Override
  public double volume() {
    // Points always have a volume of 0.
    return 0;
  }

  /**
   * Allow access on discrete coordinates, useful for e.g. Volume class.
   * @param i index of coordinate
   * @return value of coordinate
   */
  public double getCoord(int i) {
    return coords[i];
  }
  
  /**
   * Helper method, does what it is called.
   * @return copy of this instance
   */
  public Point copy() {
    return new Point(coords);
  }

  /*
   * Compare two objects. Other object must be a Geometry. Points are always
   * equal (return value 0), since all points have no volume. anything else
   * than a Point will be bigger (return value 1) than a Point.
   */
  @Override
  public int compareTo(Object o) {
    if(o instanceof Geometry) {
      // Comparing to another Point (with a volume of 0)?
      if(((Geometry) o).volume() == 0)
        return 0;
      // No further if, since we will not have negative volumes.
      else
        return 1;
    }
    else
      throw new RuntimeException("Cannot compare Geometry with non-Geometry.");
  }

  public String toString() {
    String output = this.getClass().getSimpleName();
    for(int i = 0; i < dimensions(); i++)
      output += (i == 0 ? "(<" : ", ") + coords[i];
    return output + ">)";
  }
}

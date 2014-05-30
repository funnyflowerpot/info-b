package pset04.p2;

import geometry.Geometry;

@SuppressWarnings("rawtypes")
public class Volume extends Geometry implements Comparable {

  private Point point1;
  private Point point2;

  public Volume(Point point1, Point point2) {
    super(point1.dimensions());
    if(point1.dimensions() != point2.dimensions())
      throw new RuntimeException("Points must have same dimensions.");
    this.point1 = point1;
    this.point2 = point2;
  }

  @Override
  public Geometry intersect(Geometry other) {
    
    // Sanity check of dimensions.
    if(dimensions() != other.dimensions())
      throw new RuntimeException("Geometries must have equal dimensions.");
    
    if(other instanceof Point) {
      
      // Only cast once, outside the following loop.
      Point otherAsPoint = (Point) other;
      
      double minThis, maxThis;
      
      // Check on intersection by considering each dimension on its own.
      for(int i = 0; i < dimensions(); i++) {
        minThis = Math.min(this.point1.getCoord(i), this.point2.getCoord(i));
        maxThis = Math.max(this.point1.getCoord(i), this.point2.getCoord(i));
        
        // If point-ordinate is outside the volume-interval for this
        // dimension, we do not have an intersection.
        if(minThis > otherAsPoint.getCoord(i) || maxThis < otherAsPoint.getCoord(i))
          return null;
      }
      
      // A intersection with an point is always a point.
      return otherAsPoint.copy();
    }
    
    if(other instanceof Volume) {

      // Only cast once, outside the following loop.
      Volume otherAsVolume = (Volume) other;

      double minOther, maxOther, minThis, maxThis, minOfMaxBoth, maxOfMinBoth;
      double[] minCoords = new double[dimensions()];
      double[] maxCoords = new double[dimensions()];
      
      for(int i = 0; i < dimensions(); i++) {
        minOther = Math.min(otherAsVolume.point1.getCoord(i), otherAsVolume.point2.getCoord(i));
        maxOther = Math.max(otherAsVolume.point1.getCoord(i), otherAsVolume.point2.getCoord(i));
        minThis = Math.min(this.point1.getCoord(i), this.point2.getCoord(i));
        maxThis = Math.max(this.point1.getCoord(i), this.point2.getCoord(i));
        minOfMaxBoth = Math.min(maxOther, maxThis);
        maxOfMinBoth = Math.max(minOther, minThis);
        
        if(maxOfMinBoth > minOfMaxBoth)
          return null;
        
        minCoords[i] = maxOfMinBoth;
        maxCoords[i] = minOfMaxBoth;
      }
      
      return new Volume(new Point(minCoords), new Point(maxCoords));
    }
    
    // No Point, no Volume: We do not know how to intersect with an unknown
    // Geometry.
    throw new RuntimeException("Unsupported Geometry-subtype.");
  }

  @Override
  public double volume() {
    double volumeProduct = 1;
    for(int i = 0; i < dimensions(); i++)
      volumeProduct *= point1.getCoord(i) - point2.getCoord(i);
    return Math.abs(volumeProduct);
  }

  @Override
  public int compareTo(Object o) {
    if(o instanceof Geometry) {
      double otherVolume = ((Geometry) o).volume();
      double thisVolume = volume();
      if(thisVolume == otherVolume)
        return 0;
      else if(thisVolume < otherVolume)
        return -1;
      else
        return 1;
    }
    else
      throw new RuntimeException("Cannot compare Geometry with non-Geometry.");
  }

  public String toString() {
    String output = this.getClass().getSimpleName();
    for(int i = 0; i < dimensions(); i++)
      output += (i == 0 ? "(<" : ", ") + point1.getCoord(i);
    for(int i = 0; i < dimensions(); i++)
      output += (i == 0 ? ">, <" : ", ") + point2.getCoord(i);
    return output + ">)";
  }
}

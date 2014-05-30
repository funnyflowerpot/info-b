package pset04.p2;

import java.util.Scanner;

import geometry.Geometry;

public class CustomGeometryTest {
  
  public static final String REGEX_VOLUME = "^[^;]+;[^;]+$";
  public static Scanner scanner;
  

  /**
   * Convert a string to a double array. Raise an exception if a value cannot
   * get parsed.
   * @param input
   * @return
   */
  public static double[] stringToDoubleArray(String input) {
    String[] tokens = input.split(" +");
    double[] values = new double[tokens.length];
    for(int i = 0; i < tokens.length; i++)
      values[i] = Double.parseDouble(tokens[i]);
    return values;
  }
  
  /**
   * Read a Geometry from STDIN.
   * @param id distinguishable reference as feedback for the user
   * @return valid Geometry instance
   */
  public static Geometry promptGeometry(String id) {
    Geometry result = null;
    String line;
    
    // Prompt until we get valid input that results in a Geometry instance.
    while(result == null) {

      // Fetch input.
      System.out.println("Please enter the " + id + " geometry.");
      line = scanner.nextLine().trim();
      
      // Handle empty input.
      if(line.length() == 0)
        continue;

      // In case we're encountering trouble, an exception will be thrown and
      // the input loop will get restarted.
      try {
        
        // For a Volume we must only have one semicolon in our input string,
        // that separates to point-specifications from each other.
        if(line.matches(REGEX_VOLUME)) {
          String[] pointStrings = line.split(" *; *");
          
          // Parse both sides of the semicolon. Exceptions might get thrown.
          double[] point1coords = stringToDoubleArray(pointStrings[0]);
          double[] point2coords = stringToDoubleArray(pointStrings[1]);
          
          // Both points must have equal dimensions and must be big enough.
          if(point1coords.length != point2coords.length || point1coords.length < 2) {
            System.err.println("Dimensions of points for Volume are not equal or less than two.");
            continue; 
          }
          
          // Distinguish between Rectangle and Volume.
          if(point1coords.length == 2) {
            System.out.println("Rectangle detected.");
            result = new Rectangle(
                new Point2D(point1coords[0], point1coords[1]), 
                new Point2D(point2coords[0], point2coords[1]));
          } else {
            System.out.println("Volume detected.");
            result = new Volume(new Point(point1coords), new Point(point2coords));
          }
          
        // If we do not have a volume, assume a point. If this does not hold,
        // The program will abort anyway.
        } else {
          
          // Parse input. Exceptions might get thrown.
          double[] pointCoords = stringToDoubleArray(line);
          
          // Distinguish between Point and Point2D.
          if(pointCoords.length < 2) {
            System.err.println("Dimensions for point are less than two.");
          } else if(pointCoords.length == 2) {
            System.out.println("Will use Point2D.");
            result = new Point2D(pointCoords[0], pointCoords[1]);
          } else {
            System.out.println("Will use Point.");
            result = new Point(pointCoords);
          }
        }
        
      // Handle badly formatted double values and restart prompting loop.
      } catch(NumberFormatException nfe) {
        System.err.println("Could not parse value: " + nfe.getMessage());
      }
    }

    return result;
  }
  
  public static void main(String[] args) {

    scanner = new Scanner(System.in);

    System.out.println("Please enter values for two subclasses of Geometry to run tests on. The syntax");
    System.out.println("for points and volumes are as follows (noted in EBNF):");
    System.out.println("  point  ::= coord \" \" { coord }");
    System.out.println("  volume ::= point \";\" point");
    System.out.println("A <coord> is a double value. If exactly two coordinates are given, the two-");
    System.out.println("dimensional special case (Point2D or Rectangle, respectively) will be assumed.");
    System.out.println("For volumes, corresponding points must have equal dimensions.");
    System.out.println();
    
    Geometry geom1 = promptGeometry("first");
    Geometry geom2 = promptGeometry("second");
    
    System.out.println();
    System.out.println("Thank you. Your entered geometries are:");
    System.out.println("geom1: " + geom1);
    System.out.println("geom2: " + geom2);

    System.out.println();
    System.out.println("geom1.dimensions() == " + geom1.dimensions());
    System.out.println("geom2.dimensions() == " + geom2.dimensions());
    System.out.println("geom1.volume() == " + geom1.volume());
    System.out.println("geom2.volume() == " + geom2.volume());

    System.out.println();
    System.out.println("geom1.intersect(geom2) == " + geom1.intersect(geom2));
    System.out.println("geom2.intersect(geom1) == " + geom2.intersect(geom1));
 }
}

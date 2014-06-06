package geometry;

/**
 * Extends the Geometry by the interface <code>Comparable</code>
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public abstract class ComparableGeometry extends Geometry implements Comparable {

	/**
	 * Create a new ComparableGeometry. Every ComparableGeometry must have a
	 * <code>dimension</code> > 1.
	 * 
	 * @param dimension
	 *            number of dimensions of the data space of this Geometry
	 */
	public ComparableGeometry(int dimension) {
		super(dimension);
	}

	/**
	 * Compares this Geometry with another one. Throws a
	 * <code>RuntimeException</code> if <code>o</code> is not of type Geometry.
	 * Otherwise compare two Geometries by their volume.
	 * 
	 * @returns if o is of type Geometry, the difference between the volumes
	 */
	public int compareTo(Object o) {
		if (o instanceof Geometry) {
			return (int) ((this.volume() - ((Geometry) o).volume()));
		} else {
			throw new RuntimeException("");
		}
	}

}

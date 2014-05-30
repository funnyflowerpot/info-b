package pset05.p1;
/**
 * This class defines library items for the library class
 * It is a superclass for any library item e.g.: books or blurays
 * @author pwicke
 * @date 23.05.14
 */
public abstract class LibraryItem {
	
	// data field marking an item as borrowed(true)
	private boolean isBorrowed;
	
	// Constructor
	public LibraryItem(){
		setBorrowed(false);
	}
	
	/**
	 * Setter method for availability status
	 * @param isBorrowed <code>true</code> if borrowed, <code>false</code> if item returned
	 */
	public void setBorrowed(boolean isBorrowed){
		this.isBorrowed = isBorrowed;
	}
	/**
	 * Checks availability of an item
	 * @return <code>true</code> if item is unavailable/borrowed
	 */
	public boolean isBorrowed(){
		return isBorrowed;
	}
	
	/**
	 * <code>abstract</code> method that forces the subclasses
	 * to implement a method which returns a descriptive string
	 * @return String description of item
	 */
	public abstract String getDescription(); {
		// return description string
	}

}

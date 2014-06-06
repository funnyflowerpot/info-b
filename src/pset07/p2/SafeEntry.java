package pset07.p2;

/**
 * This class enables save entries for the TypeSaveList.
 * All methods are protected generic to maintain absolute 
 * type safety.
 * @author pwicke
 * @param <T> is the placeholder for the data type of the list entry 
 */
public class SafeEntry<T> {
	
	// protected and generic data
	protected T elem;	
	protected SafeEntry<T> next;
	
	// Constructor chaining
	protected SafeEntry(T one, SafeEntry<T> entry){
		this.elem = one;
		this.next = entry;
	}
	protected SafeEntry(T single){
		this(single, null);
	}
	protected SafeEntry(){
		this(null,null);
	}
}


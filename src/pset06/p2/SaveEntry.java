package pset06.p2;

/**
 * This class enables save entries for the TypeSaveList.
 * All methods are protected generic to maintain absolute 
 * type safety.
 * @author pwicke
 * @param <T> is the placeholder for the data type of the list entry 
 */
public class SaveEntry<T> {
	
	// protected and generic data
	protected T elem;	
	protected SaveEntry<T> next;
	
	// Constructor chaining
	protected SaveEntry(T one, SaveEntry<T> entry){
		this.elem = one;
		this.next = entry;
	}
	protected SaveEntry(T single){
		this(single, null);
	}
	protected SaveEntry(){
		this(null,null);
	}
}


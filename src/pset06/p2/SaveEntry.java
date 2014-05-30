package pset06.p2;

/**
 * @author pwicke
 *
 * TODO (pw) more javadoc for this nation!
 *
 * @param <T>
 */
public class SaveEntry<T> {
	
  // TODO (pw) the name "generic" might be misleading. suggestion: "elem"
	protected T generic;	
	protected SaveEntry<T> next;
	
	protected SaveEntry(){
		this(null,null);
	}
	protected SaveEntry(T single){
		this(single, null);
	}
	protected SaveEntry(T one, SaveEntry<T> entry){
		this.generic = one;
		this.next = entry;
	}
}


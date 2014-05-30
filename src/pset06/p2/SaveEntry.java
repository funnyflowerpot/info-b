package pset06.p2;

public class SaveEntry<T> {
	
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


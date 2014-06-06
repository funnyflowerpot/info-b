package pset07.p2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is the type Safe list. It achieves this
 * type safety as the class is <bold>generic</bold>.
 * It implements the Interface Iterable and uses the
 * the SafeEntry class to maintain type safety. 
 * 
 * @author pwicke
 * @date 05.06.2014
 * @param <T> is the placeholder for type of all List entries
 */
public class TypeSafeList<T> implements Iterable<T> {
	
	
	  /**
	    * Reference on the first Entry of this List
	    */
	   protected SafeEntry<T> begin;
	   /**
	    * References before the actual Entry of this List
	    */
	   protected SafeEntry<T> pos; 

	   /**
	    * Create a new empty List.
	    */
	   public TypeSafeList() {
	      pos = begin = new SafeEntry<T>();
	   }
	   
	    @Override
	   public TypeSafeList<T> clone() {
		   // try catch block to avoid CloneNotSupportedException - as seen in Tutorial
		try {
			@SuppressWarnings("unchecked")
			// by convention, the clones is created by calling super.clone()
			TypeSafeList<T> clonedList = (TypeSafeList<T>) super.clone();
			return clonedList;
			// if the generic does not implement Cloneable, throw Exception
		} catch (CloneNotSupportedException e) {
			throw new InternalError("Clone not supported!");
		} 
	}
	   	   /**
	   	    * Adds a new generic type to the TypeSafeList.
	   	    * It instantiates a new SafeEntry
	   	    * @param x of type <code>generic</t> that you want to add
	   	    */
		   public void add(T x) {
		      SafeEntry<T> newone = new SafeEntry<T>( x, pos.next);
		      pos.next = newone;
		   }
		   
		   /**
		    * Returns the actual element of this TypeSafeList.
		    * 
		    * @return the actual element of <code>generic</code> type
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this TypeSafeList already has been reached.
		    */
		   public T elem() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this TypeSafeList");
		      }
		      return pos.next.elem;
		   }
		   /**
		    * Deletes the actual element of this TypeSafeList. The element after the actual
		    * element will become the new actual element.
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this TypeSafeList already has been reached.
		    */
		   public void delete() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this TypeSafeList");
		      }
		      pos.next = pos.next.next;
		   }
		   /**
		    * Determines if this TypeSafeList is empty or not.
		    * 
		    * @return <code>true</code>, if there are no elements in this TypeSafeList
		    */
		   public boolean empty() {
		      return begin.next == null;
		   }

		   /**
		    * Determines if it is possible to {@link #advance()} in this TypeSafeList. Returns
		    * <code>true</code> if the last position of this TypeSafeList has been reached. An
		    * {@link #empty()} TypeSafeList will alway deliver <code>true</code>
		    * 
		    * @return <code>true</code> if the last Entry in this TypeSafeList already has been
		    *         reached.
		    */
		   public boolean endpos() { // true, wenn am Ende
		      return pos.next == null;
		   }

		   /**
		    * Returns to the beginning of this TypeSafeList.
		    */
		   public void reset() {
		      pos = begin;
		   }

		   /**
		    * Advances one step in this TypeSafeList.
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this TypeSafeList already has been reached.
		    */
		   public void advance() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this TypeSafeList");
		      }
		      pos = pos.next;
		   }
		   
		/* (non-Javadoc)
		 * @see java.lang.Iterable#iterator()
		 */
		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>(){
				// flag: true if next() is called at least once, false if remove() called twice 
				//		 in a row, thus indicating a state of valid modification of the collection.
				private boolean modifiableState = false;
				
				private SafeEntry<T> element = (SafeEntry<T>) TypeSafeList.this.begin;
				
				@Override
				public boolean hasNext() {
					// if list is empty or endpos reached, there is no further elem in list.
						return (element.next!=null);
				}


				@Override
				public T next() {
					// Check if in modifiable state
					if(!modifiableState) modifiableState=true;
					
					if(this.hasNext()){
						// if there is an element answer with current element and
						// iterate to the next element
						SafeEntry<T> current = element.next;
						element = element.next; 
						return (T) current.elem;
					}
					else
						throw new NoSuchElementException();
				}

				@Override
				public void remove() {
					if(this.hasNext() && modifiableState){
						
						// take current element and assign pointer to next element
						// this will leave the element unreferenced, thus it will be deleted
						TypeSafeList.this.pos.next = TypeSafeList.this.pos.next.next;
						// set unmodifiable state
						modifiableState = false; 
					}
					else throw new IllegalStateException();
				}
			};
		}
}
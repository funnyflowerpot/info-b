package pset07.p3;
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
public class VisitList<T> implements Visitable<T>{
	
	
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
	   public VisitList() {
	      pos = begin = new SafeEntry<T>();
	   }
	   
	    @Override
	   public VisitList<T> clone() {
		   // try catch block to avoid CloneNotSupportedException - as seen in Tutorial
		try {
			@SuppressWarnings("unchecked")
			// by convention, the clones is created by calling super.clone()
			VisitList<T> clonedList = (VisitList<T>) super.clone();
			return clonedList;
			// if the generic does not implement Cloneable, throw Exception
		} catch (CloneNotSupportedException e) {
			throw new InternalError("Clone not supported!");
		} 
	}
	   	   /**
	   	    * Adds a new generic type to the VisitList.
	   	    * It instantiates a new SafeEntry
	   	    * @param x of type <code>generic</t> that you want to add
	   	    */
		   public void add(T x) {
		      SafeEntry<T> newone = new SafeEntry<T>( x, pos.next);
		      pos.next = newone;
		   }
		   
		   /**
		    * Returns the actual element of this VisitList.
		    * 
		    * @return the actual element of <code>generic</code> type
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this VisitList already has been reached.
		    */
		   public T elem() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this VisitList");
		      }
		      return pos.next.elem;
		   }
		   /**
		    * Deletes the actual element of this VisitList. The element after the actual
		    * element will become the new actual element.
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this VisitList already has been reached.
		    */
		   public void delete() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this VisitList");
		      }
		      pos.next = pos.next.next;
		   }
		   /**
		    * Determines if this VisitList is empty or not.
		    * 
		    * @return <code>true</code>, if there are no elements in this VisitList
		    */
		   public boolean empty() {
		      return begin.next == null;
		   }

		   /**
		    * Determines if it is possible to {@link #advance()} in this VisitList. Returns
		    * <code>true</code> if the last position of this VisitList has been reached. An
		    * {@link #empty()} VisitList will alway deliver <code>true</code>
		    * 
		    * @return <code>true</code> if the last Entry in this VisitList already has been
		    *         reached.
		    */
		   public boolean endpos() { // true, wenn am Ende
		      return pos.next == null;
		   }

		   /**
		    * Returns to the beginning of this VisitList.
		    */
		   public void reset() {
		      pos = begin;
		   }

		   /**
		    * Advances one step in this VisitList.
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this VisitList already has been reached.
		    */
		   public void advance() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this VisitList");
		      }
		      pos = pos.next;
		   }

		@Override
		public void accept(Visitor<T> v) {
			// TODO (pw): Auto-generated method stub PLUS SUGGESTION
		  reset();
		  while(!endpos()) {
		    if(!v.visit(elem()))
		      break;
		    advance();
		  }
		}
}
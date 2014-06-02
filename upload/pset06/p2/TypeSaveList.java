package util;

import java.util.NoSuchElementException;

/**
 * This class is the type save list. It achieves this
 * type safety as the class is <bold>generic</bold>.
 * It implements the Interface Clonable and uses the
 * the SaveEntry.class to maintain type safety. 
 * 
 * @author pwicke
 * @date 01.06.2014
 * @param <T> is the placeholder for type of all List entries
 */
public class TypeSaveList<T> implements Cloneable {
	
	
	  /**
	    * Reference on the first Entry of this List
	    */
	   private SaveEntry<T> begin;
	   /**
	    * References before the actual Entry of this List
	    */
	   private SaveEntry<T> pos; 

	   /**
	    * Create a new empty List.
	    */
	   public TypeSaveList() {
	      pos = begin = new SaveEntry<T>();
	   }
	   
	   /**
	    * By convention, the returned object should be obtained by calling super.clone.
	    * The method clone for class Object performs a specific cloning operation. First,
	    * if the class of this object does not implement the interface Cloneable,
	    * then a CloneNotSupportedException is thrown. Otherwise, this method creates a new
	    * instance of the class of this object and initialises all its fields with exactly the
	    * contents of the corresponding fields of this object, as if by assignment; the contents
	    * of the fields are not themselves cloned.
	    * Thus, this method performs a "shallow copy" of this object, not a "deep copy" operation. 
	    * @return TypeSaveList that is a clone 
	    * @throws CloneNotSupportedException if 
	    */
	   public TypeSaveList<T> clone() {
		   // try catch block to avoid CloneNotSupportedException - as seen in Tutorial
		try {
			@SuppressWarnings("unchecked")
			// by convention, the clones is created by calling super.clone()
			TypeSaveList<T> clonedList = (TypeSaveList<T>) super.clone();
			return clonedList;
			// if the generic does not implement Cloneable, throw Exception
		} catch (CloneNotSupportedException e) {
			throw new InternalError("Clone not supported!");
		} 
	}
	   
/* The methods add and elem require to act with type Object in their signature. If you extend the
 * list you have to override these two methods with generic parameters and return types. 
 * If you just override these methods with the generic types - this won't work, as Java uses
 * type erasure, i.e. Java will delete all type-information and replace it with the Object type.
 * This will result in ambiguous signatures. The 'overridden' and 'original' are then
 * indistinguishable -> this will result in a compiler error
 */
	   	   /**
	   	    * Adds a new generic type to the TypeSaveList.
	   	    * It instantiates a new SaveEntry
	   	    * @param x of type <code>generic</t> that you want to add
	   	    */
		   public void add(T x) {
		      SaveEntry<T> newone = new SaveEntry<T>( x, pos.next);
		      pos.next = newone;
		   }
		   
		   /**
		    * Returns the actual element of this TypeSaveList.
		    * 
		    * @return the actual element of <code>generic</code> type
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this TypeSaveList already has been reached.
		    */
		   public T elem() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this TypeSaveList");
		      }
		      return pos.next.elem;
		   }
////////////* Same methods as List class implements */////////////////////////////////////////////
		   /**
		    * Deletes the actual element of this TypeSaveList. The element after the actual
		    * element will become the new actual element.
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this TypeSaveList already has been reached.
		    */
		   public void delete() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this TypeSaveList");
		      }
		      pos.next = pos.next.next;
		   }
		   /**
		    * Determines if this TypeSaveList is empty or not.
		    * 
		    * @return <code>true</code>, if there are no elements in this TypeSaveList
		    */
		   public boolean empty() {
		      return begin.next == null;
		   }

		   /**
		    * Determines if it is possible to {@link #advance()} in this TypeSaveList. Returns
		    * <code>true</code> if the last position of this TypeSaveList has been reached. An
		    * {@link #empty()} TypeSaveList will alway deliver <code>true</code>
		    * 
		    * @return <code>true</code> if the last Entry in this TypeSaveList already has been
		    *         reached.
		    */
		   public boolean endpos() { // true, wenn am Ende
		      return pos.next == null;
		   }

		   /**
		    * Returns to the beginning of this TypeSaveList.
		    */
		   public void reset() {
		      pos = begin;
		   }

		   /**
		    * Advances one step in this TypeSaveList.
		    * 
		    * @throws NoSuchElementException
		    *            if the last Entry of this TypeSaveList already has been reached.
		    */
		   public void advance() {
		      if (endpos()) {
		         throw new NoSuchElementException("Already at the end of this TypeSaveList");
		      }
		      pos = pos.next;
		   }

}

/* This is just for purposes of the 'Testat'
*public interface Cloneable
*A class implements the Cloneable interface to indicate to the Object.clone() method
*that it is legal for that method to make a field-for-field copy of instances of that class.
*Invoking Object's clone method on an instance that does not implement the Cloneable interface
*results in the exception CloneNotSupportedException being thrown.
*By convention, classes that implement this interface should override Object.clone
*(which is protected) with a public method. See Object.clone() for details on overriding
*this method.
*Note that this interface does not contain the clone method.
*Therefore, it is not possible to clone an object merely by virtue of the fact
*that it implements this interface. Even if the clone method is invoked reflectively,
*there is no guarantee that it will succeed.
*Since:
*JDK1.0
 */


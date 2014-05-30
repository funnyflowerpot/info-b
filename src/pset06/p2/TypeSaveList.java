package pset06.p2;

import java.util.NoSuchElementException;

public class TypeSaveList<T> implements Cloneable {
	
	//TODO (answered): Answer me the following question
	// In which relation does the T of TypeSaveList stand to the <T> of SaveEntry
	// Does it assure that both generic classes uses the same type for T ?

  //>>> Yes.
  
	// Is it even possible to have e.g. SaveEntry<G> and implement it in TypeSaveList<T> ?

  //>>> No. The T in TypeSaveList<T> is some kind of declaration, which means
  //>>> that T can be referred to somewhere else in the class.
  
  //>>> You can work around that by having your class declaration saying:
  //>>> public class TypeSaveList<T, G> implements Cloneable {
  
	// I tested implementing SaveEntry<G> and TypeSaveList MUST ONLY use SaveEntry<T> to refer
	// to entries. This means, that T is the same TYPE for both classes, once it is defined in
	// the TypeSaveList<T>. Even though the SaveEntry class is not forced to call it 'T' in its
	// class.
  
  //>>> Unfortunately, I am not completely sure whether I get, what you are
  //>>> trying to say, but it sounds like you're right.
	
	// This class defines with it's <T> what kind of type the OTHER class MUST use. Correct if 
	// I'm wrong.
  
  //>>> Since I am uncertain about the cause for your question I will provide
  //>>> a short monologue.
  
  //>>> I'd say yes, but it is worth noticing, that this is one effect of the
  //>>> generic in this class. The T for class definition TypeSaveList<T> is a
  //>>> "place holder" for an arbitrary class, as well as for the class
  //>>> definition of SaveEntry<T>. Since the class definition TypeSaveList<T>
  //>>> wants to use the class SaveEntry, the parameter for the class must be
  //>>> specified. It happens, that for the usage of class SaveEntry the T,
  //>>> declared for TypeSaveList<T>, gets used as parameter for SaveEntry<T>.

  //>>> You could say the parameter for class TypeSaveList simultaneously
  //>>> determines the parameter for class SaveEntry (within TypeSaveList),
  //>>> since the T in the definition of class SaveEntry does not only have 
  //>>> the same name ("T" for generics by convention), but it is equal to the
  //>>> parameter for class TypeSaveList (again: only for all SaveEntry within
  //>>> TypeSaveList). Outside of TypeSaveList the T for SaveEntry must be
  //>>> specified specially and can be arbitrary again.
	
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
	   
/* The methods add and elem require to act with Objects in their signature. If you extend the
 * list you have to override these two methods with generic parameters and return types. 
 * If you just override these methods with the generic types - this won't work, as Java uses
 * type erasure, i.e. Java will delete all type-information and replace it with the Object type.
 * This will result in ambiguous signatures. The 'overridden' and 'original' are then
 * indistinguishable -> this will result in a compiler error
 */
	   	   /**
	   	    * Adds a new generic type to the TypeSaveList.
	   	    * It instatiates a new SaveEntry
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
		      return pos.next.generic;
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


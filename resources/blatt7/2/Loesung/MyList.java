package util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple linked list. One may go through this list by {@link #advance()}
 * until the last position ({@link #endpos()}) is reached. One also may
 * {@link #delete()} and {@link #insert(Object)} elements. After advancing it is
 * possible to go back to the beginning by {@link #reset()}.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class MyList<E> implements Cloneable, Iterable<E>, Visitable<E> {

   /**
    * Reference on the first Entry of this List
    */
   private MyEntry<E> begin;
   private int modCount;

   /**
    * References before the actual Entry of this List
    */
   private MyEntry<E> pos;

   /**
    * Create a new empty List.
    */
   public MyList() {
      pos = begin = new MyEntry<E>();
      modCount = 0;
   }

   @Override
   public void accept(Visitor<E> v) {
      MyEntry<E> current = this.begin.next;

      boolean moveOn = true;

      while (current != null && (moveOn = v.visit(current.o))) {
         current = current.next;
      }
   }

   /**
    * Inserts <code>o</code> in this List. It will be placed before the actual
    * element. After insertion the inserted element will become the actual
    * element.
    * 
    * @param x
    *           the element to be inserted
    */
   public void add(E x) {
      MyEntry<E> newone = new MyEntry<E>(x, pos.next);

      pos.next = newone;
      modCount++;
   }

   /**
    * Advances one step in this List.
    * 
    * @throws RuntimeExcpetion
    *            if the last Entry of this List already has been reached.
    */
   public void advance() {
      if (endpos()) {
         throw new NoSuchElementException("Already at the end of this List");
      }
      pos = pos.next;
   }

   /**
    * Clones this MyList. Will create a new independent MyList which actual
    * position lies at the beginning of this MyList. This clone operation also
    * fulfills the optional requirements defined by the {@link Object#clone()}
    * operation. NOTE: Inserted elements will not be cloned, due to the fact,
    * that the {@link Object#clone()} is <code>protected</code>.
    * 
    * @see Object#clone()
    */
   @Override
   public MyList<E> clone() {
      try {

         MyList<E> clone = (MyList<E>) super.clone();
         clone.begin = this.begin.clone();
         clone.pos = clone.begin;

         return clone;
      } catch (CloneNotSupportedException e) {
         throw new InternalError();
      }
   }

   /**
    * Deletes the actual element of this List. The element after the actual
    * element will become the new actual element.
    * 
    * @throws RuntimeExcpetion
    *            if the last Entry of this List already has been reached.
    */
   public void delete() {
      if (endpos()) {
         throw new NoSuchElementException("Already at the end of this List");
      }
      modCount++;
      pos.next = pos.next.next;
   }

   /**
    * Returns the actual element of this List.
    * 
    * @return the actual element
    * 
    * @throws RuntimeException
    *            if the last Entry of this List already has been reached.
    */
   public E elem() {
      if (endpos()) {
         throw new NoSuchElementException("Already at the end of this List");
      }
      return pos.next.o;
   }

   /**
    * Determines if this List is empty or not.
    * 
    * @return <code>true</code>, if there are no elements in this List
    */
   public boolean empty() {
      return begin.next == null;
   }

   /**
    * Determines if it is possible to {@link #advance()} in this List. Returns
    * <code>true</code> if the last position of this List has been reached. An
    * {@link #empty()} List will alway deliver <code>true</code>
    * 
    * @return <code>true</code> if the last Entry in this List already has been
    *         reached.
    */
   public boolean endpos() { // true, wenn am Ende
      return pos.next == null;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      MyList other = (MyList) obj;
      if (other.begin != null)
         return false;
      if (!begin.equals(other.begin))
         return false;
      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((begin == null) ? 0 : begin.hashCode());
      return result;
   }

   @Override
   public Iterator<E> iterator() {
      return new ListIterator();
   }

   /**
    * Returns to the beginning of this List.
    */
   public void reset() {
      pos = begin;
   }

   /**
    * A simple Iterator for the iteration through a {@link GenericList}.
    * 
    * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
    * 
    */
   public class ListIterator implements Iterator<E> {

      /**
       * The expected number of modifications on the list which belongs to this
       * Iterator.
       */
      private int expectedModCount;

      /**
       * The Entry that has been returned by the last call of the method
       * {@link #next()}
       */
      private MyEntry<E> last;

      /**
       * The next Entry which will be returned by the method {@link #next()}
       */
      private MyEntry<E> next;

      /**
       * The Entry that has been returned before {@link #last} has been
       * returned. Will be set to null after {@link #remove()} calling
       * {@link #remove()} until the next call of {@link #next()}.
       */
      private MyEntry<E> previousLast;

      /**
       * Create a new ListIterator which start iterating at the front of this
       * list.
       */
      private ListIterator() {
         this.next = begin.next;
         this.last = begin;
         this.expectedModCount = MyList.this.modCount;
         this.previousLast = null;
      }

      @Override
      public boolean hasNext() {
         return next != null;
      }

      /**
       * Returns the next element from the list that belongs to this Iterator.
       * 
       * @return the next element
       * 
       * @throws ConcurrentModificationException
       *            if the list that belongs to this Iterator has been modified
       *            after this Iterator has been instantiated.
       * @throws NoSuchElementExcetpion
       *            If this Iterator has already reached the last element of the
       *            list
       */
      @Override
      public E next() {
         if (expectedModCount != MyList.this.modCount) {
            throw new ConcurrentModificationException();
         }
         if (next == null) {
            throw new NoSuchElementException();
         }
         previousLast = last;
         last = next;
         next = next.next;

         return last.o;

      }

      /**
       * Removes the element from the list that belongs to this Iterator, that
       * has last been returned by {@link #next()}.
       * 
       * @throws ConcurrentModificationException
       *            if the list that belongs to this Iterator has been modified
       *            after this Iterator has been instantiated.
       * @throws NoSuchElementException
       *            If this Iterator has already reached the last element of the
       *            List
       * @throws IllegalStateException
       *            If the {@link #next()} method has not yet been called
       */
      @Override
      public void remove() {
         if (expectedModCount != MyList.this.modCount) {
            throw new ConcurrentModificationException();
         }
         if (previousLast == null) {
            throw new IllegalStateException();
         }
         expectedModCount++;
         MyList.this.modCount++;
         previousLast.next = next;
         previousLast = null;
      }
   }
}
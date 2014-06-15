package pset07.p2;

import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * SCHMANKERL' nur zu Übungszwecken! Nicht bewerten!
 * @author sriegl
 *
 * @param <T>
 */
public class ListIterator<T> implements Iterator<T> {

			// flag: true if next() is called at least once, false if remove called twice 
			//		 in a row
			private boolean modifiableState = false;
			
			
			// Desperate try to reach the data fields of TypeSafeList
			protected SafeEntry<T> head;

			private TypeSafeList<T> list; 
			
			public ListIterator(TypeSafeList<T> list){
				this.head = list.begin;
//				this.list = list;
			}
			
			@Override
			public boolean hasNext() {
				// if list is empty or endpos reached, there is no further elem in list.
				if(list.empty()||list.endpos())
					return false;
				else{
					return true;
				}
			}

			@Override
			public T next() {
				if(!modifiableState) modifiableState=true;
			
				if(this.hasNext())
					return list.elem();
				else
					throw new NoSuchElementException();
				}

			@Override
			public void remove() {
				if(this.hasNext() && modifiableState){
					list.delete();
					modifiableState = false;
				}
				else throw new IllegalStateException();
			}
		}

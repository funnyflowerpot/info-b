package pset07.p2;
import java.util.Iterator;
import java.util.NoSuchElementException;
import pset07.p2.TypeSafeList;


/**
 * Test class to check whether the Iterator is implemented correctly according
 * to the problem set 07. It tests all methods 'hasNext()','next()' and 'remove()'
 * by iterating with the iterator through the list, removing some elements and 
 * checking if the Exceptions are properly thrown. Fully automated, of course.
 * @author Wicke
 * @date 05.06.2014 
 *
 */
public class IteratorTest {
	
	// flag for automation process
	private static boolean checkFlag 	= false;
	
	public static void main(String[] args){
		
		// Introduce program
		System.out.println("This program will check the Iterator-Implementation:");
		
		// Create some integers and a list for integers
		int[] intArray 	= {2,4,6,8,10};
		TypeSafeList<Integer> intList = new TypeSafeList<Integer>();
		for(int i=0; i<intArray.length; i++){
			intList.add(intArray[i]);
			intList.advance();
		}
		intList.reset();
		
		// Create iterator
		Iterator<Integer> it = intList.iterator();
		
		/*
		 * FIRST CHECK:
		 * As long as the iterator hasNext-element, 
		 * check if "list.elem" equals "iterator.elem"
		 */
		while(it.hasNext()){
			if( intList.elem().equals(it.next()) ) {
				checkFlag = true;
				intList.advance();
			}
			else{
				checkFlag = false;
			}
		}
		// Return informative output about iteration check
		System.out.format("%s iteration check!",(checkFlag)? "Successful" : "Failed");
		System.out.println();
		
		
		/*
		 * SECOND CHECK:
		 * Check if hasNext() method is correctly implemented: returns false in this
		 * case as iteration is done:
		 */
		if(it.hasNext()) checkFlag = true;
		else checkFlag = false;
		System.out.format("%s hasNext-implementation check!",(checkFlag)? "Failed" : "Successful");
		System.out.println();
		
		/*
		 * THIRD CHECK:
		 * Check if next() method is correctly implemented: throws NoSuchElementException
		 * as iteration has no more elements.
		 */
		try {
			it.next();
			System.out.println("Failed next-implementation check!");
			checkFlag = false; //TODO: Question: boolean-flag
			// is there a way to say: if checkFlag is false at any time DO such and such?
			// otherwise, checkflag is useless at positions like these, other than for the programmer and might
			// as well be deleted, or what do you think?
		} catch (NoSuchElementException e) {
			System.out.println("Successful next-implementation check!");
			checkFlag = true;
		}
		
		/*
		 * CHECK 4
		 */
		// reset list and create new iterator
		intList.reset();
		Iterator<Integer> newIt = intList.iterator();
		// test if it throws IllegalStateException - if the next method has not yet been called,
		// or the remove method has already been called after the last call to the next method.
		try {
			newIt.remove();
			System.out.println("First failed next-exception check!");
		} catch (IllegalStateException e) {
			System.out.println("Successful next-exception check: remove() before next().");
		}
		
		/*
		 * CHECK 5
		 */
		// check if first element is the same in iteration and list
		// then call remove() and check again if the element was removed in list and iteration
		if( intList.elem().equals(newIt.next()) ){
			// check current element and store it, then remove with iterator method 
			int i = intList.elem();
			newIt.remove();
			// check if "next" element is equal to current elem in list, but different to prior 'i'
			if(intList.elem().equals(newIt.next()) && i!= intList.elem()){
				System.out.println("Successful remove-method implementation.");
			}
			else{
				System.out.println("Failure! Object removed from iteration, but not from list.");
			}
		}
		else{
			System.out.println("Failure! Matching iterator with list, failed!");
		}
		
		/*
		 * CHECK 6:
		 */
		// Check that two remove-calls in a row will fail, due to remove() implementation convent.
		try {
			newIt.remove();
			newIt.remove();
		} catch (IllegalStateException e) {
			System.out.println("Successful implementation of remove regarding double call.");
		}

		/*
		 * CHECK 7:
		 */
		while(newIt.hasNext()){
			if( intList.elem().equals(newIt.next()) ) {
				checkFlag = true;
				intList.advance();
			}
			else{
				checkFlag = false;
			}
		}
		System.out.format("%s iteration with method usage!",(checkFlag)? "Successful" : "Failed");
		System.out.println();
		// Be polite.
		System.out.println("I hope the test was satisfying to you.");
	}
}

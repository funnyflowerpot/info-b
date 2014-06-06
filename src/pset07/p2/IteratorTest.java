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
		int[] intArray 	= {4,8,15,16,23,42};
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
		System.out.format("%s iteration check!",(checkFlag)? "[ OK ]" : "[ FAIL ]");
		System.out.println();
		
		
		/*
		 * SECOND CHECK:
		 * Check if hasNext() method is correctly implemented: returns false in this
		 * case as iteration is done:
		 */
		if(it.hasNext()) checkFlag = false;
		else checkFlag = true;
		System.out.format("%s hasNext-implementation check!",(checkFlag)? "[ OK ]" : "[ FAIL ]");
		System.out.println();
		
		/*
		 * THIRD CHECK:
		 * Check if next() method is correctly implemented: throws NoSuchElementException
		 * as iteration has no more elements.
		 */
		try {
			it.next();
			System.out.println("[ FAIL ] next-implementation check!");
		} catch (NoSuchElementException e) {
			System.out.println("[ OK ] next-implementation check!");
		}
		
		
		/*
		 * CHECK 4:
		 * test if it throws IllegalStateException - if the next method has not yet been called,
		 * or the remove method has already been called after the last call to the next method.
		 */
		// reset list and create new iterator
		intList.reset();
		Iterator<Integer> newIt = intList.iterator();
		
		try {
			newIt.remove();
			System.out.println("First [ FAIL ] next-exception check!");
		} catch (IllegalStateException e) {
			System.out.println("[ OK ] next-exception check: remove() before next().");
		}
		
		/*
		 * CHECK 5
		 * check if first element is the same in iteration and list
		 * then call remove() and check again if the element was removed in list and iteration
		 */
		if( intList.elem().equals(newIt.next()) ){
			// check current element and store it, then remove with iterator method 
			int i = intList.elem();
			newIt.remove();
			// check if "next" element is equal to current elem in list, but different to prior 'i'
			if(intList.elem().equals(newIt.next()) && i!= intList.elem()){
				System.out.println("[ OK ] remove-method implementation.");
			}
			else{
				System.out.println("Failure! Object removed from iteration, but not from list.");
			}
		}
		else{
			System.out.println("Failure! Matching iterator with list, [ FAIL ]!");
		}
		
		/*
		 * CHECK 6:
		 * Check that two remove-calls in a row will fail, due to remove() implementation convent.
		 */
		try {
			newIt.remove();
			newIt.remove();
		} catch (IllegalStateException e) {
			System.out.println("[ OK ] implementation of remove regarding double call.");
		}

		/*
		 * CHECK 7:
		 * Check if iteration is successful despite all used methods
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
		System.out.format("%s iteration with method usage!",(checkFlag)? "[ OK ]" : "[ FAIL ]");
		System.out.println();
		// Be polite.
		System.out.println("I hope the test satisfying.");
	}
}

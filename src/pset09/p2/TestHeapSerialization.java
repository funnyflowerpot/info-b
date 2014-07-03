package pset09.p2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * This test class will initialise two <code>Integer Heaps</code>.
 * Then it will use the <code>specialSerialize()</code> and <code>specialDeserialize()</code>.
 * After this process it will translate the content into a String (this will empty the heap).
 * In the end the <code>Strings</code> will be compared, testing the implementation.
 * @author Wicke
 * @date 26.06.2014 
 */
public class TestHeapSerialization {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// Initialise two heaps
		Heap<Integer> serializedHeap = new Heap<Integer>();
		Heap<Integer> untouchedHeap  = new Heap<Integer>();
 				
		// Feed both heaps with the same integer values from 0-42
		for (int i = 0; i < 43; i++){
			serializedHeap.insert(i);						
			untouchedHeap.insert(i);
			}

		// Serialize the serializedHeap:
		// Streams: File <- FileOutputStream <- ObjectOutputStream <- Object
		// Write the serialization in the heap.ser file
		try (FileOutputStream fout = new FileOutputStream("heap.ser");
			ObjectOutputStream oout = new ObjectOutputStream(fout);)
		{
			// SERIALIZE:
			oout.writeObject(serializedHeap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Now deserialize the serializedHeap:
		// Streams: File -> FileOutputStream -> ObjectOutputStream -> Object
		// Deserialization of the heap.ser file
		try (FileInputStream fis = new FileInputStream("heap.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);)
			{
			// DESERIALIZE:
			serializedHeap = (Heap<Integer>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Now read both Strings:
		String serializedString = tradeHeapForString(serializedHeap);
		String untouchedString  = tradeHeapForString(untouchedHeap);
		// Compare both Strings, if they match the Serialization was successful.
		System.out.format("Test: %s\n", serializedString.matches(untouchedString) ? "[OK]" : "[FAIL]");
		
		
		System.out.println("String 01:"+serializedString);
		System.out.println("String 02:"+untouchedString);
		
	}
	
	
	/**
	 * This method will return the given heap as <code>String</code>.
	 * It will empty the heap. Improvement: One could implement a
	 * deep copy and copy the heap before reading it out in order to
	 * prevent destruction of heap. But as this method is for testing
	 * use only, it will destroy the heap. 
	 * @param heap <code>Generic Heap</code> that you want to trade for a String
	 * @return <code>String</code> with the elements of the heap
	 * @throws <code>IllegalArgumentException</code> if the provided heap is empty
	 */
	private static <T> String tradeHeapForString(Heap<T> daHeap){
		// check if this heap is not empty
		if(daHeap.empty())
			throw new IllegalArgumentException("Your heap was empty, cannot trade for String.");
		// Initialise a StringBuilder
		StringBuilder sbuilder = new StringBuilder();
	    // iterate through entire heap, THIS EMPTIES THE HEAP!
		while(!daHeap.empty()){ 
			// append the element to a String, and delete it
			sbuilder.append(daHeap.deleteFirst());
			sbuilder.append(" ");
		}
		// return the read heap-String
		return sbuilder.toString();
	}

}

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
			serializedHeap.specialSerialize(oout);
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
			serializedHeap.specialDeserialize(ois);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Now read both Strings:
		String serializedString = serializedHeap.tradeHeapForString();
		String untouchedString  = untouchedHeap.tradeHeapForString();
		// Compare both Strings, if they match the Serialization was successful.
		System.out.format("Test: %s\n", serializedString.matches(untouchedString) ? "[OK]" : "[FAIL]");
		
		/* Visual test: 
		System.out.println("String 01:"+serializedString);
		System.out.println("String 02:"+untouchedString);
		*/
	}

}

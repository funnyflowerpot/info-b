package pset09.p1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Class to calculate the Fibonacci number. Uses a HashMap to reduce the
 * calculation cost.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
@SuppressWarnings("unchecked")
public class Fibonacci implements Serializable{

   /**
	 * Standard Serializenumber
	 */
	private static final long serialVersionUID = 1L;
	
	private static HashMap<Integer, Long> fibonacciHash;
	// if fibonacciHash would be final (see comment below)

   /**
    * Fill HashMap with initial key-value-pairs.
    */
   static {
	   // Initialise a File Object with name of savedHash
	   File savedHash = new File("savedHash.ser");
	   // Check if the savedHash file does exist
	   if(!savedHash.exists()){	
		   // if it doesn't exist, initialise HashMap with 2 values
		   fibonacciHash = new HashMap<>();
		   fibonacciHash.put(0, 0L);
		   fibonacciHash.put(1, 1L);
		   saveHashMap();
		   
	   }
	   else{ // If there already exists a file containing a HashMap
		   
		   try(FileInputStream fis = new FileInputStream("savedHash.ser");
			   ObjectInputStream ois = new ObjectInputStream(fis);) 
		   {
			   // import the given HashMap 
			   // deserialization via ois.readObject() -> must be castet!
			   fibonacciHash = (HashMap<Integer, Long>) ois.readObject();
			
		   } catch (FileNotFoundException e) {
			e.printStackTrace();
		   } catch (IOException e1) {
			e1.printStackTrace();
			// Deserialization look for concrete class and builds the object according to class
		   } catch (ClassNotFoundException e) {
			e.printStackTrace();
			/*
			 * At this point the fibonacciHash as a final field would need to be
			 * initialised, this cannot be guaranteed at this point. A finally block
			 * that would initialise the HashMap no matter what, would complain, that
			 * the HashMap might have been initialised before. Thus, we hava a 
			 * contradiction.
			 */
		   }
	   }
   }
   
   /**
    * Saves the current HashMap to the File
 	*/
   private static void saveHashMap(){
	// Save the initialised HashMap to a file with the correct name
	   // Streams: File<--FileOutputStream<--ObjectOutputStream<--write HashMap in OOS
	   // File output should be of *.ser format
	   try(FileOutputStream fos = new FileOutputStream("savedHash.ser");
		   ObjectOutputStream oos = new ObjectOutputStream(fos);) 
	   { //Save the HashMap in the file
		 // writeObject will be serialised to the ObjectOutputStreamObject
		 // Class must implement Serializable and all references must implement Serializable
		 oos.writeObject(fibonacciHash); 
		
	   } catch (FileNotFoundException fnfe) {
		fnfe.printStackTrace();
	   } catch (IOException ioE) {
		   ioE.printStackTrace();
	   }
   }
   
   /**
    * Calculates the fibonacci value of n.
    * 
    * @param n
    *           a natural number >= 0 to calculate the fibonacci value of
    * 
    * @return fibonacci value of n
    */
   public static long fibonacci(int n) {
      if (n < 0) {
         throw new IllegalArgumentException("n = " + n);
      }
      return getFibonacci(n);
   }

   private static long getFibonacci(int n) {
      if (fibonacciHash.containsKey(n)) {
    	  // Next line: For testing purposes
    	  System.out.println("The value was already in the HashMap!");
         return fibonacciHash.get(n);
      } else {
    	  // Next line: For testing purposes
    	  System.out.println("I computed the value!");
         long nMinus1 = getFibonacci(n - 1);
         long nMinus2 = getFibonacci(n - 2);
         long fibonacci = nMinus1 + nMinus2;

         fibonacciHash.put(n, fibonacci);
         // If the object receives new input it will be written/serialised in file
         saveHashMap();
         
         return fibonacci;
      }
   }

   public static void main(String[] args) {
      if (args.length != 1) {
         printUsage();
      }
      try {
         System.out.println(fibonacci(Integer.parseInt(args[0])));

      } catch (IllegalArgumentException ex) {
         printUsage();
      }
   }

   private static void printUsage() {
      System.out.println("java calc/Fiboncci n");
      System.out.println("n must be a natural number >= 0");
   }

}

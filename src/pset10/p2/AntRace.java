package pset10.p2;

/**
 * This program simulates an AntRace
 * whilst using multiple threads 
 * @author pwicke, sriegl
 *
 */
public class AntRace implements AntFields {

	public static void main(String[] args) throws InterruptedException {
		
		// create an AntField by using one of the AntFields
		AntField field = new AntField(FIELD4);
		
		// Set the first and manually on one of the fields
		// suppose it is one of the free fields
		Ant ant = new Ant(field, 2, 4, 1);
		
		// Now create a new antThread (Ant implements Runnable)
		Thread antThread = new Thread(ant);		
		// start the first AntThread
		antThread.start();
		// this method waits for the first ant(thread) to die
		antThread.join();
		
		// print the field with ant steps on it
		System.out.println(field.toString());
	}
}

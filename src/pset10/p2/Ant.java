package pset10.p2;

import java.util.ArrayList;

/**
 * An {@code Ant} is created at a specific position of an {@link AntField} with
 * an initial {@code stepCount}. When running an Ant, it will lookup the values
 * on the current and all surrounding {@link AntField.Field}
 * (Moore-neighborhood) instances and test if the position is free, i.e. has a
 * value of {@code 0}, or if the value is greater than the {@code stepCount} of
 * this Ant. For both cases, the Ant will set the value of the {@code Field} at
 * the visited position to its own {@code stepCount+1}. After an {@code Ant} has
 * successfully visited one field, it will create new {@code Ant} instances with
 * an incremented {@code stepCount} to visit the other available {@code Field}
 * elements. The Ant will run until it finds no more {@code Field} elements in
 * its neighborhood to be altered.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Ant implements Runnable {
	
	// These instance variable hold the coordinates and stepCount of an ant
	private int xPos, yPos, stepCount;
	// This instance variable holds the field where the ant currently is on
	private AntField field;

	/**
	 * 
	 * @param fields
	 *            the {@code AntField} on which this {@code Ant} operates
	 * @param x
	 *            x-axis value of the starting position
	 * @param y
	 *            y-axis value of the starting position
	 * @param stepCount
	 *            initial stepCount of this {@code Ant}.
	 * 
	 * @throws IllegalArgumentException
	 *             If the {@code Field} at position {@code x,y} does not exist,
	 *             or if its value is < 0
	 */
	public Ant(AntField fields, int x, int y, int stepCount) {
		this.xPos = x;
		this.yPos = y;
		this.stepCount = stepCount;
		this.field = fields;
	}
	
	/**
	 * This method implements the run() method of the Runnable Interface
	 * 
	 * It uses thread safety synchronised blocks on the field as monitor object
	 * and sends multiple ants to the neighbouring fields, recursively sending
	 * further ants on free fields or field with a lower stepCount.
	 * 
	 * All threads that are started (Ants send out) will be stored in an arrayList
	 * Therefore the first thread waits until the last thread, that was sent out is done
	 * only after that, the first thread will stop and will respond to the join() in the
	 * main thread
	 */
	public void run() {
		
		// Creates a reference for the new ants that might be sent out
		Ant newAnt = null;
		// Creates our list of threads (see below)
		ArrayList<Thread> threads = new ArrayList<Thread>();
		
		/*
		 * The checking of an ant must be synchronised
		 * Monitor object is the very own field the ant sits on
		 * This prevents other ants check whenever ONE ant is checking
		 * and it provides a very small scope of the monitor
		 */
		synchronized (this.field.getField(this.xPos,this.yPos)) {
			 /* CHECKING EVIRONMENT:
			 * the entering ant will check if its own field is free and the
			 * stepCount of the field is lower
			 * Otherwise the ant is wrong at its current position
			 */
			if ((this.field.getField(xPos, yPos).getValue() == 0)
					|| (this.field.getField(xPos, yPos).getValue() > stepCount)) {
				// if the field that the ant stands on is valid (see above) it will assign
				// its stepCount to the value the field
				this.field.getField(xPos, yPos).setValue(stepCount);
			}
			// this field is already visited / the ant is not supposed to visit this field
			// (prevent unnecessary creation of threads by aborting thread)
			else
				return;
		} // we can now leave the monitor again		
		
		// now the ant will check its moore environment			
		for (int i = -1; i < 2; i++) {
			// iterate through columns
			for (int j = -1; j < 2; j++) {
				// iterate through all lines, but its own field
				// 	if (!(i== 0 && j== 0)) with DMG:
				if (i != 0 || j != 0) {
					
					// before sending out new ants, this should be in the monitor again:
					synchronized(this.field.getField(this.xPos,this.yPos)){ 
						
						// Define the neighbouring field with iterating values
						AntField.Field neighbourfield = field.getField(
								this.xPos + i, this.yPos + j);
						// check if the field is not blocked already
						if (neighbourfield != null) {

							// set a new ant on the neighbouring field
							newAnt = new Ant(field, this.xPos + i, this.yPos
									+ j, stepCount + 1);
						} // you can now leave the monitor block again
						
						// now we can create the new Ant on the neighbouring field(s)
						Thread antThread = new Thread(newAnt);
						// we must add the latest ant to our array list to keep track of
						// all running ants(thread)
						threads.add(antThread);
						// everything is set, so start the neighbouring ant(s)
						antThread.start();						
						
					} 
				}
			}
		} 
		 // join all created threads to prevent finishing before all threads finished
		for (Thread t : threads) {
		   	try {
			   t.join();
		   	} catch (InterruptedException e) {
			   	e.printStackTrace();
		   	}
	   	}
		 
		
	}
}

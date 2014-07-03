package pset10.p1a;

/**
 * Simple {@code Thread} which continuously reads random values from the given
 * {@code Queue} and sleeps for as long as the currently read value determines.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class Sleeper extends Thread {

	private Queue<Long> values;

	public Sleeper(Queue<Long> values) {
		this.values = values;
	}

	@SuppressWarnings("static-access")
	public void run() {
		try {
			// will let the thread loop infinite 
			while (true) {
				// can be initiated outside of monitor 
				long value;
// ####################### Synchronized block START #####################
				synchronized(values){ // bound to queue as monitor object  			
					// check if queue is empty
					if(values.empty()) // sufficient, because there is only one other thread
									   // otherwise 'while' because further threads need a
									   // looping check of empty()
 						// if so, wait with dequeuing
						values.wait();
															
					// if queue is not empty, dequeue							 
					value = values.deq();
					System.out.println("Now sleeping for " + value + " ms");
					
					// after a value is taken from queue, notifyAll other threads (there only is one)
					values.notifyAll();				//notify the generator that we dequeued	
				}
// ####################### Synchronized block STOP #####################
				// the sleeper should give the generator some time before dequeuing loop
				// starts again. sleep within the synchronized block would prohibit the other thread
				// to do anything, therefore it would make the sleep useless.
				this.sleep(value);                                                        

			}						   			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

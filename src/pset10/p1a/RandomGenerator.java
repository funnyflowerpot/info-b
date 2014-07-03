package pset10.p1a;

/**
 * A simple {@code Thread} which continuously writes uniformly distribute random
 * values from 0 to {@code MAX_VALUE} to the given {@code Queue}. Will sleep
 * {@code SLEEP_TIME} after every insertion.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class RandomGenerator extends Thread {

	private Queue<Long> randoms;

	public static final long MAX_VALUE = 3000;

	public static final long SLEEP_TIME = 1000;

	public RandomGenerator(Queue<Long> randoms) {
		this.randoms = randoms;
	}

	@SuppressWarnings("static-access")
	public void run() {
		try {

			while (true) {
				// can be built outside of synchronized block
				long random;
				synchronized(randoms){
// ####################### Synchronized block START #####################
					// check if queue is already full
					if(randoms.full())    	// sufficient, because there is only one other thread		
						// if so, wait the thread (putting it in internal
						// monitor wait list
						randoms.wait();
										
					random = (long) (Math.random() * (double) MAX_VALUE);
					System.out.println("Now putting " + random);							
					// if queue is not full, enqueue another value			
					randoms.enq(random);					
					// notifyAll other threads, in this case only the other one
					randoms.notifyAll();					
				}
// ####################### Synchronized block STOP #####################
				// the sleeper should give the generator some time before dequeuing loop
				// starts again. sleep within the synchronized block would prohibit the other thread
				// to do anything, therefore it would make the sleep useless.
				this.sleep(SLEEP_TIME);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

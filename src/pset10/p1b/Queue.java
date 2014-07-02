package pset10.p1b;

import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An implementation of a Queue with a limited capacity.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 * @param <E>
 */
public class Queue<E> {

	/**
	 * Holds the ReentrantLock used within this {@code Queue}
	 */
	final Lock lock = new ReentrantLock();

	final Condition condFull = lock.newCondition();
	final Condition condEmpty = lock.newCondition();

	/**
	 * Holds the objects stored by this {@code Queue}.
	 */
	private Object[] objects;
	/**
	 * index of the first instance stored by this {@code Queue}.
	 */
	private int first;
	/**
	 * number of elements contained in this {@code Queue}
	 */
	private int size;

	/**
	 * @param capacity
	 *            number of objects which may be hold in this {@code Queue}.
	 */
	public Queue(int capacity) {
		this.objects = new Object[capacity];
		this.first = 0;
		this.size = 0;
	}

	/**
	 * Inserts {@code o} at the first free position of this {@code Queue}
	 * 
	 * @param o
	 *            object to be inserted
	 */
	public void enq(E o) {

		try{//####################### locked - block START #####################
			lock.lock();
			// if the queue is already full
			if (this.full()){	
				// wait for signal of 'full-condition'
				condFull.await();
				// during wait, another thread is able to run
			}
			// now the state is locked, as the thread is released from await()
			// at this point the queue is not full (this does not imply that it is empty)
			objects[(first + size) % objects.length] = o;
			size++;	
			condEmpty.signal();
		
		// Now all Exception will be caught 		
		} catch (IllegalMonitorStateException imse){
			imse.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		// We must make sure, that the thread will unlock again, therefore -> finally
		} finally{//####################### locked - block END #####################
			lock.unlock();
		}

	}

	/**
	 * Removes the object at the first position of this {@code Queue}.
	 * 
	 * @return the removed object
	 * @throws NoSuchElementException
	 *             if this {@code Queue} is already empty
	 */
	@SuppressWarnings("unchecked")
	public E deq() {
		try{
			lock.lock();
			
			try {
				if(this.empty())
					condEmpty.await();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			E o = (E) objects[first];
			first = (first + 1) % objects.length;
			size--;
			condFull.signal();
			return o;
			
		} catch (IllegalMonitorStateException imse){
			imse.printStackTrace();
		
		} finally{//####################### locked - block END #####################
			lock.unlock();
		}
		return null;	//Will never be reached
		
	}

	/**
	 * Returns the object at the first position of this {@code Queue}
	 * 
	 * @return the first element of this {@code Queue}
	 * @throws NoSuchElementException
	 *             if this {@code Queue} is already empty
	 */
	@SuppressWarnings("unchecked")
	public E front() {
		if (this.empty()) {
			throw new NoSuchElementException();
		}
		return (E) objects[first];
	}

	/**
	 * 
	 * @return {@code true} if this {@code Queue} is empty
	 */
	public boolean empty() {
		return this.size == 0;
	}

	/**
	 * 
	 * @return {@code true} if this {@code Queue} is full
	 */
	public boolean full() {
		return this.size == objects.length;
	}

}

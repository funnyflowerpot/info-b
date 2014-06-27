package pset09.p4;

import java.io.File;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This app will constantly check the size of a given file (and all its including data).
 * It will display any change of size and can only be aborted via 'ctrl+C'
 * @author pwicke, sriegl
 *
 */
public class SizeCheckApp {
	
	private static long currentSize;


	/**
	 * This is where the magic happens.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		// check if input argument is correct
		if(args.length != 1 ) {
			System.err.println("Program must be run with a file or directory path as only argument.");
			System.exit(1);
		}
		
		// initialize a new file according to input
		final File theFile = new File(args[0]);
		Timer timer = new Timer();
		// as we want to print the initial size, we compare it to '-1'
		// this will evoke a "change" -> printing the first size there is
		currentSize = -1;
		
		// make sure that the given file is readable
		if(!theFile.canRead()) {
			System.err.println("Invalid file or directory path specified.");
			System.exit(1);
		}
	
		// From the exercise description:
		// "Nutzen Sie dazu einen java.util.TimerTask der einmal pro Sekunde 
		// überprüft, ob sich die Größe einer File-Instanz verändert hat und 
		// im Falle einer Änderung die Größe auf der Kommandozeile ausgibt."
		// Therefore, we do not create a subclass of TimerTask, but use a
		// anonymous class.
		// To allow a thread-persistent storage of data (i.e. the size that
		// was returned by <code>totalSizeOfFile()</code> the last time), we utilize the
		// static field size of
		TimerTask myTimerTask = new TimerTask() {
			@Override
			public void run() {
				// Get the current size of the file, this will be done every second
				long newSize = SizeCheckApp.totalSizeOfFile(theFile);
				// if size has changed, return appropriate output via System.out
				if(newSize != currentSize) {
					// use fancy ANSI escape codes for fancy wearesocool output
					// http://en.wikipedia.org/wiki/ANSI_escape_code
					// with "\r", jump back to beginning of the line. note the
					// absence of a new line character
					System.out.printf("\rFile: \u001B[1m%s\u001B[0m size: \u001B[1;31m%d\u001B[0m byte", theFile.getAbsolutePath(), newSize);
					System.out.flush();
					// refresh the currentSize to newSize
					currentSize = newSize;
				}
			}
		};
	
		// run task, i.e. begin output
		timer.schedule(myTimerTask, 0, 1000);
		
		// if 'ctrl+C' will end the main thread, the shutdown hook will be activated
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println();
				System.out.println("Program ended: The times they are a-changing.");
			}
		}));
	}
	
	/**
	 * Return the size of a file or directory. In case of a directory, iterate
	 * through all subdirectories and cumulate size of all directories.
	 * 
	 * Since recursion is evil, we ain't gonna do size summation recursively. 
	 * 
	 * @param file file or directory we want to iterate through
	 * @return cumulated size of file or cumulated size of directory specified
	 */
	public static long totalSizeOfFile(File file) {
		
		long size = 0;
		File currentFile;
		Stack<File> todoList = new Stack<>();

		// the file we start our iteration with
		todoList.add(file);
		
		// as long we have files to check...
		while(!todoList.empty()) {
			
			// ... get next file for investigation
			currentFile = todoList.pop();
			
			// if file is directory, add all contained files to todo list
			// if file is not directory, add size to variable
			if(currentFile.isDirectory()) {
				for(File fileInDirectory : currentFile.listFiles()) {
					todoList.push(fileInDirectory);
				}
			} else {
				size += currentFile.length();
			}
		}
		return size;
	}
}

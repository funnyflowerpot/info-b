package pset08.p1;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

/**
 * This class implements a MatchReader which extends the {@link LineNumberReader}
 * According to the Decorator Pattern this reader allows to read a line of the 
 * given stream, it allows to get the line number and to count the matches, found in
 * one line.
 * 
 * All inherited read()-methods are not implemented, since this class should and will 
 * operate line-wise only. This is okay, since no read()-method call happens within
 * this project (pset08.p1) and implementing unused features is a sin of efficiency.
 * 
 * @author sriegl, pwicke
 * @date 20.06.2014
 */
public class MatchReader extends LineNumberReader {
	
	/* Instance variables:
	 * 
	 * The lastLine String is a datafield containing the last line 
	 * that has been read.
	 * The regex String will receive the matching expression via Constructor
	 */
	private String lastLine = null;
	private Pattern regex	= null;
	
	/**
	 * This constructor takes a reader and calls the super constructor
	 * @param arg0 <code>Reader</code> that will be decorated with the MatchReader
	 */
	public MatchReader(Reader arg0) {
		super(arg0);
	}

	/**
	 * This constructor takes a reader and a given regular expression that you want to
	 * match the input stream with.
	 * @param reader <code>Reader</code> that will be decorated with the MatchReader
	 * @param regex <code>String</code> that will be matched with the stream input
	 */
	public MatchReader(Reader reader, String regex) {
		super(reader);
		setRegex(regex);
	}
	
	@Override
	public String readLine() throws IOException{
		String buffer = super.readLine();
		setLastLine(buffer);
		return buffer;
	}
	
	@Override
	public int read() throws IOException {
	  throw new UnsupportedOperationException("MatchReader must be used with readLine() only.");
	}
	
  @Override
  public int read(char[] cbuf) throws IOException {
    throw new UnsupportedOperationException("MatchReader must be used with readLine() only.");
  }
  
  @Override
  public int read(CharBuffer target) throws IOException {
    throw new UnsupportedOperationException("MatchReader must be used with readLine() only.");
  }
  
  @Override
  public int read(char[] arg1, int arg2, int arg3) throws IOException {
    throw new UnsupportedOperationException("MatchReader must be used with readLine() only.");
  }
  
	/**
	 * This method will check the amount of matches of stream and regular expression
	 * It will return the number of matches
	 * @return <code>int</code> countMatch: number of matches
	 */
	public int getAmountofMatches(){
		// set match counter to 0
		int countMatch = 0;
		// initialise a matcher that matches the regex pattern and the last line
		Matcher matcher;
		matcher = this.regex.matcher(getLastLine());
		
		// let the counter increase while the matcher can find another match
		while(matcher.find())
			countMatch++;
		// return the match counter
		return countMatch;
	}
	
	/**
	 * This is the getter method that will return the last line as a <code>String</code>
	 * @return lastLine of type <code>String</code>
	 */
	public String getLastLine() {
		return lastLine;
	}
	
	/**
	 * This is the setter method for the last line. It is only called by the readLine() method
	 * @param lastLine of type <code>String</code>
	 */
	private void setLastLine(String lastLine) {
		this.lastLine = lastLine;
	}
	
	/**
	 * This is the getter method for the regex. It will allow to return the current regex.
	 * @return regex of type <code>Pattern</code>
	 */
	public Pattern getRegex() {
		return regex;
	}
	
	/**
	 * This is the setter method for the regex. It will take a String and transform it into
	 * a Pattern
	 * @param regex of type <code>Pattern</code>
	 */
	public void setRegex(String regex) {
		try{
		this.regex = Pattern.compile(regex);
		} catch(PatternSyntaxException pse){
			throw new IllegalArgumentException("Regular expression is not valid.");
		}
		
	}
}


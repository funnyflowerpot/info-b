package pset08.p1;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatchReader extends LineNumberReader {
	
	private String lastLine = null;
	private String regex	= null;
	
	public MatchReader(Reader arg0) {
		super(arg0);
	}

	public MatchReader(Reader reader, String regex) {
		super(reader);
		setRegex(regex);
	}
	
	
	public String readLine() throws IOException{
		String buffer = super.readLine();
		setLastLine(buffer);
		return buffer;
	}
	

	public int getAmountofMatches(){
		// set match counter to 0
		int countMatch = 0;
		// initialise a pattern with constructor given regex
		Pattern regex = Pattern.compile(this.regex);
		// initialise a matcher that matches regex pattern and the last line
		Matcher matcher = regex.matcher(lastLine);
		// as long as the matcher can find a match in the line, increment the count
		while(matcher.find())
			countMatch++;
		// return the match counter
		return countMatch;
	}

	public String getLastLine() {
		return lastLine;
	}

	public void setLastLine(String lastLine) {
		this.lastLine = lastLine;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
	

}

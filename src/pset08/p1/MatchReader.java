package pset08.p1;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatchReader extends LineNumberReader {
	
	private String lastLine = null;

	public MatchReader(Reader arg0) {
		super(arg0);
	}

	public MatchReader(Reader arg0, int arg1) {
		
		// TODO:   super(reader);
		//  pat= Pattern.compile(p);
		super(arg0, arg1);
	}
	
	
	public String readLine() throws IOException{
		String buffer = super.readLine();
		setLastLine(buffer);
		return buffer;
	}
	

	public int getAmountofMatches(Pattern regex){
		int countMatch = 0;
		
		Matcher matcher = regex.matcher(lastLine);
		
		while(matcher.find())
			countMatch++;
		
		return countMatch;
	}

	public String getLastLine() {
		return lastLine;
	}

	public void setLastLine(String lastLine) {
		this.lastLine = lastLine;
	}
	

}

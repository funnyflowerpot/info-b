package pset08.p2;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * A {@link java.io.FileFilter} which filters the given files by matching their
 * names to a given regular expression.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class PatternFileFilter implements FileFilter {

	/**
	 * the regular expression to match the files with
	 */
	private final Pattern regex;

	/**
	 * Create this {code PatternFileFilter} which will accept all instances of
	 * {@code File} that match the given regular expression {@code regex}.
	 * 
	 * @param regex
	 *            the regular expression to match the files with
	 * 
	 * @throws PatternSyntaxExcpetion
	 *             if {@code regex} is not a valid regular expression.
	 */
	public PatternFileFilter(final String regex) {
		this.regex = Pattern.compile(regex == null ? ".*" : regex);
	}

	/**
	 * If the regular expression with which this {@code PatternFileFilter} has
	 * been created matches the name of the given {@code File f}, true will be
	 * returned.
	 * 
	 * @param f
	 *            the {@code File} which should be matched with the regular
	 *            expression of this {@code PatternFileFilter}
	 * 
	 * @return {code true} if the name of {@code f} matches the regular
	 *         expression in this {@code PatternFileFilter}
	 * 
	 */
	@Override
   public boolean accept(File f) {
		return regex.matcher(f.getName()).matches();
	}

}

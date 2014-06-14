package pset07.p4;

import java.io.File;

public class VisitableFile extends File implements Visitable<File> {

	public VisitableFile(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}



	/**
	 * SerialVersionUID as necessity of file extension
	 */
	private static final long serialVersionUID = -1064693652865606286L;

	

	@Override
	public void accept(Visitor<File> v) {
		// TODO Auto-generated method stub
		
	}

}

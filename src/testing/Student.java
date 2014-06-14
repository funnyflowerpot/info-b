package testing;

public class Student extends Person {
	
	// data
	private int semester 	= 0;
	private boolean isTutor	= false;
	private int	matNum		= 0;

	// constructor
	public Student(String name, boolean alive, double height, int age, int semester, boolean isTutor, int matNum) {
		super(name, alive, height, age);
		setSemester(semester);
		setTutor(isTutor);
		setMatNum(matNum);
	}
	public Student(String name, int matNum) {
		super(name);
		setMatNum(matNum);
	}
	public Student(String name, boolean alive, double height, int age) {
		super(name, alive, height, age);
	}


	// getter and setter
	public int getSemester() {
		return semester;
	}



	public void setSemester(int semester) {
		this.semester = semester;
	}



	public boolean isTutor() {
		return isTutor;
	}



	public void setTutor(boolean isTutor) {
		this.isTutor = isTutor;
	}



	public int getMatNum() {
		return matNum;
	}



	public void setMatNum(int matNum) {
		this.matNum = matNum;
	}	
	
	


}

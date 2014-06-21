package testing;

public class Person {

	// data
	private String name		= "NoName";
	private boolean alive	= true;
	private double height	= 0;
	private int age 		= 0;
	
	// constructor
	public Person(String name, boolean alive, double height, int age){
		setName(name);
		setAlive(alive);
		setHeight(height);
		setAge(age);
	}
	public Person(String name, boolean alive, double height){
		this(name,alive,height,0);
	}
	public Person(String name, boolean alive){
		this(name, alive, 0);
	}
	public Person(String name){
		this(name, true);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		if(height<=-1)throw new RuntimeException("Height can't be "+height+".");
		this.height = height;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		if(age<=-1 || age>=200) throw new RuntimeException("Age, can't be "+age+"!");
		this.age = age;
	}

	
}

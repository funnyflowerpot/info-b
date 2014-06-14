package pset07.p3;

import testing.Person;


/**
 * This test class will check the implementation of the Visitor interface in
 * the VisitList.class file.
 * @author Wicke
 * @date 12.06.2014 
 *
 */
public class VisitorTest {

	public static void main(String[] args) {
		/* * * * * * * * * * * * * * * * * * *  * * *
		 * FIRST CHECK: basic test - pattern search	*
		 * * * * * * * * * * * * * * * * * * * * * **/ 
		
		// Create new VisitableList that takes Persons
		VisitList<Person> cipAVZ = new VisitList<Person>();
	
		// Fill in arbitrary people
		cipAVZ.add(new Person("Paul",true, 1.76, 23));
		cipAVZ.advance();
		cipAVZ.add(new Person("Till",true, 1.71, 19));
		cipAVZ.advance();
		cipAVZ.add(new Person("Kevin",true, 1.80));
		cipAVZ.advance();
		cipAVZ.add(new Person("Corinna",true));
		cipAVZ.advance();
		cipAVZ.add(new Person("Peda"));
		cipAVZ.reset();
		
		// target pattern/name
		final String pattern = "Corinna";
		
		// send Visitor via accept to cipAVZ
		cipAVZ.accept(new Visitor<Person>(){
			
			@Override
			public boolean visit(Person o) {
				// if you find the name, declared in the pattern, stop visit
				if(o.getName().equals(pattern)) {
					return false;
				// if visited Person has a different name than the pattern, visit next
				// Person by returning true
				} else
					return true;
			}
		});
		
		// now check if Person could be found
		if(cipAVZ.elem().getName().equals(pattern)) System.out.println("Visitor basic test: OK.");
		else System.out.println("Visitor basic test: FAIL.");
		// remove "Corinna" out of list
		cipAVZ.delete();
		

		/* * * * * * * * * * * * * * * * *
		 * SECOND CHECK: run-through test*
		 * * * * * * * * * * * * * * * * */
		
		// Check if Visitor will run through entire list
		// create a new visitor subclass that will visit all Persons
		cipAVZ.reset();
		cipAVZ.accept(new Visitor<Person>(){
			
			@Override
			public boolean visit(Person o) {
				// if visited Person is not alive, show statement.
				if(!o.isAlive()) {
					System.out.println(o.getName()+" is dead!");
					return false;
				} else
				// else, visit next Person in list by returning true
					return true;
			}
		});
		// check if end of list could be reached
		if(cipAVZ.endpos()) System.out.println("Visitor run-through test: OK.");
		else System.out.println("Visitor run-through test: FAIL.");

	}
}

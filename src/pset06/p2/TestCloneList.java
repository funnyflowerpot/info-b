package pset06.p2;

/**
 * @author pwicke
 *
 * TODO (pw) DOC, DOC, JAVA-DOC! DOC, DOC, JAVA-DOC!
 */
public class TestCloneList {
	

  // FIXME (pw) a lot of recurring code in main(). might be worth to have an
  // own method for each part of the tests. At least, some kind of stupid
  // loop ("for(int i = 0; i < 2; i++)") might be worth to avoid copy-and-
  // pasting code.
	public static void main(String[] args){
	
		// lets create some integers and a list for integers
		int[] intArray 	= {4,8,15,16,23,42};
		TypeSaveList<Integer> intList = new TypeSaveList<Integer>();
		
		// and some chars and a list for integers
		// TODO (pw) how about {'c','o','r','i','n','n','a','<','3'} ?
		char[] charArray = {'s','r','i','e','g','l'};
		TypeSaveList<Character> charList =  new TypeSaveList<Character>();
		
		// put the integers in a list
		for(int i=0; i<intArray.length; i++){
			intList.add(intArray[i]);
		}
		intList.reset();
		// put the characters in a list
		for(int i=0; i<intArray.length; i++){
			charList.add(charArray[i]);
		}
		charList.reset();
		
		
		
		// First test:  x.clone() != x
		System.out.println("1.) x.clone() != x ");
		
		if(intList.clone() != intList)
			System.out.println("OK.");
		else System.out.println("FAIL.");
		System.out.println("(intList)");
		
		if(charList.clone() != charList)
			System.out.println("OK.");
		else System.out.println("FAIL.");
		System.out.println("(charList)\n");
		
		
		
		// Second test:  x.clone().getClass() == x.getClass()
		System.out.println("2.) x.clone().getClass() == x.getClass()");
		
		if(intList.clone().getClass() == intList.getClass())
			System.out.println("OK.");
		else System.out.println("FAIL.");
		System.out.println("(intList)");
		
		if(charList.clone().getClass() == charList.getClass())
			System.out.println("OK.");
		else System.out.println("FAIL.");
		System.out.println("(charList)\n");
		
		
		
		// Third test: x.clone().equals(x)
		System.out.println("3.) x.clone().equals(x)");
		System.out.println("Shallow copy does not provide reference copy, FAIL expected.");
		
		if(intList.clone().equals(intList))
			System.out.println("OK.");
		else System.out.println("FAIL.");
		System.out.println("(intList)");
		
		if(charList.clone().equals(charList))
			System.out.println("OK.");
		else System.out.println("FAIL.");
		System.out.println("(charList)\n");
		
		
		
		// Fourth test: list.elem().equals( list.copy().elem() )
		System.out.println("4.) Test of each list elem: list.elem().equals( list.copy().elem() )");
		intList.reset();
		charList.reset();
		int i=1;
		int j=1;
		// check intList
		while(!intList.endpos()||!intList.clone().endpos()){
			if(intList.elem().equals(intList.clone().elem())){
				System.out.println("OK ("+i+").");
				intList.advance();
				i++;
			}
			else{ 
				System.out.println("FAIL ("+i+").");
				intList.advance();
				i++;
			}
		}
		System.out.println("(intList) (6 elements)");
		// check charList
		while(!charList.endpos()||!charList.clone().endpos()){
			if(charList.elem().equals(charList.clone().elem())){
				System.out.println("OK ("+j+").");
				charList.advance();
				j++;
			}
			else{ 
				System.out.println("FAIL ("+j+").");
				charList.advance();
				j++;
			}
		}
		System.out.println("(charList) (6 elements)");
	}
}

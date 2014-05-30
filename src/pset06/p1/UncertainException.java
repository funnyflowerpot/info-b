package pset06.p1;

public class UncertainException {

   private int i = 1;

   /*
    * uncertain1:
    result = 1, i = 3
    */
   public int uncertain1() {
	  try {
    	 // This i will be incremented after it is returned. The return is therefore 1
		 // before the return is send, the finally block is activated
         return i++;
      } catch (RuntimeException e) {
    	 // No RuntimeException will be produced, hence no caught
    	 // this incrementation will not occur
         i++;
      } finally {
    	 // This block will always be executed and increments i=2 to i=3
    	 // it won't effect the actual return though
         i++;
      }
      // the try block executes a return statement, therefore this return
      // statement is avoided
      return i++;
   }
/*
 * uncertain2:
    result = 1, i = 2
 */
  
@SuppressWarnings("finally")
public int uncertain2() throws java.io.IOException {
      try {
    	 // this try block will throw an IOException
         throw new java.io.IOException();
      } catch (RuntimeException e) {
    	 // IOException is no RuntimeException
    	 // this catch block is not active
         i++;
      } finally {
    	 // before incrementation the return value is send, here: 1
    	 // the incrementation sets i=1 to i=2
       // TODO (pw) notice insertion of next line
       // a return in a finally block erases any previous exceptions
         return i++;
      }
   }
/*
 * uncertain3:
Ex! result = 0, i = 3
 */
   public int uncertain3() {
      try {
         throw new RuntimeException();
      } catch (RuntimeException e) {
    	 // this time it is a RuntimeException, activating the catch block
         i++;
         // incrementing i=1 to i=2 and throwing a RuntimeException
         throw new RuntimeException();
      } finally {
    	 // incrementing i=2 to i=3 
         i++;
         // no return value is given back but a RuntimeException has been thrown in the catch block
         // therefore the try catch block in the main method will output "Ex!" 
         // and result remains 0.
      }
   }

/*
 * uncertain4:
    result = 2, i = 3
 */

@SuppressWarnings("finally")
public int uncertain4() {
      try {
    	  // this block will throw a ClassCastException
         throw new ClassCastException();
         // this is also a RuntimeException
      } catch (RuntimeException e) {
         // setting i=1 to i=2
    	  return i++;
      } finally {
    	  // This finally block is activated incrementing i=2 to i=3 
    	  // as i=2 was set in catch block AND not in try block, the return value is 2
    	  // return is done before incrementation
        // TODO (pw) spelling suggestion for comments above
        // Here i will be incremented to i=3. A return-value in a finally-
        // block overrides a return-value in a catch-block. Therefore, the
        // "result" will be 2 and i will be equal to 3.
         return i++;
      }
   }
/*
 * uncertain5:
Ex! result = 0, i = 3
 */
   @SuppressWarnings("finally")
public int uncertain5() {
      try {
         throw new RuntimeException();
         // will be caught
      } catch (RuntimeException e) {
         i++; // therefore incrementing i=1 to i=2, effecting the return
         throw new ClassCastException();
         // this exception will lead to the catch in the main, resulting in "Ex!" message
      } finally {
         i++; // incrementing i=2 to i=3, will give i=3
         // TODO (pw) notice insertion
         // no return value (ergo result=0), but exception will arrive in calling method
         throw new NumberFormatException();
      }
   }

   /*
    * uncertain6:
	*Ex! result = 0, i = 2	
    */
   @SuppressWarnings("finally")
public int uncertain6() {
      for (;;)  { // endless loop
        // TODO (pw) :O!
        // Wenn "for(;;)" tatsächlich in offiziellem Code anstelle von
        // "while(true)" vorkommt, dann brauchst Du Dir wegen erweiterter
        // Benutzung von "for" in unserem Code keine Gedanken mehr machen.
        // Wenn der Drache am Montag anfangen will Feuer zu speien, dann
        // zeigen wir ihr diese Ziele.
         try {
        	// ends endless loop
            break;
         } catch (RuntimeException e) {
            // will not be activated
        	i++;
         } finally {
        	// will be activated nevertheless
        	// incrementing i=1 to i=2;
        	// as above, result is returned before incrementing
            i++;
            // but this throw will activate catch block of main, resulting in "Ex!" expression
            // and result = 0.
            throw new RuntimeException();
         }
      }
    //  return i++; UNREACHABLE CODE
   }
/*
 * uncertain7:
    result = 3, i = 4
 */
   public int uncertain7() {
      do {
         try {
            throw new RuntimeException();
         } catch (RuntimeException e) {
        	 // block will be activated, due to RuntimeException caught from try block
        	 i++; // setting i=1 to i=2
            continue; // see while(false)
         } finally {
        	 // this block will be activated, incrementing i=2 to i=3
            i++;
         }
      } while (false); // this do/while will run from top until it hits the
      				   // continue, this will "realize" that while(false) must
      				   // end the loop
      // i will be incremented from i=3 to i=4 and returned before the value return=3
      return i++;
   }
   
   /**
    * This method takes a number to decide which uncertain-method to call
    * It calls the demanded uncertain method in a try-catch block that will
    * print an "Ex!"-expression if the uncertain method throws any Exception.
    * Finally it will print a result: the return value of the method
    * and it will print i: the effect that the method has on the instance variable i
    * @param number is the number of the uncertain method you want to call
    */
   public static void uncertain(int number) {
      UncertainException uncertain = new UncertainException();
      int result = 0;

      try {
         switch (number) {
         case 1:
            result = uncertain.uncertain1();
            break;
         case 2:
            result = uncertain.uncertain2();
            break;
         case 3:
            result = uncertain.uncertain3();
            break;
         case 4:
            result = uncertain.uncertain4();
            break;
         case 5:
            result = uncertain.uncertain5();
            break;
         case 6:
            result = uncertain.uncertain6();
            break;
         case 7:
            result = uncertain.uncertain7();
            break;
         }
         System.out.print("    ");
      } catch (Exception e) {
         System.out.print("Ex! ");
      } finally {
         System.out.println("result = " + result + ", i = " + uncertain.i);
      }
   }
   
   /**
    * This main method loops though all uncertain methods
    * @param args (one knows what it does)
    */
   public static void main(String args[]) {
      for (int i = 1; i <= 7; ++i) {
    	  uncertain(i);
      }
   }
}

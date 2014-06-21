public class UncertainExceptionSolved {

   private int i = 1;

   /*
    * res = 1, i = 3
    */
   public int uncertain1() {
      try {
         /*
          * interrupted by finally
          */
         return i++;
      } catch (RuntimeException e) {
         /*
          * as there is no exception this point will never be reached
          */
         i++;
      } finally {
         /*
          * will be excecuted
          */
         i++;
      }
      /*
       * as there has been a return in the try-block, this point will not be
       * reached
       */
      return i++;
   }

   /*
    * res = 1, i = 2
    */
   public int uncertain2() throws java.io.IOException { // res = 1, i = 2
      try {
         /*
          * throws Exception
          */
         throw new java.io.IOException();
         /*
          * IOExcpetion != RuntimeException
          */
      } catch (RuntimeException e) {
         i++;
      } finally {
         /*
          * will be excecuted. First return, then increment i.
          */
         return i++;
      }
   }

   /*
    * Ex! res = 0; i = 3
    */
   public int uncertain3() {
      try {
         /*
          * throws Exception
          */
         throw new RuntimeException();
         /*
          * is caught immediately
          */
      } catch (RuntimeException e) {
         /*
          * increment
          */
         i++;
         /*
          * then throw a new Exception
          */
         throw new RuntimeException();
      } finally {
         /*
          * will be excecuted
          */
         i++;
      }
      /*
       * unreachable code
       */
   }

   /*
    * res = 2, i = 3
    */
   public int uncertain4() { //
      try {
         /*
          * throw Exception
          */
         throw new ClassCastException();
         /*
          * ClassCastException is also a RuntimeException
          */
      } catch (RuntimeException e) {
         /*
          * increment i
          */
         return i++;

      } finally {
         /*
          * First return, then increment
          */
         return i++;
      }
   }

   /*
    * Ex! res = 0, i = 3
    */
   public int uncertain5() {
      try {
         /*
          * throw Exception
          */
         throw new RuntimeException();
         /*
          * catch immediately
          */
      } catch (RuntimeException e) {
         /*
          * increment
          */
         i++;
         /*
          * throw new Exception
          */
         throw new ClassCastException();
         /*
          * nonetheless excecute finally
          */
      } finally { // trotzdem finally aufrufen
         /*
          * increment
          */
         i++;
         /*
          * interrupt ClassCastException, throw NumberFormatException instead
          */
         throw new NumberFormatException();
      }
   }

   /*
    * Ex! res = 0, i = 2
    */
   public int uncertain6() { //
      for (;;) {
         try {
            /*
             * interrupt for-loop
             */
            break;
         } catch (RuntimeException e) {
            /*
             * will not be reached
             */
            i++;
         } finally {
            /*
             * increment
             */
            i++;
            /*
             * throw a new Exception
             */
            throw new RuntimeException();
         }
      }
      /*
       * unreachable code.
       */
      // return i++;
   }

   /*
    * res = 3, i = 4
    */
   public int uncertain7() {
      do {
         try {
            /*
             * new Exception
             */
            throw new RuntimeException();
            /*
             * caught immediately
             */
         } catch (RuntimeException e) {
            /*
             * increment
             */
            i++;
            /*
             * go to the end of this loop and check statement
             */
            continue;
         } finally {
            /*
             * increment
             */
            i++;
         }
         /*
          * loop terminates
          */
      } while (false);
      /*
       * first return, then increment
       */
      return i++;
   }

   public static void uncertain(int number) {

      UncertainExceptionSolved uncertain = new UncertainExceptionSolved();
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

   public static void main(String args[]) {
      for (int i = 1; i <= 7; ++i) {
         uncertain(i);
      }
   }
}

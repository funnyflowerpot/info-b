public class UncertainException {

   private int i = 1;

   public int uncertain1() {
      try {
         return i++;
      } catch (RuntimeException e) {
         i++;
      } finally {
         i++;
      }
      return i++;
   }

   public int uncertain2() throws java.io.IOException {
      try {
         throw new java.io.IOException();
      } catch (RuntimeException e) {
         i++;
      } finally {
         return i++;
      }
   }

   public int uncertain3() {
      try {
         throw new RuntimeException();
      } catch (RuntimeException e) {
         i++;
         throw new RuntimeException();
      } finally {
         i++;
      }
   }

   public int uncertain4() {
      try {
         throw new ClassCastException();
      } catch (RuntimeException e) {
         return i++;
      } finally {
         return i++;
      }
   }

   public int uncertain5() {
      try {
         throw new RuntimeException();
      } catch (RuntimeException e) {
         i++;
         throw new ClassCastException();
      } finally {
         i++;
         throw new NumberFormatException();
      }
   }

   public int uncertain6() {
      for (;;) {
         try {
            break;
         } catch (RuntimeException e) {
            i++;
         } finally {
            i++;
            throw new RuntimeException();
         }
      }
      return i++;
   }

   public int uncertain7() {
      do {
         try {
            throw new RuntimeException();
         } catch (RuntimeException e) {
            i++;
            continue;
         } finally {
            i++;
         }
      } while (false);
      return i++;
   }

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

   public static void main(String args[]) {
      for (int i = 1; i <= 7; ++i) {
         uncertain(i);
      }
   }
}
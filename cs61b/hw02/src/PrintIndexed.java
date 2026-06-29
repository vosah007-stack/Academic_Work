public class PrintIndexed {
   /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
   public static void printIndexed(String s) {
      String indexWord= "";
      for(int i = s.length() - 1; i >= 0; i--)
      {
         indexWord = indexWord + s.charAt((s.length() - 1) - i) + i;
      }
      IO.println(indexWord);
   }

   public static void main(String[] args) {
      printIndexed("hello");
      printIndexed("cat"); // should print c2a1t0
   }
}
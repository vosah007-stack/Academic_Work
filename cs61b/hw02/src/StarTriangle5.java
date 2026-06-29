public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      String stars = "     ";
      for (int i = 0; i < 5; i++)
      {
         stars = stars.substring(0, 5 - 1 - i) + "*"  + stars.substring(5 - i, 5);
         IO.println(stars);
      }

   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}
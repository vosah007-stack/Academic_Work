public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      String stars = "";
      for (int n = 0; n < N; n++)
      {
         stars = stars + " ";
      }
      for (int i = 0; i < N; i++)
      {
         stars = stars.substring(0, N - 1 - i) + "*"  + stars.substring(N - i, N);
         IO.println(stars);
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}
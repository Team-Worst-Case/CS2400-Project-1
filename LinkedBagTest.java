import java.util.Arrays;

public class LinkedBagTest {
    public static void main(String[]args){

        BagInterface<String>b1 = new LinkedBag<String>(); // create two bags
        BagInterface<String>b2 = new LinkedBag<String>();
        
      b1.add("a"); // add strings
      b1.add("b");
      b1.add("c");
      b2.add("b");
      b2.add("b");
      b2.add("d");
      b1.add("e");

      System.out.println(Arrays.toString(b1.union(b2).toArray()));  // test union, intersection, and difference method.
      System.out.println("Correct ouput: [b, b, d, a, b, c, e]");
      System.out.println(Arrays.toString(b1.intersection(b2).toArray()));
      System.out.println("Correct ouput: [b]");
      System.out.println(Arrays.toString(b1.difference(b2).toArray()));
      System.out.println("Correct ouput: [a, c, e, d]");

    }
}
 import java.util.Arrays;
    
    public class ArrayBagTest{
        
        public static void main(String[]args){
            BagInterface<String>b1 = new ResizeableArrayBag<String>(); // create two bags
            BagInterface<String>b2 = new ResizeableArrayBag<String>();

          b1.add("a"); // add strings
          b1.add("b");
          b1.add("c");
          b2.add("b");
          b2.add("b");
          b2.add("d");
          b1.add("e");

          System.out.println("bag1: " + Arrays.toString(b1.toArray())); // print bag1 and bag2
          System.out.println("bag2: " + Arrays.toString(b2.toArray()));

          System.out.println("bag1 union bag2: " + Array.toString(b1.union(b2).toArray())); // test union, intersection, and difference method.

          System.out.println("bag1 intersection bag2: " + Array.toString(b1.intersection(b2).toArray()));

          System.out.println("bag1 difference bag2: " + Array.toString(b1.difference(b2).toArray()));
        }
    }
}
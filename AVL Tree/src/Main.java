import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        Random rd = new Random();
        for (int i = 0; i < 10; i++) {
            tree.insert(rd.nextInt(100));
        }
        System.out.println("-------------------");
        tree.printTree();
    }
}
public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer> test = new RedBlackTree<>();
        test.insert(5).insert(15).insert(20).insert(19);

        test.traverse();

        test.delete(15);
        System.out.println();
        test.traverse();
    }
}
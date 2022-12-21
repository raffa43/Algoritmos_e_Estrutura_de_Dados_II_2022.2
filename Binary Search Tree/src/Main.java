public class Main {
    public static void main(String[] args) throws Exception {
        Tree<Integer> tree = new Tree<>();

        tree.insert(3).insert(1).insert(5).insert(0).insert(2).insert(4).insert(6);

        tree.traverse();
        tree.delete(0);
        System.out.println();
        tree.traverse();
        tree.delete(1);
        System.out.println();
        tree.traverse();
        tree.delete(5);
        System.out.println();
        tree.traverse();

        System.out.println();
        System.out.println(tree.getMax());
        System.out.println(tree.getMin());
    }
}
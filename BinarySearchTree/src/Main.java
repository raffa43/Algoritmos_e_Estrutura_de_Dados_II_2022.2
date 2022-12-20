public class Main {
    public static void main(String[] args) throws Exception {
        Tree<Integer> tree = new Tree<>();

        tree.addNode(3);
        tree.addNode(1);
        tree.addNode(5);
        tree.addNode(0);
        tree.addNode(2);
        tree.addNode(4);
        tree.addNode(6);

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
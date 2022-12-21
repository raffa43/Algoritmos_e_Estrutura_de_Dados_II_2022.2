public class Tree<T extends Comparable<T>>{
    private Node<T> root;

    public Tree<T> insert(T data){
        root = insert(data, root);
        return this;
    }

    private Node<T> insert(T data, Node<T> node){
        if (node == null) return new Node<>(data);

        if (data.compareTo(node.getData()) < 0) node.setLeft(insert(data, node.getLeft()));
        else if (data.compareTo(node.getData()) > 0) node.setRight(insert(data, node.getRight()));

        return node;
    }

    public void delete(T data){
        root = delete(data, root);
    }

    private Node<T> delete(T data, Node<T> node){
        if (node == null) return null;

        if (data.compareTo(node.getData()) < 0){
            node.setLeft(delete(data, node.getLeft()));
        }else if (data.compareTo(node.getData()) > 0) {
            node.setRight(delete(data, node.getRight()));
        }else {
            if (node.getLeft() == null){
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            node.setData(getMax(node.getLeft()));
            node.setLeft(delete(node.getData(), node.getLeft()));
        }
        return node;
    }

    public void traverse(){
        traverse(root);
    }
    private void traverse(Node<T> node){
        if(node.getLeft() != null) traverse(node.getLeft());
        System.out.println(node.getData());
        if(node.getRight() != null) traverse(node.getRight());
    }

    public T getMin(){
        if (isEmpty()) return null;
        return root.getLeft() != null ? getMax(root.getLeft()) : root.getData();
    }
    private T getMin(Node<T> node){
        return node.getLeft() != null ? getMin(node.getLeft()) : node.getData();
    }

    public T getMax(){
        if (isEmpty()) return null;
        return root.getRight() != null ? getMax(root.getRight()) : root.getData();
    }

    private T getMax(Node<T> node){
        return node.getRight() != null ? getMax(node.getRight()) : node.getData();
    }

    public boolean isEmpty(){
        return root == null;
    }

}

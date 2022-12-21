public class Tree<T extends Comparable<T>> {
    Node<T> root;

    public Tree() {
    }

    public Tree<T> insert(T data){
        root = insert(data, root);
        return this;
    }

    private Node<T> insert(T data, Node<T> node){
        if(node == null) return new Node<>(data);

        if (data.compareTo(node.getData()) < 0) node.setLeft(insert(data, node.getLeft()));
        else if (data.compareTo(node.getData()) > 0) node.setRight(insert(data, node.getRight()));
        else return node;

        updateHeight(node);
        return applyRotation(node);
    }

    public void delete(T data){
        root = delete(data, root);
    }

    private Node<T> delete(T data, Node<T> node){
        if (node == null) return null;

        if (data.compareTo(node.getData()) < 0) node.setLeft(delete(data, node.getLeft()));
        else if (data.compareTo(node.getData()) > 0) node.setRight(delete(data, node.getRight()));
        else {
            if (node.getLeft() == null) return node.getRight();
            else if (node.getRight() == null) return node.getLeft();

            node.setData(getMax(node.getLeft()));
            node.setLeft(delete(node.getData(), node.getLeft()));
        }
        updateHeight(node);
        return applyRotation(node);
    }

    private Node<T> applyRotation(Node<T> node){
        int balance = balance(node);
        if (balance > 1){
            if (balance(node.getLeft()) < 0) node.setLeft(rotateLeft(node.getLeft()));

            return rotateRight(node);
        } else if (balance < -1) {
            if (balance(node.getRight()) > 0) node.setRight(rotateRight(node.getLeft()));

            return rotateLeft(node);
        }
        return node;
    }

    private Node<T> rotateLeft(Node<T> node){
        Node<T> rightNode = node.getRight();
        Node<T> centerNode = rightNode.getLeft();

        rightNode.setLeft(node);
        node.setRight(centerNode);
        updateHeight(node);
        updateHeight(rightNode);
        return rightNode;
    }

    private Node<T> rotateRight(Node<T> node){
        Node<T> leftNode = node.getLeft();
        Node<T> centerNode = leftNode.getRight();

        leftNode.setRight(node);
        node.setLeft(centerNode);
        updateHeight(node);
        updateHeight(leftNode);
        return leftNode;
    }



    private int balance(Node<T> node){
        return node != null ? height(node.getLeft()) - height(node.getRight()) : 0;
    }

    private void updateHeight(Node<T> node){
        int maxHeight = Math.max(height(node.getLeft()), height(node.getRight()));
        node.setHeight(maxHeight + 1);
    }

    private int height(Node<T> node){
        return node != null ? node.getHeight() : 0;
    }


    public void traverse(){
        if(root.getLeft() != null) traverse(root.getLeft());
        System.out.println(root.getData());
        if(root.getRight() != null) traverse(root.getRight());
    }

    private void traverse(Node<T> node){
        if(node.getLeft() != null) traverse(node.getLeft());
        System.out.println(node.getData());
        if(node.getRight() != null) traverse(node.getRight());
    }

    public T getMax(){
        T max = root.getData();

        T leftMax = root.getLeft() != null ? getMax(root) : null;
        T rightMax = root.getRight() != null ? getMax(root) : null;
        if (leftMax != null) max = max.compareTo(leftMax) < 0 ? leftMax : max;
        if (rightMax != null) max = max.compareTo(rightMax) < 0 ? rightMax : max;

        return max;
    }

    private T getMax(Node<T> node){
        T max = node.getData();

        T leftMax = node.getLeft() != null ? getMax(node.getLeft()) : null;
        T rightMax = node.getRight() != null ? getMax(node.getRight()) : null;
        if (leftMax != null) max = max.compareTo(leftMax) < 0 ? leftMax : max;
        if (rightMax != null) max = max.compareTo(rightMax) < 0 ? rightMax : max;

        return max;
    }

    public T getMin(){
        T min = root.getData();

        T leftMin = root.getLeft() != null ? getMin(root.getLeft()) : null;
        T rightMin = root.getRight() != null ? getMin(root.getRight()) : null;

        if(leftMin != null) min = min.compareTo(leftMin) > 0 ? leftMin : min;
        if(rightMin != null) min = min.compareTo(rightMin) > 0 ? rightMin : min;

        return min;
    }

    private T getMin(Node<T> node){
        T min = node.getData();

        T leftMin = node.getLeft() != null ? getMin(node.getLeft()) : null;
        T rightMin = node.getRight() != null ? getMin(node.getRight()) : null;

        if(leftMin != null) min = min.compareTo(leftMin) > 0 ? leftMin : min;
        if(rightMin != null) min = min.compareTo(rightMin) > 0 ? rightMin : min;

        return min;
    }

}

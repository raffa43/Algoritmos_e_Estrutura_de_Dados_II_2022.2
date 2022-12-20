public class Node <T extends Comparable<T>>{
    T data;
    Node<T> left;
    Node<T> right;
    boolean isLeft = true;
    public Node(T data) {
        this.data = data;
    }

    public Node(T data, boolean isLeft) {
        this.data = data;
        this.isLeft = isLeft;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.isLeft = left;
    }

    public boolean isLeft() {
        return isLeft;
    }
}

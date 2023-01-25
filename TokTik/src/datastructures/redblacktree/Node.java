package datastructures.redblacktree;
/*
###############################################################
    Implementado seguindo os tutoriais disponíveis na em:
    <https://www.youtube.com/watch?v=bqOSo1f1jbo/>
    <https://www.youtube.com/watch?v=lU99loSvD8s/>
###############################################################
 */
public class Node<T extends Comparable<T>> {
    private T data;

    private boolean isRed = true;

    private Node<T> left;

    private Node<T> right;

    private Node<T> parent;

    public boolean isLeftChild(){
        return this == getParent().getLeft();
    }

    public void flipColor(){
        this.isRed = !this.isRed;
    }

    public Node(T data) {
        this.data = data;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
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

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    @Override
    public String toString(){
        return getData().toString();
    }
}

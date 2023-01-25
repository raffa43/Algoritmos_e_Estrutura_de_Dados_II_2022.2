package datastructures.btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Node<T extends Comparable<T>> {
    int t;

    ArrayList<T> keys;

    ArrayList<Node<T>> children;

    boolean isLeaf = false;

    public Node(boolean isLeaf) throws Exception {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.isLeaf = isLeaf;
    }

    public int getT() {
        return t;
    }

    public ArrayList<T> getKeys() {
        return keys;
    }

    public ArrayList<Node<T>> getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public String toString(){
        return Arrays.toString(this.children.toArray());
    }
}

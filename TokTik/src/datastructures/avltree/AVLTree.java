package datastructures.avltree;

import java.util.ArrayList;
import java.util.Arrays;

public class AVLTree<T extends Comparable<T>> {
    Node<T> root;

    public AVLTree() {
    }

    public AVLTree<T> insert(T data) {
        root = insert(data, root);
        return this;
    }

    private Node<T> insert(T data, Node<T> node) {
        if (node == null) {
            return new Node<>(data);
        }
        if (data.compareTo(node.getData()) < 0 || data.compareTo(node.getData()) == 0) {
            node.setLeft(insert(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(insert(data, node.getRight()));
        } else {
            return node;
        }
        updateHeight(node);
        return applyRotation(node);
    }


    public void delete(T data) {
        root = delete(data, root);
    }

    private Node<T> delete(T data, Node<T> node) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(delete(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(delete(data, node.getRight()));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            // Two ren
            node.setData(getMax(node.getLeft()));
            node.setLeft(delete(node.getData(), node.getLeft()));
        }
        updateHeight(node);
        return applyRotation(node);
    }

    private Node<T> applyRotation(Node<T> node) {
        int balance = balance(node);
        if (balance > 1) {
            if (balance(node.getLeft()) < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            if (balance(node.getRight()) > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }
        return node;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeft();
        Node<T> centerNode = leftNode.getRight();
        leftNode.setRight(node);
        node.setLeft(centerNode);
        updateHeight(node);
        updateHeight(leftNode);
        return leftNode;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRight();
        Node<T> centerNode = rightNode.getLeft();
        rightNode.setLeft(node);
        node.setRight(centerNode);
        updateHeight(node);
        updateHeight(rightNode);
        return rightNode;
    }

    private void updateHeight(Node<T> node) {
        int maxHeight = Math.max(
                height(node.getLeft()),
                height(node.getRight())
        );
        node.setHeight(maxHeight + 1);
    }

    private int balance(Node<T> node) {
        return node != null ? height(node.getLeft()) - height(node.getRight()) : 0;
    }

    private int height(Node<T> node) {
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
    public ArrayList<ArrayList<Node<T>>> getLevels(){
        if (root == null) return null;

        ArrayList<ArrayList<Node<T>>> levels = new ArrayList<>();
        ArrayList<Node<T>> level = new ArrayList<>();
        level.add(root);
        levels.add(level);
        level = new ArrayList<>();

        for(int i = 0; i < levels.size(); i++){
            ArrayList<Node<T>> currentLevel = levels.get(i);
            boolean empty = true;
            for (Node<T> node : currentLevel) {
                if (node == null){
                    level.add(null);
                    level.add(null);
                    continue;
                }
                empty = false;
                level.add(node.getLeft());
                level.add(node.getRight());
            }
            if(empty) break;
            levels.add(level);
            level = new ArrayList<>();
        }
        levels.remove(levels.size()-1);
        return levels;
    }

    public void printTree(){
        ArrayList<ArrayList<Node<T>>> levels = getLevels();
        int spaces = levels.get(levels.size()-1).size()-1;
        int spacesBetween = 0;
        for (int i = 0; i < levels.size(); i++) {
            ArrayList<Node<T>> currentLevel = levels.get(i);
            if (i < levels.size()-1) {
                for (int j = 0; j < spaces; j++) {
                    System.out.print(" ");
                }
            }
            for (Node<T> node: currentLevel) {
                if(node == null) System.out.print("# ");
                else System.out.print(node);
                for (int j = 0; j < spacesBetween; j++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            if (i == 0) spacesBetween = spaces;
            else spacesBetween = (int)spacesBetween/2;
            spaces = spacesBetween/2;
        }
    }


}

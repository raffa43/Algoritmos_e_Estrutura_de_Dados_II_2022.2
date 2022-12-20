public class Tree<T extends Comparable<T>> {
    private Node<T> root;

    public Tree() {
    }

    public void addNode(T data){
        Node<T> tmp = new Node<>(data);

        if (root == null){
            root = tmp;
            return;
        }

        Node<T> index = root;
        while (index != null){
            if(index.getData().compareTo(tmp.getData()) < 0){
                if(index.getRight() == null){
                    tmp.setLeft(false);
                    index.setRight(tmp);
                    return;
                }else index = index.getRight();

            }else{
                if (index.getLeft() == null){
                    index.setLeft(tmp);
                    return;
                }else index = index.getLeft();
            }
        }
    }

    public void delete(T element) throws Exception{
        if (root == null){
            throw new Exception("Empty Tree");
        } else if ((root.getLeft() == null && root.getRight() == null) && (root.getData().compareTo(element) == 0)){
            root = null;
            return;
        }

        Node<T> index = root;
        Node<T> parentIndex = root;

        while (index != null){
            if(index.getData().compareTo(element) == 0){
                if(index.getLeft() == null && index.getRight() == null){
                    noChildDelete(index, parentIndex);
                } else if ((index.getLeft() != null && index.getRight() == null) ||
                            (index.getRight() != null && index.getLeft() == null)){
                    oneChildDelete(index, parentIndex);
                }else {
                    twoChildDelete(index, parentIndex);
                }
                return;
            } else if (index.getData().compareTo(element) < 0) {
                parentIndex = index;
                index = index.getRight();
            }else {
                parentIndex = index;
                index = index.getLeft();
            }
        }

        throw new Exception("Element not found");
    }

    private void noChildDelete(Node<T> node, Node<T> parentNode){
        if (node.isLeft) parentNode.setLeft(null);
        else parentNode.setRight(null);
    }

    private void oneChildDelete(Node<T> node, Node<T> parentNode){
        if (node.getLeft() != null){
            if(node.isLeft) parentNode.setLeft(node.getLeft());
            else parentNode.setRight(node.getLeft());
        }else {
            if (node.isLeft) parentNode.setLeft(node.getRight());
            else parentNode.setRight(node.getRight());
        }
    }

    private void twoChildDelete(Node<T> node, Node<T> parentNode){
        Node<T> replacement = node.getRight();
        Node<T> replacementParent = node;
        boolean hasDepth = false;

        while (replacement.getLeft() != null){
            replacementParent = replacement;
            replacement = replacement.getLeft();
            hasDepth = true;
        }

        if(hasDepth) replacementParent.setLeft(null);
        else replacementParent.setRight(null);

        if (node.getRight() != null) replacement.setRight(node.getRight());
        if (!hasDepth && node.getLeft() != null) replacement.setLeft(node.getLeft());

        if(node.isLeft) parentNode.setLeft(replacement);
        else parentNode.setRight(replacement);

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

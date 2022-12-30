public class RedBlackTree<T extends Comparable<T>> {
    Node<T> root;

    public RedBlackTree<T> insert(T data){
        Node<T> node = new Node<>(data);
        root = insert(root, node);
        recolorAndRotate(node);
        return this;
    }

    private Node<T> insert(Node<T> node, Node<T> nodeToInsert){
        if (node == null){
            return nodeToInsert;
        }
        if (nodeToInsert.getData().compareTo(node.getData()) < 0){
            node.setLeft(insert(node.getLeft(), nodeToInsert));
            node.getLeft().setParent(node);
        } else if (nodeToInsert.getData().compareTo(node.getData()) > 0) {
            node.setRight(insert(node.getRight(), nodeToInsert));
            node.getRight().setParent(node);
        }
        return node;
    }

    private void recolorAndRotate(Node<T> node){
        Node<T> parent = node.getParent();
        if (node != this.root && parent.isRed()){
            Node<T> grandParent = parent.getParent();
            Node<T> uncle = parent.isLeftChild() ? grandParent.getRight() : grandParent.getLeft();

            if (uncle != null && uncle.isRed()){
                handleRecoloring(parent, uncle, grandParent);
            } else if (parent.isLeftChild()) {
                handleLeftSituation(node, parent, grandParent);
            } else if (!parent.isLeftChild()) {
                handleRightSituation(node, parent, grandParent);
            }
        }
        root.setRed(false);
    }

    private void handleRecoloring(Node<T> parent, Node<T> uncle, Node<T> grandParent){
        uncle.flipColor();
        parent.flipColor();
        grandParent.flipColor();
        recolorAndRotate(grandParent);
    }

    private void handleLeftSituation(Node<T> node, Node<T> parent, Node<T> grandParent){
        if (!node.isLeftChild()) rotateLeft(parent);

        parent.flipColor();
        grandParent.flipColor();
        rotateRight(grandParent);
        recolorAndRotate(node.isLeftChild() ? parent : grandParent);
    }
    private void handleRightSituation(Node<T> node, Node<T> parent, Node<T> grandParent){
        if (node.isLeftChild()) rotateRight(parent);
        parent.flipColor();
        grandParent.flipColor();
        rotateLeft(grandParent);
        recolorAndRotate(node.isLeftChild() ? grandParent : parent);
    }

    private void rotateRight(Node<T> node){
        Node<T> leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        if (node.getLeft() != null) node.getLeft().setParent(node);
        leftNode.setRight(node);
        leftNode.setParent(node.getParent());
        updateNodeParent(node, leftNode);
        node.setParent(leftNode);
    }

    private void rotateLeft(Node<T> node){
        Node<T> rightNode = node.getRight();
        node.setRight(rightNode.getLeft());
        if (node.getRight() != null) node.getRight().setParent(node);
        rightNode.setLeft(node);
        rightNode.setParent(node.getParent());
        updateNodeParent(node, rightNode);
        node.setParent(rightNode);
    }

    private void updateNodeParent(Node<T> node, Node<T> tempNode){
        if (node.getParent() == null) root = tempNode;
        else if (node.isLeftChild()) node.getParent().setLeft(tempNode);
        else node.getParent().setRight(tempNode);

    }
    public void delete(T data){
        Node<T> nodeToDelete = this.getNode(data);
        Node<T> newNode = null;
        boolean successorIsRed = true;
        if (nodeToDelete.getLeft() == null && nodeToDelete.getRight() == null){
            if (nodeToDelete.isLeftChild()) nodeToDelete.getParent().setLeft(null);
            else nodeToDelete.getParent().setRight(null);
        } else if (nodeToDelete.getLeft() == null){
            newNode = nodeToDelete.getRight();
            transplant(nodeToDelete, nodeToDelete.getRight());
        } else if (nodeToDelete.getRight() == null) {
            newNode = nodeToDelete.getLeft();
            transplant(nodeToDelete, nodeToDelete.getLeft());
        }else {
            Node<T> successor = getNode(getMin(nodeToDelete.getRight()));
            successorIsRed = successor.isRed();
            newNode = successor.getRight();
            if(successor.getParent() == nodeToDelete){
                newNode.setLeft(successor);
            }else {
                transplant(successor, successor.getRight());
                successor.setRight(nodeToDelete.getRight());
                successor.getRight().setParent(successor);
            }
            transplant(nodeToDelete, successor);
            successor.setLeft(nodeToDelete.getLeft());
            successor.getLeft().setParent(successor);
            successor.setRed(nodeToDelete.isRed());
        }
        if (!successorIsRed){
            deleteFixUp(newNode);
        }
    }
    private void transplant(Node<T> node, Node<T> newNode){
        if(node.getParent() == null)root = newNode;
        else if (node.isLeftChild()) node.getParent().setLeft(newNode);
        else node.getParent().setRight(newNode);

        if (newNode != null)newNode.setParent(node.getParent());
    }

    private void deleteFixUp(Node<T> newNode){
        while (newNode != root && !newNode.isRed()){
            if (newNode.isLeftChild()){
                Node<T> sibling = newNode.getParent().getRight();
                if (sibling.isRed()){
                    sibling.setRed(false);
                    newNode.getParent().setRed(true);
                    rotateLeft(newNode.getParent());

                    sibling = newNode.getParent().getRight();
                }
                if (!sibling.getLeft().isRed() && !sibling.getRight().isRed()){
                    sibling.setRed(true);
                    newNode = newNode.getParent();
                }else {
                    if (!sibling.getRight().isRed()){
                        sibling.getLeft().setRed(false);
                        sibling.setRed(true);
                        rotateRight(sibling);
                        sibling = newNode.getParent().getRight();
                    }
                    sibling.setRed(newNode.getParent().isRed());
                    newNode.getParent().setRed(false);
                    sibling.getRight().setRed(false);
                    rotateLeft(newNode.getParent());
                    newNode = root;
                }
            }
        }
        newNode.setRed(false);
    }

    public Node<T> getNode(T data) {
        return root.getData().compareTo(data) == 0 ? root  :
                (data.compareTo(root.getData()) < 0 ? getNode(data, root.getLeft()) : getNode(data, root.getRight()));
    }

    private Node<T> getNode(T data, Node<T> node) {
        return node.getData().compareTo(data) == 0 ? node  :
                (data.compareTo(node.getData()) < 0 ? getNode(data, node.getLeft()) : getNode(data, node.getRight()));
    }

    public void traverse(){
        if(root.getLeft() != null) traverse(root.getLeft());
        System.out.println(root.toString());
        if(root.getRight() != null) traverse(root.getRight());
    }

    private void traverse(Node<T> node){
        if(node.getLeft() != null) traverse(node.getLeft());
        System.out.println(node.toString());
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

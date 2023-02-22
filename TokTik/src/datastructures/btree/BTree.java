package datastructures.btree;

import java.util.ArrayList;

/*###############################################################################
## Desenvovido com base na playlist "B-Trees // Michael Sambol" dísponivel em: ##
## <https://youtube.com/playlist?list=PL9xmBV_5YoZNFPPv98DjTdD9X6UI9KMHz/>     ##
###############################################################################*/
public class BTree<T extends Comparable<T>> {
    Node<T> root;

    int t;

    public BTree(int t) throws Exception {
        if (t < 2){
            throw new Exception("Invalid degree");
        }
        this.t = t;
        root = new Node<>(true);
    }



    public T get(T key){
        Node<T> node = root;

        int i = 0;
        while (i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) > 0) i++;
        if (i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) == 0) return node.keys.get(i);
        else if (node.isLeaf()) return null;
        else return get(key, node.getChildren().get(i));
    }

    private T get(T key, Node<T> node){

        int i = 0;
        while (i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) > 0) i++;
        if (i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) == 0) return node.keys.get(i);
        else if (node.isLeaf()) return null;
        else return get(key, node.getChildren().get(i));
    }

    public void delete(Node<T> x, T k){

        int i = 0;
        while (i < x.getKeys().size() && k.compareTo(x.getKeys().get(i)) < 0) i++;
        if (x.isLeaf()){
            if (i < x.getKeys().size() && k.compareTo(x.getKeys().get(i)) == 0) x.getKeys().remove(i);
            return;
        }

        if (i < x.getKeys().size() && k.compareTo(x.getKeys().get(i)) == 0){
            deleteInternalNode(x, k, i);
        } else if (x.getChildren().get(i).getKeys().size() >= t) {
            delete(x.getChildren().get(i), k);
        }else {
            if (i != 0 && (i + 2) < x.getChildren().size()){
                if (x.getChildren().get(i-1).getKeys().size() >= t)deleteSibling(x, i, i-1);
                else if (x.getChildren().get(i+1).getKeys().size() >= t) deleteSibling(x, i, i+1);
                else deleteMerge(x, i, i+1);
            } else if (i == 0) {
                if (x.getChildren().get(i+1).getKeys().size() >= t) deleteSibling(x, i, i-1);
                else deleteMerge(x, i, i+1);
            } else if (i+1 == x.getChildren().size()) {
                if (x.getChildren().get(i-1).getKeys().size() >= t) deleteSibling(x, i, i-1);
                else deleteMerge(x, i, i-1);
            }
            delete(x.getChildren().get(i), k);
        }
    }

    public void deleteInternalNode(Node<T> x, T k, int i){
        if (x.isLeaf()){
            if (k.compareTo(x.getKeys().get(i)) == 0) x.getKeys().remove(i);
            return;
        }
        if (x.getChildren().get(i).getKeys().size() >= t){
            x.getKeys().set(i, deletePredecessor(x.getChildren().get(i)));
            return;
        } else if (x.getChildren().get(i+1).getKeys().size() >= t) {
            x.getKeys().set(i, deleteSuccessor(x.getChildren().get(i+1)));
            return;
        }else {
            deleteMerge(x, i, i+1);
            deleteInternalNode(x.getChildren().get(i), k, this.t - 1);
        }
    }

    public T deletePredecessor(Node<T> x){
        if (x.isLeaf()){
            return x.getKeys().remove(x.getKeys().size()-1);
        }

        int n = x.getKeys().size() - 1;

        if(x.getChildren().get(n).getKeys().size() >= this.t){
            deleteSibling(x, n + 1, n);
        }else {
            deleteMerge(x, n, n);
        }
        deletePredecessor(x.getChildren().get(n));
        return null;
    }

    private T deleteSuccessor(Node<T> x){
        if (x.isLeaf()){
            return x.getKeys().remove(0);
        }
        if (x.getChildren().get(1).getKeys().size() >= this.t){
            deleteSibling(x, 0, 1);
        }else {
            deleteMerge(x, 0, 1);
        }
        deleteSuccessor(x.getChildren().get(0));
        return null;
    }

    private void deleteMerge(Node<T> x, int i, int j){
        Node<T> cnode = x.getChildren().get(i);
        Node<T> newNode;
        if (j > i){
            Node<T> rsnode = x.getChildren().get(j);
            cnode.getKeys().add(x.getKeys().get(i));
            for (int k = 0; k < rsnode.getKeys().size(); k++) {
                cnode.getKeys().add(rsnode.getKeys().get(k));
                if (rsnode.getChildren().size() > 0) cnode.getChildren().add(rsnode.getChildren().get(k));
            }
            if (rsnode.getChildren().size() > 0) cnode.getChildren().add(rsnode.getChildren().remove(rsnode.getChildren().size()-1));
            newNode = cnode;
            x.getKeys().remove(i);
            x.getChildren().remove(j);
        }else {
            Node<T> lsnode = x.getChildren().get(j);
            lsnode.getKeys().add(x.getKeys().get(j));
            for (i = 0; i < cnode.getKeys().size(); i++) {
                lsnode.getKeys().add(cnode.getKeys().get(i));
                if (lsnode.getChildren().size() > 0) lsnode.getChildren().add(cnode.getChildren().get(i));
            }
            if (lsnode.getChildren().size() > 0) lsnode.getChildren().add(cnode.getChildren().remove(cnode.getChildren().size()-1));
            newNode = lsnode;
            x.getKeys().remove(j);
            x.getChildren().remove(i);
        }

        if (x.equals(this.root) && x.getKeys().size() == 0) this.root = newNode;
    }

    private void deleteSibling(Node<T> x, int i, int j){
        Node<T> cnode = x.getChildren().get(i);
        if (i < j){
            Node<T> rsnode = x.getChildren().get(j);
            cnode.getKeys().add(x.getKeys().get(i));
            x.getKeys().set(i, rsnode.getKeys().get(0));
            if (rsnode.getChildren().size() > 0){
                cnode.getChildren().add(rsnode.getChildren().get(0));
                rsnode.getChildren().remove(0);
            }
            rsnode.getKeys().remove(0);
        }else {
            Node<T> lsnode = x.getChildren().get(j);
            cnode.getChildren().add(0, lsnode.getChildren().remove(lsnode.getChildren().size()-1));
        }
    }

    public void traverse(){
        if(root == null) return;
        for (int i = 0; i < root.getChildren().size(); i++) {
            traverse(root.getChildren().get(i));
        }
    }

    private void traverse(Node<T> node) {
        if (node == null) return;
        for(int i = 0; i < node.getChildren().size(); i++) {
            traverse(node.getChildren().get(i));
        }
    }

    public ArrayList search(T key){
        Node<T> node = root;
        ArrayList found = new ArrayList();

        int i = 0;
        while (i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) > 0) i++;
        if (i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) == 0){
            found.add(node.keys);
            found.add(i);
            return found;
        }
        else if (node.isLeaf()) return null;
        else return search(key, node.getChildren().get(i));
    }

    private ArrayList search(T key, Node<T> node){
        ArrayList found = new ArrayList();
        int i = 0;
        while (i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) > 0) i++;
        if (i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) == 0){
            found.add(node.keys);
            found.add(i);
            return found;
        }
        else if (node.isLeaf()) return null;
        else return search(key, node.getChildren().get(i));
    }

    public void insert(T data) throws Exception {
        Node<T> root = this.root;
        if (root.getKeys().size() == (2 * this.t)-1){
            this.root = new Node<>(false);
            this.root.children.add(0, root);
            splitChild(this.root, 0);
            insertNonFull(this.root, data);
        }else {
            insertNonFull(root, data);
        }
    }

    private void insertNonFull(Node<T> x, T k) throws Exception {
        int i = x.getKeys().size()-1;

        if (x.isLeaf()){
            x.getKeys().add(null);
            while (i >= 0 && k.compareTo(x.getKeys().get(i)) < 0){
                x.getKeys().set(i + 1, x.getKeys().get(i));
                i--;
            }
            x.getKeys().set(i + 1, k);
        }else {
            while (i >= 0 && k.compareTo(x.getKeys().get(i)) < 0) i--;
            i++;
            if (x.getChildren().get(i).getKeys().size() == (2 * this.t) - 1){
                splitChild(x, i);
                if (k.compareTo(x.getKeys().get(i)) > 0) i++;
            }
            insertNonFull(x.getChildren().get(i), k);
        }
    }

    private void splitChild(Node<T> node, int i) throws Exception {
        Node<T> child = node.getChildren().get(i);

        Node<T> newNode = new Node<T>(child.isLeaf());
        node.getChildren().add(newNode);

        node.getKeys().add(i, child.getKeys().get(this.t-1));

        for (int j = this.t; j < (2*t)-1; j++) {
            newNode.getKeys().add(child.getKeys().get(j));
        }

        ArrayList<T> aux1 = new ArrayList<>();
        for (int j = 0; j < this.t-1; j++) {
            aux1.add(child.getKeys().get(j));
        }
        child.getKeys().clear();
        child.getKeys().addAll(aux1);

        if(!child.isLeaf){
            for (int j = this.t; j < (2*t)-1; j++) {
                newNode.getChildren().add(child.getChildren().get(j));
            }

            ArrayList<Node<T>> aux2 = new ArrayList<Node<T>>();
            for (int j = 0; j < this.t-1; j++) {
                aux2.add(child.getChildren().get(j));
            }
            child.getChildren().clear();
            child.getChildren().addAll(aux2);
        }
    }
}

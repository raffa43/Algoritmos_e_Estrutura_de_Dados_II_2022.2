package datastructures.invertedindex;

import datastructures.btree.BTree;
import app.Post;
import datastructures.btree.BTree;

import java.util.LinkedList;

public class InvertedNode<T> {
    LinkedList<Post> posts;
    String key;
    InvertedNode<T> nextWord;

    public InvertedNode(String word) throws Exception {
        this.key = word;
        posts = new LinkedList<>();
    }

    public InvertedNode(String word, Post post) throws Exception {
        this.key = word;
        posts = new LinkedList<>();
        posts.add(post);
    }

    public void addPost(Post post) throws Exception {
        posts.add(post);
    }

    public void removeString(Post post){
        posts.remove(post);
    }
    public LinkedList<Post> getPosts() {
        return posts;
    }

    public void setPosts(LinkedList<Post> words) {this.posts = words;}

    public String getKey() {
        return key;
    }

    public void setKey(String index) {
        this.key = index;
    }

    public InvertedNode<T> getNextWord() {
        return nextWord;
    }

    public void setNextWord(InvertedNode<T> nextWord) {
        this.nextWord = nextWord;
    }
}

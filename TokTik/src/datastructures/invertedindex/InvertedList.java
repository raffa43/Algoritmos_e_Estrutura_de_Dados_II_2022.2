package datastructures.invertedindex;

import app.Post;

import java.util.ArrayList;
import java.util.LinkedList;

public class InvertedList<T> {
    InvertedNode<T> root;
    public InvertedList() throws Exception {
        root = new InvertedNode<>(" ");
    }

    public void insert(Post post) throws Exception {
        String postContent = post.getContent().toLowerCase();
        String[] strs = postContent.split(" ");

        for(int i = 0; i < strs.length; i++) {
            if (!isWord(strs[i])) continue;
            InvertedNode<T> index = root;
            boolean indexed = false;
            while(index.getNextWord() != null){
                if(strs[i].equals(index.getKey())){
                    index.addPost(post);
                    indexed = true;
                    break;
                }else{
                    index = index.getNextWord();
                }
            }
            if(!indexed){
                index.setNextWord(new InvertedNode<>(strs[i], post));
            }
        }
    }

    public LinkedList<Post> getPosts(String str){
        str = str.toLowerCase();

        InvertedNode<T> index = root;
        while (index.getNextWord() != null){
            if(isWord(str) && index.getKey().equals(str)) return index.getPosts();
            index = index.getNextWord();
        }
        return null;
    }

    public void listKeys(){
        InvertedNode<T> index = root;
        while (index.getNextWord() != null){
            index = index.getNextWord();
            System.out.println(index.getKey());
        }
    }

    public void removeKey(String key){
        InvertedNode<T> index = root;
        InvertedNode<T> parent = index;

        while (index.getNextWord() != null){
            if (index.getKey().equals(key.toLowerCase())){
                parent.setNextWord(index.getNextWord());
            }else{
                parent = index;
                index = index.getNextWord();
            }
        }
    }

    public void removeString(Post post){
        InvertedNode<T> index = root;
        while (index.getNextWord() != null){
            if (index.getPosts().contains(post)){
                index.removeString(post);
                return;
            }
            else index = index.getNextWord();
        }

    }

    private boolean isWord(String str){

        switch (str) {
            case "o", "a", "os", "as", "ao", "à", "aos", "às", "do", "da", "dos", "das", "no", "na", "nas", "nos", "pelo",
                    "pela", "pelos", "pelas", "um", "uma", "uns", "umas", "dum", "duma", "duns", "dumas", "num", "numa",
                    "nuns", "numas", "an", "the", "or", "if", "and" -> {
                return false;
            }
            default -> {return true;}
        }

    }
}

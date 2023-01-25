package app;

import java.util.Date;

public class Post implements Comparable<Post> {
    User user;
    String content;
    Date postDate;

    /*
     * Construtor da classe Post
     * Ele recebe o long postDate para reinstanciar ele, porém não implementei persistência
     */
    public Post(User user, String content, long postDate) {
        this.user = user;
        this.content = content;
        this.postDate = new Date(postDate);
    }


    /*
     * toString da classe, utilizado para "mostrar" o post
     */
    @Override
    public String toString() {
        return "Posted by: " + getUser().getHandle() + "\n" + getContent() + "\nPosted on: " + getPostDate().toString() + "\n";
    }

    /*
     * compareTo do post que retorna posts mais novos como menores,
     */
    @Override
    public int compareTo(Post post) {
        if (postDate.after(post.getPostDate())) return -1;
        else if (postDate.before(post.getPostDate())) return 1;
        return 0;
    }

    /*
     * Getters e Setters utilizados
     */

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }

}

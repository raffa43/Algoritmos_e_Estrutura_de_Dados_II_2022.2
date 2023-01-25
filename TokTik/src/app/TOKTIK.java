package app;

import datastructures.avltree.AVLTree;
import datastructures.invertedindex.InvertedList;
import datastructures.redblacktree.Node;
import datastructures.redblacktree.RedBlackTree;

import java.util.LinkedList;
public class TOKTIK {
    User loggedUser;
    RedBlackTree<User> users;
    InvertedList<Post> posts;
    /*
     * Construtor da classe, inicia as listas
     */
    public TOKTIK() throws Exception {
        this.users = new RedBlackTree<>();
        this.posts = new InvertedList<>();
    }
    /*
     * Loga o usuário ao aplicativo atribuindo o User na lista de Users com o mesmo handle que passado ao metodo
     * Caso, a senha (pwd) esteja correta
     */
    public void logIn(String handle, String pwd){
        this.loggedUser = users.get(new User(handle)).getData().authenticate(pwd);
        if (loggedUser != null){
            System.out.println("Logged in successfully\nWelcome, " + loggedUser.getHandle());
        }else {
            System.out.println("Invalid credentials");
        }
    }
    /*
     * Desloga o usuário
     */
    public void logOut(){
        this.loggedUser = null;
    }

    /*
     * Cria um user, adiciona ele a árvore de users e automáticamente faz login
     */
    public void createAccount(String handle, String pwd){
        User newUser = new User(handle, pwd);
        this.loggedUser = newUser;
        users.insert(newUser);
        System.out.println("Account created\nWelcome, " + loggedUser.getHandle());
    }
    /*
     * Cria um post, adiciona ele ao indicie invertido e a árvore de posts do User que está logado
     */
    public void makePost(String content) throws Exception {
        Post newPost = new Post(loggedUser, content, System.currentTimeMillis());
        posts.insert(newPost);
        loggedUser.getPosts().insert(newPost);
        System.out.println(newPost);
    }
    /*
     * Manda um pedido de amizade ao User passado, utiliza o searchUser na classe main para
     * receber o User
     */
    public void sendFriendRequest(User user){
        user.getFriendRequests().add(new FriendRequest(loggedUser, user));
        System.out.println("Friend request sent to: " + user.getHandle());
    }
    /*
     * Método de busca lexigrafica dentro da árvore rubro-negra de User atráves do handle
     */
    public User searchUser(String handle){
        Node<User> user = users.get(new User(handle));
        return user != null? user.getData() : null;
    }


    /*
     * Método de busca de posts atráves do indicie invertido
     * Mostrando os posts que contenham as palavras passadas ao método
     */
    public void searchPosts(String search){
        search = search.toLowerCase();
        String[] strs = search.split(" ");
        LinkedList<Post> foundPosts = new LinkedList<>();
        for(String word : strs) {
            foundPosts.addAll(posts.getPosts(word));
        }

        System.out.println("Posts found: ");
        for (Post post: foundPosts) {
            System.out.println(post.toString());
        }
    }
    /*
     * Getters do app
     */
    public User getLoggedUser() {
        return loggedUser;
    }
}

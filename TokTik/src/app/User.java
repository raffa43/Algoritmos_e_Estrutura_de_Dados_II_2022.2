package app;

import java.util.LinkedList;
import java.util.Scanner;

import datastructures.avltree.*;

public class User implements Comparable<User> {
    private final Scanner sc = new Scanner(System.in);
    String handle;
    String pwd;
    LinkedList<User> friends;
    LinkedList<FriendRequest> friendRequests;
    AVLTree<Post> posts;

    /*
     * Cria uma instância da classe somente com o handle para usar o compareTo
     */
    public User(String handle) {
        this.handle = handle;
    }
    /*
     * Cria uma instância completa da classe
     */
    public User(String handle, String pwd) {
        this.handle = handle;
        this.pwd = pwd;
        this.friends = new LinkedList<>();
        this.friendRequests = new LinkedList<>();
        this.posts = new AVLTree<>();
    }
    /*
     * Esse construtor seria utilizado para reinstanciação de um usuário, porém eu não implementei persistência
    public User(String handle, String pwd, LinkedList<User> friends, LinkedList<FriendRequest> friendRequests, RedBlackTree<Post> posts) {
        this.handle = handle;
        this.pwd = pwd;
        this.friends = friends;
        this.friendRequests = friendRequests;
        this.posts = posts;
    }*/

    /*
     * Mostra a timeline do usuário através de uma travessia em ordem da árvore rubro-negra
     */
    public void showTimeline(){
        System.out.println(getHandle() + "'s timeline:");
        posts.traverse();
    }

    /*
     * Mostra o atributo handle dos Users presentes na lista de amigos deste User
     */
    public void showFriends(){
        System.out.println(getHandle() + "'s friend list:");
        for (User friend: friends) {
            System.out.println(friend.getHandle());
        }
    }
    /*
     * Mostrar e aceitar ou rejeitar os pedidos de amizade
     */
    public void checkFriendRequests(){
        int totalFriends = friendRequests.size();
        System.out.println("A total of " + totalFriends + " were found.");
        int i = 0;
        while(!friendRequests.isEmpty()) {
            FriendRequest friendRequest = friendRequests.get(i);
            System.out.println("Type \"y\" to accept or \"n\" to reject " + friendRequest.getSent().getHandle()+ "'s friend request, or \"q\" to quit");
            String answer = sc.next();
            if(answer.equalsIgnoreCase("y")){
                friendRequest.getSent().addFriend(friendRequest.getReceived());
                friendRequest.getReceived().addFriend(friendRequest.getSent());
                friendRequests.remove(friendRequest);
                System.out.println("Friend request accepted");
            }else if (answer.equalsIgnoreCase("n")){
                friendRequests.remove(friendRequest);
                System.out.println("Friend request rejected");
            } else if (answer.equalsIgnoreCase("q")) {
                break;
            } else {
                System.out.println("Invalid answer");
                i--;
            }
        }
    }

    /*
     * Método simples para simplificar o código de aceitar amigo do metodo anterior
     */
    private void addFriend(User user){
        this.friends.add(user);
    }

    /*
     * Remove o User que possui o handle passado ao metodo da lista de amigos e
     * Tambem remove este User da lista de amigos do outro User
     */
    public void removeFriend(String handle){
        for (int i = 0; i < friends.size(); i++) {
            if(friends.get(i).getHandle().equals(handle)){
                friends.remove(friends.get(i));
                friends.get(i).getFriends().remove(this);
                return;
            }
        }
        System.out.println("Handle not found in friends list");
    }
    /*
     * Método de autenticação utilizado pelo programa
     */
    public User authenticate(String pwd){
        return this.getPwd().equals(pwd) ? this : null;
    }

    /*
     * Getters utilizados pelo app
     */

    public String getHandle() {
        return handle;
    }

    public String getPwd() {
        return pwd;
    }

    public LinkedList<User> getFriends() {
        return friends;
    }

    public LinkedList<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public AVLTree<Post> getPosts() {
        return posts;
    }

    /*
     * CompareTo que compara lexograficamente o atributo handle
     */
    @Override
    public int compareTo(User user) {
        return this.getHandle().compareTo(user.getHandle());
    }
}

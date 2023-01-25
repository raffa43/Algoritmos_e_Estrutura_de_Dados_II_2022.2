package app;
/*
 * Classe simples para facilitar o armazenamento de pedidos de amizade
 * Contem somente seu construtor e getters
 * Armazena quem Enviou e quem recebeu o pedido de amizade
 */
public class FriendRequest {
    User sent;
    User received;
    public FriendRequest(User sent, User received) {
        this.sent = sent;
        this.received = received;
    }

    public User getSent() {
        return sent;
    }

    public User getReceived() {
        return received;
    }
}

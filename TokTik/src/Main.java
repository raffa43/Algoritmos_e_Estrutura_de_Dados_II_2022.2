import app.TOKTIK;
import app.User;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/*## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## #
##      Autor(es): Rafael Oliveira Dos Santos                                                    ##
##      Concluído em: 19/01/2023                                                                 ##
## Declaro que este código foi elaborado por mim de forma individual e não contém nenhum         ##
## trecho de código de outro colega ou de outro autor, tais como provindos de livros e           ##
## apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código         ##
## de outra autoria que não a minha está destacado com uma citação para o autor e a fonte        ##
## do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.    ##
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## # */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        TOKTIK app = new TOKTIK();
        int option = Integer.MAX_VALUE;
        //Método para preencher o app com usuários e postagens
        fillUp(app);
        System.out.println("Welcome to TOKTIK");
        while (true){
            if (app.getLoggedUser() == null){
                System.out.println("Type 1 to log in.");
                System.out.println("Type 2 to create an account.");
                System.out.println("Type 0 to quit");
                try {
                    option = Integer.parseInt(sc.nextLine());
                }catch (NumberFormatException e){
                    System.out.println("Invalid input");
                }
                switch (option){
                    case 1 -> {
                        System.out.println("Type your user handle:");
                        String handle = sc.nextLine();
                        System.out.println("Type your password: ");
                        String pwd = sc.nextLine();
                        app.logIn(handle, pwd);
                    }
                    case 2 -> {
                        System.out.println("Type your user handle:");
                        String handle = sc.nextLine();
                        System.out.println("Type your password: ");
                        String pwd = sc.nextLine();
                        app.createAccount(handle, pwd);
                    }
                    case 0 -> {
                        System.out.println("Exiting application.");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid input.");
                }
            }else {
                System.out.println("Type 1 to show timeline");
                System.out.println("Type 2 to show friend list");
                System.out.println("Type 3 to search for user");
                System.out.println("Type 4 to search for a post");
                System.out.println("Type 5 to make a post");
                System.out.println("Type 6 to send a friend request");
                System.out.println("Type 7 to see your friend requests");
                System.out.println("Type 8 to remove a friend");
                System.out.println("Type 9 to log out");
                System.out.println("Type 0 to exit application");
                try {
                    option = Integer.parseInt(sc.nextLine());
                }catch (NumberFormatException e){
                    System.out.println("Invalid input");
                }
                switch (option){
                    case 1 -> app.getLoggedUser().showTimeline();
                    case 2 -> app.getLoggedUser().showFriends();
                    case 3 ->{
                        System.out.println("Type the user handle to search for: ");
                        String handle = sc.nextLine();
                        User search = app.searchUser(handle);
                        if(search != null){
                            System.out.println("User found. Show " + search.getHandle() + "'s posts? (y/n)");
                            String answer = sc.nextLine();
                            if (answer.equalsIgnoreCase("y")) search.showTimeline();
                        }else {
                            System.out.println("No user with that handle was found");
                        }
                    }
                    case 4 -> {
                        System.out.println("Type the content to search for: ");
                        String content = sc.nextLine();
                        app.searchPosts(content);
                    }
                    case 5 -> {
                        System.out.println("Type your post's content: ");
                        String content = sc.nextLine();
                        app.makePost(content);
                    }
                    case 6 -> {
                        System.out.println("Type user handle to send friend request");
                        String handle = sc.nextLine();
                        User user = app.searchUser(handle);
                        if (user != null){
                            app.sendFriendRequest(user);
                        }else{
                            System.out.println("No user with that handle was found");
                        }
                    }
                    case 7 -> app.getLoggedUser().checkFriendRequests();
                    case 8 ->{
                        System.out.println("Type the friend's handle to remove");
                        String handle = sc.nextLine();
                        app.getLoggedUser().removeFriend(handle);
                    }
                    case 9 -> {
                        System.out.println("Logged out");
                        app.logOut();
                    }
                    case 0 ->{
                        System.out.println("Exiting application.");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid input.");
                }
            }
            TimeUnit.SECONDS.sleep(5);
            option = Integer.MAX_VALUE;
        }
    }

    /*
     * Método para preencher o applicativo com usuários e postagens
     */
    public static void fillUp(TOKTIK app) throws Exception {
        app.createAccount("Nietzsche", "123");
        app.createAccount("Socrates", "456");
        app.createAccount("Platão", "789");
        app.createAccount("Diógenes", "s3nh4");

        app.logIn("Nietzsche", "123");
        app.makePost("Aquele que tem um forte por quê viver, suporta, praticamente, qualquer como.");
        app.makePost("O inimigo mais perigoso que você poderá encontrar será sempre você mesmo.");
        app.makePost("Não é a intensidade dos sentimentos elevados que faz os homens superiores, mas a sua duração.");
        app.sendFriendRequest(app.searchUser("Platão"));
        app.logOut();

        app.logIn("Socrates", "456");
        app.makePost("Sábio é aquele que conhece os limites da própria ignorância.");
        TimeUnit.SECONDS.sleep(1);
        app.makePost("Para conseguir a amizade de uma pessoa digna é preciso desenvolvermos em nós mesmos as qualidades que naquela admiramos.");
        TimeUnit.SECONDS.sleep(1);
        app.makePost("Só sei que nada sei.");
        app.sendFriendRequest(app.searchUser("Platão"));
        app.sendFriendRequest(app.searchUser("Diógenes"));
        app.logOut();

        app.logIn("Diógenes", "s3nh4");
        app.makePost("Os piores escravos são aqueles que estão servindo constantemente as suas paixões.");
        app.makePost("Só é verdadeiramente livre quem está sempre pronto a morrer.");
        app.makePost("Entre os animais ferozes, o de mais perigosa mordedura é o delator; entre os animais domésticos, o adulador.");
        app.sendFriendRequest(app.searchUser("Socrates"));
        app.sendFriendRequest(app.searchUser("Nietzsche"));
        app.logOut();

        app.logIn("Platão", "789");
        app.makePost("Deve-se temer a velhice, porque ela nunca vem só. Bengalas são provas de idade e não de prudência");
        app.makePost("De todos os animais selvagens, o homem jovem é o mais difícil de domar.");
        app.makePost("Uma vida não questionada não merece ser vivida.");
        app.logOut();
        System.out.println("\n---------------End of pre-written data---------------\n");
    }
}
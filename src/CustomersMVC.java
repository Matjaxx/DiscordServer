import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class CustomersMVC {
    //creer un getter de la cette list


    private List<String> listCustomer = new ArrayList<String>();

    private List<String> friendRequest = new ArrayList<String>();
    private List<String> friends = new ArrayList<String>();

    private Controller controller = new Controller();
    private String Username;
    private String Password;
    private String Email;
    private String FirstName;
    private String LastName;
    private String permission;
    boolean isConnected = false;



    public List<String> getListCustomer() {
        return listCustomer;
    }
    //creer un getter pour isConnected
    public boolean getIsConnected() {
        return isConnected;
    }
    //creer un getter pour username
    public String getUsername() {
        return Username;
    }

    public void setInfoCustomer(List<String> infoCustomer) {
        System.out.println("setInfoCustomer");
        isConnected = true;
        Username = infoCustomer.get(1);
        FirstName = infoCustomer.get(2);
        LastName = infoCustomer.get(3);
        Password = infoCustomer.get(4);
        Email = infoCustomer.get(5);
        permission = infoCustomer.get(6);
    }

    public void setInfoFriendlist(List<String> AnswerServerFriendlist) {
        System.out.println("setFriendrequestInfo");
        friendRequest.clear();
        for (int i = 1; i < AnswerServerFriendlist.size(); i++) {
            friendRequest.add(AnswerServerFriendlist.get(i));
        }
    }
    public void setInfoFriend(List<String> AnswerServerFriendlist) {
        System.out.println("setFriendListInfo");
        friends.clear();
        for (int i = 1; i < AnswerServerFriendlist.size(); i++) {
            friends.add(AnswerServerFriendlist.get(i));
        }

    }
    public String YourConv(List<String> AnswerServerFriendlist) {
        AnswerServerFriendlist.remove(0);
        String reciever = AnswerServerFriendlist.get(1);
        AnswerServerFriendlist.remove(1);
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < AnswerServerFriendlist.size(); i++) {
            if (AnswerServerFriendlist.get(i).equals(Username)) {
                System.out.println(AnswerServerFriendlist.get(i+2) +"  Me: " +AnswerServerFriendlist.get(i+3)  );
                i+=4;
            }
            else{
                System.out.println(AnswerServerFriendlist.get(i+2) +"                "+reciever+": " +AnswerServerFriendlist.get(i+3)  );
                i+=4;
            }
        }
        System.out.println("Type your message");
        String message = s.nextLine();
        return "SendM"+" "+ reciever + " " + Username + " " + message;
    }

    public String Conversation() {
        Scanner s = new Scanner(System.in);
        String Receiver;
        System.out.println("Type the name of the person you want to talk to");
        Receiver = s.nextLine();
        return "conversation" + " " + Username + " " + Receiver;
    }

    public String setInfoFriendRequest() {
        for (int i = 0; i < friendRequest.size(); i++) {
            System.out.println(friendRequest.get(i)+" want to be your friend!");
        }
        Scanner s = new Scanner(System.in);
        Scanner l = new Scanner(System.in);
        String answer;
        System.out.println("If you want to deny the friend request press -->0");
        System.out.println("If you want to accept the friend request press -->1");
        System.out.println("You can do it later by pressing -->2");

        int a = s.nextInt();

        if (a==1){
            System.out.println("type the name of the person you want to accept");
            answer= l.nextLine();
            System.out.println(answer);
            return "acceptfriendrequest" + " " +answer+ " " +Username;
        }
        else if(a==0) {
            System.out.println("type the name of the person you want to deny");
            answer = l.nextLine();
            System.out.println(answer);
            return "denyfriendrequest" + " " +answer+ " " +Username;
        }
        else if(a==2){
            return "cancel";
        }
        else{
            return "cancel";
        }
    }
    //creer une methode qui affiche dans la console tout les private string
    public void displayInfoCustomer(){
        System.out.println("Username : " + Username);
        System.out.println("First name : " + FirstName);
        System.out.println("Last name : " + LastName);
        System.out.println("Email : " + Email);
        System.out.println("Password : " + Password);
        System.out.println("Permission : " + permission);
    }
    public void seeMyFriends(){
        for (int i = 0; i < friends.size(); i++) {
            System.out.println("your friends are :");
            System.out.println(friends.get(i));
        }
    }
    public void connection(){
        int a;
        Scanner s = new Scanner(System.in);
        //creer ue list de string


        System.out.println("If you want to connect to the apps press -->1");
        System.out.println("If you want to create an account the apps press -->2");
        a =s.nextInt();
        System.out.println(a);
        if (a==2){
            listCustomer.add("createAccount");
            System.out.println("entrer votre ID");
            String UserCharacteristic = s.nextLine();
            UserCharacteristic = s.nextLine();
            listCustomer.add(UserCharacteristic);
            System.out.println("entrer votre pseudo");
            UserCharacteristic = s.nextLine();
            listCustomer.add(UserCharacteristic);
            System.out.println("entrer votre prenom");
            UserCharacteristic = s.nextLine();
            listCustomer.add(UserCharacteristic);
            System.out.println("entrer votre nom");
            UserCharacteristic = s.nextLine();
            listCustomer.add(UserCharacteristic);
            System.out.println("entrer votre adresse mail");
            UserCharacteristic = s.nextLine();
            listCustomer.add(UserCharacteristic);
            System.out.println("entrer votre mot de passe");
            UserCharacteristic = s.nextLine();
            UserCharacteristic = hashingPassword(UserCharacteristic);
            listCustomer.add(UserCharacteristic);
            listCustomer.add("User");

            for (int i = 0; i < listCustomer.size(); i++) {
                System.out.println(listCustomer.get(i));
            }

        }
        if (a==1) {
            listCustomer.add("connectionAccount");
            System.out.println("entrer votre pseudo");
            String UserCharacteristic2 = s.nextLine();
            UserCharacteristic2 = s.nextLine();
            listCustomer.add(UserCharacteristic2);
            System.out.println("entrer votre password");
            UserCharacteristic2 = s.nextLine();
            UserCharacteristic2 = hashingPassword(UserCharacteristic2);
            listCustomer.add(UserCharacteristic2);


            for (int i = 0; i < listCustomer.size(); i++) {
                System.out.println(listCustomer.get(i));
            }
        }


    }


    public String hashingPassword(String password)  {
        String hashPassword = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] passwordBytes = password.getBytes();
            byte[] hash = md.digest(passwordBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException){
            System.out.println("Erreur lors du hashage du mot de passe : " + password);
        }
        return hashPassword;
    }

    public String requestFrienship(){
        String request = "requestFriendship" + " " + Username + " ";
        Scanner s = new Scanner(System.in);
        System.out.println("entrer le pseudo de la personne que vous voulez ajouter");
        request += s.nextLine();
        return request;
    }
}




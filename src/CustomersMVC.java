import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class CustomersMVC {
    //creer un getter de la cette list

    private List<String> listCustomer = new ArrayList<String>();

    private List<String> Conversation = new ArrayList<String>();

    private List<String> UserInConversation = new ArrayList<String>();
    private List<String> friendRequest = new ArrayList<String>();
    private List<String> friends = new ArrayList<String>();
    private String Username;
    private String Password;
    private String Email;
    private String FirstName;
    private String LastName;
    private String permission;
    private String userLVL;
    private boolean isConnected = false;
    private boolean isBanned = false;

    private int indexPermission;
    //0 USER
    //1 MODO
    //2 ADMIN





    public List<String> getListCustomer() {
        return listCustomer;
    }
    public List<String> getConversation() {
        return Conversation;
    }
    public List<String> getUserinConversation() {
        return UserInConversation;
    }
    //creer un getter pour isConnected
    public boolean getIsConnected() {
        return isConnected;
    }
    public List<String> getListRequest() {
        return friendRequest;
    }


    public void RemoveFriendRequest(int index) {
        friendRequest.remove(index);
    }
    public void RemoveFriendRequestList(int index) {
        friends.remove(index);
    }
    public List<String> getListFriends() {
        return friends;
    }

    //creer un setter pour ajouter un ami
    public void AddFriendList(String friend) {
        friends.add(friend);
    }
    public void ADDFriendList(String friend) {
        friendRequest.add(friend);
    }
    public boolean getIsBanned() {
        return isBanned;
    }
    //creer un getter pour username
    public String getUsername() {
        return Username;
    }
    private String SpeakingWith;

    public void setSpeakingWith(String a){
        SpeakingWith = a;
    }
    public String getSpeakingWith() {
        return SpeakingWith;
    }

    public void setInfoCustomer(List<String> infoCustomer) {
        System.out.println(infoCustomer.get(0));
        if (infoCustomer.get(0).equals("banned")) {
            //TODO
            isBanned = true;
        }else{
            System.out.println("setInfoCustomer");
            isConnected = true;
            Username = infoCustomer.get(1);
            FirstName = infoCustomer.get(2);
            LastName = infoCustomer.get(3);
            Password = infoCustomer.get(6);
            Email = infoCustomer.get(4);
            permission = infoCustomer.get(5);
            userLVL = infoCustomer.get(7);
            if (infoCustomer.get(7).equals("USER")) {
                indexPermission = 0;
                System.out.println("You are USER");
            }
            if (infoCustomer.get(7).equals("MODO")) {
                indexPermission = 1;
                System.out.println("You are MODO");
            }
            if (infoCustomer.get(7).equals("ADMIN")) {
                indexPermission = 2;
                System.out.println("You are ADMIN");
            }

        }

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
        System.out.println("-------------------");

        List<String> newFriendList = new ArrayList<String>(AnswerServerFriendlist);
        newFriendList.remove(0);

        for (int i = 0; i < newFriendList.size(); i++) {
            System.out.println("-------------------");
            System.out.println(newFriendList.get(i));
        }
        String reciever="";

        if (newFriendList.size() != 0) {
            reciever = newFriendList.get(1);
            if (!newFriendList.get(0).equals(Username)) {
                reciever = newFriendList.get(0);
            }
            if (!newFriendList.get(1).equals(Username)) {
                reciever = newFriendList.get(1);
            }
        }

        Scanner s = new Scanner(System.in);
        /*for (int i = 0; i < newFriendList.size(); i++) {
            if (newFriendList.get(i).equals(Username)) {
                System.out.println(newFriendList.get(i));
                System.out.println(newFriendList.get(i+1) + " " + newFriendList.get(i+2)+"  Me: " + newFriendList.get(i+3)  );
                i+=3;
            }
            else{
                System.out.println(newFriendList.get(i));
                System.out.println(newFriendList.get(i+1)+ newFriendList.get(i+2) +"    "+  newFriendList.get(i+3)+ "                :"+reciever+" "   );
                i+=3;
            }
        }*/
        int k = 0;
        if (newFriendList.size() != 0) {
            while (k != newFriendList.size() / 5) {
                if (newFriendList.get(0).equals(Username)) {
                    newFriendList.set(4, newFriendList.get(4).replace("_", " "));

                    System.out.println(newFriendList.get(3) + " " + newFriendList.get(2) + "  Me: " + newFriendList.get(4));
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);

                } else {
                    newFriendList.set(4, newFriendList.get(4).replace("_", " "));
                    Conversation.add(newFriendList.get(3) + " " + newFriendList.get(2) + "               " + newFriendList.get(4) + "                :" + newFriendList.get(0) + " ");
                    System.out.println(newFriendList.get(3) + " " + newFriendList.get(2) + "               " + newFriendList.get(4) + "                :" + newFriendList.get(0) + " ");
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                }
            }
        }
        System.out.println("Type your message");
        String message = s.nextLine();
        message = message.replaceAll(" ", "_");
        System.out.println("testLine " + message);
        return "SendM " + reciever + " " + Username + " " + message;
    }

    public String SendMessageGraph(String message) {

        message = message.replaceAll(" ", "_");
        System.out.println("testLine " + message);
        return "SendMessageGraph " + SpeakingWith + " " + Username + " " + message;
    }

    public  void flushConversation(){
        Conversation.clear();
    }
    public void YourConvs(List<String> AnswerServerFriendlist) {
        System.out.println("-------------------");

        List<String> newFriendList = new ArrayList<String>(AnswerServerFriendlist);
        newFriendList.remove(0);

        for (int i = 0; i < newFriendList.size(); i++) {
            System.out.println("-------------------");
            System.out.println(newFriendList.get(i));
        }
        String reciever="";

        if (newFriendList.size() != 0) {
            reciever = newFriendList.get(1);
            if (!newFriendList.get(0).equals(Username)) {
                reciever = newFriendList.get(0);
            }
            if (!newFriendList.get(1).equals(Username)) {
                reciever = newFriendList.get(1);
            }
        }

        Scanner s = new Scanner(System.in);
        /*for (int i = 0; i < newFriendList.size(); i++) {
            if (newFriendList.get(i).equals(Username)) {
                System.out.println(newFriendList.get(i));
                System.out.println(newFriendList.get(i+1) + " " + newFriendList.get(i+2)+"  Me: " + newFriendList.get(i+3)  );
                i+=3;
            }
            else{
                System.out.println(newFriendList.get(i));
                System.out.println(newFriendList.get(i+1)+ newFriendList.get(i+2) +"    "+  newFriendList.get(i+3)+ "                :"+reciever+" "   );
                i+=3;
            }
        }*/
        int k = 0;
        if (newFriendList.size() != 0) {
            while (k != newFriendList.size() / 5) {
                if (newFriendList.get(0).equals(Username)) {
                    newFriendList.set(4, newFriendList.get(4).replace("_", " "));
                    Conversation.add(newFriendList.get(3) + " " + newFriendList.get(2) + "      Me: " + newFriendList.get(4));
                    System.out.println(newFriendList.get(3) + " " + newFriendList.get(2) + "      Me: " + newFriendList.get(4));
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);

                } else {
                    newFriendList.set(4, newFriendList.get(4).replace("_", " "));
                    Conversation.add(newFriendList.get(3) + " " + newFriendList.get(2) + "               " + newFriendList.get(0) + ":                " + newFriendList.get(4) + " ");
                    System.out.println(newFriendList.get(3) + " " + newFriendList.get(2) + "               " + newFriendList.get(0) + ":                " + newFriendList.get(4) + " ");
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                    newFriendList.remove(0);
                }
            }
        }
    }
    //creer un fonction qui affiche la list Conversation
    public void ConversationGRAPH() {
        System.out.println("--------CONV--------");
        for (int i = 0; i < Conversation.size(); i++) {
            System.out.println(Conversation.get(i));
        }
    }

    public void GetConvs(List<String> AnswerServerConvlist) {
        //creer une list
        List<String> newConvList = new ArrayList<String>(AnswerServerConvlist);
        newConvList.remove(0);
        System.out.println("--------CONVLIST--------");
        for (int i = 0; i < newConvList.size(); i++) {
            UserInConversation.add(newConvList.get(i));
        }
        for (int i = 0; i < UserInConversation.size(); i++) {
            System.out.println(UserInConversation.get(i));
        }
    }

    public String Conversation() {
        Scanner s = new Scanner(System.in);
        String Receiver;
        System.out.println("Type the name of the person you want to talk to");
        Receiver = s.nextLine();
        System.out.println("testLine");
        return "conversation" + " " + Username + " " + Receiver;
    }

    public String Conv() {
        Scanner s = new Scanner(System.in);

        String Receiver;
        /*System.out.println("Type the name of the person you want to talk to");
        Receiver = s.nextLine();
        System.out.println("testLine");
        SpeakingWith = Receiver;*/
        return "Convs" + " " + Username + " " + SpeakingWith;
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


    public String tryToBanUser() {
        Scanner s = new Scanner(System.in);
        Scanner l = new Scanner(System.in);
        String answer;
        System.out.println("If you want to ban someone -->0");
        System.out.println("If you want to free someone -->1");


        int a = s.nextInt();

        if (a==1){
            System.out.println("type the name of the person you want to free");
            answer= l.nextLine();
            System.out.println(answer);
            return "freeUser" + " " + Username+ " " +answer;
        }
        else if(a==0) {
            System.out.println("type the name of the person you want to ban");
            answer = l.nextLine();
            System.out.println(answer);
            return "banUser" + " " + Username+ " " +answer;
        }
        else{
            return "cancel";
        }
    }


    public String tryToBeOnline() {
        return "beOnline" + " " + Username;
    }

    public String tryToBeDisconnect() {
        return "beDisconnect" + " " + Username;
    }

    public String tryToBeAway() {
        return "beAway" + " " + Username;
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
    public void connectionGraphique(int a, String pseudo, String password, String nom, String mail, String prenom, String id){
        //creer ue list de string

        if (a==2){
            listCustomer.add("createAccount");
            String UserCharacteristic;
            UserCharacteristic = id;
            listCustomer.add(UserCharacteristic);
            UserCharacteristic = pseudo;
            listCustomer.add(UserCharacteristic);
            UserCharacteristic = prenom;
            listCustomer.add(UserCharacteristic);
            UserCharacteristic = nom;
            listCustomer.add(UserCharacteristic);
            UserCharacteristic = mail;
            listCustomer.add(UserCharacteristic);
            UserCharacteristic = password;
            UserCharacteristic = hashingPassword(UserCharacteristic);
            listCustomer.add(UserCharacteristic);
            listCustomer.add("Free");
            listCustomer.add("User");

            for (int i = 0; i < listCustomer.size(); i++) {
                System.out.println(listCustomer.get(i));
            }

        }
        if (a==1) {
            listCustomer.add("connectionAccount");
            String UserCharacteristic2 = pseudo;
            listCustomer.add(UserCharacteristic2);
            UserCharacteristic2 = password;
            UserCharacteristic2 = hashingPassword(UserCharacteristic2);
            listCustomer.add(UserCharacteristic2);

            for (int i = 0; i < listCustomer.size(); i++) {
                System.out.println(listCustomer.get(i));
            }
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
            UserCharacteristic = "1";
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
            listCustomer.add("Free");
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

    public String requestFrienshipGraphic(String name){
        String request = "requestFriendship" + " " + Username + " ";
        request += name;
        return request;
    }

    public String banUser(){
        String request = "banUser" + " " + Username + " ";
        Scanner s = new Scanner(System.in);
        System.out.println("entrer le pseudo de la personne que vous voulez bannir");
        request += s.nextLine();
        return request;
    }

    public String freeUser(){
        String request = "freeUser" + " " + Username + " ";
        Scanner s = new Scanner(System.in);
        System.out.println("entrer le pseudo de la personne que vous voulez débannir");
        request += s.nextLine();
        return request;
    }

    public String seeMyFriendsOnline(){
        String stringFriends =  " " + Username;
        for (String friend:friends) {
            stringFriends +=  " " + friend;
        }
        System.out.println(stringFriends);
        return stringFriends;
    }

}




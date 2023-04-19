import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Client extends JFrame {
    private List<String> ServerContent = new ArrayList<String>();
    private Socket socket;
    private PrintWriter out;
    public List<String> infoCustomers = new ArrayList<>();
    private BufferedReader in;
    private String host;
    private int port;
    public JFrame homePage = new JFrame();
    public JFrame createAccountPage = new JFrame();
    public JFrame messagePage = new JFrame();
    private String pseudoTextt;
    private String passwordTextt;
    private String nameText;
    private String firstNameText;
    private String mailText;
    private String idText;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static List<String> separateWords(String inputLine) {
        String[] wordsArray = inputLine.split("\\s+");
        List<String> wordsList = Arrays.asList(wordsArray);

        for (int i = 0; i < wordsList.size(); i++) {
            System.out.println(wordsList.get(i));
        }
        return wordsList;
    }

    public void execute() {
        try {
            // Se connecte au serveur
            socket = new Socket(host, port);
            List<String> infoCustomers = new ArrayList<>();
            CustomersMVC customer = new CustomersMVC();

            // Initialise les flux d'entrée/sortie
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Boucle pour lire l'entrée utilisateur et l'envoyer au serveur
            String userInput;

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            homePage.setTitle("Home Page");

            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            ImageIcon logo = new ImageIcon("Images/Logo.JPG");
            JLabel logoLabel = new JLabel(logo);
            Image image = logo.getImage();
            Image image2 = image.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
            ImageIcon logoFinal = new ImageIcon(image2);
            logoLabel.setIcon(logoFinal);

            JLabel pseudo = new JLabel("Enter your pseudo :");
            JTextField pseudoInput = new JTextField(12);
            JLabel password = new JLabel("Enter your password :");
            JPasswordField passwordInput = new JPasswordField(12);
            JButton buttonConnexion = new JButton("Connect");
            JLabel space = new JLabel("-----------------");
            JLabel textAccount = new JLabel("Don't have an account ?");
            JButton buttonCreateAccount = new JButton("Create an account");

            c.insets = new Insets(5,5,5,5);
            c.gridx=0;
            c.gridy=0;
            c.gridwidth = 2;
            c.anchor=GridBagConstraints.CENTER;
            panel.add(logoLabel,c);

            c.gridwidth = 1;
            c.gridheight = 1;
            c.gridx=0; c.gridy=1;
            panel.add(pseudo,c);
            c.gridx=1; c.gridy=1;
            panel.add(pseudoInput,c);

            c.gridx=0; c.gridy=2;
            panel.add(password,c);
            c.gridx=1; c.gridy=2;
            panel.add(passwordInput,c);

            c.gridwidth = 2;
            c.gridx=0; c.gridy=3;
            panel.add(buttonConnexion,c);

            c.gridx=0; c.gridy=4;
            panel.add(space,c);

            c.insets = new Insets(1,1,1,1);
            c.gridwidth = 1;
            c.gridx=0; c.gridy=5;
            c.anchor=GridBagConstraints.EAST;
            panel.add(textAccount,c);
            c.gridx=1; c.gridy=5;
            c.anchor=GridBagConstraints.WEST;
            panel.add(buttonCreateAccount,c);

            buttonConnexion.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent a){
                    //visualMessagePage();
                    //homePage.dispose();
                    pseudoTextt = pseudoInput.getText();
                    passwordTextt = new String(passwordInput.getPassword());

                    customer.connectionGraphique(1, pseudoTextt,passwordTextt,null,null,null, null);
                    String convertListToString = "";
                    for (int i = 0; i < customer.getListCustomer().size(); i++) {
                        convertListToString += customer.getListCustomer().get(i)+" ";
                    }
                    out.println(convertListToString);
                    String serverResponse = null;
                    try {
                        serverResponse = in.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (serverResponse != null) {
                        System.out.println("Received message from server: " + serverResponse);
                        ServerContent = separateWords(serverResponse);
                        if (Objects.equals(ServerContent.get(0), "connected")) {
                            customer.setInfoCustomer(ServerContent);
                            customer.displayInfoCustomer();
                        }
                        if (Objects.equals(ServerContent.get(0), "friendRequest")) {
                            customer.setInfoFriendlist(ServerContent);
                            customer.displayInfoCustomer();
                        }
                        if (Objects.equals(ServerContent.get(0), "requestlist")) {
                            customer.setInfoCustomer(ServerContent);
                            customer.displayInfoCustomer();
                        }
                        if (Objects.equals(ServerContent.get(0), "friendListUpdate")) {
                            customer.setInfoFriend(ServerContent);
                            System.out.println("friendListUpdated");
                        }
                        if (Objects.equals(ServerContent.get(0), "conversation")) {
                            String ForServ = customer.YourConv(ServerContent);
                            out.println(ForServ);
                        }
                        if (Objects.equals(ServerContent.get(0), "banUser")) {
                            customer.setInfoFriend(ServerContent);
                            System.out.println("banUser");
                        }
                        if (Objects.equals(ServerContent.get(0), "freeUser")) {
                            customer.setInfoFriend(ServerContent);
                            System.out.println("freeUser");
                        }
                        if (Objects.equals(ServerContent.get(0), "beOnline")) {
                            customer.setInfoFriend(ServerContent);
                            System.out.println("beOnline");
                        }
                        if (Objects.equals(ServerContent.get(0), "beDisconnect")) {
                            customer.setInfoFriend(ServerContent);
                            System.out.println("beDisconnect");
                        }

                    }
                }
            }
            );
            buttonCreateAccount.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){

                    homePage.dispose();
                    createAccountPage.setTitle("Create an account");

                    JPanel panel = new JPanel();
                    panel.setLayout(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();
                    JLabel id = new JLabel("ID :");
                    JTextField idInput = new JTextField(12);
                    JLabel pseudo = new JLabel("Pseudo :");
                    JTextField pseudoInput = new JTextField(12);
                    JLabel firstname = new JLabel("Firstname :");
                    JTextField firstnameInput = new JTextField(12);
                    JLabel name = new JLabel("Name :");
                    JTextField nameInput = new JTextField(12);
                    JLabel email = new JLabel("Email :");
                    JTextField emailInput = new JTextField(12);
                    JLabel password = new JLabel("Password :");
                    JPasswordField passwordInput = new JPasswordField(12);
                    JButton buttonCreateAccount1 = new JButton("Create an account");

                    c.insets = new Insets(5,5,5,5);
                    c.gridx=0; c.gridy=0;
                    c.gridwidth = 2;
                    c.anchor=GridBagConstraints.CENTER;
                    panel.add(logoLabel,c);

                    c.gridwidth = 1;
                    c.gridx=0; c.gridy=1;
                    panel.add(id,c);
                    c.gridx=1; c.gridy=1;
                    panel.add(idInput,c);

                    c.gridx=0; c.gridy=2;
                    panel.add(pseudo,c);
                    c.gridx=1; c.gridy=2;
                    panel.add(pseudoInput,c);

                    c.gridx=0; c.gridy=3;
                    panel.add(firstname,c);
                    c.gridx=1; c.gridy=3;
                    panel.add(firstnameInput,c);

                    c.gridx=0; c.gridy=4;
                    panel.add(name,c);
                    c.gridx=1; c.gridy=4;
                    panel.add(nameInput,c);

                    c.gridx=0; c.gridy=5;
                    panel.add(email,c);
                    c.gridx=1; c.gridy=5;
                    panel.add(emailInput,c);

                    c.gridx=0; c.gridy=6;
                    panel.add(password,c);
                    c.gridx=1; c.gridy=6;
                    panel.add(passwordInput,c);

                    c.gridwidth = 2;
                    c.gridx=0; c.gridy=7;
                    panel.add(buttonCreateAccount1,c);

                    buttonCreateAccount1.addActionListener(new ActionListener() {
                                                               public void actionPerformed(ActionEvent a) {
                                                                   pseudoTextt = pseudoInput.getText();
                                                                   passwordTextt = new String(passwordInput.getPassword());
                                                                   nameText = nameInput.getText();
                                                                   firstNameText = firstnameInput.getText();
                                                                   mailText = emailInput.getText();
                                                                   idText = idInput.getText();

                                                                   customer.connectionGraphique(2, pseudoTextt, passwordTextt, nameText, mailText, firstNameText,idText);
                                                                   String convertListToString = "";
                                                                   for (int i = 0; i < customer.getListCustomer().size(); i++) {
                                                                       convertListToString += customer.getListCustomer().get(i) + " ";
                                                                   }

                                                                   out.println(convertListToString);
                                                               }
                                                           }
                    );


                    panel.setBackground(Color.getHSBColor(0.6f,0.3f,0.9f));
                    createAccountPage.add(panel);
                    createAccountPage.pack();
                    createAccountPage.setSize(500, 500);
                    createAccountPage.setLocationRelativeTo(null);
                    createAccountPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    createAccountPage.setVisible(true);



                }
            }
            );

            panel.setBackground(Color.getHSBColor(0.6f,0.3f,0.9f));
            homePage.add(panel);
            homePage.pack();
            homePage.setSize(500, 500);
            homePage.setLocationRelativeTo(null);
            homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            homePage.setVisible(true);

            while ((userInput = stdIn.readLine()) != null) {

                if (userInput.equals("'quit()'")) {
                    break;
                }
                else if (userInput.equals("connection()")) {
                    customer.connection();
                    infoCustomers = customer.getListCustomer();
                    String convertListToString = "";
                    for (int i = 0; i < customer.getListCustomer().size(); i++) {
                        convertListToString += customer.getListCustomer().get(i)+" ";
                    }

                    out.println(convertListToString);

                }
                else if (userInput.equals("requestfriendship()") && customer.getIsConnected()) {
                    String message = customer.requestFrienship();
                    out.println(message);
                }
                else if (userInput.equals("seemyfriends()") && customer.getIsConnected()) {
                    customer.seeMyFriends();
                    out.println("bipboop");
                }
                else if (userInput.equals("UpdateRequestFriend()") && customer.getIsConnected()) {
                    String message = customer.getUsername();
                    out.println("requestlist "+ message);
                }
                else if (userInput.equals("UpdateFriendList()") && customer.getIsConnected()) {
                    String message = customer.getUsername();
                    out.println("friendlist "+ message);
                }
                else if (userInput.equals("friendManager()") && customer.getIsConnected()){
                    String message = customer.setInfoFriendRequest();
                    out.println(message);
                }
                else if (userInput.equals("banUser()") && customer.getIsConnected()){
                    String message = customer.tryToBanUser();
                    out.println(message);
                }
                else if (userInput.equals("freeUser()") && customer.getIsConnected()){
                    String message = customer.tryToBanUser();
                    out.println(message);
                }
                else if (userInput.equals("beOnline()") && customer.getIsConnected()){
                    String message = customer.tryToBeOnline();
                    out.println(message);
                }
                else if (userInput.equals("beDisconnect()") && customer.getIsConnected()){
                    String message = customer.tryToBeDisconnect();
                    out.println(message);
                }
                else if (userInput.equals("seeMyFriendsOnline()") && customer.getIsConnected()){
                    String message = customer.seeMyFriendsOnline();
                    out.println("seeMyFriendsOnline" + message);
                }
                else if (userInput.equals("Writte message") && customer.getIsConnected()){
                    String message = customer.Conversation();;
                    out.println(message);
                }
                else{
                    System.out.println("commande not found");
                    out.println("commande not found");
                }

                // Attend et affiche la réponse du serveur
                String serverResponse = in.readLine();
                if (serverResponse != null) {
                    System.out.println("Received message from server: " + serverResponse);
                    ServerContent = separateWords(serverResponse);
                    if (Objects.equals(ServerContent.get(0), "connected")) {
                        customer.setInfoCustomer(ServerContent);
                        customer.displayInfoCustomer();
                    }
                    if (Objects.equals(ServerContent.get(0), "friendRequest")) {
                        customer.setInfoFriendlist(ServerContent);
                        customer.displayInfoCustomer();
                    }
                    if (Objects.equals(ServerContent.get(0), "requestlist")) {
                        customer.setInfoCustomer(ServerContent);
                        customer.displayInfoCustomer();
                    }
                    if (Objects.equals(ServerContent.get(0), "friendListUpdate")) {
                        customer.setInfoFriend(ServerContent);
                        System.out.println("friendListUpdated");
                    }
                    if (Objects.equals(ServerContent.get(0), "Conversation")) {
                        String ForServ = customer.YourConv(ServerContent);
                        out.println(ForServ);
                    }
                    if (Objects.equals(ServerContent.get(0), "banUser")) {
                        customer.setInfoFriend(ServerContent);
                        System.out.println("banUser");
                    }
                    if (Objects.equals(ServerContent.get(0), "freeUser")) {
                        customer.setInfoFriend(ServerContent);
                        System.out.println("freeUser");
                    }
                    if (Objects.equals(ServerContent.get(0), "beOnline")) {
                        customer.setInfoFriend(ServerContent);
                        System.out.println("beOnline");
                    }
                    if (Objects.equals(ServerContent.get(0), "beDisconnect")) {
                        customer.setInfoFriend(ServerContent);
                        System.out.println("beDisconnect");
                    }
                    if (Objects.equals(ServerContent.get(0), "seeMyFriendsOnline")) {
                        customer.setInfoFriend(ServerContent);
                        System.out.println("seeMyFriendsOnline");
                    }

                }

            }

            // Ferme les flux et la socket
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

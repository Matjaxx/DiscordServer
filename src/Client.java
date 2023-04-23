import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import java.util.stream.Collectors;




public class Client extends JFrame {
    private List<String> ServerContent = new ArrayList<String>();
    private Socket socket;
    private PrintWriter out;
    public List<String> infoCustomers = new ArrayList<>();
    private BufferedReader in;
    private String host;
    private int k;
    private int port;
    public JFrame homePage = new JFrame();
    public JFrame createAccountPage = new JFrame();
    public JFrame messagePage = new JFrame();
    public JFrame friendPage = new JFrame();
    public JFrame statPage = new JFrame();
    private String pseudoTextt;
    private String passwordTextt;
    private String nameText;
    private String firstNameText;
    private String mailText;

    private volatile boolean affichage = false;
    private Thread currentThread = null;

    private Thread affichageThread = null;
    private String idText;
    private List<JButton> buttonTest1 = new ArrayList<JButton>();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JTextArea messageArea = new JTextArea();
    private int mousex = 0;
    private int mousey = 0;
    private int y = 120;
    private int y2 = 152;
    private int j = 120;
    private int j2 = 152;
    private int a1 = 400;
    private int a2 = 420;
    private int b = 100;
    private int b1 = 40;
    private int i = 0;


    private boolean iconConnected = true;
    private int boucle = 0;

    private JButton sendButton = new JButton();
    private JButton pictureButton = new JButton();
    private JButton microButton = new JButton();
    private JTextField messageText = new JTextField();
    private int connexion = 0;
    private int z = 10;

    private CustomersMVC customer = new CustomersMVC();

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public int getBoucle(){return boucle;}

    public static List<String> separateWords(String inputLine) {
        String[] wordsArray = inputLine.split("\\s+");
        List<String> wordsList = Arrays.asList(wordsArray);

        for (int i = 0; i < wordsList.size(); i++) {
            System.out.println(wordsList.get(i));
        }
        return wordsList;
    }


    private void startAffichageThread() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(affichage){
                    System.out.print("ee");
                    String message = customer.Conv();
                    out.println(message);

                    String serverResponse5 = null;
                    try {
                        serverResponse5 = in.readLine();
                    } catch (IOException s) {
                        throw new RuntimeException(s);
                    }

                    if (serverResponse5 != null) {
                        System.out.println("Received message from server: " + serverResponse5);
                        ServerContent = separateWords(serverResponse5);
                        if (Objects.equals(ServerContent.get(0), "Convs")) {
                            customer.flushConversation();
                            customer.YourConvs(ServerContent);
                            customer.displayInfoCustomer();
                        }

                        messageArea.setText(""); // effacer les anciens messages
                        for (String msg : customer.getConversation()) { // afficher tous les messages
                            messageArea.append(msg + "\n");
                        }
                    }

                    try{
                        Thread.sleep(5000);
                    }catch(InterruptedException p){
                        p.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }





    public void execute() {
        try {


            // Se connecte au serveur
            socket = new Socket(host, port);
            List<String> infoCustomers = new ArrayList<>();


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

                    String serverResponse4 = null;
                    try {
                        serverResponse4 = in.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (serverResponse4 != null) {
                        System.out.println("Received message from server: " + serverResponse4);
                        ServerContent = separateWords(serverResponse4);
                        if (Objects.equals(ServerContent.get(0), "connected")) {
                            customer.setInfoCustomer(ServerContent);
                            customer.displayInfoCustomer();
                        }
                        if (Objects.equals(ServerContent.get(0), "banned")) {
                            customer.setInfoCustomer(ServerContent);
                            customer.displayInfoCustomer();
                        }
                    }


                    //pseudoInput.setText("");
                    //passwordInput.setText("");

                    if(customer.getIsBanned()){
                        JDialog dialog = new JDialog(homePage,"BAN",true);
                        dialog.setLayout(new BorderLayout());
                        dialog.setPreferredSize(new Dimension(250,250));
                        ImageIcon iconBan = new ImageIcon("Images/banni.png");
                        JLabel ban = new JLabel(iconBan);
                        ban.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
                        JLabel banLabel = new JLabel("  You are banned !");
                        banLabel.setFont(new Font("Serif", Font.BOLD,30));
                        dialog.add(ban,BorderLayout.SOUTH);
                        dialog.add(banLabel,BorderLayout.CENTER);
                        dialog.pack();
                        dialog.setLocationRelativeTo(homePage);
                        dialog.setVisible(true);
                        messagePage.dispose();
                        homePage.setVisible(true);
                    }
                    else{

                        System.out.println("aaa");
                        homePage.dispose();
                        messagePage.setTitle("Your account");
                        messagePage.setSize(1000,700);

                        JPanel panel1 = new JPanel();
                        panel1.setLayout(null);
                        panel1.setBackground(Color.lightGray);
                        panel1.setBounds(0,0,60,700);

                        JPanel panel2 = new JPanel();
                        panel2.setLayout(null);
                        panel2.setBackground(Color.getHSBColor(0.1f,0.2f,0.9f));
                        panel2.setBounds(60,0,340,700);

                        panel3.setLayout(null);
                        panel3.setBackground(Color.getHSBColor(0.6f,0.3f,1f));
                        panel3.setBounds(400,0,600,700);

                        JPanel panel5 = new JPanel();

                        JButton statutButton = new JButton();
                        JLabel label = new JLabel(pseudoTextt);
                        label.setFont(new Font("Serif", Font.BOLD,30));
                        label.setBounds(65,30,150,30);

                        JLabel labelConnect = new JLabel();
                        labelConnect.setText("Connected");
                        labelConnect.setFont(new Font("Serif", Font.ITALIC,15));
                        labelConnect.setBounds(65,62,100,15);

                        JButton messageButton = new JButton();
                        JButton friendButton = new JButton();
                        JButton statButton = new JButton();
                        JButton decoButton = new JButton();
                        JButton newMessButton = new JButton();
                        messageText.setBounds(70,630,490,35);
                        JButton statutFriend = new JButton();


                        messageArea.setBackground(Color.getHSBColor(0.6f,0.3f,1f));
                        messageArea.setBounds(10,80,580,550);
                        messageArea.setEditable(false);
                        messageArea.setLineWrap(true);
                        messageArea.setMargin(new Insets(5,5,5,5));


                        //JScrollPane scrollPane = new JScrollPane(messageArea);
                        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                        statutButton.setIcon(new ImageIcon("Images/profilConnected.png"));
                        statutButton.setBounds(15,25,40,40);
                        statutButton.setContentAreaFilled(false);
                        statutButton.setBorderPainted(false);
                        statutButton.setFocusPainted(false);

                        statutFriend.setIcon(new ImageIcon("Images/profil.png"));
                        statutFriend.setBounds(15,25,40,40);
                        statutFriend.setContentAreaFilled(false);
                        statutFriend.setBorderPainted(false);
                        statutFriend.setFocusPainted(false);

                        messageButton.setIcon(new ImageIcon("Images/discuter.png"));
                        messageButton.setBounds(15,25,30,30);
                        messageButton.setContentAreaFilled(false);
                        messageButton.setBorderPainted(false);
                        messageButton.setFocusPainted(false);

                        friendButton.setIcon(new ImageIcon("Images/friend.png"));
                        friendButton.setBounds(15,70,30,30);
                        friendButton.setContentAreaFilled(false);
                        friendButton.setBorderPainted(false);
                        friendButton.setFocusPainted(false);

                        statButton.setIcon(new ImageIcon("Images/stat.png"));
                        statButton.setBounds(15,115,30,30);
                        statButton.setContentAreaFilled(false);
                        statButton.setBorderPainted(false);
                        statButton.setFocusPainted(false);

                        decoButton.setIcon(new ImageIcon("Images/deco.png"));
                        decoButton.setBounds(15,620,30,30);
                        decoButton.setContentAreaFilled(false);
                        decoButton.setBorderPainted(false);
                        decoButton.setFocusPainted(false);

                        newMessButton.setIcon(new ImageIcon("Images/new.png"));
                        newMessButton.setBounds(290,30,30,30);
                        newMessButton.setContentAreaFilled(false);
                        newMessButton.setBorderPainted(false);
                        newMessButton.setFocusPainted(false);

                        sendButton.setIcon(new ImageIcon("Images/send.png"));
                        sendButton.setBounds(565,637,20,20);
                        sendButton.setContentAreaFilled(false);
                        sendButton.setBorderPainted(false);
                        sendButton.setFocusPainted(false);

                        pictureButton.setIcon(new ImageIcon("Images/picture.png"));
                        pictureButton.setBounds(10,637,20,20);
                        pictureButton.setContentAreaFilled(false);
                        pictureButton.setBorderPainted(false);
                        pictureButton.setFocusPainted(false);

                        microButton.setIcon(new ImageIcon("Images/micro.png"));
                        microButton.setBounds(40,637,20,20);
                        microButton.setContentAreaFilled(false);
                        microButton.setBorderPainted(false);
                        microButton.setFocusPainted(false);

                        panel1.add(messageButton);
                        panel1.add(friendButton);
                        panel1.add(statButton);
                        panel1.add(decoButton);

                        out.println("GetConv " + customer.getUsername());

                        String serverResponse3 = null;
                        try {
                            serverResponse3 = in.readLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (serverResponse3 != null) {
                            System.out.println("Received message from server: " + serverResponse3);
                            ServerContent = separateWords(serverResponse3);

                            if (Objects.equals(ServerContent.get(0), "getConvs")) {
                                customer.GetConvs(ServerContent);
                            }
                        }

                        for(i=0; i<customer.getUserinConversation().size(); i++) {
                            JButton buttonTest = new JButton();
                            buttonTest.setIcon(new ImageIcon("Images/profil.png"));
                            buttonTest.setBounds(15, y, 40, 40);
                            buttonTest.setContentAreaFilled(false);
                            buttonTest.setBorderPainted(false);
                            buttonTest.setFocusPainted(false);

                            JLabel labelTest = new JLabel(customer.getUserinConversation().get(i));
                            labelTest.setFont(new Font("Serif", Font.BOLD, 30));
                            labelTest.setBounds(65, y, 150, 30);

                            JLabel labelConnectTest = new JLabel();
                            labelConnectTest.setFont(new Font("Serif", Font.ITALIC, 15));
                            labelConnectTest.setBounds(65, y2, 100, 15);

                            if(customer.getIsConnected()){
                                labelConnectTest.setText("Connected");
                            }else{
                                labelConnectTest.setText("Disconnected");
                            }

                            panel2.add(buttonTest);
                            panel2.add(labelTest);
                            panel2.add(labelConnectTest);
                            panel2.revalidate();
                            panel2.repaint();
                            y += 70;
                            y2 += 70;
                            buttonTest1.add(buttonTest);
                        }






                        for (boucle = 0; boucle < buttonTest1.size(); boucle++) {
                            final JButton currentButton = buttonTest1.get(boucle);
                            currentButton.addMouseListener(new MouseAdapter() {
                                public void mouseClicked(MouseEvent a) {
                                    Point buttonLocationOnScreen = currentButton.getLocationOnScreen();
                                    int mousex = buttonLocationOnScreen.x + a.getX();
                                    int mousey = buttonLocationOnScreen.y + a.getY();
                                    k = (mousey - 150) / 70;
                                    System.out.println("Mouse clicked at x=" + mousex + ", y=" + mousey);
                                    System.out.println("K:"+k);
                                    customer.setSpeakingWith(customer.getUserinConversation().get(k));
                                    panel3.removeAll();
                                    affichage = true;

                                    System.out.println("Le K est toujours :"+k);
                                    System.out.println(mousex);

                                    JLabel labelFriend = new JLabel(customer.getUserinConversation().get(k));
                                    labelFriend.setFont(new Font("Serif", Font.BOLD,30));
                                    labelFriend.setBounds(65,30,150,30);

                                    startAffichageThread(); // appel de la méthode qui contient la boucle while

                                    panel3.add(messageText);
                                    panel3.add(sendButton);
                                    panel3.add(microButton);
                                    panel3.add(labelFriend);
                                    panel3.add(pictureButton);
                                    panel3.add(messageArea);

                                    panel3.revalidate();
                                    panel3.repaint();
                                }
                            });
                        }


                        panel2.add(statutButton);
                        panel2.add(label);
                        panel2.add(labelConnect);
                        panel2.add(newMessButton);

                        friendButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                friendPage.setTitle("Your friends");
                                friendPage.setSize(1000,700);

                                JPanel panelListFriend = new JPanel();
                                panelListFriend.setLayout(null);
                                panelListFriend.setBackground(Color.getHSBColor(0.1f,0.2f,0.9f));
                                panelListFriend.setBounds(60,0,340,700);
                                JLabel labelListFriend = new JLabel("List of your friends");
                                labelListFriend.setBounds(10,10,340,30);
                                panelListFriend.add(labelListFriend);

                                String message = customer.getUsername();
                                out.println("friendlist "+ message);
                                String serverResponse = null;
                                try {
                                    serverResponse = in.readLine();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if (serverResponse != null) {
                                    System.out.println("Received message from server: " + serverResponse);
                                    ServerContent = separateWords(serverResponse);
                                    if (Objects.equals(ServerContent.get(0), "friendListUpdate")) {
                                        customer.setInfoFriend(ServerContent);
                                        System.out.println("friendListUpdated");
                                    }
                                }

                                for(int i=0; i<customer.getListFriends().size(); i++){
                                    JLabel labelFriend1 = new JLabel(customer.getListFriends().get(i));
                                    labelFriend1.setBounds(20,b,200,30);
                                    labelFriend1.setFont(new Font("Serif", Font.BOLD,30));
                                    panelListFriend.add(labelFriend1);
                                    panelListFriend.revalidate();
                                    panelListFriend.repaint();
                                    panelListFriend.add(panel1);
                                    b += 70;
                                }

                                JPanel panelRequest = new JPanel();
                                panelRequest.setLayout(null);
                                panelRequest.setBackground(Color.getHSBColor(0.6f,0.3f,1f));
                                panelRequest.setBounds(400,0,600,350);
                                JLabel labelRequest = new JLabel("Request");
                                labelRequest.setBounds(10,10,600,30);
                                panelRequest.add(labelRequest);

                                String message1 = customer.getUsername();
                                out.println("requestlist "+ message1);

                                String serverResponse1 = null;
                                try {
                                    serverResponse1 = in.readLine();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if (serverResponse1 != null) {
                                    System.out.println("Received message from server: " + serverResponse1);
                                    ServerContent = separateWords(serverResponse1);
                                    if (Objects.equals(ServerContent.get(0), "friendRequest")) {
                                        customer.setInfoFriendlist(ServerContent);
                                        customer.displayInfoCustomer();
                                    }
                                }

                                for(int i=0; i<customer.getListRequest().size();i++){
                                    JLabel labelFriend = new JLabel(customer.getListRequest().get(i));
                                    labelFriend.setBounds(80,b1,200,30);
                                    labelFriend.setFont(new Font("Serif", Font.BOLD,30));

                                    JButton buttonAccepted = new JButton();
                                    JButton buttonRejected = new JButton();

                                    buttonAccepted.setIcon(new ImageIcon("Images/jaccepte.png"));
                                    buttonAccepted.setBounds(10,b1,32,32);
                                    buttonAccepted.setContentAreaFilled(false);
                                    buttonAccepted.setBorderPainted(false);
                                    buttonAccepted.setFocusPainted(false);

                                    buttonRejected.setIcon(new ImageIcon("Images/rejete.png"));
                                    buttonRejected.setBounds(45,b1,32,32);
                                    buttonRejected.setContentAreaFilled(false);
                                    buttonRejected.setBorderPainted(false);
                                    buttonRejected.setFocusPainted(false);

                                    panelRequest.add(buttonAccepted);
                                    panelRequest.add(buttonRejected);
                                    panelRequest.add(labelFriend);
                                    panelRequest.add(panelListFriend);
                                    panelRequest.revalidate();
                                    panelRequest.repaint();
                                    panelRequest.add(panel1);
                                    b1 += 70;
                                }


                                JPanel panelAdd = new JPanel();
                                panelAdd.setLayout(null);
                                panelAdd.setBackground(Color.getHSBColor(0.4f,0.3f,1f));
                                panelAdd.setBounds(400,350,600,350);
                                JLabel labelAdd = new JLabel("Add a friend : ");
                                labelAdd.setBounds(410,360,100,30);
                                JTextField textAdd = new JTextField(50);
                                textAdd.setBounds(500,360,420,30);
                                JButton buttonAdd = new JButton("ADD");
                                buttonAdd.setBounds(930,360,60,30);
                                panelAdd.add(buttonAdd);
                                panelAdd.add(textAdd);
                                panelAdd.add(labelAdd);

                                buttonAdd.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        String message3 = textAdd.getText();
                                        String message2 = customer.requestFrienshipGraphic(message3);
                                        out.println(message2);
                                        JLabel labelFriend = new JLabel(message2);
                                        labelFriend.setBounds(410,a1,100,30);
                                        labelFriend.setFont(new Font("Serif", Font.BOLD,30));
                                        JLabel labelAttente = new JLabel("En attente de réponse");
                                        labelAttente.setFont(new Font("Serif", Font.ITALIC,15));
                                        labelAttente.setBounds(510,a2,150,15);
                                        panelAdd.add(labelFriend);
                                        panelAdd.add(labelAttente);
                                        panelAdd.revalidate();
                                        panelAdd.repaint();
                                        panelAdd.add(panelListFriend);
                                        panelAdd.add(panelRequest);
                                        panelAdd.add(panel1);
                                        a1 += 70;
                                        a2 += 70;

                                    }
                                });

                                friendPage.add(panel1);
                                friendPage.add(panelListFriend);
                                friendPage.add(panelRequest);
                                friendPage.add(panelAdd);
                                friendPage.setVisible(true);
                                friendPage.setLocationRelativeTo(null);
                                friendPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            }
                        });

                        messageButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                messagePage.add(panel1);
                                messagePage.add(panel2);
                                messagePage.add(panel3);
                                messagePage.add(panel4);
                                messagePage.add(panel5);
                                messagePage.setVisible(true);
                            }
                        });

                        statButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                out.println("getUSERLVL " + customer.getUsername());
                                String serverResponse = "";
                                try {
                                    serverResponse = in.readLine();
                                }
                                catch (IOException ioException){}

                                System.out.println(serverResponse);

                                if (serverResponse.contains("ADMIN")){
                                    statPage.setTitle("Stat");
                                    JButton buttonCamembert = new JButton("Camembert");
                                    buttonCamembert.setBounds(200, 200, 200, 50);
                                    JButton buttonSeeStat = new JButton("buttonSeeStat");
                                    buttonSeeStat.setBounds(200, 500, 200, 50);
                                    JButton buttonSeeLog = new JButton("buttonSeeLog");
                                    buttonSeeLog.setBounds(700, 500, 200, 50);
                                    statPage.setSize(1000,700);
                                    JPanel panelStat = new JPanel();
                                    panelStat.setLayout(null);
                                    panelStat.setBackground(Color.getHSBColor(0.6f,0.3f,1f));
                                    panelStat.setBounds(60,0,940,700);
                                    panelStat.add(buttonCamembert);
                                    panelStat.add(buttonSeeStat);
                                    panelStat.add(buttonSeeLog);
                                    panelStat.setVisible(true);
                                    buttonCamembert.setVisible(true);
                                    buttonSeeStat.setVisible(true);
                                    buttonSeeLog.setVisible(true);
                                    statPage.add(panel1);
                                    statPage.add(panelStat);
                                    statPage.setVisible(true);
                                    statPage.setLocationRelativeTo(null);
                                    statPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    buttonCamembert.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            out.println("countLog ");
                                            String serverResponse = "";
                                            try {
                                                serverResponse = in.readLine();
                                            }
                                            catch (IOException ioException){}

                                            System.out.println(serverResponse);
                                            List<Integer> nbLog = new ArrayList<>();
                                            String number = "";

                                            for (int i = 0; i < serverResponse.length(); i++) {
                                                if (serverResponse.charAt(i) == ' '){
                                                    nbLog.add(Integer.parseInt(number));
                                                    number = "";
                                                }
                                                else {
                                                    number += serverResponse.charAt(i);
                                                }
                                            }

                                            System.out.println(nbLog);

                                            DefaultPieDataset dataset = new DefaultPieDataset();
                                            dataset.setValue("Connection Customer (" + nbLog.get(0) + ")", nbLog.get(0));
                                            dataset.setValue("Add Message(" + nbLog.get(1) + ")", nbLog.get(1));
                                            dataset.setValue("Ban User Request(" + nbLog.get(4) + ")", nbLog.get(4));
                                            dataset.setValue("Free User Request(" + nbLog.get(5) + ")", nbLog.get(5));
                                            dataset.setValue("See My Friends Online DAO(" + nbLog.get(3) + ")", nbLog.get(3));
                                            dataset.setValue("Friend Update(" + nbLog.get(2) + ")", nbLog.get(2));
                                            dataset.setValue("Be Online(" + nbLog.get(7) + ")", nbLog.get(7));
                                            dataset.setValue("Be Disconnect(" + nbLog.get(6) + ")", nbLog.get(6));

                                            JFreeChart chart = ChartFactory.createPieChart("Requests", dataset, true, true, false);

                                            PiePlot plot = (PiePlot) chart.getPlot();
                                            plot.setSectionOutlinesVisible(false);
                                            plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
                                            plot.setCircular(false);

                                            ChartFrame frame = new ChartFrame("Requests", chart);
                                            frame.setVisible(true);
                                            frame.setSize(500, 500);                                    }
                                    });


                                    buttonSeeStat.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            out.println("seeStat");
                                            String serverResponse = "";
                                            try {
                                                serverResponse = in.readLine();
                                            }
                                            catch (IOException ioException){}

                                            System.out.println(serverResponse);

                                            List<String> stats = new ArrayList<>();

                                            String stat = "";
                                            for (int i = 0; i < serverResponse.length(); i++) {
                                                if (serverResponse.charAt(i) == ' '){
                                                    stats.add(stat);
                                                    stat = "";
                                                }
                                                else {
                                                    stat += serverResponse.charAt(i);
                                                }
                                            }

                                            JTextArea textArea = new JTextArea();
                                            textArea.setEditable(false);

                                            for (String s:stats) {
                                                switch (s){
                                                    case "ADMIN":
                                                        System.out.println();
                                                        textArea.append("\n");
                                                        break;
                                                    case "MODO":
                                                        System.out.println();
                                                        textArea.append("\n");
                                                        break;
                                                    case "USER":
                                                        System.out.println();
                                                        textArea.append("\n");
                                                        break;
                                                    case "ONLINE":
                                                        System.out.println();
                                                        textArea.append("\n");
                                                        break;
                                                    case "DISCONNECT":
                                                        System.out.println();
                                                        textArea.append("\n");
                                                        break;
                                                    case "AWAY":
                                                        System.out.println();
                                                        textArea.append("\n");
                                                        break;
                                                    case "BAN":
                                                        System.out.println();
                                                        textArea.append("\n");
                                                        break;
                                                }
                                                textArea.append(s + "\n");
                                            }
                                            JScrollPane scrollPane = new JScrollPane(textArea);
                                            JFrame frame = new JFrame("Liste");
                                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                            frame.setBackground(Color.getHSBColor(0.6f,0.3f,1f));
                                            frame.add(scrollPane);
                                            frame.pack();
                                            frame.setVisible(true);


                                                                      }
                                    });

                                    buttonSeeLog.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            String message = customer.seeMyFriendsOnline();
                                            out.println("seeEveryLog " + message);                                            String serverResponse = "";
                                            try {
                                                serverResponse = in.readLine();
                                            }
                                            catch (IOException ioException){}

                                            System.out.println(serverResponse);
                                            List<String> logs = new ArrayList<>();

                                            String log = "";
                                            for (int i = 0; i < serverResponse.length(); i++) {
                                                if (serverResponse.charAt(i) == ' '){
                                                    logs.add(log);
                                                    log = "";
                                                }
                                                else {
                                                    log += serverResponse.charAt(i);
                                                }
                                            }



                                            JTextArea textArea = new JTextArea();
                                            textArea.setEditable(false);

                                            for (String element : logs) {
                                                textArea.append(element + "\n");
                                            }

                                            JScrollPane scrollPane = new JScrollPane(textArea);
                                            JFrame frame = new JFrame("Liste");
                                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                            frame.setBackground(Color.getHSBColor(0.6f,0.3f,1f));
                                            frame.add(scrollPane);
                                            frame.pack();
                                            frame.setVisible(true);
                                        }
                                    });
                                }
                                else {
                                    System.out.println("Vous n'êtes pas admin");
                                    JDialog dialogStat = new JDialog(homePage,"STATISTIQUE",true);
                                    dialogStat.setLayout(new BorderLayout());
                                    dialogStat.setPreferredSize(new Dimension(320,100));
                                    JLabel stati = new JLabel("You are not ADMIN !");
                                    stati.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                                    stati.setFont(new Font("Serif", Font.BOLD,30));
                                    dialogStat.add(stati,BorderLayout.CENTER);
                                    dialogStat.pack();
                                    dialogStat.setLocationRelativeTo(homePage);
                                    dialogStat.setVisible(true);
                                }

                            }


                        });



                        decoButton.addActionListener(new ActionListener() {
                                                         public void actionPerformed(ActionEvent a) {
                                                             friendPage.dispose();
                                                             statPage.dispose();
                                                             messagePage.dispose();
                                                             homePage.setVisible(true);
                                                         }
                                                     }
                        );

                        sendButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent a) {
                                String message = messageText.getText();
                                String message2 = customer.SendMessageGraph(message);
                                out.println(message2);
                                messageText.setText("");
                                startAffichageThread();
                            }
                        });

                        statutButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if (connexion==0){
                                    labelConnect.setText("Disconnect");
                                    statutButton.setIcon(new ImageIcon("Images/profilDisconnected.png"));
                                    connexion=1;
                                }
                                else if(connexion==1){
                                    labelConnect.setText("Away");
                                    statutButton.setIcon(new ImageIcon("Images/away.png"));
                                    connexion=2;
                                }else{
                                    labelConnect.setText("Connected");
                                    statutButton.setIcon(new ImageIcon("Images/profilConnected.png"));
                                    connexion=0;
                                }

                            }
                        });

                        newMessButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JDialog dialog = new JDialog(messagePage,"Name of a friend:",true);
                                dialog.setLayout(new BorderLayout());
                                dialog.setPreferredSize(new Dimension(200,80));
                                JTextField textField = new JTextField();
                                textField.setPreferredSize(new Dimension(200,50));
                                JButton buttonOK = new JButton("OK");
                                dialog.add(textField,BorderLayout.CENTER);
                                dialog.add(buttonOK,BorderLayout.SOUTH);
                                buttonOK.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        String text = textField.getText();
                                        customer.setSpeakingWith(text); // déplacer cette ligne ici
                                        dialog.dispose();
                                        JButton buttonTest = new JButton();
                                        buttonTest.setIcon(new ImageIcon("Images/profil.png"));
                                        buttonTest.setBounds(15,y,40,40);
                                        buttonTest.setContentAreaFilled(false);
                                        buttonTest.setBorderPainted(false);
                                        buttonTest.setFocusPainted(false);
                                        JLabel labelTest = new JLabel(text);
                                        labelTest.setFont(new Font("Serif", Font.BOLD,30));
                                        labelTest.setBounds(65,y,150,30);
                                        JLabel labelConnectTest = new JLabel("Connexion");
                                        labelConnectTest.setFont(new Font("Serif", Font.ITALIC,15));
                                        labelConnectTest.setBounds(65,y2,100,15);
                                        panel2.add(buttonTest);
                                        panel2.add(labelTest);
                                        panel2.add(labelConnectTest);
                                        panel2.revalidate();
                                        panel2.repaint();
                                        buttonTest1.add(buttonTest);
                                        y += 70;
                                        y2 += 70;
                                        buttonTest.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                panel3.removeAll();

                                                JLabel labelFriend = new JLabel();
                                                labelFriend.setFont(new Font("Serif", Font.BOLD,30));
                                                labelFriend.setBounds(65,30,150,30);
                                                labelFriend.setText(text);
                                                customer.setSpeakingWith(text);
                                                startAffichageThread();
                                                System.out.println(customer.getSpeakingWith());

                                                panel3.add(messageText);
                                                panel3.add(sendButton);
                                                panel3.add(microButton);
                                                panel3.add(labelFriend);
                                                panel3.add(pictureButton);
                                                panel3.add(messageArea);

                                                panel3.revalidate();
                                                panel3.repaint();
                                            }
                                        });
                                    }
                                });
                                dialog.pack();
                                dialog.setLocationRelativeTo(messagePage);
                                dialog.setVisible(true);
                            }
                        });



                        messagePage.add(panel1);
                        messagePage.add(panel2);
                        messagePage.add(panel3);
                        //messagePage.add(panel4);
                        messagePage.add(panel5);
                        messagePage.setLocationRelativeTo(null);
                        messagePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        messagePage.setLocation(0,0);
                        messagePage.setVisible(true);

                        /*
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
                    }*/
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

                if (userInput.equals("quit()")) {
                    System.out.println("Bye bye");
                    String message = customer.tryToBeDisconnect();
                    out.println(message);
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
                else if (userInput.equals("Conv()") && customer.getIsConnected()){
                    String message = customer.Conv();
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
                    out.println("seeMyFriendsOnline " + message);
                }
                else if (userInput.equals("seeEveryLog()") && customer.getIsConnected()){
                    String message = customer.seeMyFriendsOnline();
                    out.println("seeEveryLog " + message);
                }

                else if (userInput.equals("GraphMessage") && customer.getIsConnected()){
                    //String message = customer.SendMessageGraph();
                    //out.println(message);
                }
                else if (userInput.equals("GetConv") && customer.getIsConnected()){
                    out.println("GetConv " + customer.getUsername());
                }
                else if (userInput.equals("Writte message") && customer.getIsConnected()){
                    String message = customer.Conversation();;
                    out.println(message);
                }//seeEveryLog
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
                    if (Objects.equals(ServerContent.get(0), "Convs")) {
                        customer.YourConvs(ServerContent);
                        customer.displayInfoCustomer();
                    }
                    if (Objects.equals(ServerContent.get(0), "banned")) {
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
                    if (Objects.equals(ServerContent.get(0), "Conversation")) {
                        String ForServ = customer.YourConv(ServerContent);
                        out.println(ForServ);
                    }
                    if (Objects.equals(ServerContent.get(0), "getConvs")) {
                        customer.GetConvs(ServerContent);
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
                    if (Objects.equals(ServerContent.get(0), "seeEveryLog")) {
                        customer.setInfoFriend(ServerContent);
                        System.out.println("seeEveryLog");
                    }
                }

            }

            // Ferme les flux et la socket
            out.close();
            in.close();
            socket.close();
        } catch (IOException e){
                e.printStackTrace();

        }
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Graphique extends JFrame {
    public JFrame homePage = new JFrame();
    public JFrame createAccountPage = new JFrame();
    public JFrame messagePage = new JFrame();
    public void visualHomePage(){
        //BACKGROUND IMAGE
        /*ImageIcon img = new ImageIcon("src/background.PNG");
        Image image1 = img.getImage();
        Image image3 = image1.getScaledInstance(homePage.getWidth(), homePage.getHeight(),java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image3);
        homePage.setContentPane(new JLabel(icon));
        homePage.setOpacity(0.5f);*/

        homePage.setTitle("Home Page");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ImageIcon logo = new ImageIcon("src/Logo.JPG");
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
                                                  visualMessagePage();
                                                  homePage.dispose();

                                              }
                                          }
        );

        buttonCreateAccount.addActionListener(new ActionListener(){
                                                  public void actionPerformed(ActionEvent e){
                                                      visualCreateAccountPage();
                                                      homePage.dispose();
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
    }

    public void visualCreateAccountPage(){
        createAccountPage.setTitle("Create an account");

        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel textHome = new JLabel("Create an account !");
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
        JTextField passwordInput = new JTextField(12);
        JButton buttonCreateAccount1 = new JButton("Create an account");

        c.insets = new Insets(5,5,5,5);
        c.gridx=0; c.gridy=0;
        c.gridwidth = 2;
        c.anchor=GridBagConstraints.CENTER;
        panel.add(textHome,c);

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

        JButton home = new JButton("Home");
        panel.add(home);

        home.addActionListener(new ActionListener(){
                                   public void actionPerformed(ActionEvent a){
                                       visualHomePage();
                                       createAccountPage.dispose();
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

    public void visualMessagePage(){
        messagePage.setTitle("Your account");
        JPanel panel = new JPanel();
        JLabel textHome = new JLabel("Welcome to your account !");
        panel.add(textHome);

        JButton home = new JButton("Home");
        panel.add(home);
        home.addActionListener(new ActionListener(){
                                   public void actionPerformed(ActionEvent a){
                                       visualHomePage();
                                       messagePage.dispose();
                                   }
                               }
        );

        panel.setBackground(Color.getHSBColor(0.6f,0.3f,0.9f));
        messagePage.add(panel);
        messagePage.pack();
        messagePage.setSize(500, 500);
        messagePage.setLocationRelativeTo(null);
        messagePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        messagePage.setVisible(true);

    }
    public void background(){
        ImageIcon img = new ImageIcon("src/background.PNG");
        Image image = img.getImage();
        Image image1 = image.getScaledInstance(homePage.getWidth(), homePage.getHeight(),java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image1);
        homePage.setContentPane(new JLabel(icon));
        //setOpacity(0.5f);
    }
}


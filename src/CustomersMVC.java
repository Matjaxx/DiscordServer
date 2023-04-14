import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class CustomersMVC {
    //creer un getter de la cette list


    private List<String> listCustomer = new ArrayList<String>();
    private Controller controller = new Controller();
    private String Username;
    private String Password;
    private String Email;
    private String FirstName;
    private String LastName;
    private String permission;



    public List<String> getListCustomer() {
        return listCustomer;
    }

    public void setInfoCustomer(List<String> infoCustomer) {
        System.out.println("setInfoCustomer");
        Username = infoCustomer.get(2);
        FirstName = infoCustomer.get(3);
        LastName = infoCustomer.get(4);
        Password = infoCustomer.get(5);
        Email = infoCustomer.get(6);
        permission = infoCustomer.get(7);
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
            listCustomer.add(UserCharacteristic2);


            for (int i = 0; i < listCustomer.size(); i++) {
                System.out.println(listCustomer.get(i));
            }
        }


    }
}


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class CustomersMVC {
    //creer un getter de la cette list
    private List<String> listCustomer = new ArrayList<String>();
    private Controller controller = new Controller();

    public List<String> getListCustomer() {
        return listCustomer;
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
}


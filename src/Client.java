import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Client {
    private List<String> ServerContent = new ArrayList<String>();
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String host;
    private int port;

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
            while ((userInput = stdIn.readLine()) != null) {

                out.println(userInput);
                if (userInput.equals("'quit()'")) {
                    break;
                }
                if (userInput.equals("connection()")) {
                    customer.connection();
                    infoCustomers = customer.getListCustomer();
                    String convertListToString = "";
                    for (int i = 0; i < customer.getListCustomer().size(); i++) {
                        convertListToString += customer.getListCustomer().get(i)+" ";
                    }

                    out.println(convertListToString);

                }

                // Attend et affiche la réponse du serveur
                String serverResponse = in.readLine();
                if (serverResponse != null) {
                    System.out.println("Received message from server: " + serverResponse);
                    ServerContent = separateWords(serverResponse);
                    if (Objects.equals(ServerContent.get(1), "connected")) {
                        customer.setInfoCustomer(ServerContent);
                        customer.displayInfoCustomer();
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void execute() {
        try {
            // Se connecte au serveur
            socket = new Socket(host, port);

            // Initialise les flux d'entrée/sortie
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Boucle pour lire l'entrée utilisateur et l'envoyer au serveur
            String userInput;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                if (userInput.equals("quit()")) {
                    break;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SocketServer {
    private ServerSocket serverSocket;

    public SocketServer() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    public void execute() throws IOException {
        System.out.println("Server is running and listening on port 1234...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected");

            Thread t = new Thread(new ClientHandler(clientSocket));
            t.start();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public static List<String> separateWords(String inputLine) {
            String[] wordsArray = inputLine.split("\\s+");
            List<String> wordsList = Arrays.asList(wordsArray);

            for (int i = 0; i < wordsList.size(); i++) {
                System.out.println(wordsList.get(i));
            }
            return wordsList;
        }

        public void run() {
            try {
                CustomersDAO customersDAO = new CustomersDAO();
                // Initialise les flux d'entrée/sortie

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                List<String> listOrder = new ArrayList<String>();
                // Boucle pour lire les messages du client
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received message from client: " + inputLine);


                    listOrder = separateWords(inputLine);
                    inputLine = listOrder.get(0);

                    if (inputLine.equals("createAccount")) {
                        System.out.println("test");
                        out.println("le client recoit un message");
                        customersDAO.ServerJBDCConnection(listOrder);
                    }
                    if (inputLine.equals("connectionAccount")) {
                        System.out.println("test2");
                        customersDAO.ServerJBDCConnection(listOrder);
                        if (customersDAO.getVerifConnection()){
                            System.out.println("success");
                            out.println("success");
                        }
                        else {
                            System.out.println("conenction failed : please retry");
                        }


                    }
                    if (inputLine.equals("sendToClient")) {
                        System.out.println("Enter the message to send to the client:");
                        String messageToSend = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        out.println(messageToSend);
                    }
                    if (inputLine.equals("quit()")) {
                        break;
                    }
                }

                // Ferme les flux et la socket
                out.close();
                in.close();
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

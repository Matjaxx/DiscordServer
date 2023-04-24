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

    private boolean friendManger = false;

    public SocketServer() throws IOException {
        serverSocket = new ServerSocket(1997);
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

                // Boucle pour lire les messages du client
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received message from client: " + inputLine);
                    List<String> listOrder = new ArrayList<String>();
                    listOrder = separateWords(inputLine);
                    inputLine = listOrder.get(0);


                    if (inputLine.equals("createAccount")) {
                        System.out.println("test");
                        customersDAO.ServerJBDCConnection(listOrder);
                    }
                    else if (inputLine.equals("connectionAccount")) {
                        System.out.println("test2");
                        customersDAO.ServerJBDCConnection(listOrder);
                        if (customersDAO.getVerifConnection()){
                            System.out.println("success");
                            out.println(customersDAO.getAnswerServer());
                        }
                        else if (customersDAO.getBanned()){
                            System.out.println("You have been banned from the server");
                            out.println("banned");
                        }
                        else{
                            System.out.println("conenction failed : please retry");
                            out.println("Connection failed: please retry.");
                        }
                    }
                    else if (inputLine.equals("requestFriendship")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println("Friendship request sent");
                    }
                    else if (inputLine.equals("requestlist")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("friendlist")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("denyfriendrequest")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println("Friendship request has been denied");
                    }
                    else if (inputLine.equals("acceptfriendrequest")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println("Friendship request has been accepted");
                    }
                    else if (inputLine.equals("conversation")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("GetConv")) {
                        System.out.println("MLMLMLML");
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("Convs")) {
                        System.out.println("testtt");
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("SendMessageGraph")) {
                        System.out.println("MLMLMLML");
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("SendMessage")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("SendM")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.flush();
                        //out.println("Message sent");
                    }
                    else if (inputLine.equals("banUser")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println("You are going to ban someone");
                    }
                    else if (inputLine.equals("freeUser")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println("You are going to free someone");
                    }
                    else if (inputLine.equals("beOnline")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println("You are going to be online");
                    }
                    else if (inputLine.equals("beDisconnect")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println("You are going to be disconnect");
                    }
                    else if (inputLine.equals("seeMyFriendsOnline")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("seeEveryLog")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("countLog")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("getUSERLVL")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("seeStat")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("seeMyFriendsOnlineDAO2")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("viewlistFree")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("viewlistBan")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("modo")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("admin")) {
                        customersDAO.ServerJBDCConnection(listOrder);
                        out.println(customersDAO.getAnswerServer());
                    }
                    else if (inputLine.equals("quit()")) {
                        break;
                    }
                    else {
                        out.println("Command not found");
                    }
                }

                // Ferme les flux et la socket

                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

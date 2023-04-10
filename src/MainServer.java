import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        SocketServer socketserver = new SocketServer();
        socketserver.execute();
    }
}

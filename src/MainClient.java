import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("192.168.254.1", 1234);
        client.execute();
    }
}

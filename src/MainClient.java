import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("192.168.15.164", 1299);
        client.execute();
    }
}

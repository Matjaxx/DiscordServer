import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("172.20.10.3", 1666);
        client.execute();
    }
}

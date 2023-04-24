import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("192.168.1.15", 1324);
        client.execute();
    }
}

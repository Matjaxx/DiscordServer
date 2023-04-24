import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("10.63.19.123", 1324);
        client.execute();
    }
}

import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("10.63.16.153", 1100);
        client.execute();
    }
}

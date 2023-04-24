import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("10.63.20.18", 1995);
        client.execute();
    }
}

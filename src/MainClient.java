import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("86.215.36.144", 1234);
        client.execute();
    }
}

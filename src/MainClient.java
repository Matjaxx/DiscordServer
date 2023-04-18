import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("10.63.17.93", 1234);
        client.execute();
    }
}

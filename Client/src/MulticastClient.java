import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MulticastClient {
    private static int clientID;
    private static ClientThreadSending clientSending;
    private static ClientThreadReceiving clientReceiving;

    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        clientID = rand.nextInt(10000);

        clientSending = new ClientThreadSending(clientID);
        clientSending.start();

        clientReceiving = new ClientThreadReceiving(clientID);
        clientReceiving.start();
    }
}
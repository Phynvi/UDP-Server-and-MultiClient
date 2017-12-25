import java.io.*;
import java.util.ArrayList;

public class MulticastServer {
    static ArrayList<String> received;

    public static void main(String[] args) throws IOException {
        received = new ArrayList<>();
        new ServerThreadSending(received).start();
        new ServerThreadReceiving(received).start();
    }
}
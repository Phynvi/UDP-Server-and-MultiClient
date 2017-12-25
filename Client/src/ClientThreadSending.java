import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;


public class ClientThreadSending extends Thread {

    private final long FIVE_SECONDS = 5000;
    private DatagramSocket socket = null;
    private final int clientID;
    public static final String CLIENT_SIG = new String("~CLIENT");
    private InetAddress group;

    public ClientThreadSending(int clientID) throws IOException {
        this.clientID = clientID;
        socket = new DatagramSocket();
    }


    public void run() {
        while (!ServerStats.isConnected)
        {
            connect();
        }
        while (ServerStats.isConnected) {
            sendData("Client message!");
        }
    }

    private void connect() {
        try {
            group = InetAddress.getByName("230.0.0.1");

            sendData("Connection Request*" + clientID);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void sendData(String dString) {
        try {
            dString += CLIENT_SIG;
            byte[] buf = dString.getBytes();

            // send it
            DatagramPacket packet = new DatagramPacket(buf, buf.length,
                    group, 4445);
            System.out.println("Sending: " + dString);
            socket.send(packet);

            // sleep for a while
            try {
                sleep(FIVE_SECONDS);
            } catch (InterruptedException e) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
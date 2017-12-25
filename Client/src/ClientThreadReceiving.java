import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class ClientThreadReceiving extends Thread {

    private final int clientID;

    public ClientThreadReceiving(int clientID) {
        this.clientID = clientID;
    }

    public void run() {
        MulticastSocket socket;

        try {
            socket = new MulticastSocket(4445);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            socket.joinGroup(group);
            String received;

            DatagramPacket packet;
            while (true) {
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                received = new String(packet.getData(), packet.getOffset(), packet.getLength());
                if (received.contains("~SERVER"))
                {
                    received = received.split("~")[0];
                    handle(received);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handle(String received) {
        if (received.equals("Connection established" + clientID))
        {
            ServerStats.isConnected = true;
            System.out.println("Connection established!");
        }
        else
        {
            System.out.println("Garbage: " + received);
        }
    }
}

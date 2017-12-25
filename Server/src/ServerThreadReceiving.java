import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class ServerThreadReceiving extends Thread {

    ArrayList<String> receivedQueue;

    public ServerThreadReceiving(ArrayList<String> received) {
        receivedQueue = received;
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
                    //Discard
                    continue;
                }
                received = received.split("~")[0];

                handle(received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handle(String received) {
        if (received.contains("Connection Request"))
        {
            receivedQueue.add("Connection established" + received.split("\\*")[1]); //Connection accepted
        }
        else
        {
            System.out.println("Garbage: " + received);
        }
    }
}

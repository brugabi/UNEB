import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This program demonstrates how to implement a UDP client program.
 *
 *
 * @author www.codejava.net
 */

public class QuoteClient {
    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 17;

        try {
            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();

            while (true) {

                DatagramPacket request = new DatagramPacket(new byte[1], 1 , address, port);
                socket.send(request);

                byte[] buffer = new byte[512];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);

                String quote = new String(buffer, 0, request.getLength());

                System.out.println(quote);
                System.out.println();

                Thread.sleep(10000);
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

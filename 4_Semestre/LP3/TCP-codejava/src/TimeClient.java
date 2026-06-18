import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This program demonstrates a simple TCP/IP socket client.
 *
 * @author www.codejava.net
 */

public class TimeClient {

    public static void main(String[] args) {
        int port = 12345;

        try(Socket socket = new Socket("localhost", port)) {

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String time = reader.readLine();

            System.out.println(time);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

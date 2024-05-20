import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Q3Cliente {

    public static void main(String[] args) {
        int port = 12345;

        try {
            Socket socket = new Socket("localhost", port);

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String msg;
            Scanner scanner = new Scanner(System.in);

            do {
                System.out.println("Digite a palavra: ");
                msg = scanner.nextLine();
                writer.println(msg);

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String td = reader.readLine();
                String palavra = td.split(",")[0];
                String snf = td.split(",")[1];

                System.out.println(palavra + " " + snf );
            }
            while (!msg.equals("quit"));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

import java.io.*;
import java.net.Socket;

public class WhoisClient {

    public static void main(String[] args) {
        if (args.length < 1) return;

        String domainName = args[0];

        String hostname = "whois.internic.net";
        int port = 43;

        try {
            Socket socket = new Socket(hostname,port);
            //outputstream serve para MANDAR mensagem para o servidor
            OutputStream output = socket.getOutputStream();
            //writer serve para escrever a mensagem que sera mandada
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(domainName);

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

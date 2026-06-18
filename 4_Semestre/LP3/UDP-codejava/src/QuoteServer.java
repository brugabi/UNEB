import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuoteServer {
    private DatagramSocket socket;
    private List<String> listQuotes = new ArrayList<String>();
    private Random random;

    public QuoteServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
        random = new Random();
    }

    public static void main(String[] args) {
        String qtfile = "Quotes.txt";
        int port = 17;

        try {
            QuoteServer server = new QuoteServer(port);
            server.loadQuotesFromFile(qtfile);
            server.service();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void service () throws IOException{
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1], 1);
            socket.receive(request);

            String quote = getRandomQuote();
            byte [] buffer = quote.getBytes();

            InetAddress clientAdress = request.getAddress();
            int clientPort = request.getPort();

            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAdress, clientPort);
            socket.send(response);
        }
    }

    private void loadQuotesFromFile (String qtfile) throws IOException{
        BufferedReader reader= new BufferedReader(new FileReader(qtfile));
        String aQuote;

        while ((aQuote = reader.readLine()) != null) {
            listQuotes.add(aQuote);
        }
        reader.close();
    }
    private String getRandomQuote () {
        int randomIndex = random.nextInt(listQuotes.size());
        String randomQuote = listQuotes.get(randomIndex);

        return randomQuote;
    }
}

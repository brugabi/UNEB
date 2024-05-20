import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class DaytimeClient {
    public static void main(String[] args) {
        String hostname = "time.nist.gov";
        int port = 13;
        try {

            Socket socket = new Socket(hostname, port);
            //input stream RECEBE os dados do servidor
            InputStream input = socket.getInputStream();

            //o stream reader e buffered reader permite ler dados a um maior nivel, string ou caracter.
            InputStreamReader reader = new InputStreamReader(input);

            int character;
            StringBuilder data = new StringBuilder();

            while ((character = reader.read()) != -1){
                data.append((char) character);
            }
            System.out.println(data);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

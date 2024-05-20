import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Q3Server {
    public ArrayList<Q3Dict> dicionario = new ArrayList<>();

    public ArrayList<Q3Dict> getDicionario() {
        return dicionario;
    }

    public void setDicionario(ArrayList<Q3Dict> dicionario) {
        this.dicionario = dicionario;
    }

    public void addPalavra (Q3Dict palavra) {
        this.dicionario.add(palavra);
    }

    public String buscar (String nome) {

        for (Q3Dict palvara : dicionario) {
            if (nome.equals(palvara.getPalavra())) {
                return palvara.getSignificado();
            }
        }
        return "Palavra nao encontrada";
    }

    public static void main(String[] args) {

        Q3Dict alysson = new Q3Dict("alysson", "puta");
        Q3Server dict = new Q3Server();
        dict.addPalavra(alysson);
        dict.addPalavra(new Q3Dict("vitinho", "esquizofrenico"));
        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Servidor escutando na porta: " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String text;

                do {

                    text = reader.readLine();
                    String significado = dict.buscar(text);

                    writer.println("Palavra: " + text +    ",Significado: " + significado);

                } while (!text.equals("quit"));
                socket.close();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

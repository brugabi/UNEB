import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class Cliente  {
    public static void run() {
    	try {
            Socket  socket = new Socket("localhost",4444);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fromServer = "";
            String comando ="Inicio";
            String comandoAnterior ="";
            boolean chave = true;
            fromServer = in.readLine();
            out.println(Console.readLine("Tecle Enter"));  
            fromServer = in.readLine();
            System.out.println(fromServer);
            out.flush();
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException {          
         Cliente.run();
    }
}
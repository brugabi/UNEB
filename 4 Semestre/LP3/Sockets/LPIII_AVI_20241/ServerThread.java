import java.net.*;
import java.io.*;
import java.applet.*;
import java.awt.*;

public class ServerThread extends Thread {
	
	private Socket socket;
	private ServerM serverM;
	
	public ServerThread(Socket socket_,ServerM serverM_) {
		this.socket = socket_;
        this.serverM = serverM_;
    }
    
	public void run(){
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine ="";
			out.println("Inicio");
			inputLine = in.readLine();
			out.println("Ok");
			System.out.println(this.serverM.getAviao().getListaCadeira());
            out.flush();
			out.close();
	        in.close();
	        socket.close();
		} catch (Exception e1) {
			System.out.println(e1);
		}
	}
}

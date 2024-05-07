import java.net.*;
import java.io.*;
import java.util.*;

public class ServerM {
	
	private Aviao aviao = new Aviao();
	
	public Aviao getAviao(){
		return this.aviao;
	}
	
	public  void run(){
		boolean chave=true;
		try {
			ServerSocket serverSocket = new ServerSocket(4444);
			this.aviao.criaListaCadeiras(10);
			while (chave){
				Socket socket = serverSocket.accept();
				ServerThread  serverThread = new ServerThread(socket,this);
				serverThread.start();
			}
			serverSocket.close();
			
		} catch (Exception e) {
			System.err.println(e);
			System.exit(-1);
		}
    }
	
    public static void main(String[] args) throws IOException {
    	System.out.println("Servidor...Ok");
    	ServerM s = new ServerM();
    	s.run();
     }
}

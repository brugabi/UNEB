import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    public static void main(String[] args) throws SocketException {
        int num1, num2;
        String operacao;
        try {
            DatagramSocket server = new DatagramSocket(12345);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            System.out.println("Server UDP aberto");
            while(true) {
                DatagramPacket receiver = new DatagramPacket(receiveData, receiveData.length);
                server.receive(receiver);
                InetAddress enderecoCliente = receiver.getAddress();
                int portaCliente = receiver.getPort();
                sendData = "Escolha opcao".getBytes();
                DatagramPacket enviar = new DatagramPacket(sendData, sendData.length, enderecoCliente, portaCliente);
                server.send(enviar);



            }
        }
        catch (Exception e){
            System.out.println("Erro" + e.getMessage());
        }
    }
}
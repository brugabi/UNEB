import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
    public static void main(String[] args) {
        try {
            DatagramSocket cliente = new DatagramSocket();
            InetAddress serverIPAddress = InetAddress.getByName("localhost");
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            DatagramPacket recebePacote = new DatagramPacket(receiveData, receiveData.length,serverIPAddress,12345);
            cliente.receive(recebePacote);
            String msgRecebida = new String(recebePacote.getData());
            System.out.println("Msg do server " + msgRecebida);
        }
        catch (Exception e){
            System.out.println("Erro" + e.getMessage());
        }
    }
}

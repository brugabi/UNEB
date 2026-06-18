package Threads;

public class Main {
    public static void main(String[] args) {
        Cliente cliente01 = new Cliente("localhost",12345);
        Server servidor = new Server(12345);
        servidor.start();
        cliente01.connect();

    }
}

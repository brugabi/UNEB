package roteiro3.parte2;

public class TesteNotificacao {

    public static void main(String[] args) {

        Newsletter newsletter = new Newsletter();

        Cliente cli01 = new Cliente("Cliente Jose", "jose@empresa.com");
        Cliente cli02 = new Cliente("Cliente Maria", "maria@empresa.com");

        newsletter.adicionarObserver(cli01);
        newsletter.adicionarObserver(cli02);

        newsletter.enviarMensagem("Oferta da Semana: 50% de desconto!");

    }
}
package roteiro3.parte4;

public class NotificacaoSMS implements NotificacaoStrategy{

    @Override
    public void enviarMensagem(String destinatario, String mensagem) {
        System.out.println("------------------------------------------------------ ");
        System.out.println("SMS enviado para " + destinatario + ": \n" + mensagem);
    }
}

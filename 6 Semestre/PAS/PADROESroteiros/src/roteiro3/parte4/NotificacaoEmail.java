package roteiro3.parte4;

public class NotificacaoEmail implements NotificacaoStrategy{
    @Override
    public void enviarMensagem(String destinatario, String mensagem) {
        System.out.println("------------------------------------------------------ ");
        System.out.println("Email enviado para " + destinatario + ": \n" + mensagem);
    }
}

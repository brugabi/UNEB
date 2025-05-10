package roteiro5.parte1;

public class NotificacaoService {
    public void enviarNotificacaoEmail(String mensagem) {
        System.out.println("Enviando e-mail: " + mensagem);
    }

    public void enviarNotificacaoSMS(String mensagem) {
        System.out.println("Enviando SMS: " + mensagem);
    }

    public void enviarNotificacaoPush(String mensagem) {
        System.out.println("Enviando Push Notification: " + mensagem);
    }
}

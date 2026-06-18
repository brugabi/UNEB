package roteiro5.parte4;

public class TesteNotificacao {
    public static void main(String[] args) {
        Notificacao email = new NotificacaoEmail();
        Notificacao sms = new NotificacaoSMS();
        Notificacao push = new NotificacaoPush();
        Notificacao whatsApp = new NotificacaoWhatsApp();

        email.enviar("Bem-vindo ao sistema!");
        sms.enviar("Seu código de verificação é 1234.");
        push.enviar("Você tem uma nova mensagem.");
        whatsApp.enviar("Seu código de verificação é 1234.");
    }
}

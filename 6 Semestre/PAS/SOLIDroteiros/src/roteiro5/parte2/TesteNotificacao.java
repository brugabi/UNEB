package roteiro5.parte2;

public class TesteNotificacao {
    public static void main(String[] args) {
        Notificacao email = new NotificacaoEmail();
        Notificacao sms = new NotificacaoSMS();
        Notificacao push = new NotificacaoPush();

        NotificacaoService service1 = new NotificacaoService(email);
        NotificacaoService service2 = new NotificacaoService(sms);
        NotificacaoService service3 = new NotificacaoService(push);

        service1.enviarNotificacao("Bem-vindo ao sistema!");
        service2.enviarNotificacao("Seu código de verificação é 1234.");
        service3.enviarNotificacao("Você tem uma nova mensagem.");
    }
}

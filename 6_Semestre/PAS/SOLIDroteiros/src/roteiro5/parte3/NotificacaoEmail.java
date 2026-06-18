package roteiro5.parte3;

public class NotificacaoEmail implements Notificacao {

 @Override
    public void enviar(String mensagem)
    {
        System.out.println("Enviando e-mail: " + mensagem);
    }
}

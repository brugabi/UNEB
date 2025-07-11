package roteiro3.parte2;

import java.util.ArrayList;

public class Newsletter {

    private ArrayList<Observer> observers;

    public Newsletter() {
        this.observers = new ArrayList<>();
    }

    public void adicionarObserver(Observer observer) {
        observers.add(observer);
    }

    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }

    public void enviarMensagem(String mensagem) {
        for (Observer observers : observers) {
            observers.update(mensagem);
        }
    }
}

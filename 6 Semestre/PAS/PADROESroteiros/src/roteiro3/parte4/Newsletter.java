package roteiro3.parte4;

import java.util.ArrayList;
import java.util.List;

public class Newsletter {

    private List<Observer> observers;

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
        // A variável do laço foi corrigida de "observers" para "observer"
        for (Observer observer : this.observers) {
            observer.update(mensagem);
        }
    }
}
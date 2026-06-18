package roteiro3.parte5;

import java.util.ArrayList;
import java.util.List;

public abstract class Sensor {

    private List<Observer> observers = new ArrayList<>();
    protected double valor;
    protected String nome;

    public Sensor(String nome) {
        this.nome = nome;
    }

    public void registrarObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removerObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notificarObservers() {

        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }

    public void setValor(double valor) {
        this.valor = valor;
        System.out.println("\n-> " + this.nome + " registrou nova leitura: " + this.valor + getUnidade());
        notificarObservers();
    }

    public double getValor() {
        return this.valor;
    }

    public String getNome() {
        return this.nome;
    }

    public abstract String getUnidade();
}

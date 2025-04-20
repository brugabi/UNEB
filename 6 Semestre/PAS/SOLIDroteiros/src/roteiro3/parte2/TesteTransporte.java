package roteiro3.parte2;

public class TesteTransporte {
    public static void main(String[] args) {
        VeiculoTransporte veiculo1 = new Onibus(5.00);
        VeiculoTransporte veiculo2 = new Bicicleta();

        System.out.println("Tarifa do ônibus: R$ " + veiculo1.calcularTarifa());
        System.out.println("Tarifa da bicicleta: R$ " + veiculo2.calcularTarifa());

    }
}

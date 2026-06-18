package roteiro3.parte3;

public class TesteTransporte {
    public static void main(String[] args) {
        VeiculoTransporte veiculo1 = new Onibus(5.00,10.00);
        VeiculoTransporte veiculo2 = new Bicicleta(10.00);

        System.out.println("Tarifa do ônibus: R$ " + veiculo1.calcularTarifa());
        System.out.println("Distancia Onibus: " + veiculo1.getDistancia());
        System.out.println("Consumo Total Onibus: " + veiculo1.calcularTotalCombustivel(12));

        System.out.println("Tarifa da bicicleta: R$ " + veiculo2.calcularTarifa());
        System.out.println("Distancia Bicicleta: " + veiculo2.getDistancia());
        System.out.println("Consumo Total Bicicleta: " + veiculo2.calcularTotalCombustivel(12));

    }
}

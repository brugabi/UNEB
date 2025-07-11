package roteiro4.parte2;

public class Teste {
    public static void main(String[] args) {

        Arma pistola = new Pistola();
        Atirador atirador01 = new Atirador("Sniper Joaquim", pistola);

        System.out.println("\n" + atirador01.getNome() + " brincando com a Pistola");
        atirador01.carregarArma();
        atirador01.fazerMira();
        atirador01.usarArma();

        // ******************************************************
        Arma fuzil = new Fuzil();
        atirador01.setArma(fuzil);

        System.out.println("\n" + atirador01.getNome() + " brincando com o Fuzil");
        atirador01.carregarArma();
        atirador01.fazerMira();
        atirador01.usarArma();

        // ******************************************************
        Arma rifle = new Rifle();
        atirador01.setArma(rifle);

        System.out.println("\n" + atirador01.getNome() + " brincando com o Rifle");
        atirador01.carregarArma();
        atirador01.fazerMira();
        atirador01.usarArma();

        // ******************************************************
        Arma shotgun = new ShotGunAdapter();
        atirador01.setArma(shotgun);

        System.out.println("\n" + atirador01.getNome() + " brincando com a ShotGun");
        atirador01.carregarArma();
        atirador01.fazerMira();
        atirador01.usarArma();


    }
}

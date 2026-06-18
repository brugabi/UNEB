package roteiro1.parte3;

public class TesteJogo {

    public static void main(String[] args) {
        System.out.println("Detalhes do Jogador de Tenis");
        TennisPlayer guga = new TennisPlayer("Gustavo Kuerten");
        guga.treinar();
        guga.estiloCompetidor();
        guga.definirTatica();
        guga.correr();
        System.out.println("******************");

        System.out.println("Detalhes do Jogador de Futebol");
        SoccerPlayer ronaldo = new SoccerPlayer("Ronaldinho Gaucho");
        ronaldo.treinar();
        ronaldo.estiloCompetidor();
        ronaldo.definirTatica();
        ronaldo.correr();
        System.out.println("******************");

        System.out.println("Detalhes do Jogador de Cartas");
        CardPlayer joseCartas = new CardPlayer("Jose das Cartas");
        joseCartas.treinar();
        joseCartas.estiloCompetidor();
        joseCartas.definirTatica();

        System.out.println("******************");

        System.out.println("Detalhes do Jogador de Xadrez");
        ChessPlayer kasparov = new ChessPlayer("Kasparov");
        kasparov.treinar();
        kasparov.estiloCompetidor();
        kasparov.definirTatica();

        System.out.println("******************");

        System.out.println("Detalhes do Jogador de Golf");
        GolfPlayer tigerwoods = new GolfPlayer("Tiger Woods");
        tigerwoods.treinar();
        tigerwoods.estiloCompetidor();
        tigerwoods.definirTatica();

        System.out.println("******************");
    }
}

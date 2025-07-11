package roteiro1.parte4;

public class TesteJogo {

    public static void main(String[] args) {

        RunFast correRapido = new RunFast();
        RunNoWay naoCorre = new RunNoWay();

        System.out.println("Detalhes do Jogador de Tenis");
        TennisPlayer guga = new TennisPlayer("Gustavo Kuerten", correRapido);
        guga.treinar();
        guga.estiloCompetidor();
        guga.definirTatica();
        guga.correr();
        System.out.println("******************");

        System.out.println("Detalhes do Jogador de Futebol");
        SoccerPlayer ronaldo = new SoccerPlayer("Ronaldinho Gaucho", correRapido);
        ronaldo.treinar();
        ronaldo.estiloCompetidor();
        ronaldo.definirTatica();
        ronaldo.correr();
        System.out.println("******************");

        System.out.println("Detalhes do Jogador de Cartas");
        CardPlayer joseCartas = new CardPlayer("Jose das Cartas", naoCorre);
        joseCartas.treinar();
        joseCartas.estiloCompetidor();
        joseCartas.definirTatica();
        joseCartas.correr();
        System.out.println("******************");

        System.out.println("Detalhes do Jogador de Xadrez");
        ChessPlayer kasparov = new ChessPlayer("Kasparov", naoCorre);
        kasparov.treinar();
        kasparov.estiloCompetidor();
        kasparov.definirTatica();
        kasparov.correr();
        System.out.println("******************");

        System.out.println("Detalhes do Jogador de Golf");
        GolfPlayer tigerwoods = new GolfPlayer("Tiger Woods", naoCorre);
        tigerwoods.treinar();
        tigerwoods.estiloCompetidor();
        tigerwoods.definirTatica();
        tigerwoods.correr();
        System.out.println("******************");
    }
}

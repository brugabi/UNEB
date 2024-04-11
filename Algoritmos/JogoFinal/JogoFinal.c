#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <locale.h>
#define LINHA 50
#define COL 3

int tab[LINHA][COL] = {0};// zera a matriz
int pos1, pos2;
int esp [] = {6, 13 ,17 ,20 ,24 ,28 ,35 ,38 ,41 ,44}; //  declarando um vetor com as casas especiais


menssagemInicio(){
char frase[]={"ESTA PREPARADO PARA A DIVERSÃO??!!"};
int tFrase=strlen(frase);// atribui a tFrase o tamanho do vetor frase

    for (int i=0; i<=tFrase; i++)
    {
        Sleep(5); // Sleep 0,5 segundos
        printf("%c", frase[i]);

    }
}

menssagemRegras(){
char frase[]={"DESEJA JOGAR??"};
int tFrase=strlen(frase);

    for (int i=0; i<=tFrase; i++)
    {
        Sleep(5); // Sleep 0,5 segundos
        printf("%c", frase[i]);

    }
}

menssagemHistoria(){
char frase[]={"ANTES DO TORNEIO DICK VIGARISTA, NOVAMENTE TRAMOU UMA TRAPAÇA, FUROU OS PNEUS E SABOTOU O MOTOR DE TODOS OS SEUS COMPETIDORES,\n DEIXANDO APENAS A PENELOPE DE FORA PORQUE ELA NÃO ESTAVA NO LOCAL DURANTE ESSE ATO DE VIGARICE, \n POIS A COITADA ESTAVA NOVAMENTE TENTANDO SE LIVRAR DAS FALCATRUAS DO TIÃO GAVIÃO, QUE QUER ROUBAR SUAS RIQUEZAS, E SEUS CAPANGAS OS IRMÃOS BACALHAU\n  ASSIM, DURANTE A LARGADA ELE SAIU NA FRENTE COM VANTAGEM  E SOMENTE COM A PENELOPE DE COMPETIDORA\n CABE A PENELOPE CHARMOSA ULTRAPASSA-LO E IMPEDIR QUE ELE GANHE DESSA FORMA HORRENDA E ASSIM FAZER COM QUE A JUSTIÇA SEJA FEITA\n\n"};
int tFrase=strlen(frase);

    for (int i=0; i<=tFrase; i++)
    {
        Sleep(5); // Sleep 0,5 segundos
        printf("%c", frase[i]);

    }
}

tituloMenu(){


}


arteMenu(){//imagem do menu
printf("                                                             ..:^^^^^:^^^^:::::.                        \n");
printf("                                                        :!?Y5YJ?7!^^:^^^^^:::::.                        \n");
printf("                                                    ^7PGG5?!~^::..~~^::.                                \n");
printf("                                                 ^JB&BY!^:........~77?77!~^.                            \n");
printf("                                              :?B@#Y!^::::::::::~7!!~:..         ..:^^.    7!           \n");
printf("                                            ~P@@@@P55YYJ?7~^.  7^            .^~^::.^J!?JPG~J7          \n");
printf("                                          !B@B5J!~^^^^^^~!7JY5YY?!.        .!~:    ^~?.P@@@G~!.~:       \n");
printf("                                        ~PP7:...             .:!YB#555?:..:?.       7? .5G777GB7J.      \n");
printf("                                ..:.  .!7: .::.                   G@@@@!.^Y^      .^77 P@##Y!YJ?^:^     \n");
printf("                          .^!!77!^: ~B&J :^:                7:. ^G@@@P:  :?:      .^!7 B@#B5^7^P7~?~    \n");
printf("                       :755J!~~..^^^Y@@@G!                 7#?~P&@&Y^    :7.       ^!7 !J.   .J@Y:P?    \n");
printf("              :^:^:. ^P&5!^::~!^7~.  :JB@@G?^.           .^B&#@#5!.      :J.      ^~!7 G@&#P:7P@?.G?    \n");
printf("          ..^~7^^^^::5P5!~~~^. ^^       :75GBBBGGPPPPPPGBBBGY7:   .::::. :J        ^~!.B&BBB^?7&Y.?~    \n");
printf(" ....:::::::^~~.....7! ^    ^^.~.            ^!777?Y?7!~^:   ^?5GB#&&&&#BGG?^.    .!!^ ^GJ^^ .:J:^^.    \n");
printf(".::::::...  .~.:::. J?!?YJ7!^ ^^.           ^!^!7!!~7~   .7P&@@&#&&@@@@@@@@@@&BPYJ?~7^.G@@@B~7G57^      \n");
printf("       ...::^!^^^.^J&@@@@&#&&BGP7~:..   ..  ^!~!!~~^~!. Y&@@@@7..:!?JP&@@@@@@@@@@P~:?:^JYPGY^??!!      \n");
printf("             .~^^?5557!77^!7~B@@@&&#BGGP5?^^^^~^:::^^:::7PB&@P .^^:^~ ^&@@@@@@B57^~77:~:.   :.         \n");
printf("                 .  .:.:^^:..5&5:::::::.  ....             :~:^^^^^:: :B#5#GY~                         \n");
printf("                      .::::^:..                                .^^^^:^::. .                            \n");
}

regras(){//imagem Regras

SetColor(12);




printf("           .:^~~~~~^^:.               ....:::       .:^~~~^:.     .:^^~~~~^^:.             ^J?7!~^.               .:^~~~~^:.              \n");
printf("          :JJYYYYYYYYYYYY?!.    ^J???JJJJYYYYY5:   ^7JYYYYYYYYY7 :JJYYYYYYYYYYYYJ!:         .BPYYYYY5~          :!?JYYYYYYYYYYY!          \n");
printf("          7@JJJJJJJJJJJJJJYY~   Y&JJJJJJJJJJJJ5. !Y5YJJJJJJJJJ5! ~@YJJJJJJJJJJJJJYY!        PGJJJJJJJ5~       ~Y5YJJJJJJJYJJJYJ:          \n");
printf("          !@JJJJJJ5P5JJJJJJJ5   ?&JJJJJJJJJJJJY:5PJJJJJJJJJJ?J5. ^&YJJJJJ5P5JJJJJJJ5.      JBJJJJJJJJJ5~     !#YJJJJJJY?7JGPY7            \n");
printf("          ~@J????Y?~&5?????JJ   7&J???JYYYYYYYYBP??????JJJY5PY?  :&Y????JJ^#P?????JY.     7#J????PJ????5~    7@Y??????Y7~^7J^             \n");
printf("          ^&J?????J?5?????J7.   !&????Y?7??~^:7&??????J~^:^~JY!!!?&5?????J?5J????J7.     ^#Y????J&P?????Y^    7GG5YJJ????JJ???!:          \n");
printf("          :&Y7??????7???YY!.    ~@J???????Y!  7&?????J?GJ????????Y#57??????????JY!:     .B57????J!&Y7????Y~     :~7?YY5PY??????Y!         \n");
printf("          .#Y77777??777777???7!:~&?7777YJJ7:  ^@J7777?5&Y5Y777777JB577777??777777???7!^.PP77777JJ~PP777777Y^   .!!.    7&?777777Y:        \n");
printf("          .#577777PB57777777777J5&J7777??7777?!5#77777?J7PP77777J!GP777775BP7777777777?GB777777777777777777J^ :G57?7!!7J?7777777Y^        \n");
printf("           #5!!!!J!:5BY7!!7!7??~^&J!!!!!!!!777J.P#J!!!!!77!77!!?? PG!!!!?7.YB57!!7!!7?5#7!7777!7??J5?!777!!7Y!GY!!!7777!!777!!7J7         \n");
printf("           BP!777J   ^5BY!!7?~. .&Y!77???????7~  7GPJ7!!!!!!77?!  PB7777Y.  :YB57!7?!:GGJ7!!!!!J~:.5#7!!77?77&P7!!!!!!!!!!!!7?7^          \n");
printf("           7?777!:     ^5P?~.    YJ7!!~^::.        ~JYYYJJ??!^.   ~J777!:     :YG?!.  .^?5P5J7J:    Y#?7!^:  ^?55YJ???????77~:            \n");
printf("                         .                             ....                     ..        .~?!.      ^:         .^~!!!!~^:                \n");

}

dickWins(){

printf("                                                       .::..::..                                    \n");
printf("                                                .^!?J5577?P7~JP~7J!.                                \n");
printf("                                            .~?5JYP7:^J~  ^Y. ?! 5P5                                \n");
printf("                                          ~Y57JP  :J:  J!  7? .5.^?Y: ^~                            \n");
printf("                                        ^5?Y7 :5.  :Y: .5^ .Y. 5~~?J?Y#J                            \n");
printf("                                       ~55 ^5  !J   ~Y. 7J  J: Y:7P#J^P.                            \n");
printf("                                       J!?: J~  ??   J~ .P. ?::Y:BG~ 7?:^~!7777!~~^:.               \n");
printf("                                       :Y~?  J~ :YY: :5  5^ J~7PGJ:!JPJJ??7!!!7?JYYPPYJ7~.          \n");
printf("                      :7??~:            ^?7?:.?PJ777775!~YJJ5G#B~7P!:.              .:^?5YY7:       \n");
printf("              .:.    ^#@&@@&GJ^          .~75J!7?~~~?J75PJ^5@BYP:#J        .~~~!!77???J?7!:!YJ^     \n");
printf("             . .^^   G#G#@@@@@@G!      .:^^75P??JJJYY!YY?~.7@! ?GY~   ~!~~7?~:.~:...     ~Y^.!G:    \n");
printf("             :^~~!!:!B5#@@@@#@&PG~ ~77??J?777???7YY5?7^:~?~~@GJ?Y. :~7PJY7:^~.!Y!77~~^:.  .YP^:     \n");
printf("       ^?J?~:^!~~!^!?J55GB&@7^?. .^?YJJJ??J?7~^~~~~.:7YJJ55JGG. ..7???~~?7J!Y!Y. :J?!!7?JJJJ#5:     \n");
printf("      ~&&#B#5~!^~?J7~.  :~7J!^:::J77~:......^7?^.  !?J777.!7.5~  ^J!^ .?^5.:7:?    ^?:   .:^~?~     \n");
printf("      Y@@@@&7~: ~GY~  ^~~~~~!P!?PJPJ!!Y!!7??!JY  ^J5 !577!7J G?   ~7^ :7!7 .!7?.    .??             \n");
printf("      J#@@@#?7!!BJ^^^.7~.!:7^P?~YYGYJ?J57.    7JJ7?7 7~^~^?~??7   ?P?  ..             !5^           \n");
printf("      .:7?P5^??7: :.   7^^!~J^57:7: ^!!J.       .~P!YJ~^.:^~P~J?!^.   !Y!              ^P7          \n");
printf("       :~7~^:::         ?:!!7 .J!  !Y?!:        ~B:...  ..~5: .7Y?J5J!::. .7!         . .5Y         \n");
printf("    .~!^.               ~77J    ~7: .^!?7~.    :Y?J7~:::^?J.       ~YJJY7::JJ.      ^!~   5P.       \n");
printf("   ^!:.^7JPGGBGY.       ^?J!     :??    !P7!~:~7.  !Y7~~~:             ~PGY~    .~?J!:     5P.      \n");
printf("  .5.J#@@@@@@BBB:   .~^:!^J.      :5Y^  J?       .?J                   ^?~:^^.:J55?.       .#Y      \n");
printf("   77?BB!!#B~.^7     ~^~^~?       !~:5J^BJ7!^. .!57                 .!Y7:    .YJ:.          7&:     \n");
printf("   :J~~~^ ^: ^^:^^  .^7~~7!      .?. :BBP .^7J?J!.                :J57.      ~Y.55?~.        P5     \n");
printf("   Y: ^7!^:   :!!J^~!~~: ^^      7^  :5^^     .                 ^Y5!.       ?G.^5 .^^::      !&:    \n");
printf("  .P:   ^7777~^J~^^:.    ~7 ~~^:~:   ~5                       ~55~        !G5. .5            .#?    \n");
printf("   ~?!^:^~7^!.:: :.      !Y7^:JJ~:   ??                     ~YY^        ~B@J   .!!            PB    \n");
printf("     :^~^^. !.   ^^       !~  ::~7   Y^  :~.              ~Y?:        :J#B~    :~~:           7&:   \n");
printf("           .?    .7.      :~:^^~J.  :J  ~~7.            ^?!.         ~YYY.      ^:.           :#^   \n");
printf("           :?     ~!       .^~!^.   7:^!~?:            ^?!7?JJ??J??^~7!!                       G~   \n");
printf("           :57 .   ^^              ~?^^^7!                ......!#@&?~7..                      5~   \n");
printf("            J?~^:.^.:. :.  :^.. ~.:7.~7^.                        .Y@@G7~!!77JYJ?7777777!!~^^^::Y^   \n");
printf("            . ^~J~YJ!: !!7:Y!Y^~7J7^^^.                            ~G@&Y.  .^J#@#J~^:^^^^:::::^^    \n");
printf("             .~~!!JJ^^!7!!7?!7!^:~.                               ..^5@@#?    .!P&#Y~.              \n");
printf("             !7::::^: !~: .~^^^~!7                          :!?5PB#&&@@&@@B!     :?B&#5~.           \n");
printf("             :7J:   .:!^    .:. 7?                       ^5B@@@@@@@#GJ!^??7~  :~!7J5&@@@#5~         \n");
printf("               ^!~~~^:.:~^:. ~J~?:                       ~Y555Y?7~:.      ^?PB&@@@@@#5?5Y?~.        \n");
printf("                         :~~!~^:                                        ^B@@@@&BPJ7^.               \n");
printf("                                                                        .!!!~^.                     \n");
}

penelopeWins(){
printf("            ....                                            \n");
printf("       .~?Y5PPPP5J!:                                        \n");
printf("     ^YGBGPP5555PPPPY!~7~                                   \n");
printf("   :5#GP555PPPPPPPPP5J7^5.                                  \n");
printf("  ^##5555PGJ7JYYYYY?!~~7?  .!!~!77!:                        \n");
printf("  Y&55555PB?.77~^^~JY?YP7. 77:     ^!                       \n");
printf("  5&5PPP5J7~!!7?YYPGPPP5~.^:        ~~    7^                \n");
printf("  ~&PJ7^. .^?5BBGGPJ?PG?  :!        ?5!: 7P.                \n");
printf("   !G: :!JPPGBJYBY7!!#J~!: ^7!^^!75Y?GBPYG~                 \n");
printf("    ^55PPP555GP7P?:^^77^^Y^  ..:^:YG5BGP5^                  \n");
printf("     .7GG55555PP7^^~?7J?7PJ:      .!YPGY.          !^       \n");
printf("       .!GGPP555PPJJPGGBGBBY .:      JPP:        :!JJ       \n");
printf("        7?!J5PPPBB#BGGP#GBGGYP?      ~PP5.      .7!J~       \n");
printf("       J?:::^~P#BGGBGGPGBGGGGG5Y?~:  .55G? ...  !!?~        \n");
printf("      ?5:^^^^:!G#GGGGPP55PPPGPPPPGGY: JPPBYJ??Y555Y:   ..   \n");
printf("      B!:^^^^^^777?BPPPPPPPP55PGGP5GG.5GPGB55GGPYYGG?777:   \n");
printf("     .B!:::::^^:::^5G5PPPPPPPPPPPPPP#5GGPGGGPYJJY55PGBP!~:  \n");
printf("      75?7!~~~^^^:7?GP5PPPGPPPPPGP5GGPGBGPPG!    ..:^~!~.   \n");
printf("       .^~^^::7^^:7J!GP5P5PBP5PGGPPG#GPPPGBG!               \n");
printf("          !7~!!::~5Y^YBG5P5PGP5PGGGPPGPJ?JYPY               \n");
printf("          ~7~^^~7YY?J^:PG5P5PBG#PPPGGBG^   ..               \n");
printf("           :!77?J?7~.  .JBP5PB5BGGGGGPPP7.                  \n");
printf("                         ~GGGGGB&PBBGGGGGP7.                \n");
printf("                          .77?GP#GGPPBGGPGPP?:              \n");
printf("                             JP555555PGGGGGGPPJ^            \n");
printf("                            :G555555PPPGGGGGP5PPY!.         \n");
printf("                            !BPGGGGGGPPGBBGP5555PGPJ:       \n");
printf("                            JG55PP555PGGGP555555G!^^.       \n");
printf("                            5G55555PPPP555555PPPJ           \n");
printf("                            5G55555555555PPP5Y7:            \n");
printf("                            J#PPPPPPGGBBB#P^.               \n");
printf("                            ^GPP##BBBGGGGY.                 \n");
printf("                               .GPPPPPPGJ.                  \n");
printf("                               YGPPPPPG?                    \n");
printf("                              ?BPPPPGG7                     \n");
printf("                            :5GPPPPGY:                      \n");
printf("                          .!GGPPPGP~                        \n");
printf("                         ^!~7J55G5.                         \n");
printf("                         7.     Y7                          \n");
printf("                        :7     !J                           \n");
printf("                        !^    :5.                           \n");
printf("                        ?.    5^                            \n");
printf("                       .Y    7?                             \n");
printf("                       ^Y   :5                              \n");
printf("                       ?7   Y~                              \n");
printf("                       P:  ^5                               \n");
printf("                      7Y   J~                               \n");
printf("                     :P.  .7                                \n");
printf("                     J~  .!.                                \n");
printf("                    .J    !!^.                              \n");
printf("                     ~~7!~..^~~^:.                          \n");
printf("                      :~ ^?7!~~~!!!!:                       \n");
printf("                            .........                       \n");

}

//DADO
int dado(){
  srand((unsigned)time(NULL));
    int min = 1; // O menor numero
    int max = 6;//maior numero
    int dado;
    dado = (rand () % (max-min+1) + min); //atribui o numero gerado a variável dado
    return dado;
}
//imprimir tabuleiro
void tabuleiro()
{
    int troca;
    for (int i = 0; i< LINHA; i++)for(int j=0; j< COL; j++) tab[i][j] = 0;
    for (int i = 0; i< LINHA; i++) tab [i][1] = i+1;
    //troca visual do 0 pelo 49...49 pelo 0
    //se desejar fazer o mesmo para as outras colunas, mas vai ter que mudar a logica do jogo
    for (int i = 0; i<25; i++)
    {
        troca = tab[i][1];
        tab[i][1] = tab[49 - i][1];
        tab[49 - i][1] = troca;
    }
    tab[pos1][0] = 1;
    tab[pos2][2] = 2;
    for (int i = 0; i<LINHA;i++)
   {
       for (int j = 0; j<COL; j++)
       {
           printf("\t%i", tab[i][j]);
       }
       printf("\n");
   }
}
void SetColor(int ForgC)
{
     WORD wColor;
     //This handle is needed to get the current background attribute

     HANDLE hStdOut = GetStdHandle(STD_OUTPUT_HANDLE);
     CONSOLE_SCREEN_BUFFER_INFO csbi;
     //csbi is used for wAttributes word

     if(GetConsoleScreenBufferInfo(hStdOut, &csbi))
     {
          //To mask out all but the background attribute, and to add the color
          wColor = (csbi.wAttributes & 0xF0) + (ForgC & 0x0F);
          SetConsoleTextAttribute(hStdOut, wColor);
     }
     return;
}
//MENU
  int menu(){
    int op;
    setlocale(LC_ALL, "Portuguese");// configurando para que reconheça formatação de texto brasileira. ex: acentos.

do{
    tituloMenu();
    arteMenu();
    printf("\n====================================================================================================================\t");
    SetColor(34);
	printf("\n********************************************************************************************************************");
	printf("\n\t\t\t= = = = = = = == = = == = = =  CORRIDA   = = = = = = = = = = = == = = =\n");
	printf("\n\t\t\t= = = = = = = == = = == = = =   MALUCA    = = = = = = = = = = = == = = \n");
	printf("\n********************************************************************************************************************");
	SetColor(15);
	printf("\n====================================================================================================================\t\n");
	SetColor(12);
    menssagemInicio();
    SetColor(15);

	SetColor(9);
	printf("\n\n 1: Regras");
	printf("\n 2: Jogar ");
	printf("\n 0: Sair");
	SetColor(15);
	printf("\n\n\n Informe a opção desejada : ");
	scanf("%d", &op);



    switch(op){


    case 1:
        system("cls");
        SetColor(14);
        regras();
        printf("------------------------------------------------------------------------------------------------------------------------------------------------------\n",201,187);
        printf("  São 50 posições, o jogador que chegar ao final primeiro ganhará. Cada jogador terá um turno onde serão jogado o dado, \n e a depender do numero ele avancará e casa.\t\t  \n");
        printf("  Outro fator importante é que haverão posições especiais, quais vão dar vantagens e desvantagens randomicamentem então \n é tudo pela sua sorte.\t\t\t \n");
        printf("  Jogador 1 você é o Dick Vigarista que está usando mais uma fez trapaças para tentar ganhar algo. Jogador 2, já você é a Penelope\n Charmosa que tentará impedir que o mal vença  \n");
        printf("-----------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        menssagemRegras();
        system("pause");
        system("cls");
        SetColor(15);

    break;

    case 2: return op;
    break;

    case 3:

    system("pause");
    system("cls");
    break;

    case 0: return op;
    break;

    default:
    printf("OPÇÃO INVÁLIDA !\n");
    system("pause");
    system("cls");
    break;
}
}
while (op!=0);
}

int main(void)
{
    int inicio;
    setlocale(LC_ALL, "Portuguese");
    for ( ; ; )
    {
        inicio = menu();
        //inicializar o jogo
        if (inicio == 2) break;
        //saida do jogo
        if (inicio == 0)
        {
            SetColor(13);
            system("cls");
            printf("********** ATÉ A PROXIMA **********\n\n\n");
            SetColor(15);
            exit(0);
        }
    }
    //setar posicoes iniciais
    pos1 = 49;
    pos2 = 49;
    int d;
    system("cls");
    SetColor(14);
    //historia
    menssagemHistoria();
    SetColor(10);
    printf("PREPARADOS?\n\n");
    SetColor(11);
    printf("JOGADOR[1]: DICK\n");
    SetColor(12);
    printf("JOGADOR [2]: PENELOPE\n");
    SetColor(15);
    printf("\nDECIDAM  QUEM QUEREM SER\n\n");
    system("pause");
    //jogo
    for ( ; ; )
    {
        //jogador 1
        SetColor(11);
        printf("JOGADOR [1]:\n ");
        system("pause");
        //tabuleiro com a cor AZUL antes de atualizar a posição
        tabuleiro();
        d = dado();
        printf("DICK VIGARISTA\n");
        SetColor(15);
        //Menu para o jogador com a posição, dado e a nova posição. Como está com lógica invertida usa 50 - "indice de pos"
        printf("DADO: %i\t", d);
        printf("POSIÇÃO: %i\t\t", 50-pos1);
        printf("NOVA POSIÇÃO: %i\n\n", 50-pos1+d);
        system("pause");
        system("cls");
        pos1 = pos1-d;
        //verificacao de ganhar
        if (pos1 <= 0)
        {
            SetColor(11);
            printf("NÃO DEU PARA OS HONESTOS, O DICK GANHOU!!!\n");
            dickWins();
            SetColor(15);
            break;
        }
        //loop para verificar casa especial
        for (int i =0; i <10; i++)
        {
            if (pos1 == esp[i])
            {
                //informativo de casa especial junto com qual é
                printf("CASA ESPECIAL!!!: %i\n\n", 50-esp[i]);
                system("pause");
                switch(dado())
                {
                    case 1:
                        system("cls");
                        printf("JOGADOR [1] Perdeu:");
                        SetColor(13);
                        printf(" 1 posição\n");
                        pos1 = pos1+1;
                        SetColor(15);
                        system("pause");
                    break;
                    case 2:
                        system("cls");
                        pos1 = pos1 +2;
                        printf("JOGADOR [1] Perdeu:");
                        SetColor(13);
                        printf(" 2 posições\n");
                        SetColor(15);
                        system("pause");
                    break;
                    case 3:
                        system("cls");
                        pos1 = pos1 +3;
                        printf("JOGADOR [1] Perdeu:");
                        SetColor(13);
                        printf(" 3 posições\n");
                        SetColor(15);
                        system("pause");
                    break;
                    case 4:
                        system("cls");
                        pos1 = pos1 -3;
                        printf("JOGADOR [1] Ganhou:");
                        SetColor(14);
                        printf(" 3 posições\n");
                        SetColor(15);
                        system("pause");
                    break;
                    case 5:
                        system("cls");
                        pos1 = pos1 -2;
                        printf("JOGADOR [1] Ganhou:");
                        SetColor(14);
                        printf(" 2 posições\n");
                        SetColor(15);
                        system("pause");
                    break;
                    case 6:
                        system("cls");
                        pos1 = pos1 - 1;
                         printf("JOGADOR [1] Ganhou:");
                         SetColor(14);
                         printf(" 1 posição\n");
                         SetColor(15);
                         system("pause");
                    break;
                }

            }

        }
        //tabuleiro atualizado BRANCO
        tabuleiro();
        //jogador 2
        SetColor(12);
        printf("JOGADOR [2]:\n ");
        system("pause");
        //tabuleiro com a cor VERMELHA antes de atualizar a posição
        tabuleiro();
        d = dado();
        //Menu para o jogador com a posição, dado e a nova posição. Como está com lógica invertida usa 50 - "indice de pos"
        printf("PENELOPE CHARMOSA\n");
        SetColor(15);
        printf("DADO: %i\t", d);
        printf("POSIÇÃO: %i\t\t", 50-pos2);
        printf("NOVA POSIÇÃO: %i\n\n", 50-pos2+d);
        system("pause");
        system("cls");
        pos2 = pos2-d;
        //verificar ganhar
        if (pos2 <= 0)
        {
            SetColor(12);
            printf("PENELOPE SAIU ATRÁS MAS MESMO ASSIM GANHOU MOSTRANDO QUE TRAPAÇA NÃO LEVA A NADA E FAZENDO A JUSTIÇA ESPORTIVA \n");
            penelopeWins();
            SetColor(15);
            break;
        }
        //especial pro 2
        for (int i =0; i <10; i++)
        {
            if (pos2 == esp[i])
            {
                //informativo de casa especial junto com qual é
                printf("CASA ESPECIAL!!!: %i\n\n", 50-esp[i]);
                system("pause");
                switch(dado())
                {
                    case 1:
                        system("cls");
                        printf("JOGADOR [2] Perdeu:" );
                        SetColor(13);
                        printf(" 1 posição\n");
                        pos2 = pos2 +1;
                         SetColor(15);
                         system("pause");

                    break;
                    case 2:
                        system("cls");
                        printf("JOGADOR [2] Perdeu:");
                        SetColor(13);
                        printf(" 2 posições\n");
                         pos2 = pos2 +2;
                         SetColor(15);
                         system("pause");
                    break;
                    case 3:
                        system("cls");
                        printf(" JOGADOR [2] Perdeu:");
                        SetColor(13);
                        printf(" 3 posições\n");
                        pos2 = pos2 +3;
                        SetColor(15);
                        system("pause");
                    break;
                    case 4:
                        system("cls");
                        printf("JOGADOR [2] Ganhou:");
                        SetColor(14);
                        printf(" 3 posições\n");
                        pos2 = pos2 - 3;
                        SetColor(15);
                        system("pause");
                    break;
                    case 5:
                        system("cls");
                        printf("JOGADOR [2] Ganhou:");
                        SetColor(14);
                        printf(" 2 posições\n");
                        pos2 = pos2 - 2;
                        SetColor(15);
                        system("pause");
                    break;
                    case 6:
                        system("cls");
                        printf("JOGADOR [2] Ganhou:");
                        SetColor(14);
                        printf(" 1 posição\n");
                        pos2 = pos2 -1;
                        SetColor(15);
                        system("pause");
                    break;
                }

            }

        }
        //tabuleiro atualizado BRANCO
        tabuleiro();

    }

}


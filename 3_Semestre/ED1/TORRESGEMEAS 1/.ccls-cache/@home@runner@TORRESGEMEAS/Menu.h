#ifndef MENU_H_INCLUDED
#define MENU_H_INCLUDED
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

void pause() {
  printf("Pressione Enter para continuar...\n");
  int c;
  while ((c = getchar()) != '\n' && c != EOF)
    ;
  getchar();
}

void red () {
  printf("\033[1;31m");
}

void yellow() {
  printf("\033[1;33m");
}

void cyan() {
  printf("\033[0;36m");
}

void green() {
  printf("\033[0;32m");
}

void reset () {
  printf("\033[0m");
}

void upper(char *string) {
    while (*string) {
        *string = toupper((unsigned char)*string);
        string++;
    }
}

void torreImg () {
  red();
  printf("                           :^.                                           \n");
  printf("                           !5.                                           \n");
  printf("                           !5.                                           \n");
  printf("                           75:                                           \n");
  printf("                         ^J5557.                                         \n");
  printf("                       :75PPPPPJ!.    ^77777777!                         \n");
  printf("                       ^GPP5555PP:.   !P5PPGGGG5                         \n");
  printf("                       ^GGPP555P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGPP55P5:    !P5PPGGGG5                         \n");
  printf("                       ^GGGGGGGGG5YJ?!?P55PGGGG5                         \n");
  cyan();
  printf("        .:^~~~~~~~~^~~!?YGGGGGGGGGGGGGGGGGPPGGGGP7?Y5PPGGGGGP5?!:.       \n");
  printf("     :~?Y5PGGGGGGGGGGGGGGBGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG5J7~^:... \n");
  printf(" .^?5GBBBBGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGBBBGGPPP5Y~ \n");
  printf("                                                                                 \n");
  reset();
  
}

void exitImg() {
  cyan();
  printf("77777777777777777777777777777777777777777777777777#P!77777777777777777777777777777777777777777777777\n");
  printf("77777777777777777777777777777777777777777777777777#P!77777777777777777777777777777777777777777777777\n");
  printf("77777777777777777777777777777777777777777777777777#P!77777777777777777777777777777777777777777777777\n");
  printf("77777777777777777777777777777777777777777777777777#P777777777777777777777777777777777777777777777777\n");
  printf("7777777777777777777777777777777777777777777P&#####@&#####&J7777!P&############&J77777777777777777777\n");
  printf("777777777777777777777777777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  red();
  printf("7777777777777~!777777777777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("7777777777777!.^!~777777777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("77777777777777!: ^!!7777777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("7777777777!!7777^ .~7777777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("7777777777! ^~~^^. .^^^~777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("7777777777!.^!!!~. :~!!!777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("7777777777777777: :^!777777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("7?J777777777777^.:~77777777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y!7777777777777777777\n");
  printf("7?Y7777777777777777777777777777777777777777G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  cyan();
  printf("7?Y7777777777777777777777777777777777777777G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("7?Y7777777777777777777777777777777777777777G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("7?Y7777777777777777777777777777777777777777G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("77?777777777777777777777777777777777777777!G@@@@@@@@@@@@@@J7777!P@@@@@@@@@@@@@@Y77777777777777777777\n");
  printf("                                                                                                    \n");
  reset();
}

void LISTAR_DESTINOS() {
  printf("ESSES SAO OS PAISES E SITIOS DISPONIBILIZADOS PELA NOSSA AGENCIA\n\n");
  printf("SOMALIA : ");
  cyan();
  printf("MOGADISCIO\n");
  reset();
  printf("ISRAEL : ");
  cyan();
  printf("FAIXA DE GAZA\n");
  reset();
  printf("ESPANHA : ");
  cyan();
  printf("BARCELONA\n");
  reset();
  printf("REINO UNIDO : ");
  cyan();
  printf("LONDRES, ILHA DE MAN\n");
  reset();
  printf("ALEMANHA : ");
  cyan();
  printf("BAMBERGA\n");
  reset();
  printf("COREIA DO SUL : ");
  cyan();
  printf("SEOUL\n");
  reset();
  printf("JAPAO : ");
  cyan();
  printf("QUIOTO\n");
  reset();
  printf("CHILE, ");
  cyan();
  printf("CORDILHEIRA DOS ANDES\n");
  reset();
  printf("BRASIL, ");
  cyan();
  printf("CHAPADA DIAMONTINA\n");
  reset();
  printf("ARGENTINA, ");
  cyan();
  printf("BUENOS AIRES\n");
  reset();
  printf("CANADA: MONTREAL, ");
  cyan();
  printf("MORAINE\n");
  reset();
  printf("MEXICO: ");
  cyan();
  printf("CIDADE DO MEXICO\n");
  reset();
  printf("EUA: ");
  cyan();
  printf("NIAGRA FALLS, NOVA YORK\n\n");
  reset();
}

void menu() {
  printf("ESCOLHA UMA OPCAO:\n");
  printf("---------------------------------------------------------------------"
         "-----\n");
  printf("1 - JA SEI O DESTINO\n2 - PRECISO DE AJUDA PRA DECIDIR\n3 - PAISES "
         "QUE CLIENTES DECIDIDOS ESCOLHERAM\n4 - PAISES QUE CLIENTES QUE "
         "UTILIZARAM NOSSA AJUDA ESCOLHERAM\n");
  printf("5 - PAISES VISITADOS\n6 - PAISES NAO VISITADOS\n7 - O PAIS MAIS "
         "VISITADO E O SITIO MAIS VISITADO\n0 - SAIR\n");
  printf("---------------------------------------------------------------------"
         "-----\n");
}


#endif // MENU_H_INCLUDED

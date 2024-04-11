#include "Arvore.h"
#include "Listas.h"
#include "Menu.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
  Paises *inicio = NULL;
  Decisoes *raiz = NULL;
  int escolha;
  LER_ARQUIVO_LISTAS(&inicio);
  ARQUIVO_ARVORE(&raiz);
  char *sitio = "";
  char city[50] = "";
  int opcao;
  for (;;) {
    torreImg();
    menu();
    scanf(" %d", &opcao);
    // opcao = 6;
    switch (opcao) {
    case 1:
      system("clear");
      //MOSTRAR_PAISES_CIDADES(&inicio);
      LISTAR_DESTINOS();
      red();
      printf("OBS: PODES TANTO DIGITAR APENAS O SITIO, QUANTO DIGITAR O SITIO E O PAIS SEPARADO POR VIRGULA\n");
      green();
      printf("EXEMPLO 1: MONTREAL\n");
      printf("EXEMPLO 2: MONTREAL, CANADA\n");
      reset();
      printf("ESCOLHA O DESTINO DESEJADO:  ");
      scanf(" %[^\n]", city);
      strtok(city, ",");
      upper(city);
      ADD_TURISTAS(&inicio, city, 1);
      break;
    case 2:
      system("clear");
      printf("BEM VINDO AO AUXILIO DE ESCOLHAS, RESPONDA (S/N)\n");
      sitio = ARVORE_DECISAO(raiz);
      strtok(sitio, ",");
      if (strcmp(sitio, "NAO") == 0) {
        printf("DESTINO NAO ADICIONADO\n");
        pause();
        
      }
      else {
        ADD_TURISTAS(&inicio, sitio, 2);
      }
      
      break;
    case 3:
      system("clear");
      printf("CLIENTES DECIDIDOS ESCOLHERAM OS SEGUINTES PAISES:\n\n");
      PAISES_VISITADOS_POR_TIPO(&inicio, 1);
      pause();
      break;
    case 4:
      system("clear");
      printf("AUXILIADOS POR NOS ESCOLHERAM OS SEGUINTES PAISES:\n\n");
      PAISES_VISITADOS_POR_TIPO(&inicio, 2);
      pause();
      break;
    case 5:
      system("clear");
      printf("ESSES SAO OS PAISES VISITADOS:\n\n");
      PAISES_VISITADOS(&inicio);
      pause();
      system("clear");
      break;
    case 6:
      system("clear");
      printf("ESSES SAO OS PAISES NAO VISITADOS:\n\n");
      PAISES_NAO_VISITADOS(&inicio);
      pause();
      system("clear");
      break;
    case 7:
      system("clear");
      MAIS_VISITADO_PAIS(&inicio);
      printf("\n");
      MAIS_VISITADO_SITIO(&inicio);
      pause();
      break;
    case 0:
      system("clear");
      exitImg();
      printf("ATE A PROXIMA\n");
      FREE_PAISES_SITIOS(&inicio);
      pause();
      exit(0);
      break;
    default:
      system("clear");
      printf("OPCAO INVALIDA\n");
      pause();
      break;
    }
    system("clear");
  }
}

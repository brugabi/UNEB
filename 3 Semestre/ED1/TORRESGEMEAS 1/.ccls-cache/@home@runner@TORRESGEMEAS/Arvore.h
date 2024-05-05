#include "Menu.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef ARVORE_H_INCLUDED
#define ARVORE_H_INCLUDED
typedef struct arvore {
  int id;
  char pergunta[100];
  struct arvore *nao, *sim;
} Decisoes;

Decisoes *inserir(Decisoes *raiz, int n, char *pergunta) {
  if (raiz == NULL) {
    raiz = (Decisoes *)malloc(sizeof(Decisoes));
    if (raiz == NULL)
      exit(1);

    raiz->id = n;
    strcpy(raiz->pergunta, pergunta);
    raiz->nao = NULL;
    raiz->sim = NULL;
    return raiz;
  }

  if (n > raiz->id) {
    raiz->nao = inserir(raiz->nao, n, pergunta);
  } else if (n < raiz->id)
    raiz->sim = inserir(raiz->sim, n, pergunta);
  else {
    printf("Numero invalido");
  }
  return raiz;
}

void ARQUIVO_ARVORE(Decisoes **raiz) {
  FILE *arquivo = fopen("perguntas.txt", "r");
  if (arquivo == NULL) {
    printf("erro ao abrir");
    exit(1);
  }
  int id;
  // ALTERAR O TAMANHO SE ALTERAR O ARQUIVO
  int tamanho = 31;
  char pergunta[100];
  for (int i = 0; i < tamanho; i++) {
    fscanf(arquivo, " %d ,%[^\n]", &id, pergunta);
    // printf("%d - %s\n", id, pergunta);
    (*raiz) = inserir((*raiz), id, pergunta);
  }
  fclose(arquivo);
}

const char *ARVORE_DECISAO(Decisoes *raiz) {
  if (raiz == NULL)
    return;
  char opcao;
  printf("%s\n", raiz->pergunta);
  // verificando se e uma folha
  if (raiz->nao == NULL && raiz->sim == NULL)
  {
    printf("DESEJA CONFIRMAR VIAGEM? SIM OU NAO?: ");
    scanf(" %c", &opcao);
    printf("\n");
    if (opcao == 's' || opcao == 'S') {
      return raiz->pergunta;
    }
    else if (opcao == 'n' || opcao == 'N') {
      return "NAO";
    }
    else {
      system("clear");
      printf("CARACTERE INVALIDO\n");
      printf("DIGITE (S/N)\n");
      pause();
      system("clear");
      return ARVORE_DECISAO(raiz);
    }
  }
  scanf(" %c", &opcao);
  if (opcao == 's' || opcao == 'S')
    return ARVORE_DECISAO(raiz->sim);
  else if (opcao == 'n' || opcao == 'N')
    return ARVORE_DECISAO(raiz->nao);
  // para caractere invalido
  else {
    system("clear");
    printf("CARACTERE INVALIDO\n");
    printf("DIGITE (S/N)\n");
    pause();
    system("clear");
    return ARVORE_DECISAO(raiz);
  }
  printf("\n");
}


#endif // ARVORE_H_INCLUDED

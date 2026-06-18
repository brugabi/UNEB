#ifndef LISTAS_H_INCLUDED
#define LISTAS_H_INCLUDED
#include "Menu.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct listaSitios {
  char nome[50];
  int turista1, turista2;
  struct listaSitios *proxSitio;
} Sitios;

typedef struct listaPaises {
  int visitas;
  char nome[50];
  struct listaPaises *proxPais;
  Sitios *inicioSitios;
} Paises;

void inserirPAIS(Paises **inicio, char *nome) {
  Paises *novo = (Paises *)malloc(sizeof(Paises));
  if (!novo)
    exit(1);
  // STR COPY
  strcpy(novo->nome, nome);
  novo->visitas = 0;
  if (inicio == NULL) {
    novo->proxPais = NULL;
  } else {
    novo->proxPais = *inicio;
  }
  *inicio = novo;
}

void inserirSITIOS(Paises **inicioSitios, char *nome) {
  Sitios *novo = (Sitios *)malloc(sizeof(Sitios));
  if (!novo)
    exit(1);
  // STR COPY
  strcpy(novo->nome, nome);
  // ZERANDO OS TURISTAS
  novo->turista1 = 0;
  novo->turista2 = 0;
  if (*inicioSitios == NULL) {
    novo->proxSitio = NULL;
  } else {
    novo->proxSitio = *inicioSitios;
  }
  *inicioSitios = novo;
}

// ARQUIVO DE LISTAS
void LER_ARQUIVO_LISTAS(Paises **inicio) {
  FILE *arquivo = fopen("paises.txt", "r");
  if (arquivo == NULL) {
    printf("erro ao abrir");
    exit(1);
  }

  char nome[50];
  int cod;
  // TAMANHO DAS LINHAS
  int tamanho = 29;
  for (int i = 0; i < tamanho; i++) {
    fscanf(arquivo, " %[^,], %d,", nome, &cod);
    if (cod == 1)
      inserirPAIS(&(*inicio), nome);
    else
      inserirSITIOS(&(*inicio)->inicioSitios, nome);
  }
  fclose(arquivo);
}
// LISTAR
void MOSTRAR_CIDADES(Sitios **inicio) {
  Sitios *temp = (*inicio);
  printf("CIDADES\n");
  while (temp != NULL) {
    printf("\t\t%s\n", temp->nome);
    temp = temp->proxSitio;
  }
}
//LISTAR EM CONJUNTO
void MOSTRAR_PAISES_CIDADES(Paises **inicio) {
  Paises *temp = (*inicio);
  int indice = 1;
  if (!(*inicio))
    return;
  while (temp != NULL) {
    printf("PAIS %d - ", indice);
    printf("%s\n", temp->nome);
    MOSTRAR_CIDADES(&temp->inicioSitios);
    printf("\n");
    temp = temp->proxPais;
    indice++;
  }
}
void MOSTRAR_PAISES(Paises **inicio) {
  Paises *temp = (*inicio);
  int quantidade = 0;
  printf("PAISES:\n");
  while (temp != NULL) {
    printf("\t%s\n", temp->nome);
    temp = temp->proxPais;
    quantidade++;
  }
  
}

void ADD_TURISTAS(Paises **inicio, char *sitioEscolhido, int tipo) {
  Paises *tempPais = (*inicio);
  Sitios *tempSitios = NULL;
  int flag = 0;
  if (tempPais == NULL)
    exit(1);
  while (tempPais != NULL) {
    tempSitios = tempPais->inicioSitios;
    while (tempSitios != NULL) {
      if (strcmp(tempSitios->nome, sitioEscolhido) == 0) {
        tempPais->visitas++;
        //CHECAR SE E TIPO 1
        if (tipo == 1) {
          tempSitios->turista1++;
          printf("\nTURISTA ADICIONADO\n");
          flag++;
          pause();
          return;
        }
        //CHECAR SE E TIPO 2
        else if (tipo == 2) {
          tempSitios->turista2++;
          printf("\nTURISTA ADICIONADO\n");
          flag++;
          pause();
          return;
        }
      }
      // PASSANDO O PONTEIRO DOS SITIOS
      tempSitios = tempSitios->proxSitio;
    }
    // PASSANDO O PONTEIROS DO PAIS
    tempPais = tempPais->proxPais;
  }
  if(flag == 0){
    printf("ERRO AO ADICIONAR O CLIENTE, VERIFIQUE SE FOI DIGITADO CORRETAMENTE\n");
    pause();
  } 
}

// FUNCIONA
void PAISES_VISITADOS(Paises **inicio) {
  if (!(*inicio))
    return;
  Paises *tempPais = (*inicio);
  int indice = 1;
  while (tempPais != NULL) {
    //VE SE TEM VISITAS E AUMENTA O INDICE
    if (tempPais->visitas > 0) {
      printf("%d - PAIS: %s, VISITAS: %d\n", indice, tempPais->nome,
             tempPais->visitas);
      indice++;
    }
    tempPais = tempPais->proxPais;
  }
  //SE O INDICE PERMANECER O MESMO
  if (indice == 1)
    printf("NENHUM PAIS FOI VISITADO\n");
}
// FUNCIONA
void PAISES_VISITADOS_POR_TIPO(Paises **inicio, int tipo) {
  if (!(*inicio))
    return;
  int flag = 0;
  Paises *temPais = (*inicio);
  while (temPais != NULL) {
    Sitios *tempSitios = temPais->inicioSitios;
    while (tempSitios != NULL) {
      if (tipo == 1) {
        if (tempSitios->turista1 > 0) {
          printf("%s\n", temPais->nome);
          flag++;
        }
      } else if (tipo == 2) {
        if (tempSitios->turista2 > 0) {
          printf("%s\n", temPais->nome);
          flag++;
        }
      }
      tempSitios = tempSitios->proxSitio;
    }
    temPais = temPais->proxPais;
  }
  if (flag == 0)
    printf("INFELIZMENTE, NENHUM CLIENTE USOU ESSA OPCAO\n");
}
// FUNCIONA
void PAISES_NAO_VISITADOS(Paises **inicio) {
  Paises *tempPais = (*inicio);
  int quantidadePaisTotal = 13;
  int flagContadora = 0;
  int id = 1;
  // WHILE DO PAIS
  while (tempPais != NULL) {
    if (tempPais->visitas == 0) {
      printf("%d - %s\n", id, tempPais->nome);
      id++;
    }
    else {
      flagContadora++;
    }
    tempPais = tempPais->proxPais;
  }
  if (flagContadora == quantidadePaisTotal)
    printf("TODOS OS PAISES FORAM VISITADOS\n");
}
// FUNCIONA
void FREE_PAISES(Paises **inicio) {
  Paises *aux;
  if (!(*inicio))
    return;
  while ((*inicio) != NULL) {
    aux = (*inicio);
    (*inicio) = (*inicio)->proxPais;
    free(aux);
  }
}
//FREE NO SITIO
void FREE_SITIOS(Sitios **inicio) {
  Sitios *aux;
  if (!(*inicio))
    return;
  while ((*inicio) != NULL) {
    aux = (*inicio);
    (*inicio) = (*inicio)->proxSitio;
    free(aux);
  }
}
//FREE NA LISTA
void FREE_PAISES_SITIOS(Paises **inicio) {
  Paises *aux;
  if (!(*inicio))
    return;
  while ((*inicio) != NULL) {
    aux = (*inicio);
    FREE_SITIOS(&(*inicio)->inicioSitios);
    (*inicio) = (*inicio)->proxPais;
    free(aux);
  }
}

// FUNCIONA
void MAIS_VISITADO_PAIS(Paises **inicio) {
  if (!(*inicio))
    return;
  Paises *tempPais = (*inicio);
  Paises *lista = NULL;
  int mais_pais = 0;
  while (tempPais != NULL) {
    if (tempPais->visitas > mais_pais) {
      // lista = NULL;
      FREE_PAISES(&lista);
      mais_pais = tempPais->visitas;
      inserirPAIS(&lista, tempPais->nome);
    } else if (tempPais->visitas == mais_pais) {
      inserirPAIS(&lista, tempPais->nome);
    }
    tempPais = tempPais->proxPais;
  }
  if (mais_pais == 0) {
    FREE_PAISES(&lista);
    printf("NAO EXISTE O(S) PAIS(ES) MAIS VISITADO(S), NENHUM FOI\n");
    return;
  }
  MOSTRAR_PAISES(&lista);
  printf("QUANTIDADE DE VISITAS %d\n", mais_pais);
}
//FUNCIONA
void MAIS_VISITADO_SITIO(Paises **inicio) {
  Paises *tempPais = (*inicio);
  Sitios *lista = NULL;
  int mais_sitio = 0;
  int total_sitio = 0;
  while (tempPais != NULL) {
    Sitios *tempSitios = tempPais->inicioSitios;
    while (tempSitios != NULL) {
      total_sitio = tempSitios->turista1 + tempSitios->turista2;
      if (total_sitio > mais_sitio) {
        FREE_SITIOS(&lista);
        mais_sitio = total_sitio;
        inserirSITIOS(&lista, tempSitios->nome);
      } else if (total_sitio == mais_sitio) {
        inserirSITIOS(&lista, tempSitios->nome);
      }
      tempSitios = tempSitios->proxSitio;
    }
    tempPais = tempPais->proxPais;
  }
  if (mais_sitio == 0) {
    printf("NAO EXISTE O(S) SITIO(S) MAIS VISITADO(S), NENHUM FOI\n");
    FREE_SITIOS(&lista);
    return;
  }
  MOSTRAR_CIDADES(&lista);
  printf("QUANTIDADE DE VISITAS: %d\n", mais_sitio);
}
#endif // LISTAS_H_INCLUDED

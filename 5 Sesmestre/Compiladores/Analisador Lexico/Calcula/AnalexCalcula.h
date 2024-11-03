#ifndef ANALEXCALCULA
#define ANALEXCALCULA

#define TAM_MAX_LEXEMA 31

enum TOKEN_CAT {ID = 1, SN, CT_I, FIM_EXPR, FIM_ARQ}; 
/* Onde: ID: Identificador, SN: Sinal; CT_I: Constante numérica inteira */

enum SINAIS {ATRIB = 1, ADICAO, SUBTRACAO, MULTIPLIC, DIVISAO, ABRE_PAR, FECHA_PAR}; 
// Sinais válidos da linguagem

typedef struct {  
    enum TOKEN_CAT cat; // deve receber uma das constantes de enum TOKEN_CAT
    union { // parte variável do registro
        int codigo; // para tokens das categorias SN
        char lexema[TAM_MAX_LEXEMA]; // cadeia de caracteres que corresponde ao nome do token da cat. ID
        int valInt; // valor da constante inteira em tokens da cat. CT_I
    };
} TOKEN; // Tipo TOKEN

#endif 

/* Contador de linhas do código fonte */ 
int contLinha = 1;

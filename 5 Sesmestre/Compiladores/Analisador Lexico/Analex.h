#ifndef ANALEX
#define ANALEX

#define TAM_MAX_LEXEMA 70

//CT_ I = INTEIRO CT_R = REAL LT = STRING PR = PALAVRAS RESERVADAS SN = SINAIS
enum TOKEN_CAT {ID = 1, CT_I, CT_R, CT_C, LT, PR, SN, FIM_ARQ};

//OP_OR = OPERADOR OU "||" OP_AND = OPERADOR E "&&" 
enum SINAIS {ADICAO = 1, SUBTRACAO, MULTI, DIV, MAIOR, MAIOR_IGUAL, MENOR, MENOR_IGUAL, ATRIB, IGUALDADE, ABRE_PAR,
                FECHA_PAR, ABRE_COL, FECHA_COL, OP_OR, ECOM, OP_AND, VIRGULA, NEGACAO, DIF}; //ECOM = E COMERCIAL

typedef struct token{
    enum TOKEN_CAT cat;
    union{
        int codPR;
        int codSN;
        char lexema[TAM_MAX_LEXEMA];
        char charcon;
        int valINT;
        float valREAL; //TALVEZ N PRECISE PQ REAL = INT.INT

    };
    
} TOKEN;

//ARRAY DE PR
static const char* arrayPR[] = {
    "const",
    "pr",
    "init",
    "endp",
    "char",
    "int",
    "real",
    "bool",
    "do",
    "while",
    "endw",
    "var",
    "from",
    "to",
    "dt",
    "by",
    "if",
    "endv",
    "elif",
    "else",
    "endi",
    "getout"
};

// Função para verificar se e PR
static int is_PR(const char* lexema) {
    int num_PR = sizeof(arrayPR) / sizeof(arrayPR[0]);
    
    for (int i = 0; i < num_PR; i++) {
        if (strcmp(lexema, arrayPR[i]) == 0) {
            return 1; // É uma palavra reservada
        }
    }
    return 0; // Não é uma palavra reservada
}

#endif

int contLinha = 1;
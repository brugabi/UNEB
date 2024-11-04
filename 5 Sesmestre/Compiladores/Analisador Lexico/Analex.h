#ifndef ANALEX
#define ANALEX

#define TAM_MAX_LEXEMA 31

//CT_ I = INTEIRO CT_R = REAL LT = STRING PR = PALAVRAS RESERVADAS SN = SINAIS
enum TOKEN_CAT {ID = 1, CT_I, CT_R, CT_C, LT, PR, SN, FIM_ARQ};

//PALAVARS RESERVADAS
enum PR {
    PR_CONST = 1, PR_PR, PR_INIT, PR_ENDP, PR_CHAR, PR_INT, PR_REAL, PR_BOOL,
    PR_DO, PR_WHILE, PR_ENDW, PR_VAR, PR_FROM, PR_TO, PR_DT, PR_BY, PR_IF,
    PR_ENDV, PR_ELIF, PR_ELSE, PR_ENDI, PR_GETOUT
};

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

#endif

int contLinha = 1;
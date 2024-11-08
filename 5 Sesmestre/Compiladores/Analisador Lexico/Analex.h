#ifndef ANALEX
#define ANALEX

#define TAM_MAX_LEXEMA 31
#define TAM_PALAVRAS_RESERVADAS 28

//CT_ I = INTEIRO CT_R = REAL LT = STRING PR = PALAVRAS RESERVADAS SN = SINAIS
enum TOKEN_CAT {ID = 1, CT_I, CT_R, CT_C, LT, PALAVRAS_RESERVADAS, SN, FIM_ARQ};

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
        float valREAL;

    };
    
} TOKEN;

//ARRAY DE PR
const char arrayPR[TAM_PALAVRAS_RESERVADAS][TAM_MAX_LEXEMA] = {
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
    "getout",
    "getint",
    "getreal",
    "getchar",
    "putint",
    "putreal",
    "putchar"  
};

typedef enum {
    CONST,
    PR,
    INIT,
    ENDP,
    CHAR,
    INT,
    REAL,
    BOOL,
    DO,
    WHILE,
    ENDW,
    VAR,
    FROM,
    TO,
    DT,
    BY,
    IF,
    ENDV,
    ELIF,
    ELSE,
    ENDI,
    GETOUT,
    GETINT,
    GETREAL,
    GETCHAR,
    PUTINT,
    PUTREAL,
    PUTCHAR,
    FIM
} PalavrasReservadas;


// Função para verificar se e PR
static int is_PR(const char* lexema) {
        
    for (int i = 0; i < TAM_PALAVRAS_RESERVADAS; i++) {

        if (strcmp(lexema, arrayPR[i]) == 0) {
            return i; // É uma palavra reservada
        }
    }
    return -1; // Não é uma palavra reservada
}

#endif

int contLinha = 1;
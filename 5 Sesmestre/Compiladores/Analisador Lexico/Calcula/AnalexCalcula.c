#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "AnalexCalcula.h"

#define TAM_LEXEMA 50
#define TAM_NUM 20

void error(char msg[]) {
    printf("%s\n", msg);
    exit(1);
}

TOKEN AnaLex(FILE *fd) {
    int estado = 0;
    char lexema[TAM_LEXEMA] = "";
    int tamL = 0;
    char digitos[TAM_NUM] = "";
    int tamD = 0;
    TOKEN t;

    while (1) {
        char c = fgetc(fd);
        switch (estado) {
            case 0:
                if (c == ' ' || c == '\t') estado = 0;
                else if (c >= 'a' && c <= 'z') {
                    estado = 1;
                    lexema[tamL++] = c;
                    lexema[tamL] = '\0';
                } else if (c >= '1' && c <= '9') {
                    estado = 10;
                    digitos[tamD++] = c;
                    digitos[tamD] = '\0';
                } else if (c == '+') { t.cat = SN; t.codigo = ADICAO; return t; }
                else if (c == '-') { t.cat = SN; t.codigo = SUBTRACAO; return t; }
                else if (c == '*') { t.cat = SN; t.codigo = MULTIPLIC; return t; }
                else if (c == '/') { t.cat = SN; t.codigo = DIVISAO; return t; }
                else if (c == '=') { t.cat = SN; t.codigo = ATRIB; return t; }
                else if (c == '(') { t.cat = SN; t.codigo = ABRE_PAR; return t; }
                else if (c == ')') { t.cat = SN; t.codigo = FECHA_PAR; return t; }
                else if (c == '\n') { t.cat = FIM_EXPR; return t; }
                else if (c == EOF) { t.cat = FIM_ARQ; return t; }
                else error("Caracter inválido na expressão!");
                break;
            case 1:
                if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || (c == '_')) {
                    lexema[tamL++] = c;
                    lexema[tamL] = '\0';
                } else {
                    estado = 2;
                    ungetc(c, fd);
                    t.cat = ID;
                    strcpy(t.lexema, lexema);
                    return t;
                }
                break;
            case 10:
                if (c >= '0' && c <= '9') {
                    digitos[tamD++] = c;
                    digitos[tamD] = '\0';
                } else {
                    estado = 11;
                    ungetc(c, fd);
                    t.cat = CT_I;
                    t.valInt = atoi(digitos);
                    return t;
                }
                break;
        }
    }
}

int main() {
    FILE *fd;
    TOKEN tk;

    if ((fd = fopen("expressao.dat", "r")) == NULL)
        error("Arquivo de entrada da expressão não encontrado!");

    while (1) {
        tk = AnaLex(fd);
        switch (tk.cat) {
            case ID: printf("<ID, %s> ", tk.lexema); break;
            case SN:
                switch (tk.codigo) {
                    case ADICAO: printf("<SN, ADICAO> "); break;
                    case SUBTRACAO: printf("<SN, SUBTRACAO> "); break;
                    case MULTIPLIC: printf("<SN, MULTIPLICACAO> "); break;
                    case DIVISAO: printf("<SN, DIVISAO> "); break;
                    case ATRIB: printf("<SN, ATRIBUICAO> "); break;
                    case ABRE_PAR: printf("<SN, ABRE_PARENTESES> "); break;
                    case FECHA_PAR: printf("<SN, FECHA_PARENTESES> "); break;
                }
                break;
            case CT_I: printf("<CT_I, %d> ", tk.valInt); break;
            case FIM_EXPR: printf("<FIM_EXPR, %d>\n", 0); break;
            case FIM_ARQ: printf(" <Fim do arquivo encontrado>\n"); break;
        }
        if (tk.cat == FIM_ARQ) break;
    }

    fclose(fd);
    return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Analex.h"

#define TAM_LEXEMA 50
#define TAM_NUM 20

void error(char msg[]) {
    printf("%s\n", msg);
    exit(1);
}

TOKEN AnaLex (FILE *fd) {
    int estado = 0;
    char lexema[TAM_MAX_LEXEMA] = "";
    int tamL = 0;
    char digitos[TAM_NUM] = "";
    int tamD = 0;
    TOKEN t;

    while (1){
        char c = fgetc(fd);
        switch (estado){
        case 0:
            if (c == ' ' || c == '\t') estado = 0;
            //SINAIS
            else if (c == '+') { t.cat = SN; t.codSN = ADICAO; return t;}
            else if (c == '-') { t.cat = SN; t.codSN = SUBTRACAO; return t;}
            else if (c == '/') { estado = 4;} //divisao ou comentariio
            else if (c == '>') { estado = 7;} //maior ou maior igual
            else if (c == '=') {estado = 13;} //atribuicao ou igualdade
            else if (c == '(') { t.cat = SN; t.codSN = ABRE_PAR; return t;}
            else if (c == ')') { t.cat = SN; t.codSN = FECHA_PAR; return t;}
            else if (c == '[') { t.cat = SN; t.codSN = ABRE_COL; return t;}
            else if (c == ']') { t.cat = SN; t.codSN = FECHA_COL; return t;}
            else if (c == '|') {estado = 20;} //talvez operador ou
            else if (c == '&') { estado = 22;} // operador and ou endereco
            else if (c == ',') { t.cat = SN; t.codSN = VIRGULA; return t;}
            else if (c == '!') { estado = 26;} //diferenca ou negacao
            else if (c == EOF) { t.cat = FIM_ARQ; return t; }
            //ID
            else if (c == '_') {
                estado = 29;
                lexema[tamL++] = c;
                lexema[tamL] = '\0';
            }
            else if (c >= 'A' && c <='z') {
                estado = 30;
                lexema[tamL++] = c;
                lexema[tamL] = '\0';
            }
            //DIGITO
            else if (c >= '0' && c <='9') {
                estado = 32;
                digitos[tamD++] = c;
                digitos[tamD]= '\0';
            }
            //CHAR
            else if (c == '\''){estado = 37;} //pode ser um char, um \n ou um \0
            break;
        case 4: //COMENTARIO OU DIVISAO
            if (c == '/') {estado = 5;} // COMENTARIO
            else {
                //DIVISAO
                estado = 6;
                ungetc(c,fd);
                t.cat = SN;
                t.codSN = DIV;
                return t;
            }
            break;
        case 5: //COMENTARIO
            if (c == '\n')
            {
                estado = 0;
                ungetc(c, fd);
                t.cat = SN;
                t.codSN = COMENT;
                strcpy(t.lexema, lexema);
                return t;
            }
            else{
                lexema[tamL++] = c;
                lexema[tamL] = '\0';
            }
            break;
        case 7:
            if (c == '=') {estado = 8; t.cat = SN; t.codSN = MAIO_IGUAL; return t;} //MAIOR IGUAL
            else {estado = 9; t.cat = SN; t.codSN = MAIOR; return t;} //MAIOR
            break;
        case 10:
            if (c == '=') {estado = 11; t.cat = SN; t.codSN = MENOR_IGUAL; return t;} //MENOR IGUAL
            else {estado = 12; t.cat = SN; t.codSN = MENOR; return t;} //MENOR
            break;
        case 13: //PAREI AQUI, ATRIB OU IGUALDADE
            if (c == '=') {estado = 14; t.cat = SN; t.codSN = IGUALDADE; return t;} //IGUALDADE
            else {estado = 15; t.cat = SN; t.codSN = ATRIB; return t;} //ATRIBUICAO
            break;
        default:
            break;
        }
    }
    
}

int main () {
    FILE *fd;
    TOKEN tk;

    if ((fd = fopen ("expressao.dat", "r")) == NULL)
        error ("ARQUIVO DE EXPRESSAO NAO ENCONTRADO!!");

    while (1){
        tk = AnaLex(fd);
        switch (tk.cat){
        case SN:
            switch (tk.codSN){
            case ADICAO: printf ("<SN, ADICAO> "); break;
            case SUBTRACAO: printf ("<SN, SUBTRACAO> "); break;
            case ATRIB: printf ("<SN, ATRIBUICAO> "); break;
            case IGUALDADE: printf ("<SN, IGUALDADE> "); break;
            }
            break;
        case FIM_ARQ: printf("FIM DO ARQUIVO ENCONTRADO\n"); break;
        }
        if (tk.cat == FIM_ARQ) break; //break do while
    }
    fclose(fd);
    return 0;
}
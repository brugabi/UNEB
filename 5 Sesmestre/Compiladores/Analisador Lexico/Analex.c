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
            else if (c == '*') { t.cat = SN; t.codSN = MULTI; return t;}
            else if (c == '/') { estado = 4;} //divisao ou comentariio
            else if (c == '>') { estado = 7;} //maior ou maior igual
            else if (c == '<') { estado = 10;} //menor ou menor igual
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
            else if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
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
            //else error("Caracter invalido!");
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
                return t;
                
            }
            else{
                lexema[tamL++] = c;
                lexema[tamL] = '\0';
            }
            break;
        case 7:
            if (c == '=') {estado = 8; t.cat = SN; t.codSN = MAIOR_IGUAL; return t;} //MAIOR IGUAL
            else {estado = 9; t.cat = SN; t.codSN = MAIOR; return t;} //MAIOR
            break;
        case 10:
            if (c == '=') {estado = 11; t.cat = SN; t.codSN = MENOR_IGUAL; return t;} //MENOR IGUAL
            else {estado = 12; t.cat = SN; t.codSN = MENOR; return t;} //MENOR
            break;
        case 13: //ATRIB OU IGUALDADE
            if (c == '=') {estado = 14; t.cat = SN; t.codSN = IGUALDADE; return t;} //IGUALDADE
            else {estado = 15; t.cat = SN; t.codSN = ATRIB; return t;} //ATRIBUICAO
            break;
        case 20:
            if (c == '|') {estado = 21; t.cat = SN; t.codSN = OP_OR; return t;} //OPERADOR OR
            break;
        case 22:
            if (c == '&') {estado = 23; t.cat = SN; t.codSN = OP_AND; return t;} //OPERADOR AND
            else { // E COMERCIAL
                estado = 24;
                ungetc(c, fd);
                t.cat = SN;
                t.codSN = ECOM;
                return t;
            }
            break;
        case 26:
            if (c == '=') {estado = 27; t.cat = SN; t.codSN = DIF; return t;} //DIFERENCA
            else {estado = 28; t.cat = SN; t.codSN = NEGACAO; return t;} //NEGACAO
            break;
        //CASOS DE ID
        case 29: //UNDERLINE OPCIONAL
            if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                estado = 30;
                lexema[tamL++] = c;
                lexema[tamL] = '\0'; 
            }
            else {
                estado = 29;
                lexema[tamL++] = c;
                lexema[tamL] = '\0'; 
            }
            break;
        case 30: //CASO NECESSÁRIO DE LETRA
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_') {
                lexema[tamL++] = c;
                lexema[tamL] = '\0'; 
            }
            else{
                estado = 31; //ACABOU O ID
                ungetc(c, fd);
                t.cat = ID;
                strcpy(t.lexema, lexema);
                return t;
            }
            break;
        case 32: //INT OU REAL
            if (c >= '0' && c <= '9') {
                estado = 32;
                digitos[tamD++] = c;
                digitos[tamD]= '\0';
            }
            else if (c == '.') { //REAL
                estado=34;
                digitos[tamD++] = c;
                digitos[tamD]= '\0';
            }
            else { //ACABOU O INT
                estado = 33;
                ungetc(c, fd);
                t.cat = CT_I;
                t.valINT = atoi(digitos);
                return t;
            }
            break;
        case 34: //CASO DO REAL
            if (c >= '0' && c <= '9') {
                estado = 35;
                digitos[tamD++] = c;
                digitos[tamD]= '\0';
            }
            break;
        case 35:
            if (c >= '0' && c <= '9') {
                estado = 35;
                digitos[tamD++] = c;
                digitos[tamD]= '\0';
            }
            else { //ACABOU O REAL
                estado = 36;
                ungetc(c, fd);
                t.cat = CT_R;
                t.valREAL = atof(digitos);
                return t; 
            }
            break;
        case 37: //CHAR
            if (c >= 0 && c <=127) { //CHAR COM QUALQUER CACATER ASCII
                estado = 38;
                lexema[tamL++] = c;
                lexema[tamL] = '\0'; 
            }
            else if (c == '\n') {estado=41;}
            else if ( c == '\0') {estado=42;}
            break;
        case 38: if(c =='\'') {
            estado = 39;
            t.cat = CT_C;
            t.charcon = lexema[0];
            return t;
            }
        case 41: if (c =='\'') {estado=39; t.cat = CT_C; return t;}
            break;
        case 42: if (c =='\'') {estado=39; t.cat = CT_C; return t;}
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
                case ADICAO: printf ("<SN, ADICAO>\n"); break;
                case SUBTRACAO: printf ("<SN, SUBTRACAO>\n"); break;
                case MULTI: printf ("<SN, MULTIPLICACAO>\n"); break;
                case DIV: printf("<SN, DIVISAO>\n"); break;
                case MAIOR_IGUAL: printf("<SN, MAIOR_IGUAL>\n"); break;
                case MAIOR: printf("<SN, MAIOR>\n"); break;
                case MENOR_IGUAL: printf("<SN, MENOR_IGUAL>\n"); break;
                case MENOR: printf("<SN, MENOR>\n"); break;
                case ATRIB: printf ("<SN, ATRIBUICAO>\n "); break;
                case IGUALDADE: printf ("<SN, IGUALDADE>\n"); break;
                case ABRE_PAR: printf("<SN, ABRE_PARENTESES>\n"); break;
                case FECHA_PAR: printf("<SN, FECHA_PARENTESES>\n"); break;
                case ABRE_COL: printf("<SN, ABRE_COLCHETES>\n"); break;
                case FECHA_COL: printf("<SN, FECHA_COLCHETES>\n"); break;
                case OP_OR: printf("<SN, OPERADOR_OR>\n"); break;
                case OP_AND: printf("<SN, OPERADOR_AND>\n"); break;
                case ECOM: printf("<SN, E_COMERCIAL>\n"); break;
                case VIRGULA: printf("<SN, VIRGULA>\n"); break;
                case DIF: printf("<SN, DIFERENCA>\n"); break;
                case NEGACAO: printf("<SN, NEGACAO>\n"); break;
            }
            break;
        case ID: printf("<ID, %s>\n", tk.lexema); break;
        case CT_I: printf("<CT_I, %d>\n", tk.valINT); break;
        case CT_R: printf("<CT_R, %f>\n", tk.valREAL); break;
        case CT_C: printf("<CT_C, %c>\n", tk.charcon); break;
        case FIM_ARQ: printf("<FIM DO ARQUIVO ENCONTRADO>\n"); break;
        }
        if (tk.cat == FIM_ARQ) break; //break do while
    }
    fclose(fd);
    return 0;
}
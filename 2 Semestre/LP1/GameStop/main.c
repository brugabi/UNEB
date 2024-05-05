#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <windows.h>

typedef struct {
    int index;
    char name[255];
    char description[1000];
    char rating[10];
    int quantity;
} Game;

void SetColor(int ForgC)
 {
     WORD wColor;

      HANDLE hStdOut = GetStdHandle(STD_OUTPUT_HANDLE);
      CONSOLE_SCREEN_BUFFER_INFO csbi;

                       //We use csbi for the wAttributes word.
     if(GetConsoleScreenBufferInfo(hStdOut, &csbi))
     {
                 //Mask out all but the background attribute, and add in the forgournd     color
          wColor = (csbi.wAttributes & 0xF0) + (ForgC & 0x0F);
          SetConsoleTextAttribute(hStdOut, wColor);
     }
     return;
 }

int menu(){
    int option;
    //scanear archive
    SetColor(14);
    do{
        printf("\n----- Menu -----\n");
        printf("1. Register a Title\n");
        printf("2. Show Registered Titles\n");
        printf("3. Consult Registered Titles\n");
        printf("4. Change a Title\n");
        printf("5. Delete a Title\n");
        SetColor(4);
        printf("0. END\n\n");
        SetColor(15);
        printf("Option: ");
        scanf("%d",&option);
        if (option<0 || option>5) {
            printf("Invalid entry.\n");
            system("pause");
            system("cls");
        }
    }while(option<0 || option>5);
    system("cls");
    return option;
}

void Registry(Game *inventory,int size){
        inventory[size-1].index = inventory[size-2].index + 1;
        SetColor(14);
        printf("Enter the title of the game:\n");
        scanf(" %[^\n]",&inventory[size-1].name);
        SetColor(15);
        printf("Enter the discription:\n");
        scanf(" %[^\n]",&inventory[size-1].description);
        SetColor(2);
        printf("Enter the rating:\n");
        scanf(" %[^\n]",&inventory[size-1].rating);
        SetColor(6);
        printf("Enter the avaible quantity:\n");
        scanf("%d",&inventory[size-1].quantity);
        system("cls");
        SetColor(15);
        printf("END OF REGISTRY\n");
        system("pause");
        system("cls");
    }

void Show_Inventory(Game *inventory,int size){
    SetColor(12);
    printf("| %-5s | %-50s |\n", "INDEX", "TITLE");
    printf("|-------|----------------------------------------------------|\n");
    for (int i = 0; i < size; i++) {
    if(inventory[i].index != 0) printf("| %-5d | %-50s |\n", inventory[i].index, inventory[i].name);
    }
    SetColor(15);
    system("pause");
    system("cls");
}

void Consult (Game *inventory, int size){
    int index, flag=0;
    char affirmation;
    printf("Do you want to see the full list?(Y/N)\n");
    scanf("%s", &affirmation);
    system("cls");
    SetColor(12);
    if (affirmation == 'y' || affirmation =='Y')
    {
      printf("| %-5s | %-50s |\n", "INDEX", "TITLE");
      printf("|-------|----------------------------------------------------|\n");
      for (int i = 0; i < size; i++) {
      if(inventory[i].index != 0) printf("| %-5d | %-50s |\n", inventory[i].index, inventory[i].name);
    }
    }
    SetColor(15);
    printf("Enter the index of the game:\n");
    scanf("%d",&index);
    for(int i=0;i<size;i++){
        if(inventory[i].index == index){
            SetColor(14);
            printf("Name: %s\n",inventory[i].name);
            SetColor(2);
            printf("Age Rating: %s\n",inventory[i].rating);
            SetColor(6);
            printf("Quantity: %d\n",inventory[i].quantity);
            SetColor(15);
            printf("Description:\n\t%s\n",inventory[i].description);
            flag = 1;
            break;
        }
    }
    if (flag == 0) printf("GAME DOESN'T EXIST OR NOT FOUND\n");
    SetColor(15);
    system("pause");
    system("cls");

}
void Change (Game*inventory, int size){
    int index, flag = 0;
    int affirmation;
    char option;
    printf("Do you want to see the full list?(Y/N)\n");
    scanf("%s", &option);
    system("cls");
    if (option == 'y' || option =='Y')
    {
        SetColor(12);
      printf("| %-5s | %-50s |\n", "INDEX", "TITLE");
      printf("|-------|----------------------------------------------------|\n");
      for (int i = 0; i < size; i++) {
      if(inventory[i].index != 0) printf("| %-5d | %-50s |\n", inventory[i].index, inventory[i].name);
    }
    }
    SetColor(15);
    printf("Enter the index of the game:\n");
    scanf("%d",&index);
    for (int i=0;i<size;i++){
        if(inventory[i].index == index){
                SetColor(14);
                printf("Game to be changed: '%d' -  %s\n", inventory[i].index, inventory[i].name);
                SetColor(2);
                printf("Age Rating: %s\n",inventory[i].rating);
                SetColor(6);
                printf("Quantity: %d\n",inventory[i].quantity);
                SetColor(15);
                printf("Description:\n\t%s\n",inventory[i].description);
                printf("What you'd like to change?\n1 - NAME\t 2 - DESCRIPTION\t 3 - AGE RATING(AR)\t 4 - QUANTITY AVAIBLE\t 5  - EVERYTHING\n");
                scanf("%d", &affirmation);
                switch(affirmation)
                {
                    case 1:
                        SetColor(14);
                        printf("Enter a new name:\n");
                        scanf(" %[^\n]",&inventory[i].name);
                        break;
                    case 2:
                        printf("Enter a new discription:\n");
                        scanf(" %[^\n]",&inventory[i].description);
                        break;
                    case 3:
                       SetColor(2);
                       printf("Enter a new age rating:\n");
                       scanf(" %[^\n]",&inventory[i].rating);
                       break;
                    case 4:
                       SetColor(6);
                       printf("Enter a new avaible quanitty:\n");
                       scanf(" %d",&inventory[i].quantity);
                       break;
                    case 5:
                       SetColor(14);
                       printf("Enter a new name:\n");
                       scanf(" %[^\n]",&inventory[i].name);
                       SetColor(15);
                       printf("Enter a new discription:\n");
                       scanf(" %[^\n]",&inventory[i].description);
                       SetColor(2);
                       printf("Enter a new age rating:\n");
                       scanf(" %[^\n]",&inventory[i].rating);
                       SetColor(6);
                       printf("Enter a new avaible quanitty:\n");
                       scanf(" %d",&inventory[i].quantity);
                       break;
                    default:
                        printf("INVALID OPTION\n");
                        break;
                }
                flag = 1;

    }
  }
  SetColor(15);
  if (flag == 0) printf("GAME DOESN'T EXIST OR NOT FOUND\n");
  printf("END OF THE MODIFICATION\n");
  system("pause");
  system("cls");
}
int Delete (Game*inventory, int size){
    int index;
    char affirmation;
    char option;
    printf("Do you want to see the full list?(Y/N)\n");
    scanf("%s", &option);
    system("cls");
    if (option == 'y' || option =='Y')
    {
        SetColor(12);
      printf("| %-5s | %-50s |\n", "INDEX", "TITLE");
      printf("|-------|----------------------------------------------------|\n");
      for (int i = 0; i < size; i++) {
      if(inventory[i].index != 0) printf("| %-5d | %-50s |\n", inventory[i].index, inventory[i].name);
    }
    }
    SetColor(15);
    printf("Enter the index of the game:\n");
    scanf("%d",&index);
    SetColor(4);
    for (int i=index-1;i<size;i++){
        if(inventory[i].index == index){
                printf("Game: '%d' -  %s\n", inventory[i].index, inventory[i].name);
                printf("Are you sure you want to delete this game?(Y/N)\n\n");
                scanf("%s", &affirmation);
                if (affirmation == 'y' || affirmation == 'Y')
                {
                    for (int j=i;j<size-1;j++){
                        if(inventory[j].index > index)
                        {
                            strcpy(inventory[j].name, inventory[j+1].name);
                            strcpy(inventory[j].description, inventory[j+1].description);
                            strcpy(inventory[j].rating, inventory[j+1].rating);
                            inventory[j].quantity = inventory[j+1].quantity;
                        }
                    }
                    printf("GAME DELETED\n");
                    printf("RETURNING TO PROGRAM\n");
                    system("pause");
                    system("cls");
                    return 1;
                }
                else {
                    SetColor(2);
                    printf("GAME NOT DELETED\n");
                    printf("RETURNING TO PROGRAM\n");
                    system("pause");
                    system("cls");
                    return 0;
                }
    }
  }
    SetColor(15);
    printf("INDEX NOT FOUND\n");
    printf("RETURNING TO PROGRAM\n");
    system("pause");
    system("cls");
    return 0;
}

// Carregar Dados
void Load_File(Game **Inventory, int *size){
    FILE *archive = fopen("data.txt","r");
    if (archive == NULL) {
        printf("Unable to open the file.\n");
        return;
    }

    // Ignorar a primeira linha (cabeçalho)
    char line[255];
    fgets(line, sizeof(line), archive);

    int i = 0;
    while (fgets(line, sizeof(line), archive) != NULL) {
        Game game; // Cria uma instância temporária da estrutura Game

        sscanf(line, "%d,%[^,],%[^,],%[^,],%d",
               &game.index,
               game.name,
               game.description,
               game.rating,
               &game.quantity);

        *Inventory = realloc(*Inventory, (i + 1) * sizeof(Game));
        (*Inventory)[i] = game;

        i++;
        *size = i; // Atualizar o size do inventário
    }

    fclose(archive);
}


void Save_File(Game *Inventory,int size){
    FILE *archive = fopen("data.txt","w");
    if (archive == NULL) {
        printf("Error to open the file.\n");
        return;
    }
    fprintf(archive, "Index,Name,Description,Age Ration,Quantity\n");
    for(int i=0;i<size;i++){
        if(Inventory[i].index != 0){
            fprintf(archive, "%d,%s,%s,%s,%d\n", Inventory[i].index, Inventory[i].name, Inventory[i].description, Inventory[i].rating, Inventory[i].quantity);
        }
    }
    fclose(archive);
}

void END() {
    printf("\n------------------\n");
    printf("END OF THE PROGRAM!\n");
    printf("------------------\n");
}

int main(){
    int size = 0;
    Game *Inventory = NULL;
    Load_File(&Inventory, &size);

    while(1){
        switch(menu()){
        case 1:
            size ++;
            Inventory = (Game*) realloc(Inventory,size*sizeof(Game));
            Registry(Inventory,size); // Cadastrando os games
            break;
        case 2:
            Show_Inventory(Inventory,size); // Mostrando o inventário
            break;
        case 3:
            Consult(Inventory, size);
            break;
        case 4:
            Change(Inventory, size);
            break;
        case 5:
            if(Delete(Inventory, size)== 1)
                {
                size --;
                Inventory = (Game*) realloc(Inventory,size*sizeof(Game));
                }
            break;
        case 0:
            Save_File(Inventory,size);
            END();
            return 0;
        }
    }
    return 0;
}

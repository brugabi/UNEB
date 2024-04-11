#include <stdio.h>
#include <stdlib.h>

struct Node {
    int data;
    struct Node* next;
};

struct Node* createAndInsert(int values[], int numValues) {
    struct Node* head = NULL;

    for (int i = 0; i < numValues; ++i) {
        struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));

        newNode->data = values[i];
        newNode->next = NULL;

        if (head == NULL) {
            head = newNode;
        } else {
            struct Node* last = head;
            while (last->next != NULL) {
                last = last->next;
            }
            last->next = newNode;
        }
    }

    return head;
}

void printList(struct Node* head) {
    while (head != NULL) {
        printf("%d -> ", head->data);
        head = head->next;
    }
    printf("NULL\n");
}

int countNodes(struct Node* head) {
    int count = 0;
    while (head != NULL) {
        count++;
        head = head->next;
    }
    return count;
}

void insertNode(struct Node** head, int value, int position) {
    if (position < 0) {
        printf("Posição inválida. Insira uma posição não negativa.\n");
        return;
    }

    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    newNode->data = value;

    if (position == 0 || *head == NULL) {
        newNode->next = *head;
        *head = newNode;
    } else {
        struct Node* current = *head;
        for (int i = 0; i < position - 1 && current != NULL; ++i) {
            current = current->next;
        }

        if (current == NULL) {
            printf("Posição fora dos limites. Inserção no final da lista.\n");
            free(newNode);
            return;
        }

        newNode->next = current->next;
        current->next = newNode;
    }
}

void removeNode(struct Node** head, int value) {
    struct Node* current = *head;
    struct Node* prev = NULL;

    while (current != NULL && current->data != value) {
        prev = current;
        current = current->next;
    }

    if (current == NULL) {
        printf("Valor %d não encontrado na lista.\n", value);
        return;
    }

    if (prev == NULL) {
        *head = current->next;
    } else {
        prev->next = current->next;
    }

    free(current);
}

void reverseList(struct Node** head) {
    struct Node* prev = NULL;
    struct Node* current = *head;
    struct Node* next = NULL;

    while (current != NULL) {
        next = current->next;
        current->next = prev;
        prev = current;
        current = next;
    }

    *head = prev;
}

void freeList(struct Node** head) {
    while (*head != NULL) {
        struct Node* temp = *head;
        *head = (*head)->next;
        free(temp);
    }
}

int main() {
    struct Node* head = NULL;

    int choice, value, position, numValues;

    do {
        printf("\n1. Criar Lista\n2. Inserir em uma posição específica\n");
        printf("3. Imprimir lista\n4. Contar elementos\n5. Remover elemento\n");
        printf("6. Inverter lista\n0. Sair\n");

        printf("Escolha: ");
        scanf("%d", &choice);

        switch (choice) {
            case 0:
                break;
            case 1:
                printf("Digite o número de valores a serem inseridos: ");
                scanf("%d", &numValues);

                int* values = (int*)malloc(numValues * sizeof(int));

                printf("Digite os valores separados por espaço: ");
                for (int i = 0; i < numValues; ++i) {
                    scanf("%d", &values[i]);
                }

                head = createAndInsert(values, numValues);
                free(values);

                printf("Lista criada com sucesso.\n");
                break;
            case 2:
                printf("Digite o valor a ser inserido: ");
                scanf("%d", &value);
                printf("Digite a posição de inserção: ");
                scanf("%d", &position);
                insertNode(&head, value, position);
                break;
            case 3:
                printf("Lista Encadeada: ");
                printList(head);
                break;
            case 4:
                printf("Número de elementos na lista: %d\n", countNodes(head));
                break;
            case 5:
                printf("Digite o valor a ser removido: ");
                scanf("%d", &value);
                removeNode(&head, value);
                break;
            case 6:
                reverseList(&head);
                printf("Lista invertida: ");
                printList(head);
                break;
            default:
                printf("Opção inválida.\n");
        }
    } while (choice != 0);

    freeList(&head);

    return 0;
}

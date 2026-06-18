import json
import os
from time import sleep

def menu():
    menu = """
    Menu:

    1. Inserir um profissional
    2. Inserir relacionamentos de um profissional
    3. Eliminar uma pessoa ou relacionamento
    4. Listar os nomes em ordem alfabetica
    5. Buscar uma pessoa
    6. Salvar o dicionario em um arquivo
    7. Sair do programa.
    """
    print(menu)

def limpar_tela():
    os.system('cls' if os.name == 'nt' else 'clear')

caminho_arquivo = "E:\Codigos\ED2\Atividade_Lab2\dicionario.json"
try:
    with open(caminho_arquivo, 'r') as arquivo:
        dicionario = json.load(arquivo)
        print("Dicionário carregado com sucesso:")
        input("Pressione Enter para continuar...\n")
        limpar_tela()
except FileNotFoundError:
    dicionario = {}

def add_dic (dicionario):
    nome = input("Digite o nome da pessoa: ").capitalize()
    cargo = input("Digite o cargo da pessoa: ").capitalize()
    salario = float(input("Digite o salario da pessoa: "))
    list_relacionamento = input('Digite os relacionamentos separados por ","').split()
    list_relacionamento = [item.capitalize() for item in list_relacionamento]
    dicionario[nome] = {'Cargo': cargo, 'Salario': salario, 'Relacionamento' : list_relacionamento}

def add_relacionamento(dicionario):
    nome = input('Digite o nome da pessoa: ').strip().capitalize()
    if nome in dicionario:
        novo_relacionamento = input('Digite os relacionamentos das pessoas separadas por "," \n').split(',')
        novo_relacionamento = [item.capitalize() for item in novo_relacionamento]
        dicionario[nome].update({'Relacionamento' : novo_relacionamento})
    else:
        print('Pessoa nao encontrada')

def busca(dicionario):
    nome = input('Digite o nome da pessoa ').capitalize()
    if nome in dicionario:
        print(f'Pessoa encontrada: {nome} e seus relacionamentos sao: {dicionario[nome]['Relacionamento']}')
    else:
        print('Pessoa nao encotrada')
def deletar(dicionario):
    nome = input('Digite o nome da pessoa ').capitalize()
    if nome in dicionario:
        op = input('Quer deletar pessoa ou relacionamento? ').lower()
        if op == 'relacionamento':
            del dicionario[nome]['Relacionamento']
        elif op == 'pessoa':
            for nome_dic, dados in dicionario.items():
                if nome in dados['Relacionamento']:
                    dados['Relacionamento'].remove(nome)
                    print(f' {nome} foi removido do Relacionamento de: {nome_dic}')
                    sleep(2)
            del dicionario[nome]
        else:
            print('Opcao invalida')
    else:
        print('Nome nao encotrado')
    return dicionario

def ordenacao(dicionario):
    dicionario = dict(sorted(dicionario.items()))
    return dicionario

while True:
    menu()
    escolha = input('Digite o numero da opcao desejada: ').strip()
    if escolha == '1':
        add_dic(dicionario)
        print('Operacao finalizada')
        sleep(2)
    elif escolha == '2':
        add_relacionamento(dicionario)
        print('Operacao finalizada')
        sleep(2)
    elif escolha == '3':
        dicionario = deletar(dicionario)
        print('Operacao finalizada')
        sleep(2)
    elif escolha == '4':
        dicionario_ordenado = ordenacao(dicionario)
        for item in dicionario_ordenado.items():
            print(item)
        input("Pressione Enter para continuar...\n")
        limpar_tela()
    elif escolha == '5':
        busca(dicionario)
        sleep(2)
        limpar_tela()
    elif escolha == '6':
        with open(caminho_arquivo, 'w') as arquivo:
            json.dump(dicionario, arquivo, indent=-1)
            print(f'O dicionário foi salvo no arquivo "dicionario.jason" com caminho de {caminho_arquivo}.')
            sleep(2)
            limpar_tela()
    elif escolha == '7':
        limpar_tela()
        break
    else:
        print("Opcao invalida.")
        limpar_tela()

"""
Componentes Fortemente Conectadas em Digrafos
Algoritmo de Kosaraju-Sharir

Uso:
    python kosaraju.py <arquivo_de_entrada>
    python kosaraju.py digrafo_scc.txt
"""

import os
import sys


def ler_grafo_arquivo(nome_arquivo):
    """
    Lê um arquivo e devolve a lista de adjacência.

    Retorna (adj, eh_digrafo, ponderado). Em caso de erro, retorna
    (None, False, False).
    """
    try:
        with open(nome_arquivo, "r") as f:
            linhas = [linha.strip() for linha in f if linha.strip()]

        if not linhas:
            print("Arquivo vazio!")
            return None, False, False

        tipo, peso = linhas[0].split()
        if tipo not in ("G", "D"):
            print("Erro: Primeiro caractere deve ser 'G' (grafo) ou 'D' (dígrafo).")
            return None, False, False

        eh_digrafo = tipo == "D"
        ponderado = peso == "W"
        adj = {}
        num_arestas = 0

        for linha in linhas[1:]:
            partes = linha.split()
            if ponderado:
                if len(partes) != 3:
                    print(f"Erro: Esperado formato 'u v w' para grafos ponderados. Linha: '{linha}'")
                    return None, False, False
                u, v, _w = partes
            else:
                if len(partes) != 2:
                    print(f"Erro: Esperado formato 'u v' para grafos não ponderados. Linha: '{linha}'")
                    return None, False, False
                u, v = partes

            if u not in adj:
                adj[u] = []
            if v not in adj:
                adj[v] = []

            adj[u].append(v)
            num_arestas += 1

            if not eh_digrafo:
                adj[v].append(u)

        num_vertices = len(adj)
        print(
            f"Grafo criado ({'dígrafo' if eh_digrafo else 'grafo'}, "
            f"{'ponderado' if ponderado else 'não ponderado'}) com "
            f"{num_vertices} vértices e {num_arestas} arestas."
        )
        return adj, eh_digrafo, ponderado

    except FileNotFoundError:
        print(f"Arquivo '{nome_arquivo}' não encontrado.")
        return None, False, False


def construir_grafo_transposto(adj):
    """Cria o grafo transposto, invertendo todas as arestas."""
    adj_t = {v: [] for v in adj}
    for u in adj:
        for v in adj[u]:
            adj_t[v].append(u)
    return adj_t


def dfs_ordem_termino(adj, vertices):
    """
    Primeira fase do algoritmo: calcula a ordem de término da DFS.

    A versão iterativa evita depender do limite de recursão do Python.
    Cada item da pilha guarda o vértice atual e um iterador sobre seus vizinhos.
    """
    visitados = set()
    ordem = []

    for v in vertices:
        if v in visitados:
            continue

        pilha = [(v, iter(adj[v]))]
        visitados.add(v)

        while pilha:
            atual, vizinhos_iter = pilha[-1]
            try:
                proximo = next(vizinhos_iter)
                if proximo not in visitados:
                    visitados.add(proximo)
                    pilha.append((proximo, iter(adj[proximo])))
            except StopIteration:
                pilha.pop()
                ordem.append(atual)

    return ordem


def dfs_coletar_componente(adj_transposto, vertice, visitados):
    """
    Segunda fase do algoritmo: coleta uma CFC no grafo transposto.

    O conjunto visitados e compartilhado entre as chamadas para garantir que
    cada vértice seja atribuído a apenas uma componente.
    """
    componente = []
    pilha = [vertice]
    visitados.add(vertice)

    while pilha:
        atual = pilha.pop()
        componente.append(atual)

        for vizinho in adj_transposto[atual]:
            if vizinho not in visitados:
                visitados.add(vizinho)
                pilha.append(vizinho)

    return componente


def kosaraju(adj):
    """
    Executa o algoritmo de Kosaraju-Sharir e retorna a lista de CFCs.

    A primeira DFS define a ordem de término; a segunda, no transposto,
    agrupa os vértices mutuamente alcançáveis.
    """
    adj_t = construir_grafo_transposto(adj)

    print("\nLista de adjacência do grafo original:")
    for v in sorted(adj):
        print(f"  {v}: {adj[v]}")

    print("\nLista de adjacência do grafo transposto:")
    for v in sorted(adj_t):
        print(f"  {v}: {adj_t[v]}")

    vertices = sorted(adj.keys())
    ordem_termino = dfs_ordem_termino(adj, vertices)
    print(f"\nOrdem de término (post-order): {ordem_termino}")

    visitados = set()
    sccs = []

    for v in reversed(ordem_termino):
        if v not in visitados:
            sccs.append(dfs_coletar_componente(adj_t, v, visitados))

    return sccs


def visualizar_sccs(adj, sccs, ponderado=False):
    """
    Desenha o dígrafo com cada CFC em uma cor diferente.

    A visualização é opcional e só roda se matplotlib e networkx estiverem
    instalados.
    """
    try:
        import matplotlib.pyplot as plt
        import networkx as nx
    except ImportError:
        print("\nNota: Para visualização gráfica, instale matplotlib e networkx:")
        print("  pip install matplotlib networkx")
        return

    G = nx.DiGraph()
    for u in adj:
        G.add_node(u)
        for v in adj[u]:
            G.add_edge(u, v)

    paleta = plt.cm.Set3([i / max(len(sccs), 1) for i in range(len(sccs))])

    mapa_cores = {}
    for i, scc in enumerate(sccs):
        for vertice in scc:
            mapa_cores[vertice] = paleta[i]

    lista_cores = [mapa_cores[v] for v in G.nodes()]
    pos = nx.spring_layout(G, seed=42)

    nx.draw(
        G,
        pos,
        with_labels=True,
        node_color=lista_cores,
        edge_color="gray",
        node_size=1000,
        font_size=12,
        arrows=True,
        arrowsize=20,
    )

    if ponderado:
        labels = nx.get_edge_attributes(G, "weight")
        nx.draw_networkx_edge_labels(G, pos, edge_labels=labels)

    for i, scc in enumerate(sccs):
        membros = ", ".join(sorted(scc))
        plt.plot([], [], "o", color=paleta[i], label=f"CFC {i + 1}: {{{membros}}}")
    plt.legend(loc="best", fontsize=9)

    plt.title("Componentes Fortemente Conectadas (Kosaraju-Sharir)")
    plt.show()


def main():
    """Lê o arquivo de entrada, executa o algoritmo e imprime as CFCs."""
    if len(sys.argv) >= 2:
        arquivo = sys.argv[1]
    else:
        # Sem argumento, usa o arquivo de exemplo ao lado do script.
        base_dir = os.path.dirname(os.path.abspath(__file__))
        arquivo = os.path.join(base_dir, "digrafo_scc.txt")

    print("=" * 60)
    print("ALGORITMO DE KOSARAJU-SHARIR")
    print("Identificação de Componentes Fortemente Conectadas (CFCs)")
    print("=" * 60)

    adj, eh_digrafo, ponderado = ler_grafo_arquivo(arquivo)
    if adj is None:
        return

    if not eh_digrafo:
        print("Erro: O algoritmo de Kosaraju-Sharir requer um dígrafo (grafo direcionado).")
        print("O arquivo fornecido define um grafo não direcionado (tipo 'G').")
        return

    sccs = kosaraju(adj)

    print("\n" + "=" * 60)
    print(f"Foram encontradas {len(sccs)} componente(s) fortemente conectada(s):")
    print("=" * 60)
    for i, scc in enumerate(sccs, 1):
        membros = ", ".join(sorted(scc))
        print(f"  CFC {i}: {{ {membros} }}")

    visualizar_sccs(adj, sccs, ponderado)


if __name__ == "__main__":
    main()

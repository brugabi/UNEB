import heapq
import itertools
import math
import os

os.environ.setdefault("MPLCONFIGDIR", "/tmp/matplotlib-codigo-base")
os.environ.setdefault("XDG_CACHE_HOME", "/tmp/codigo-base-cache")

import networkx as nx
import matplotlib

matplotlib.use("Agg")


def ler_grafo_arquivo(nome_arquivo):
    """
    Lê um arquivo de texto e cria um grafo/dígrafo (ponderado ou não).
    
    Formato esperado:
    1ª linha: [G|D] [N|W]
        G = Grafo não direcionado
        D = Dígrafo
        N = Não ponderado
        W = Ponderado
    Demais linhas:
        Se não ponderado: u v
        Se ponderado:     u v w
    """
    try:
        with open(nome_arquivo, "r") as f:
            linhas = [linha.strip() for linha in f if linha.strip()]

        if not linhas:
            print("Arquivo vazio!")
            return None, False

        # Definição do tipo de grafo.
        tipo, peso = linhas[0].split()
        if tipo == "G":
            G = nx.Graph()
        elif tipo == "D":
            G = nx.DiGraph()
        else:
            raise ValueError("Primeiro caractere deve ser 'G' ou 'D'.")

        if peso not in ("N", "W"):
            raise ValueError("Segundo caractere deve ser 'N' ou 'W'.")

        ponderado = (peso == "W")

        # Leitura das arestas
        for linha in linhas[1:]:
            partes = linha.split()
            if ponderado:
                if len(partes) != 3:
                    raise ValueError("Esperado formato 'u v w' para grafos ponderados.")
                u, v, w = partes
                adicionar_aresta(G, u, v, w, ponderado)
            else:
                if len(partes) != 2:
                    raise ValueError("Esperado formato 'u v' para grafos não ponderados.")
                u, v = partes
                adicionar_aresta(G, u, v, ponderado)  # peso padrão 1

        print(f"Grafo criado ({'dígrafo' if tipo=='D' else 'grafo'}, "
              f"{'ponderado' if ponderado else 'não ponderado'}) com "
              f"{G.number_of_nodes()} vértices e {G.number_of_edges()} arestas.")
        return G, ponderado

    except FileNotFoundError:
        print(f"Arquivo '{nome_arquivo}' não encontrado.")
        return None, False
    except ValueError as erro:
        print(f"Erro ao ler o arquivo: {erro}")
        return None, False


def adicionar_vertice(G, v):
    """Adiciona um vértice ao grafo, se não existir."""
    if v not in G:
        G.add_node(v)
        print(f"Vértice '{v}' adicionado.")
    else:
        print(f"Vértice '{v}' já existe.")


def adicionar_aresta(G, u, v, w=1, ponderado=False):
    """Adiciona uma aresta ao grafo."""
    if ponderado:
        G.add_edge(u, v, weight=float(w))
    else:
        G.add_edge(u, v)


def ler_opcao_com_padrao(mensagem, padrao):
    """Lê uma opção do usuário. Enter ou entrada encerrada usam o padrão."""
    try:
        valor = input(mensagem).strip()
    except EOFError:
        print()
        return padrao
    except KeyboardInterrupt:
        print()
        return padrao

    if not valor:
        return padrao
    return valor


def visualizar_grafo(G, ponderado=False, caminho=None, titulo="Visualização do Grafo"):
    """Desenha o grafo e destaca o menor caminho, se ele for informado."""
    import matplotlib.pyplot as plt

    pos = posicoes_em_grade(G)

    arestas_caminho = set()
    if caminho and len(caminho) > 1:
        arestas_caminho = set(zip(caminho, caminho[1:]))

    cores_arestas = []
    for u, v in G.edges():
        if isinstance(G, nx.DiGraph):
            esta_no_caminho = (u, v) in arestas_caminho
        else:
            esta_no_caminho = (u, v) in arestas_caminho or (v, u) in arestas_caminho
        cores_arestas.append("red" if esta_no_caminho else "black")

    nx.draw(
        G, pos, with_labels=True, node_color="lightblue",
        edge_color=cores_arestas, node_size=1000, font_size=12,
        arrows=isinstance(G, nx.DiGraph), arrowsize=20, width=2
    )

    if ponderado:
        labels = nx.get_edge_attributes(G, "weight")
        nx.draw_networkx_edge_labels(G, pos, edge_labels=labels)

    plt.title(titulo)
    plt.show()


def kruskal(G):
    """
    Calcula a árvore geradora mínima usando o algoritmo de Kruskal.
    A função retorna um novo grafo só com as arestas escolhidas.
    """
    arvore = nx.Graph()
    arvore.add_nodes_from(G.nodes())

    pai = {v: v for v in G.nodes()}
    ordem = {v: 0 for v in G.nodes()}

    def encontrar(v):
        while pai[v] != v:
            pai[v] = pai[pai[v]]
            v = pai[v]
        return v

    def unir(u, v):
        raiz_u = encontrar(u)
        raiz_v = encontrar(v)

        if raiz_u == raiz_v:
            return False

        if ordem[raiz_u] < ordem[raiz_v]:
            pai[raiz_u] = raiz_v
        elif ordem[raiz_u] > ordem[raiz_v]:
            pai[raiz_v] = raiz_u
        else:
            pai[raiz_v] = raiz_u
            ordem[raiz_u] += 1
        return True

    arestas = sorted(
        G.edges(data=True),
        key=lambda aresta: aresta[2].get("weight", 1)
    )

    for u, v, dados in arestas:
        if unir(u, v):
            arvore.add_edge(u, v, weight=dados.get("weight", 1))

        if arvore.number_of_edges() == G.number_of_nodes() - 1:
            break

    return arvore


def visualizar_arvore_geradora_minima(G, mst, nome_imagem):
    """Desenha e salva o grafo com as arestas da MST em azul."""
    import matplotlib.pyplot as plt

    pos = posicoes_em_grade(G)
    arestas_mst = {frozenset((u, v)) for u, v in mst.edges()}

    cores_arestas = []
    larguras = []
    for u, v in G.edges():
        if frozenset((u, v)) in arestas_mst:
            cores_arestas.append("blue")
            larguras.append(3)
        else:
            cores_arestas.append("gray")
            larguras.append(1.5)

    plt.figure(figsize=(8, 6))
    nx.draw(
        G, pos, with_labels=True, node_color="lightblue",
        edge_color=cores_arestas, width=larguras,
        node_size=1000, font_size=12
    )

    labels = {
        (u, v): formatar_custo(dados.get("weight", 1))
        for u, v, dados in G.edges(data=True)
    }
    nx.draw_networkx_edge_labels(G, pos, edge_labels=labels)

    plt.title("Árvore Geradora Mínima - Kruskal")
    plt.savefig(nome_imagem)
    print(f"Imagem salva em: {nome_imagem}")

    if "agg" in plt.get_backend().lower():
        plt.close()
    else:
        plt.show()


def obter_arestas_orientadas(G):
    """
    Retorna as arestas no sentido em que podem ser percorridas.
    Em grafo não direcionado, cada aresta entra nos dois sentidos.
    """
    arestas = []
    for u, v, dados in G.edges(data=True):
        peso = dados.get("weight", 1)
        arestas.append((u, v, peso))
        if not isinstance(G, nx.DiGraph):
            arestas.append((v, u, peso))
    return arestas


def existe_peso_negativo(G):
    """Verifica se o grafo tem alguma aresta com peso negativo."""
    for _, _, dados in G.edges(data=True):
        if dados.get("weight", 1) < 0:
            return True
    return False


def colocar_peso_padrao(G, peso=1):
    """Coloca o mesmo peso em todas as arestas de um grafo não ponderado."""
    for u, v in G.edges():
        G[u][v]["weight"] = peso


def normalizar_vertice(G, valor_digitado):
    """
    Procura um vértice sem diferenciar maiúsculas e minúsculas.
    O retorno mantém o nome original que veio no arquivo.
    """
    valor = valor_digitado.strip()

    if valor in G:
        return valor, None

    encontrados = [v for v in G.nodes() if str(v).casefold() == valor.casefold()]

    if len(encontrados) == 1:
        return encontrados[0], None
    if len(encontrados) > 1:
        return None, f"Vértice '{valor}' é ambíguo no grafo."

    return None, None


def reconstruir_caminho(antecessor, origem, destino):
    """Monta o caminho final usando o dicionário de antecessores."""
    caminho = []
    atual = destino

    while atual is not None:
        caminho.append(atual)
        if atual == origem:
            break
        atual = antecessor.get(atual)

    caminho.reverse()
    if not caminho or caminho[0] != origem:
        return []
    return caminho


def dijkstra(G, origem, destino):
    """
    Calcula o menor caminho com o algoritmo de Dijkstra.
    Esse algoritmo só é usado quando todos os pesos são não negativos.
    """
    if origem not in G or destino not in G:
        return None, []

    if existe_peso_negativo(G):
        raise ValueError("Dijkstra não pode ser usado com pesos negativos.")

    distancia = {v: float("inf") for v in G.nodes()}
    antecessor = {v: None for v in G.nodes()}
    distancia[origem] = 0

    fila = [(0, origem)]
    visitados = set()

    while fila:
        dist_atual, atual = heapq.heappop(fila)

        if atual in visitados:
            continue
        visitados.add(atual)

        if atual == destino:
            break

        for vizinho, dados in G[atual].items():
            peso = dados.get("weight", 1)
            nova_distancia = dist_atual + peso

            if nova_distancia < distancia[vizinho]:
                distancia[vizinho] = nova_distancia
                antecessor[vizinho] = atual
                heapq.heappush(fila, (nova_distancia, vizinho))

    if distancia[destino] == float("inf"):
        return None, []

    return distancia[destino], reconstruir_caminho(antecessor, origem, destino)


def bellman_ford(G, origem, destino):
    """
    Calcula o menor caminho com Bellman-Ford.
    Ele aceita pesos negativos e também detecta ciclo negativo alcançável.
    """
    if origem not in G or destino not in G:
        return None, [], False

    distancia = {v: float("inf") for v in G.nodes()}
    antecessor = {v: None for v in G.nodes()}
    distancia[origem] = 0

    arestas = obter_arestas_orientadas(G)

    for _ in range(len(G.nodes()) - 1):
        mudou = False
        for u, v, peso in arestas:
            if distancia[u] != float("inf") and distancia[u] + peso < distancia[v]:
                distancia[v] = distancia[u] + peso
                antecessor[v] = u
                mudou = True
        if not mudou:
            break

    for u, v, peso in arestas:
        if distancia[u] != float("inf") and distancia[u] + peso < distancia[v]:
            return None, [], True

    if distancia[destino] == float("inf"):
        return None, [], False

    return distancia[destino], reconstruir_caminho(antecessor, origem, destino), False


def formatar_custo(custo):
    """Mostra número inteiro sem casas decimais quando for possível."""
    if custo is None:
        return ""
    if float(custo).is_integer():
        return str(int(custo))
    return str(custo)


def exibir_resultado(nome_algoritmo, origem, destino, custo, caminho):
    """Exibe o resultado de um algoritmo de menor caminho."""
    print(f"\n{nome_algoritmo}")
    if not caminho:
        print(f"Não existe caminho de {origem} até {destino}.")
        return

    print(f"Menor caminho de {origem} até {destino}: {' -> '.join(caminho)}")
    print(f"Custo total: {formatar_custo(custo)}")


def dfs_trilhas(G, u, v, k):
    """
    Conta e exibe todos os caminhos simples (sem repetição de vértices)
    de u até v com comprimento <= k.
    Nota: proibir vértices repetidos corresponde a caminhos simples na teoria
    dos grafos; o termo "trilha" clássico proíbe apenas arestas repetidas.
    Retorna o número total de caminhos encontrados.
    """
    if u not in G or v not in G:
        print(f"Vértice '{u}' ou '{v}' não existe no grafo.")
        return 0

    if u == v and k >= 0:
        print(f"\nTrilhas simples de '{u}' até '{v}' com comprimento <= {k}:")
        print(f"  {u}")
        print("Total: 1 trilha(s).")
        return 1

    encontradas = []

    def _dfs(atual, visitados, caminho):
        if len(caminho) - 1 > k:
            return
        if atual == v and len(caminho) > 1:
            encontradas.append(list(caminho))
            return
        for vizinho in G.neighbors(atual):
            if vizinho not in visitados:
                visitados.add(vizinho)
                caminho.append(vizinho)
                _dfs(vizinho, visitados, caminho)
                caminho.pop()
                visitados.remove(vizinho)

    _dfs(u, {u}, [u])

    print(f"\nTrilhas simples de '{u}' até '{v}' com comprimento <= {k}:")
    if not encontradas:
        print("  Nenhuma trilha encontrada.")
    for trilha in encontradas:
        print("  " + " -> ".join(str(n) for n in trilha))
    print(f"Total: {len(encontradas)} trilha(s).")
    return len(encontradas)


def verificar_sequencia(G, S):
    """
    Recebe uma sequência de vértices S e classifica:
    - Passeio válido (todas as arestas existem)
    - Caminho (sem vértices repetidos)
    - Trilha (sem arestas repetidas)
    - Circuito (trilha fechada)
    """
    print(f"\nAnalisando sequência: {' -> '.join(str(n) for n in S)}")

    if len(S) < 2:
        print("Sequência muito curta para análise.")
        return

    # Passeio: todas as arestas da sequência devem existir
    for i in range(len(S) - 1):
        if not G.has_edge(S[i], S[i + 1]):
            print(f"  Aresta ({S[i]}, {S[i+1]}) não existe — não é um passeio válido.")
            return

    print("  Passeio válido: sim")

    # Caminho: nenhum vértice se repete
    if len(S) == len(set(S)):
        print("  Caminho (sem vértices repetidos): sim")
    else:
        repetidos = [n for n in set(S) if S.count(n) > 1]
        print(f"  Caminho (sem vértices repetidos): não  (repetidos: {repetidos})")

    # Trilha: nenhuma aresta se repete
    arestas = [(S[i], S[i + 1]) for i in range(len(S) - 1)]
    if not isinstance(G, nx.DiGraph):
        arestas_norm = [tuple(sorted(e)) for e in arestas]
    else:
        arestas_norm = arestas

    if len(arestas_norm) == len(set(arestas_norm)):
        eh_trilha = True
        print("  Trilha (sem arestas repetidas): sim")
    else:
        eh_trilha = False
        repetidas = [e for e in set(arestas_norm) if arestas_norm.count(e) > 1]
        print(f"  Trilha (sem arestas repetidas): não  (repetidas: {repetidas})")

    # Circuito: trilha fechada (primeiro == último)
    if S[0] == S[-1] and eh_trilha:
        print("  Circuito (trilha fechada): sim")
    elif S[0] == S[-1]:
        print("  Circuito (trilha fechada): não  (passeio fechado, mas com arestas repetidas)")
    else:
        print("  Circuito (trilha fechada): não  (sequência aberta)")


def posicoes_em_grade(G):
    """Gera posições simples em grade, sem usar layouts automáticos."""
    vertices = list(G.nodes())
    if not vertices:
        return {}

    colunas = math.ceil(math.sqrt(len(vertices)))
    pos = {}
    for i, vertice in enumerate(vertices):
        linha = i // colunas
        coluna = i % colunas
        pos[vertice] = (coluna, -linha)
    return pos


def preparar_grafo_simples(G):
    """
    Converte o grafo lido para um grafo simples não direcionado.
    Pesos não interferem no teste de planaridade.
    """
    if isinstance(G, nx.DiGraph):
        print("Esta tarefa considera apenas grafos simples não direcionados (G N ou G W).")
        return None

    if list(nx.selfloop_edges(G)):
        print("O arquivo possui laço. A tarefa pede grafo simples, então a entrada foi rejeitada.")
        return None

    simples = nx.Graph()
    simples.add_nodes_from(G.nodes())
    simples.add_edges_from(G.edges())
    return simples


def desenhar_embedding_planar(G, embedding, nome_imagem):
    """Desenha o grafo usando as posições obtidas do embedding planar."""
    import matplotlib.pyplot as plt

    pos = nx.combinatorial_embedding_to_pos(embedding)

    plt.figure(figsize=(8, 6))
    nx.draw(
        G,
        pos,
        with_labels=True,
        node_color="lightblue",
        edge_color="black",
        node_size=900,
        font_size=11,
        width=2,
    )
    plt.title("Embedding planar pelo algoritmo de Boyer-Myrvold")
    plt.axis("equal")
    plt.savefig(nome_imagem)
    print(f"Imagem gerada com embedding planar: {nome_imagem}")

    if "agg" in plt.get_backend().lower():
        plt.close()
    else:
        plt.show()


def contrair_vertices_grau_dois(H):
    """
    Contrai vértices de grau 2 para revelar o núcleo K5 ou K3,3.
    O subgrafo de Kuratowski pode vir como uma subdivisão desses grafos.
    """
    nucleo = nx.Graph(H)
    mudou = True

    while mudou:
        mudou = False
        for vertice in list(nucleo.nodes()):
            if nucleo.degree(vertice) != 2:
                continue

            vizinhos = list(nucleo.neighbors(vertice))
            if len(vizinhos) != 2:
                continue

            u, v = vizinhos
            nucleo.remove_node(vertice)
            if u != v:
                nucleo.add_edge(u, v)
            mudou = True
            break

    return nucleo


def classificar_kuratowski(kuratowski):
    """Identifica se o certificado é uma subdivisão de K5 ou de K3,3."""
    nucleo = contrair_vertices_grau_dois(kuratowski)
    vertices = list(nucleo.nodes())

    if len(vertices) == 5 and all(nucleo.degree(v) == 4 for v in vertices):
        return "K5", vertices, None, nucleo

    if len(vertices) == 6 and all(nucleo.degree(v) == 3 for v in vertices):
        for lado_a in itertools.combinations(vertices, 3):
            lado_a = set(lado_a)
            lado_b = set(vertices) - lado_a

            sem_arestas_internas = (
                nucleo.subgraph(lado_a).number_of_edges() == 0
                and nucleo.subgraph(lado_b).number_of_edges() == 0
            )
            todas_cruzadas = all(
                nucleo.has_edge(u, v) for u in lado_a for v in lado_b
            )

            if sem_arestas_internas and todas_cruzadas:
                particoes = (sorted(lado_a), sorted(lado_b))
                return "K3,3", vertices, particoes, nucleo

    return "Subdivisão de Kuratowski", vertices, None, nucleo


def exibir_kuratowski(kuratowski):
    """Mostra os vértices e arestas do subgrafo que prova a não planaridade."""
    tipo, principais, particoes, nucleo = classificar_kuratowski(kuratowski)

    print(f"Certificado identificado: {tipo}")
    print("Vértices envolvidos no subgrafo de Kuratowski:")
    print("  " + ", ".join(str(v) for v in kuratowski.nodes()))
    print("Arestas do subgrafo de Kuratowski:")
    for u, v in kuratowski.edges():
        print(f"  {u} - {v}")

    print("Vértices principais após contrair caminhos de grau 2:")
    print("  " + ", ".join(str(v) for v in principais))

    if particoes:
        lado_a, lado_b = particoes
        print("Partições do K3,3:")
        print("  A = {" + ", ".join(str(v) for v in lado_a) + "}")
        print("  B = {" + ", ".join(str(v) for v in lado_b) + "}")

    print("Arestas do núcleo contraído:")
    for u, v in nucleo.edges():
        print(f"  {u} - {v}")


def testar_planaridade_boyer_myrvold(G, nome_imagem):
    """
    Usa o teste de planaridade do NetworkX com certificado.
    Com True, a função retorna embedding se for planar ou Kuratowski se não for.
    """
    eh_planar, certificado = nx.check_planarity(G, True)

    if eh_planar:
        print("O grafo é planar.")
        print("Ordem circular dos vizinhos no embedding:")
        for vertice, vizinhos in certificado.get_data().items():
            print(f"  {vertice}: {vizinhos}")
        desenhar_embedding_planar(G, certificado, nome_imagem)
    else:
        print("O grafo NÃO é planar.")
        exibir_kuratowski(certificado)

    return eh_planar, certificado


if __name__ == "__main__":
    base_dir = os.path.dirname(os.path.abspath(__file__))

    try:
        print("=" * 50)
        print("PLANARIDADE - ALGORITMO DE BOYER-MYRVOLD")
        print("=" * 50)

        arquivo_padrao = "grafo.txt"

        print(f"Arquivo padrão: {arquivo_padrao}")
        print(f"Aperte Enter para usar {arquivo_padrao} ou digite outro arquivo.")

        nome_arquivo = ler_opcao_com_padrao(
            f"Informe o arquivo do grafo [{arquivo_padrao}]: ",
            arquivo_padrao
        )

        caminho_arquivo = nome_arquivo
        if not os.path.isabs(caminho_arquivo):
            caminho_arquivo = os.path.join(base_dir, nome_arquivo)

        print(f"\nArquivo lido: {nome_arquivo}")
        G, _ = ler_grafo_arquivo(caminho_arquivo)

        if G is None:
            raise SystemExit

        grafo_simples = preparar_grafo_simples(G)
        if grafo_simples is None:
            raise SystemExit

        print("Aperte Enter para salvar como embedding_planar.png ou digite outro nome.")
        nome_imagem = ler_opcao_com_padrao(
            "Informe o nome da imagem de saída [embedding_planar.png]: ",
            "embedding_planar.png"
        )

        caminho_imagem = nome_imagem
        if not os.path.isabs(caminho_imagem):
            caminho_imagem = os.path.join(base_dir, nome_imagem)

        testar_planaridade_boyer_myrvold(grafo_simples, caminho_imagem)
    except KeyboardInterrupt:
        print("\nExecução cancelada pelo usuário.")

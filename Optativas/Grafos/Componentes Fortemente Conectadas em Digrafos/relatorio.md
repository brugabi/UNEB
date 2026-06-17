# Componentes Fortemente Conectadas em Digrafos

## Algoritmo escolhido

Para este trabalho, escolhi o algoritmo de **Kosaraju-Sharir** para identificar componentes fortemente conectadas (CFCs) em um dígrafo. Ele foi escolhido por ser mais direto de explicar: primeiro calcula-se uma ordem de término por DFS no grafo original e, depois, usa-se essa ordem para percorrer o grafo transposto e separar as componentes.

Uma **Componente Fortemente Conectada** é um conjunto maximal de vértices em que cada vértice alcança todos os outros seguindo a direção das arestas.

## Kosaraju-Sharir e Tarjan

Kosaraju-Sharir e Tarjan resolvem o mesmo problema em tempo **O(V + E)**, mas seguem estratégias diferentes:

| Característica        | Kosaraju-Sharir                     | Tarjan          |
| --------------------- | ----------------------------------- | --------------- |
| Busca em profundidade | 2 passagens                         | 1 passagem      |
| Estrutura principal   | Grafo transposto + ordem de término | Pilha + lowlink |
| Intuição              | Mais simples de acompanhar          | Mais compacto   |

O Tarjan é mais econômico por não precisar construir o transposto, mas o Kosaraju-Sharir foi a opção adotada por ser mais didático para implementação e análise.

## Ideia do algoritmo

O algoritmo segue três etapas:

1. Fazer uma DFS no grafo original e registrar a ordem de término dos vértices.
2. Construir o grafo transposto, invertendo todas as arestas.
3. Processar os vértices na ordem inversa de término e executar DFS no transposto. Cada busca dessa segunda fase produz uma CFC.

Na implementação, as duas DFSs foram feitas de forma iterativa, com pilha explícita, para evitar depender do limite de recursão do Python.

## Grafo de teste

O arquivo `digrafo_scc.txt` usa o mesmo formato dos trabalhos anteriores:

```text
D N
A B
B C
C A
D E
E F
F D
G H
H I
I G
C D
F G
I J
```

Esse dígrafo possui 10 vértices e 12 arestas. Ele contém três ciclos principais ligados em cadeia e uma componente final com apenas um vértice:

- `{A, B, C}`
- `{D, E, F}`
- `{G, H, I}`
- `{J}`

As arestas `C -> D`, `F -> G` e `I -> J` conectam essas componentes, mas não criam novas CFCs porque não existe caminho de volta entre elas.

## Resultado obtido

Ao executar o programa, as componentes encontradas são:

| CFC | Vértices    |
| --- | ----------- |
| 1   | `{A, B, C}` |
| 2   | `{D, E, F}` |
| 3   | `{G, H, I}` |
| 4   | `{J}`       |

A última componente é unitária: o vértice `J` recebe uma aresta de `I`, mas não participa de nenhum ciclo.

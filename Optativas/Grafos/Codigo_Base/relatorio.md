# Relatório - Planaridade pelo algoritmo de Boyer-Myrvold

Esta atividade foi implementada no arquivo `codigobase.py`. O programa lê um grafo simples a partir de um arquivo texto, testa se ele é planar e mostra um certificado do resultado.

## Leitura do grafo

O formato de entrada é o mesmo usado nas aulas práticas. A primeira linha informa o tipo:

```text
G N
```

ou:

```text
G W
```

As demais linhas informam as arestas. Para `G N`, cada linha tem:

```text
u v
```

Para `G W`, cada linha tem:

```text
u v w
```

Nesta tarefa os pesos são ignorados, pois a planaridade depende apenas da estrutura das arestas. O programa rejeita dígrafos e laços, porque o enunciado pede grafo simples não direcionado.

## Algoritmo de Boyer-Myrvold

O algoritmo de Boyer-Myrvold é um teste de planaridade em tempo linear. A ideia geral é percorrer o grafo por uma busca em profundidade e tentar construir uma imersão planar enquanto as arestas de retorno são processadas. Durante esse processo, o algoritmo mantém informações sobre a ordem em que as arestas podem aparecer ao redor dos vértices.

Se todas as partes do grafo puderem ser inseridas sem cruzamentos, o algoritmo devolve um embedding planar, isto é, a ordem circular dos vizinhos em cada vértice. Se isso não for possível, o algoritmo encontra uma obstrução de Kuratowski: uma subdivisão de `K5` ou de `K3,3`.

No código foi usada a função permitida:

```python
nx.check_planarity(G, True)
```

O parâmetro `True` é importante porque obriga a função a retornar um certificado:

- se o grafo é planar, retorna um `PlanarEmbedding`;
- se o grafo não é planar, retorna um subgrafo de Kuratowski.

Não foram usadas as variantes proibidas do teste de planaridade, layouts automáticos ou busca automática pronta de subgrafo de Kuratowski.

## Embedding planar

Quando o grafo é planar, o programa imprime a ordem circular dos vizinhos armazenada no embedding. Depois converte esse embedding em posições de desenho usando:

```python
nx.combinatorial_embedding_to_pos(embedding)
```

Assim, a imagem gerada usa a estrutura planar retornada pelo teste, e não um layout genérico.

O código configura o Matplotlib com o backend `Agg` para salvar a imagem diretamente em arquivo. Isso evita dependência de janela gráfica no ambiente de execução e não altera o embedding usado no desenho.

## Subgrafo de Kuratowski

Quando o grafo não é planar, `nx.check_planarity(G, True)` retorna um subgrafo que prova a não planaridade. O programa exibe:

- os vértices envolvidos no subgrafo;
- as arestas desse subgrafo;
- o núcleo obtido depois de contrair vértices de grau 2.

Essa contração ajuda a identificar se a obstrução é uma subdivisão de `K5` ou de `K3,3`.

## Exemplos

Arquivo `grafo.txt`:

```text
G N
A B
B C
C D
D A
A C
```

Saída esperada:

```text
O grafo é planar.
Imagem gerada com embedding planar: embedding_planar.png
```

Arquivo `grafo_k5.txt`:

```text
G N
A B
A C
A D
A E
B C
B D
B E
C D
C E
D E
```

Saída esperada:

```text
O grafo NÃO é planar.
Certificado identificado: K5
```

Arquivo `grafo_k33.txt`:

```text
G N
A D
A E
A F
B D
B E
B F
C D
C E
C F
```

Saída esperada:

```text
O grafo NÃO é planar.
Certificado identificado: K3,3
```

# Instruções de tarefas do Teams

Este arquivo guarda os enunciados das tarefas recebidas pelo Teams para este projeto.
Quando houver uma nova tarefa, coloque o enunciado aqui antes de alterar o código.

## Tarefa atual - Planaridade pelo Algoritmo de Boyer-Myrvold

Prazo: 17 de junho de 2026, 23:59.

Fechamento: 22 de junho de 2026, 23:59.

### Instruções

Implemente ou inclua em código próprio, de forma controlada e explícita, o Algoritmo de Boyer-Myrvold para realizar o teste de planaridade em um grafo simples.

O código deverá:

- ler um grafo a partir de um arquivo texto, seguindo o mesmo formato utilizado nas aulas práticas;
- verificar se o grafo é planar utilizando o algoritmo de Boyer-Myrvold;
- caso o grafo seja planar, produzir um embedding planar, ou seja, uma imagem do grafo desenhado em sua forma planar;
- caso o grafo não seja planar, identificar e exibir um subgrafo de Kuratowski, `K5` ou `K3,3`, indicando os vértices envolvidos.

### Formato do arquivo de entrada

Mesmo formato dos trabalhos anteriores.

### Requisitos do programa

#### 1. Leitura do grafo

- ler o arquivo texto;
- construir o grafo usando NetworkX ou estruturas próprias.

#### 2. Execução do Algoritmo Boyer-Myrvold

O programa deve:

- aplicar o teste de planaridade baseado no algoritmo de Boyer-Myrvold;
- não usar funções de alto nível que façam tudo automaticamente sem que seja demonstrada compreensão;
- pode usar `networkx.check_planarity`, desde que o aluno explique o algoritmo Boyer-Myrvold no relatório e mostre no código como extrair embedding ou Kuratowski a partir do retorno da função.

#### 3. Saída do programa

Se o grafo for planar:

- exibir a mensagem `O grafo é planar.`;
- produzir uma representação planar;
- desenhar o grafo usando a estrutura de embedding retornada pela função;
- exibir ou gerar a imagem planar.

Se o grafo não for planar:

- exibir a mensagem `O grafo não é planar.`;
- mostrar o subconjunto de vértices que formam o subgrafo de Kuratowski identificado.

### Exemplo de saída esperada

Caso planar:

```text
Arquivo: exemplo1.txt
Resultado: O grafo é planar.
Imagem gerada com embedding planar.
```

Caso não planar:

```text
Arquivo: exemplo2.txt
Resultado: O grafo NÃO é planar.
Sequência dos vértices compondo o grafo de Kuratowski.
```

### Critérios de avaliação

- 4 pts: aplicação correta do Algoritmo de Boyer-Myrvold;
- 1 pt: leitura e construção adequada do grafo;
- 2 pts: identificação e exibição correta de embedding ou Kuratowski;
- 2 pts: clareza e organização do código;
- 1 pt: relatório curto explicando o algoritmo e como foi usado.

### Observações importantes

Não é necessário implementar Boyer-Myrvold do zero, pois é complexo, mas é obrigatório compreender o funcionamento.

O relatório deve conter uma explicação resumida do algoritmo, conforme visto em aula.

A visualização pode ser feita com NetworkX e Matplotlib.

Para garantir que o aluno compreenda e manipule diretamente a estrutura resultante do algoritmo de Boyer-Myrvold, é proibido utilizar funções que realizam automaticamente o teste de planaridade ou que produzem layouts que ignoram o embedding planar.

Não podem ser usadas:

- `nx.is_planar`;
- `nx.check_planarity(G, False)`;
- `nx.spring_layout`;
- `nx.circular_layout`;
- `nx.kamada_kawai_layout`;
- `nx.random_layout`;
- `nx.shell_layout`;
- qualquer função automática de detecção de subgrafos de Kuratowski, como `nx.algorithms.planarity.kuratowski_subgraph`.

Somente é permitido o uso de:

- `nx.check_planarity(G, True)`, obrigatoriamente com retorno do embedding ou certificado;
- `nx.combinatorial_embedding_to_pos(embedding)` para converter o embedding em coordenadas de desenho;
- funções básicas de manipulação e desenho de grafos do NetworkX e Matplotlib.

O desenho final deve necessariamente utilizar as posições obtidas a partir do embedding planar retornado pelo algoritmo.

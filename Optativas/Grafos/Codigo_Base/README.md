# Planaridade pelo algoritmo de Boyer-Myrvold

Este programa lê um grafo de um arquivo texto e testa se ele é planar. Para isso usa `nx.check_planarity(G, True)`, que retorna um certificado:

- se o grafo for planar, retorna um embedding planar;
- se o grafo não for planar, retorna um subgrafo de Kuratowski.

## Como executar

Instale as dependências, se ainda não estiverem instaladas:

```bash
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
```

Execute:

```bash
python codigobase.py
```

Ao pedir o arquivo, apertar Enter usa `grafo.txt`.

## Formato do arquivo

Primeira linha:

```text
G N
```

ou:

```text
G W
```

Depois informe as arestas:

```text
u v
```

ou, em grafo ponderado:

```text
u v w
```

Para planaridade, os pesos são ignorados.

## Exemplos

- `grafo.txt`: grafo planar usado como entrada padrão.
- `grafo_k5.txt`: exemplo não planar com `K5`.
- `grafo_k33.txt`: exemplo não planar com `K3,3`.

Quando o grafo é planar, a imagem é salva por padrão como `embedding_planar.png`, usando as posições obtidas do embedding retornado pelo algoritmo.

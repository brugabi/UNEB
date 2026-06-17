# Estudo Dirigido 4 — Introdução à Aprendizagem por Reforço

## Contexto

Atividade individual da disciplina de Inteligência Artificial.
Substitui a aula de 16/06. Vale até 1 ponto extra no Trabalho 4 (T4).

**Prazo:** 18/06/2026 às 11h  
**Entrega:** PDF com respostas manuscritas ou digitadas com as próprias palavras do aluno

### Critérios de avaliação
- **1,0** — completa, clara, sem erros graves e sem "rastros de IA"
- **0,5** — parcial, com erros graves ou suspeita de geração por IA
- **0,0** — não entregue, quase sem respostas ou claramente gerada por IA

**Atenção:** Respostas com suspeita de geração integral por IA zeram a pontuação.

## Material de referência

**Arquivo:** `RLbook2020-SuttonBarto.pdf` (548 páginas)  
Sutton & Barto, *Reinforcement Learning: An Introduction*, 2nd ed. (MIT Press, 2020)

> Nota: o PDF tem ~21 páginas de front matter antes da página 1 do livro.  
> Página do PDF ≈ página do livro + 21.

### Seções obrigatórias — páginas do livro (PDF)

| Seção | Título | Pág. livro | Pág. PDF |
|---|---|---|---|
| 1.1 | Reinforcement Learning | 1 | ~22 |
| 1.2 | Examples | 4 | ~25 |
| 1.3 | Elements of Reinforcement Learning | 6 | ~27 |
| 1.5 | An Extended Example: Tic-Tac-Toe | 8 | ~29 |
| 2.1 | A k-armed Bandit Problem | 25 | ~46 |
| 2.2 | Action-value Methods | 27 | ~48 |
| 2.4 | Incremental Implementation | 30 | ~51 |
| 3.1 | The Agent–Environment Interface | 47 | ~68 |
| 3.2 | Goals and Rewards | 53 | ~74 |
| 3.3 | Returns and Episodes | 54 | ~75 |
| 3.5 | Policies and Value Functions | 58 | ~79 |
| 3.6 | Optimal Policies and Optimal Value Functions | 62 | ~83 |
| 3.8 | Summary (opcional, recomendado) | 68 | ~89 |
| 6.1 | TD Prediction | 119 | ~140 |
| 6.4 | Sarsa: On-policy TD Control | 129 | ~150 |
| 6.5 | Q-learning: Off-policy TD Control | 131 | ~152 |
| 6.6 | Expected Sarsa | 133 | ~154 |

## Estrutura da atividade

### Parte A — Compreensão conceitual (10 questões)
1. RL vs Aprendizado Supervisionado — diferença principal
2. Agente aprende por interação — agente, ambiente, consequências ao longo do tempo
3. Dilema exploração vs explotação — o que é e por que importa
4. Multi-armed bandit — o que é e por que é simplificação do RL geral
5. Bandit vs MDP — pelo menos duas diferenças
6. Definições: estado, ação, recompensa, retorno, política
7. Valor de estado vs valor de ação — diferença intuitiva
8. Objetivo: maximizar retorno esperado — explicação simples
9. TD learning intuitivo — aprender de recompensas e estimativas futuras
10. Sarsa vs Q-learning — diferença intuitiva (sem prova formal)

### Parte B — Modelagem (1 questão)
11. Escolher um problema real simples e modelá-lo como RL:
    - estado, ações, recompensa, tipo de tarefa (episódica/contínua), objetivo do agente

### Parte C — Cálculo (2 questões)
12. **Retorno G₀** com γ=0,9, R₁=2, R₂=-1, R₃=4
    - Fórmula: G₀ = R₁ + γR₂ + γ²R₃
13. **Atualização Q-learning**: Q(s,a)=2,0, r=1,0, maxQ(s',a')=3,0, α=0,5, γ=0,9
    - Fórmula: Q(s,a) ← Q(s,a) + α[r + γ·maxQ(s',a') - Q(s,a)]

### Parte D — Reflexão (1 questão)
14. Por que RL é mais desafiador que aprendizado supervisionado? (máx. 10 linhas)

## Como este projeto está organizado

O papel do assistente aqui é:
- Explicar conceitos para que o aluno entenda
- Ajudar a verificar cálculos e raciocínio
- Sugerir estrutura e organização das respostas
- NÃO escrever as respostas pelo aluno

As respostas devem ser escritas pelo aluno com suas próprias palavras.

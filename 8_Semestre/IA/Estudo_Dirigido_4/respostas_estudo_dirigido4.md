# Estudo Dirigido 4 — Introdução à Aprendizagem por Reforço

---

## Parte A — Compreensão Conceitual

**1. O que distingue Aprendizagem por Reforço de Aprendizagem Supervisionada?**

No aprendizado supervisionado, o modelo treina com exemplos que já vêm com a resposta correta. Na aprendizagem por reforço, o agente não recebe esse gabarito. Ele precisa agir no ambiente e observar as recompensas para perceber quais escolhas funcionam melhor.

Outra diferença importante é que, em RL, uma decisão tomada agora pode mudar os estados e as recompensas que aparecem depois. Por isso o problema não é só acertar uma saída, mas aprender uma sequência de decisões.

---

**2. Por que se diz que, em RL, o agente aprende por interação com o ambiente?**

O agente aprende por interação porque ele não recebe todas as respostas prontas antes de começar. A cada passo, ele observa o estado do ambiente, escolhe uma ação e recebe uma recompensa junto com o novo estado.

Com isso, o agente vai ajustando seu comportamento com base nas consequências das ações. Uma escolha ruim pode diminuir a recompensa ou levar para uma situação pior; uma escolha boa pode aproximar o agente do objetivo.

---

**3. O que é o dilema entre exploração e explotação?**

A explotação acontece quando o agente escolhe a ação que parece melhor com base no que ele já aprendeu. A exploração ocorre quando ele testa outras ações para descobrir se alguma delas pode ser melhor do que a opção atual.

O dilema existe porque insistir apenas no que já parece bom pode prender o agente em uma solução fraca. Por outro lado, explorar demais faz com que ele deixe de aproveitar ações que já sabe que dão bons resultados. O aprendizado melhora quando existe equilíbrio entre testar e aproveitar.

---

**4. O que é um problema de multi-armed bandit?**

É um problema em que o agente precisa escolher entre k opções, como várias alavancas de uma máquina, sem saber de início qual delas costuma dar mais recompensa. O objetivo é acumular a maior recompensa possível ao longo das tentativas.

Ele é mais simples que um problema completo de RL porque não há mudança de estado nem planejamento de longo prazo. Cada escolha serve basicamente para receber uma recompensa e melhorar a estimativa sobre aquela opção.

---

**5. Em que um bandit difere de um MDP?**

No bandit, o agente decide sempre no mesmo contexto. Em um MDP, existem estados diferentes, então a melhor ação depende da situação atual.

Outra diferença é que, no bandit, a ação escolhida não muda a próxima situação do problema. No MDP, a ação pode levar para outro estado, e isso faz o agente considerar recompensas futuras, não apenas a recompensa imediata.

---

**6. Defina, com suas palavras: estado, ação, recompensa, retorno e política.**

Estado é a situação em que o ambiente se encontra em um determinado momento, do ponto de vista do agente.

Ação é a escolha que o agente faz quando está em um estado.

Recompensa é o valor numérico recebido depois da ação, indicando se o resultado foi positivo ou negativo.

Retorno é a soma das recompensas futuras a partir de certo ponto, normalmente com desconto para dar mais peso às recompensas mais próximas.

Política é a regra que indica qual ação o agente tende a escolher em cada estado.

---

**7. Qual a diferença entre valor de estado e valor de ação?**

O valor de estado indica quanto retorno se espera obter a partir de um estado, seguindo uma política.

O valor de ação indica o retorno esperado ao escolher uma ação específica em um estado e depois continuar seguindo a política.

Assim, o valor de estado avalia a situação em geral, enquanto o valor de ação avalia uma escolha concreta dentro daquela situação.

---

**8. O que significa dizer que o objetivo do agente é maximizar o retorno esperado?**

Significa que o agente deve escolher ações pensando no total de recompensas que pode receber ao longo da tarefa, e não só na recompensa imediata. Como podem existir incertezas no ambiente, fala-se em retorno esperado, ou seja, uma média do que o agente tende a ganhar se seguir determinada política.

---

**9. O que é TD learning em termos intuitivos?**

TD learning é uma forma de aprender atualizando estimativas durante a própria execução da tarefa. O agente não precisa esperar o episódio terminar para corrigir o valor de um estado.

A atualização usa a recompensa recebida agora e uma estimativa do valor do próximo estado. Se o resultado foi melhor ou pior que o esperado, a estimativa anterior é ajustada nessa direção.

---

**10. Qual a diferença básica entre Sarsa e Q-learning?**

Os dois métodos atualizam valores de ação, mas usam ideias diferentes sobre a próxima ação.

No Sarsa, a atualização usa a ação que o agente realmente escolheu no próximo estado. Por isso ele é chamado de on-policy.

No Q-learning, a atualização usa a melhor ação possível no próximo estado, isto é, o maior valor de Q. Por isso ele é off-policy: aprende como se fosse seguir a melhor ação, mesmo que a política usada para explorar escolha outra.

---

## Parte B — Modelagem

**11. Modelagem de um problema real como RL: Controle de Semáforo**

No caso de um cruzamento simples, o estado pode incluir o número de veículos parados em cada via e o tempo que o semáforo já está na fase verde atual.

As ações possíveis seriam manter a fase atual por mais alguns segundos ou trocar para liberar a outra via.

A recompensa pode ser o negativo da quantidade de carros esperando na fila. Assim, quanto maior o congestionamento, pior a recompensa.

A tarefa é contínua, pois o fluxo de carros não tem um fim natural como teria uma partida de jogo.

O objetivo do agente é ajustar os tempos do semáforo para reduzir a quantidade média de carros parados e evitar esperas muito longas em uma das vias.

---

## Parte C — Exercícios de Cálculo

**12. Cálculo do Retorno G₀**

Dados: R1 = 2, R2 = -1, R3 = 4, gamma = 0,9, tarefa episódica com T = 3.

G0 = R1 + gamma * R2 + (gamma^2) * R3
G0 = 2 + 0,9 * (-1) + (0,9^2) * 4
G0 = 2 - 0,9 + 0,81 * 4
G0 = 1,1 + 3,24
G0 = 4,34

O retorno G0 é 4,34.

---

**13. Atualização de Q-learning**

Dados: Q(s, a) = 2,0 | r = 1,0 | max Q(s', a') = 3,0 | alpha = 0,5 | gamma = 0,9

A regra de atualização do Q-learning é:
Q(s,a) = Q(s,a) + alpha * [r + gamma * max Q(s', a') - Q(s,a)]

Substituindo os valores:
Q(s,a) = 2,0 + 0,5 * [1,0 + 0,9 * 3,0 - 2,0]
Q(s,a) = 2,0 + 0,5 * [1,0 + 2,7 - 2,0]
Q(s,a) = 2,0 + 0,5 * [3,7 - 2,0]
Q(s,a) = 2,0 + 0,5 * [1,7]
Q(s,a) = 2,0 + 0,85
Q(s,a) = 2,85

O novo valor de Q(s,a) é 2,85.

---

## Parte D — Reflexão Final

**14. Por que Aprendizagem por Reforço costuma ser mais desafiadora do que Aprendizagem Supervisionada?**

Aprendizagem por reforço costuma ser mais desafiadora porque o agente não recebe respostas certas para copiar. Ele precisa testar ações no ambiente e aprender a partir das recompensas.

Além disso, uma ação pode ter efeito só depois de vários passos, o que dificulta saber qual escolha realmente causou uma recompensa boa ou ruim.

Também existe o equilíbrio entre explorar novas ações e aproveitar as que já parecem boas. Como as ações mudam os próximos estados, o problema fica mais dinâmico do que apenas treinar com uma base fixa de exemplos.

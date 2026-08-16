# Decisões do Projeto: Mini Framework de Jogos de Cartas

## Escopo do Trabalho (Enunciado)

O objetivo do trabalho é **desenvolver um mini framework orientado a objetos e reutilizável para jogos de cartas**, aplicando os conceitos estudados na disciplina. O foco é o **projeto arquitetural** (decisões justificadas) e a **implementação**, acompanhada de **testes automatizados**.

O framework deve servir de base para diferentes jogos (Blackjack, Uno, Truco, Poker, Super Trunfo, etc.), reutilizando a maior parte do código e deixando apenas os elementos específicos de cada jogo para implementação posterior.

**Abstrações mínimas exigidas (6):**
1.  Cartas
2.  Baralho
3.  Jogadores
4.  Mão de cartas
5.  Regras do jogo
6.  Partida

**Flexibilidades exigidas:** a solução deve permitir que diferentes jogos possuam:
*   diferentes tipos de cartas
*   diferentes formas de distribuir cartas
*   diferentes regras
*   diferentes formas de vencer uma partida
*   diferentes estratégias de tomada de decisão dos jogadores
*   diferentes eventos durante a partida

**Restrições do enunciado:**
*   partidas entre **dois jogadores**, com possibilidade de expansão para mais
*   variação de tipos de jogadores (**humanos e/ou automatizados**)
*   jogo funcionando em **console**

**Entregáveis e critérios de avaliação:**
*   Projeto arquitetural + implementação do framework e de pelo menos um jogo cliente.
*   Decisões justificadas: responsabilidades das classes, relacionamentos, herança/composição, baixo acoplamento, alta coesão, especialista da informação e pontos de extensão.
*   Pelo menos **4 padrões GoF** indicados (onde foram usados e por quê), com cuidado para não cair em **overengineering**.
*   Justificativa da aplicação dos princípios **SOLID**.
*   Testes automatizados e documentação javadoc da API pública.

## Escopo × Implementação

| Exigência do escopo | Implementação |
|---|---|
| Abstração **Cartas** | `Carta` (`framework/cartas`) + `CartaSuperTrunfo` (`game/models`)
| Abstração **Baralho** | `Baralho` (`framework/baralho`) + `BaralhoPadrao` (`game/models`)
| Abstração **Jogadores** | `Jogadores` (`framework/jogadores`) + `JogadorPadrao` (`game/models`)
| Abstração **Mão de cartas** | `Mao` (`framework/jogadores`) + `MaoPadrao` (`game/models`)
| Abstração **Regras do jogo** | `Regras` (`framework/regras`) + `RegrasSuperTrunfo` (`game/models`)
| Abstração **Partida** | `Partida` abstrata com Template Method (`framework/partida`) + `PartidaSuperTrunfo` (`game/partida`)
| Diferentes **tipos de cartas** | cada jogo cliente implementa a sua `Carta` (no Super Trunfo, mapa de atributos dinâmico — atributo novo no JSON não exige código novo)
| Diferentes **formas de distribuir cartas** | `Regras.distribuirCartas(...)` — a distribuição é regra do jogo, não do framework
| Diferentes **regras** | `Regras` (contrato) — comparar e distribuir variam por jogo
| Diferentes **formas de vencer** | `Partida.declararVencedor()` é abstrato; cada jogo decide o critério
| **Estratégias de decisão** dos jogadores | `EstrategiaEscolhaAtributo` + implementações `EstrategiaMelhorAtributo`, `EstrategiaAleatoria`, `EstrategiaHumana`
| **Eventos durante a partida** | `Observer` + `Partida.notificar()` — a view recebe os eventos sem o jogo conhecer a apresentação
| **Expansão para mais jogadores** | `PartidaSuperTrunfo` recebe uma lista de N jogadores (2+), cada um com a sua estratégia
| **Jogadores humanos e/ou automatizados** | estratégia por jogador no `Map<Jogadores, EstrategiaEscolhaAtributo>` (humano digita; computador usa IA)
| **Console** | views de console: `SuperTrunfoView` (Observer) e `MainSuperTrunfo` (entrada)
| **4+ padrões GoF** | Template Method, Strategy (2 frentes), Observer, Factory Method — seção abaixo com localização e justificativa
| **SOLID justificado** | seção "Padrões Arquiteturais e Princípios" abaixo
| **Testes automatizados** | 43 testes JUnit 5 em `tests/` (framework + Super Trunfo)
| **Reutilização da maior parte do código** | `framework/` não importa nada de `game/`; novo jogo implementa só o específico (guia "Como adicionar um novo jogo" abaixo)

## Visão Geral do Projeto
Desenvolvimento de um mini framework orientado a objetos e reutilizável para jogos de cartas em console (`cardgames`). A solução utiliza princípios SOLID, padrões GRASP e padrões de projeto GoF para garantir flexibilidade e criação de pontos de extensão. A estrutura separa a infraestrutura reutilizável (`framework`) dos jogos clientes (`game`), permitindo adicionar novos jogos (ex.: Blackjack, Truco) sem alterar a base.

## Jogo Cliente Inicial
**Super Trunfo**
*   **Tema:** Street Fighter.
*   **Elenco:** 32 personagens (foco em Street Fighter 6, além de Dudley e Makoto).
*   **Super Trunfo:** Akuma.
*   **Atributos das Cartas:** Força (Power), Saúde/Resistência (Health), Mobilidade (Mobility), Técnicas (Techniques), Alcance (Range). Valores na escala de 1 a 100.
*   **Armazenamento de Dados:** Os dados das cartas são consumidos e instanciados a partir do arquivo JSON `data/trunfoChars.json`, lido por `LeitorCartasFactory` com Gson.

## Padrões de Projeto (GoF) Selecionados — Status
*   **Factory Method** ✅ *implementado* — `LeitorCartasFactory` (em `game/models`) lê o JSON e cria as cartas `CartaSuperTrunfo` para compor o baralho.
*   **Template Method** ✅ *implementado* — `Partida` (em `framework/partida`) define o fluxo base (`inicializarJogo` → rodadas → `declararVencedor`), com passos abstratos que cada jogo implementa. O fluxo também já dispara notificações para observadores.
*   **Strategy** ✅ *implementado* — Duas frentes:
    *   Comparação por atributos: `EstrategiaComparacao` + `ComparadorSuperTrunfo` (regra específica do Super Trunfo, escopada ao cliente `game/models` — Blackjack soma valores; Truco compara com contexto da vira).
    *   Decisão de jogadores: `EstrategiaEscolhaAtributo` com implementações `EstrategiaMelhorAtributo` (escolhe o atributo mais forte da própria carta), `EstrategiaAleatoria` e `EstrategiaHumana` (input do console). Cada jogador tem a sua estratégia (`Map<Jogadores, EstrategiaEscolhaAtributo>`), permitindo misturar humanos e computadores — trocar a estratégia não exige alterar a partida.
*   **Observer** ✅ *integrado* — `Observer` (em `framework/partida`) define `notificar(String)`, e `Partida` agora gerencia observadores (`adicionarObserver`) e dispara eventos. A view de console `SuperTrunfoView` (em `game/view`) implementa o Observer e exibe tudo, sem conhecer as regras.
*   *(Nota sobre Decorator: Evitado para prevenir engenharia excessiva (overengineering), já que as regras do Super Trunfo são fixas.)*

## Padrões Arquiteturais e Princípios (SOLID / GRASP)
*   **MVC (Padrão Arquitetural):** ✅ *implementado* — Lógica (model) em `game/models` e `game/partida`, view em `game/view/SuperTrunfoView` (Observer do framework) e controller em `game/controller/SuperTrunfoController`, que monta as dependências (baralho, jogadores, estratégias, view) e dispara a partida.
*   **Information Expert (GRASP):** A própria classe de carta mantém o conhecimento sobre seus valores e os retorna por getters — ✅ implementado em `CartaSuperTrunfo`. Os atributos ficam em um mapa `nome → valor`, então qualquer atributo novo no JSON é carregado sem alterar código.
*   **Controller (GRASP):** ✅ implementado — `SuperTrunfoController` coordena a montagem e o início da partida.
*   **SRP (SOLID):** Separação estrita das tarefas (ex.: a mão de cartas não sabe nada sobre a leitura do arquivo JSON; a view não sabe nada das regras).
*   **OCP (SOLID):** O design permite incluir novos temas e jogos sem alterar a lógica base. As abstrações `Carta`, `Baralho`, `Jogadores`, `Mao`, `Regras` e `Partida` são os pontos de extensão — as implementações concretas (`BaralhoPadrao`, `JogadorPadrao`, `MaoPadrao`, `CartaSuperTrunfo`, estratégias) ficam no jogo cliente.
*   **DIP e ISP (SOLID):** Interfaces focadas e injeção de dependência evitam que a infraestrutura dependa de lógicas específicas do jogo cliente.

## Como rodar
*   Compilar: `javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar" -d out $(find src -name "*.java")`
*   Jogar Super Trunfo: `java -cp "out:lib/gson-2.10.1.jar" game.MainSuperTrunfo`
*   Teste da fábrica de cartas: `java -cp "out:lib/gson-2.10.1.jar" game.MainTeste`
*   Rodar os testes automatizados (JUnit 5):
    ```bash
    javac -encoding UTF-8 -cp "out:lib/gson-2.10.1.jar:lib/junit-platform-console-standalone-1.11.3.jar" -d out-tests $(find tests -name "*.java")
    java -jar lib/junit-platform-console-standalone-1.11.3.jar --class-path "out:out-tests:lib/gson-2.10.1.jar" --scan-class-path
    ```
*   Gerar o javadoc da API pública:
    ```bash
    javadoc -encoding UTF-8 -charset UTF-8 -d docs -classpath "out:lib/gson-2.10.1.jar" -sourcepath src -subpackages framework:game
    ```

## Regras do Super Trunfo implementadas
*   Partida com **N jogadores** (o jogo padrão usa 2: humano vs computador), cada um com a sua estratégia de escolha de atributo. A distribuição alternada é feita por `RegrasSuperTrunfo.distribuirCartas()` (32 cartas no modo 2 jogadores).
*   A cada rodada, os jogadores que ainda têm cartas viram a carta do topo; quem está na vez escolhe o atributo a disputar.
*   Vence a rodada quem tem o maior valor no atributo escolhido e leva as cartas viradas (e o monte, se houver) para o fundo da própria mão; o vencedor escolhe o atributo na próxima rodada.
*   Empate no topo: as cartas vão para o monte e quem está na vez continua escolhendo na próxima rodada.
*   Carta Super Trunfo (Akuma): vence qualquer carta, independentemente do atributo escolhido (regra na `EstrategiaComparacao`).
*   Fim de partida: quando sobrar no máximo um jogador com cartas; vence quem tem mais cartas na mão (desempate por rodadas vencidas).
*   Limite de segurança de 1000 rodadas (`MAX_RODADAS`): garante o término da partida mesmo quando as cartas circulam entre as mãos sem ninguém zerar (comum com estratégias determinísticas); no limite, vence quem tem mais cartas.

## Requisitos do Trabalho — Atendimento
1.  **API pública claramente definida** ✅ — Interfaces/classes do framework com javadoc completo em `docs/` (gerado com a ferramenta `javadoc`).
2.  **Pelo menos cinco pontos de extensão** ✅ — Nove: `Carta`, `Baralho`, `Jogadores`, `Mao`, `Regras`, `Partida`, `Observer`, `EstrategiaComparacao` e `EstrategiaEscolhaAtributo` (detalhados nas seções "Abstrações do Framework" e "Padrões de Projeto").
3.  **Separação entre código da solução e aplicações clientes** ✅ — `src/framework` (infraestrutura) vs `src/game` (cliente Super Trunfo). O framework não importa nada de `game`.
4.  **Pelo menos uma aplicação cliente** ✅ — Super Trunfo completo (`game/`), jogável via `MainSuperTrunfo`.
5.  **Utilização de interfaces e classes abstratas** ✅ — 9 interfaces (`Carta`, `Baralho`, `Jogadores`, `Mao`, `Regras`, `Observer`, `EstrategiaComparacao`, `EstrategiaEscolhaAtributo`) e 1 classe abstrata (`Partida`).
6.  **Tratamento adequado de exceções** ✅ — Exceção de domínio `BaralhoVazioException`; `MaoPadrao` lança `IndexOutOfBoundsException` para índice inválido; `LeitorCartasFactory` captura erros de leitura do JSON e retorna lista vazia; `EstrategiaHumana` valida entrada inválida do usuário em laço.
7.  **Encapsulamento das coleções internas** ✅ — As listas internas de `BaralhoPadrao`, `MaoPadrao` e o mapa de `CartaSuperTrunfo` são privados; o acesso é apenas via métodos de negócio (e cópias defensivas, ex.: `getNomesAtributos()`).
8.  **Testes automatizados** ✅ — 43 testes JUnit 5 em `tests/` (framework + Super Trunfo), executáveis pelo console.
9.  **Documentação javadoc da API pública** ✅ — Gerada em `docs/` sem avisos.
10. **Diagrama de classes simplificado** ✅ — Seção abaixo neste documento (especificação escrita).
11. **Exemplos de utilização** ✅ — `MainTeste` (fábrica) e `MainSuperTrunfo` (jogo completo) + seção "Como adicionar um novo jogo".
12. **Justificativa das decisões de projeto** ✅ — Seção "Justificativas" abaixo neste documento.

## Diagrama de Classes (simplificado)

### Camada Framework (infraestrutura reutilizável — apenas abstrações)
```text
        «interface»                    «interface»                    «interface»
        Carta                          Baralho                        Mao
        ─────────                      ─────────                      ─────────
        +getNome(): String             +embaralhar(): void            +adicionarCarta(Carta): void
                                       +comprarCarta(): Carta         +removerCarta(int): Carta
                                       +temCartas(): boolean          +tamanho(): int
                                                                      +estaVazia(): boolean

        «interface»                    «interface»
        Jogadores                      Observer
        ─────────                      ─────────
        +getNome(): String             +notificar(String): void
        +receberCarta(Carta): void
        +getMao(): Mao

        «interface» Regras
        ─────────────────
        +distribuirCartas(Baralho, List<? extends Jogadores>): void
        +comparar(Carta, Carta, String): int

        Partida (abstrata — Template Method + Observer)
        ────────────────────────────────────────────────
        +adicionarObserver(Observer): void
        +iniciarPartida(): void  «final»
        #notificar(String): void
        #inicializarJogo(): void        «abstrato»
        #jogarRodada(): void            «abstrato»
        #isFimDePartida(): boolean      «abstrato»
        #declararVencedor(): void       «abstrato»
```

### Camada Cliente (game — Super Trunfo)
```text
        LeitorCartasFactory (Factory Method)
        ────────────────────────────────────
        +criarCartas(String caminho): List<CartaSuperTrunfo>  «static»

        CartaSuperTrunfo  implements Carta
        ────────────────────────────────
        +getNomesAtributos(): List<String>
        +getValorAtributo(String): int
        +getForca(): int  +getSaude(): int  +getMobilidade(): int
        +getTecnicas(): int  +getAlcance(): int  +isSuperTrunfo(): boolean

        BaralhoPadrao  implements Baralho      JogadorPadrao  implements Jogadores
        (baralho do Super Trunfo)              (jogador com nome + mão)
        MaoPadrao  implements Mao
        (mão de cartas do Super Trunfo)

        «interface» EstrategiaComparacao          «interface» EstrategiaEscolhaAtributo
        +comparar(Carta, Carta, String): int      +escolherAtributo(CartaSuperTrunfo, List<String>): String
             ▲                                                  ▲
             │                                                  ├── EstrategiaMelhorAtributo
        ComparadorSuperTrunfo                                   ├── EstrategiaAleatoria
             (regra do super trunfo)                            └── EstrategiaHumana (em game/controller)

        PartidaSuperTrunfo  extends Partida
        ────────────────────────────────────
        usa: Baralho, List<Jogadores> (N jogadores), Regras,
             Map<Jogadores, EstrategiaEscolhaAtributo> e Observer

        SuperTrunfoController  →  monta dependências e chama partida.iniciarPartida()
        SuperTrunfoView        →  implements Observer (exibe mensagens no console)
```

### Fluxo da partida (Template Method)
```text
iniciarPartida()
  ├─ inicializarJogo()          // embaralha + distribui cartas
  ├─ enquanto !isFimDePartida()
  │    └─ jogarRodada()         // vira cartas, escolhe atributo, compara, distribui
  └─ declararVencedor()
```

## Justificativas das Decisões de Projeto
*   **Interfaces mínimas (ISP):** cada contrato do framework tem só o essencial. `Carta` exige apenas `getNome()` — o mínimo comum a qualquer jogo de cartas — e deixa os dados específicos (atributos, naipes) para cada cliente. Isso evita que o framework precise conhecer regras de jogo nenhum.
*   **Template Method em `Partida`:** todos os jogos de cartas seguem o mesmo esqueleto (preparar → rodadas → vencedor). Fixar essa ordem em `iniciarPartida()` (final) garante consistência e obriga o jogo cliente a implementar apenas os passos, sem poder quebrar o fluxo.
*   **Strategy para comparação e decisão:** a regra de "quem vence" varia muito entre jogos (Super Trunfo compara atributo; Blackjack soma; Truco compara com a vira). Por isso a comparação é uma estratégia no cliente. O mesmo vale para a decisão do computador: trocar `EstrategiaMelhorAtributo` por `EstrategiaAleatoria` muda a dificuldade sem tocar na partida.
*   **Observer para a view (MVC):** a partida só emite mensagens via `notificar()`. A view é plugada de fora (`adicionarObserver`) e pode ser substituída (console → GUI) sem alterar o jogo. Isso concretiza o desacoplamento view/model exigido pelo MVC.
*   **Atributos dinâmicos em `CartaSuperTrunfo` (mapa nome → valor):** adicionar um atributo novo (ex.: "sorte") no JSON funciona sem mudar código, pois o comparador e a partida leem os nomes do próprio mapa. Reforça o OCP e simplifica a criação de novos temas.
*   **Framework enxuto — só abstrações no framework:** `Carta`, `Baralho`, `Jogadores`, `Mao`, `Regras`, `Partida` e `Observer` definem apenas os contratos e o fluxo. As implementações concretas (`BaralhoPadrao`, `JogadorPadrao`, `MaoPadrao`, `CartaSuperTrunfo`, estratégias) vivem no jogo cliente (`game/`). Assim o framework não se acopla a nenhum jogo, e cada jogo novo traz as suas implementações (podendo copiar as do Super Trunfo como ponto de partida).
*   **Encapsulamento das coleções:** as listas internas são privadas e expostas apenas via métodos de negócio, com cópias defensivas onde necessário. Impede que um jogo cliente corrompa o estado do baralho/mão (ex.: remover carta fora do fluxo da partida).
*   **Exceções de domínio:** `BaralhoVazioException` comunica o erro em termos do jogo (em vez de um genérico) e é não verificada, pois representa estado inválido — o jogo cliente decide se quer tratá-la.
*   **Factory Method com Gson:** separa a origem dos dados (JSON) da criação dos objetos de jogo; o baralho recebe a lista pronta e não sabe de onde vieram as cartas (SRP).
*   **Evitar overengineering:** padrões como Decorator foram descartados porque as regras do Super Trunfo são fixas e não há requisito de composição dinâmica de comportamento — aplicar mais padrões sem necessidade violaria o YAGNI.

## Abstrações do Framework (implementadas)
*   `Carta` (`framework/cartas`) — contrato mínimo: `getNome()`.
*   `Baralho` (`framework/baralho`) — `embaralhar()`, `comprarCarta()`, `temCartas()`. Implementação concreta no cliente: `BaralhoPadrao` (`game/models`).
*   `Jogadores` (`framework/jogadores`) — contrato mínimo: `getNome()`, `receberCarta(Carta)`, `getMao()`. Implementação concreta no cliente: `JogadorPadrao` (`game/models`).
*   `Mao` (`framework/jogadores`) — mão de cartas: `adicionarCarta`, `removerCarta`, `tamanho`, `estaVazia`. Implementação concreta no cliente: `MaoPadrao` (`game/models`).
*   `Regras` (`framework/regras`) — contrato das regras do jogo: `distribuirCartas(Baralho, List<? extends Jogadores>)` e `comparar(Carta, Carta, String)`. É a abstração que permite a cada jogo distribuir e comparar do seu jeito (implementada no cliente por `RegrasSuperTrunfo`).
*   `Partida` (`framework/partida`) — Template Method do fluxo de partida + gerenciamento de observadores (`adicionarObserver`, `notificar`).
*   `Observer` (`framework/partida`) — notificação de eventos para a view.
*   `BaralhoVazioException` (`framework/excecoes`) — exceção de domínio do framework para operações inválidas sobre baralho vazio.

## Estrutura de Diretórios
```text
/cardgames
├── /src
│   ├── /framework          (A infraestrutura base e reutilizável)
│   │   ├── /cartas         Carta
│   │   ├── /baralho        Baralho
│   │   ├── /jogadores      Jogadores, Mao
│   │   ├── /regras         Regras
│   │   ├── /partida        Partida, Observer
│   │   └── /excecoes       BaralhoVazioException
│   │
│   └── /game               (Os jogos clientes — hoje: Super Trunfo)
│       ├── /models         CartaSuperTrunfo, ComparadorSuperTrunfo,
│       │                   EstrategiaComparacao, LeitorCartasFactory,
│       │                   EstrategiaEscolhaAtributo, EstrategiaMelhorAtributo,
│       │                   EstrategiaAleatoria, RegrasSuperTrunfo,
│       │                   BaralhoPadrao, JogadorPadrao, MaoPadrao
│       ├── /partida        PartidaSuperTrunfo (regras do jogo)
│       ├── /view           SuperTrunfoView (Observer de console)
│       ├── /controller     SuperTrunfoController, EstrategiaHumana
│       ├── MainSuperTrunfo (ponto de entrada)
│       └── MainTeste       (teste da fábrica de cartas)
│
├── /tests                  (43 testes JUnit 5 — framework + Super Trunfo)
├── /docs                   (javadoc gerado da API pública)
├── /data                   (trunfoChars.json)
├── /lib                    (gson-2.10.1.jar — leitura JSON;
│                            junit-platform-console-standalone-1.11.3.jar — testes)
└── spec.md                 (Documentação de decisões)
```

## Como adicionar um novo jogo (ex.: Batalha, Blackjack, Truco)
1.  Implemente `Carta` (ou reutilize uma existente) e crie as cartas do novo jogo.
2.  Crie as implementações concretas das abstrações no seu jogo (no Super Trunfo são `BaralhoPadrao`, `JogadorPadrao` e `MaoPadrao`, em `game/models` — use-as como ponto de partida) e monte os jogadores (um por participante, cada um com a sua estratégia).
3.  Implemente `Regras` com a distribuição e a comparação do novo jogo (ou reutilize `RegrasSuperTrunfo` se a mecânica for igual).
4.  Crie uma subclasse de `Partida` em `game/<jogo>/` implementando os 4 passos abstratos; use `notificar()` para reportar eventos.
5.  Crie uma view implementando `Observer` e um controller que monta as dependências (ou reutilize `SuperTrunfoView`).
6.  Nada no `framework/` precisa ser alterado para suportar o novo jogo.

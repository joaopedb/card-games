# Decisões do Projeto: Mini Framework de Jogos de Cartas

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
*   **Template Method** ✅ *implementado* — `Partida` (em `framework/partida`) define o fluxo base (`inicializarJogo` → rodadas → `declararVencedor`), com passos abstratos que cada jogo implementa.
*   **Strategy** ✅ *parcial* — A comparação por atributos numéricos está implementada (`EstrategiaComparacao` + `ComparadorSuperTrunfo`), porém **escopada ao cliente do jogo** (`game/models`): comparar atributos é regra específica do Super Trunfo, não infraestrutura reutilizável (Blackjack soma valores; Truco compara com contexto da vira). ⏳ *pendente*: estratégias de decisão de jogadores virtuais automatizados.
*   **Observer** ✅ *interface criada* — `Observer` (em `framework/partida`) define `notificar(String)`. A integração com a view/console ainda não foi ligada.
*   *(Nota sobre Decorator: Evitado para prevenir engenharia excessiva (overengineering), já que as regras do Super Trunfo são fixas.)*

## Padrões Arquiteturais e Princípios (SOLID / GRASP)
*   **MVC (Padrão Arquitetural):** Lógica (model) separada da interação do console (view). ✅ *models* implementados em `game/models`; ⏳ *views* e *controllers* pendentes.
*   **Information Expert (GRASP):** A própria classe de carta mantém o conhecimento sobre seus valores e os retorna por getters — ✅ implementado em `CartaSuperTrunfo`.
*   **Controller (GRASP):** Coordenará a entrada de comandos e o andamento da partida — ⏳ pendente.
*   **SRP (SOLID):** Separação estrita das tarefas (ex.: a mão de cartas não sabe nada sobre a leitura do arquivo JSON).
*   **OCP (SOLID):** O design permite incluir novos temas e jogos sem alterar a lógica base. As abstrações `Carta`, `Baralho`, `Jogadores`, `Mao` e `Partida` são os pontos de extensão.
*   **DIP e ISP (SOLID):** Interfaces focadas e injeção de dependência evitam que a infraestrutura dependa de lógicas específicas do jogo cliente.

## Abstrações do Framework (implementadas)
*   `Carta` (`framework/cartas`) — contrato mínimo: `getNome()`.
*   `Baralho` (`framework/baralho`) — `embaralhar()`, `comprarCarta()`, `temCartas()`.
*   `Jogadores` (`framework/jogadores`) — contrato mínimo: `getNome()`, `receberCarta(Carta)`, `getMao()`.
*   `Mao` (`framework/jogadores`) — mão de cartas: `adicionarCarta`, `removerCarta`, `tamanho`, `estaVazia`; com implementação padrão `MaoPadrao` (reutilizável por qualquer jogo).
*   `Partida` (`framework/partida`) — Template Method do fluxo de partida.
*   `Observer` (`framework/partida`) — notificação de eventos para a view.

## Estrutura de Diretórios
```text
/cardgames
├── /src
│   ├── /framework          (A infraestrutura base e reutilizável)
│   │   ├── /cartas         Carta
│   │   ├── /baralho        Baralho
│   │   ├── /jogadores      Jogadores, Mao, MaoPadrao
│   │   └── /partida        Partida, Observer
│   │
│   └── /game               (Os jogos clientes — hoje: Super Trunfo)
│       └── /models         CartaSuperTrunfo, ComparadorSuperTrunfo,
│                           EstrategiaComparacao, LeitorCartasFactory
│       (views e controllers: pendentes)
│
├── /data                   (trunfoChars.json)
├── /lib                    (gson-2.10.1.jar — dependência de leitura JSON)
└── spec.md                 (Documentação de decisões)
```

with open('/mnt/data/spec.md', 'w', encoding='utf-8') as f:
    f.write("""# Decisões do Projeto: Mini Framework de Jogos de Cartas

## Visão Geral do Projeto
Desenvolvimento de um mini framework orientado a objetos e reutilizável para jogos de cartas em console. A solução utilizará princípios SOLID, padrões GRASP e padrões de projeto GoF para garantir flexibilidade e criação de pontos de extensão.

## Jogo Cliente Inicial
**Super Trunfo**
*   **Tema:** Street Fighter.
*   **Elenco:** 32 personagens (foco em Street Fighter 6, além de Dudley e Makoto).
*   **Super Trunfo:** Akuma.
*   **Atributos das Cartas:** Força (Power), Saúde/Resistência (Health), Mobilidade (Mobility), Técnicas (Techniques), Alcance (Range). Valores na escala de 1 a 100.
*   **Armazenamento de Dados:** Os dados das cartas serão consumidos e instanciados a partir de um arquivo de banco de dados JSON (`street_fighter.json`). 

## Padrões de Projeto (GoF) Selecionados
*   **Factory Method:** Responsável por ler o arquivo JSON e criar os objetos do tipo `Carta` para compor o baralho com os atributos corretos.
*   **Template Method:** Definirá o fluxo base de uma rodada (o algoritmo principal), permitindo que regras específicas (escolha de atributo, comparação, recolhimento das cartas) sejam encaixadas.
*   **Strategy:** Encapsulará a lógica de comparação dos atributos numéricos para decidir quem ganha a rodada. Também servirá para as estratégias de decisão de jogadores virtuais automatizados.
*   **Observer:** Gerenciará as atualizações na tela do console, notificando mudanças de estado (quem venceu o turno, cartas restantes).
*   *(Nota sobre Decorator: Evitado no momento para prevenir engenharia excessiva (overengineering), já que as regras do Super Trunfo são fixas).*

## Padrões Arquiteturais e Princípios (SOLID / GRASP)
*   **MVC (Padrão Arquitetural):** Manterá as responsabilidades organizadas, separando a lógica (model) da interação do console (view).
*   **Information Expert (GRASP):** A própria classe de carta manterá o conhecimento sobre seus valores, sendo responsável por retorná-los quando consultada.
*   **Controller (GRASP):** Coordenará a entrada de comandos e o andamento da partida.
*   **SRP (SOLID):** Separação estrita das tarefas (ex: a classe de mão de cartas não saberá nada sobre a leitura do arquivo JSON).
*   **OCP (SOLID):** O design permite incluir novos temas futuramente sem alterar a lógica base do projeto.
*   **DIP e ISP (SOLID):** Criação de interfaces focadas e uso de injeção de dependência para evitar que a infraestrutura dependa de lógicas específicas do jogo cliente.

## Estrutura de Diretórios
```text
/super-trunfo-street-fighter
├── /src
│   ├── /framework          (A infraestrutura base e reutilizável)
│   │   ├── /cartas
│   │   ├── /baralho
│   │   ├── /jogadores
│   │   └── /partida
│   │
│   └── /game               (A aplicação cliente do Super Trunfo)
│       ├── /models     
│       ├── /views      
│       └── /controllers
│
├── /data                   (street_fighter.json)
└── spec.md                 (Documentação de decisões)
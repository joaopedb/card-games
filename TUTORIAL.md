# Tutorial — Como usar este repositório

Este repositório contém um **mini framework de jogos de cartas** (`src/framework`) e um
**jogo cliente de exemplo** — o Super Trunfo (`src/game`). O objetivo é que você consiga
criar um novo jogo (Batalha, Blackjack, Truco...) **reutilizando o framework sem alterá-lo**.

Este tutorial explica para que serve cada pasta e onde o código do seu novo jogo deve entrar.

---

## 1. Mapa das pastas

```
cardgames/
├── src/          → código-fonte (framework + jogo cliente)
│   ├── framework/  → a infraestrutura reutilizável (SÓ abstrações)
│   └── game/       → o jogo cliente Super Trunfo (implementações concretas)
├── tests/        → testes de unidade (JUnit 5)
├── lib/          → bibliotecas externas (jars)
├── out/          → .class compilados do src (GERADO automaticamente)
├── out-tests/    → .class compilados dos testes (GERADO automaticamente)
├── data/         → dados externos dos jogos (ex.: cartas em JSON)
├── docs/         → javadoc da API (GERADO automaticamente)
└── spec.md       → documentação das decisões de projeto (arquitetura)
```

---

## 2. Pasta por pasta

### `src/framework/` — a base reutilizável (não mexer ao criar um jogo)

Contém **apenas abstrações**: interfaces, uma classe abstrata e uma exceção.
Nenhuma classe de `framework/` importa nada de `game/` — o framework não conhece
nenhum jogo específico.

| Arquivo | O que é |
|---|---|
| `cartas/Carta` | Contrato mínimo de uma carta: `getNome()` |
| `baralho/Baralho` | Contrato do baralho: `embaralhar()`, `comprarCarta()`, `temCartas()` |
| `jogadores/Jogadores` | Contrato do jogador: `getNome()`, `receberCarta(Carta)`, `getMao()` |
| `jogadores/Mao` | Contrato da mão: `adicionarCarta`, `removerCarta`, `tamanho`, `estaVazia` |
| `regras/Regras` | Contrato das regras: `distribuirCartas(...)` e `comparar(...)` |
| `partida/Partida` | Classe **abstrata** com o Template Method do fluxo (inicializar → rodadas → vencedor) |
| `partida/Observer` | Interface de notificação de eventos (a view implementa) |
| `excecoes/BaralhoVazioException` | Exceção de domínio para baralho vazio |

> ⚠️ Regra de ouro: **não altere `src/framework/`** ao criar um novo jogo.
> Se você precisar mudar algo aqui, é sinal de que a abstração está errada — pense de novo.

### `src/game/` — o jogo cliente

Contém as **implementações concretas** do jogo. Cada jogo novo entra aqui, em um
pacote próprio (ex.: `game/batalha/`, `game/blackjack/`). O Super Trunfo usa:

| Pasta | O que contém |
|---|---|
| `game/models/` | As classes de domínio do jogo: `CartaSuperTrunfo`, `RegrasSuperTrunfo`, estratégias (`EstrategiaMelhorAtributo`, `EstrategiaAleatoria`), `LeitorCartasFactory` (lê o JSON) e as implementações concretas de baralho/jogador/mão (`BaralhoPadrao`, `JogadorPadrao`, `MaoPadrao`) |
| `game/partida/` | `PartidaSuperTrunfo` — a subclasse de `Partida` com a lógica das rodadas |
| `game/view/` | `SuperTrunfoView` — a view de console (implementa `Observer`) |
| `game/controller/` | `SuperTrunfoController` (monta as dependências) e `EstrategiaHumana` (jogador humano digita) |
| `game/MainSuperTrunfo.java` | Ponto de entrada do jogo |
| `game/MainTeste.java` | Ponto de entrada de teste da fábrica de cartas |

### `tests/` — testes de unidade (JUnit 5)

Espelha a estrutura do código:

- `tests/framework/` → testes das abstrações do framework (ex.: `PartidaTemplateTest`)
- `tests/game/` → testes do jogo (`models`, `partida`), incluindo os testes de
  `BaralhoPadrao`, `JogadorPadrao` e `MaoPadrao`

**Cada classe nova que você criar deve ter um teste aqui** (é um requisito do trabalho).

### `lib/` — bibliotecas externas

Jars de terceiros **baixados**, não gerados — **não apague**:

| Jar | Para que serve |
|---|---|
| `gson-2.10.1.jar` | Ler o JSON das cartas (`LeitorCartasFactory`) |
| `junit-platform-console-standalone-1.11.3.jar` | Executar os testes pelo console |

Se seu novo jogo não usar JSON, não precisa do Gson.

### `out/` e `out-tests/` — classes compiladas (geradas)

O `javac` escreve aqui os `.class` compilados de `src/` e `tests/`. São **gerados
automaticamente** a cada compilação — pode apagar à vontade, que eles voltam.
Nunca edite nada dentro deles.

### `data/` — dados externos

Arquivos de dados que os jogos leem em tempo de execução. O Super Trunfo lê
`data/trunfoChars.json`. Se seu jogo tiver dados externos (ex.: cartas em JSON,
configurações), coloque aqui.

### `docs/` — javadoc (gerado)

Documentação HTML da API pública, gerada com o comando `javadoc` (veja a seção 5).
Regenerável a qualquer momento.

### `spec.md` — decisões de projeto

A "memória" do trabalho: escopo, padrões GoF usados, SOLID/GRASP, diagrama de
classes e justificativas. É o documento de arquitetura — o tutorial é o "como",
a `spec.md` é o "porquê".

---

## 3. Como o framework funciona (resumo)

O framework define o **esqueleto de qualquer jogo de cartas** via Template Method:

```text
iniciarPartida()
  ├─ inicializarJogo()          // passo abstrato: embaralha + distribui cartas
  ├─ enquanto !isFimDePartida()
  │    └─ jogarRodada()         // passo abstrato: uma rodada do jogo
  └─ declararVencedor()         // passo abstrato: anuncia o vencedor
```

Seu jogo só precisa **estender `Partida`** e implementar esses 4 passos. Para
comunicar eventos à view, chame `notificar("mensagem")` — a view (Observer)
recebe sem o jogo conhecê-la.

---

## 4. Passo a passo — criar um novo jogo (ex.: "Batalha")

Vamos supor um jogo simples: dois jogadores viram a carta do topo e quem tiver a
carta mais forte leva as duas. Onde entra cada peça:

1. **Crie o pacote do jogo** em `src/game/batalha/`.
2. **Implemente `Carta`** → `CartaBatalha` com um valor de força (`int`).
3. **Implemente `Regras`** → `RegrasBatalha`: `distribuirCartas` (metade para
   cada um) e `comparar` (maior força vence).
4. **Implemente as abstrações concretas** (ou copie as do Super Trunfo):
   `BaralhoPadrao` (já genérico — `List<? extends Carta>` funciona), `JogadorPadrao`,
   `MaoPadrao`. O Super Trunfo as mantém em `game/models/` — copie para o seu pacote
   ou reescreva as suas.
5. **Estenda `Partida`** → `PartidaBatalha` implementando os 4 passos abstratos.
6. **Crie a view** → `BatalhaView implements Observer` (ou reutilize a ideia da `SuperTrunfoView`).
7. **Crie o controller + main** → `BatalhaController` monta baralho, jogadores,
   regras e view, e chama `partida.iniciarPartida()`; um `MainBatalha` como ponto de entrada.
8. **Escreva os testes** em `tests/game/batalha/`.

Ao final, `src/framework/` continua intacto — o novo jogo só **implementa contratos**
e **estende** `Partida`.

---

## 5. Comandos úteis (rodar da raiz do projeto)

```bash
# Compilar o código (gera out/)
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar" -d out $(find src -name "*.java")

# Jogar o Super Trunfo
java -cp "out:lib/gson-2.10.1.jar" game.MainSuperTrunfo

# Teste da fábrica de cartas
java -cp "out:lib/gson-2.10.1.jar" game.MainTeste

# Compilar e rodar os testes (gera out-tests/)
javac -encoding UTF-8 -cp "out:lib/gson-2.10.1.jar:lib/junit-platform-console-standalone-1.11.3.jar" -d out-tests $(find tests -name "*.java")
java -jar lib/junit-platform-console-standalone-1.11.3.jar --class-path "out:out-tests:lib/gson-2.10.1.jar" --scan-class-path

# Gerar o javadoc (gera docs/)
javadoc -encoding UTF-8 -charset UTF-8 -d docs -classpath "out:lib/gson-2.10.1.jar" -sourcepath src -subpackages framework:game
```

---

## 6. Checklist antes de considerar um jogo "pronto"

- [ ] `src/framework/` não foi modificado
- [ ] O novo jogo vive em um pacote próprio dentro de `src/game/`
- [ ] `Carta`, `Regras` e `Partida` foram implementados para o novo jogo
- [ ] View implementa `Observer` e é plugada via `adicionarObserver`
- [ ] Há testes em `tests/` cobrindo o novo jogo
- [ ] Tudo compila e os testes passam

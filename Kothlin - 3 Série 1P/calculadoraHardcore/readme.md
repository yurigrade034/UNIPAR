```markdown
# 🧮 Smart Calculator (Kotlin CLI)

Um motor de cálculo matemático desenvolvido em **Kotlin** que processa expressões complexas utilizando lógica de parênteses aninhados e prioridade de operadores.

---

## 🛠️ Tecnologias e Conceitos
Este projeto aplica conceitos avançados de manipulação de dados aprendidos em **Análise e Desenvolvimento de Sistemas**:

* **Regex (Expressões Regulares):** Utilizado para sanitização de entrada e busca de padrões em parênteses.
* **Recursividade Lógica:** Função para resolver os parênteses mais internos antes de processar a expressão principal.
* **Tokenização:** Transformação de strings brutas em listas de operandos e operadores para processamento matemático.
* **Tratamento de Erros:** Uso de `try-catch` para capturar exceções aritméticas (como divisão por zero) e erros de sintaxe.
* **Interface Estilizada:** Menu em ASCII Art e feedback visual para o usuário ("frufru" visual).

---

## ⚙️ Funcionalidades

- [x] **Hierarquia de Parênteses:** Resolve expressões dentro de `()` com níveis de prioridade.
- [x] **Operadores Suportados:** Adição (`+`), Subtração (`-`), Multiplicação (`*`) e Divisão (`/`).
- [x] **Números Decimais:** Suporte a cálculos com pontos flutuantes.
- [x] **Validação Estrita:** Bloqueia expressões com menos de 9 caracteres ou caracteres inválidos.

---

## 🚀 Como Executar

1. Abre o arquivo no Intellij IDEIA
2. Execute o arquivo 'Main.kt'

   ```
4. Rode a aplicação:
   ```bash
   java -jar Calculadora.jar
   ```

---

## 📊 Estrutura de Processamento

| Etapa | Descrição |
| :--- | :--- |
| **Leitura** | Captura a entrada e remove espaços em branco. |
| **Sanitização** | Valida contra a Regex `^[0-9 .+\-*/()]+$`. |
| **Resolução** | Identifica o parêntese mais interno e substitui seu conteúdo pelo resultado. |
| **Cálculo** | Processa primeiro `*` e `/`, seguidos de `+` e `-`. |

---

## 📝 Exemplo de Uso
```text
▶ Digite a expressão: (10 + 5) * 2 / (3 - 1)
✔  Expressão aceita!

Pilha montada
  Nível 0 → "*"
  Nível 1 → "10+5"
  Nível 1 → "3-1"
───────────────────────────────────────

 (10+5)*2/(3-1) = 15
```

---
> **Nota de Desenvolvimento:** Este projeto foi construído focando em uma estrutura de código limpa, removendo comentários desnecessários conforme as boas práticas de estudo.
```
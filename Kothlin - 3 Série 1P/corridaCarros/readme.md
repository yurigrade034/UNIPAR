```markdown
# 🏎️ Kotlin Racing Simulator

Um simulador de corrida dinâmico desenvolvido em **Kotlin** que utiliza lógica de orientação a objetos para calcular o desempenho de duplas (piloto + carro) sob diferentes condições climáticas.

---

## 🛠️ Tecnologias e Conceitos
Este projeto foi desenvolvido como parte de estudos em **Análise e Desenvolvimento de Sistemas (ADS)**, aplicando os seguintes conceitos:

* **Orientação a Objetos (OO):** Uso de classes para representar entidades como `Piloto`, `Carro` e `Pista`.
* **Coleções (Lists):** Manipulação de `MutableList` para gerenciar o grid de largada e o pódio final.
* **Lógica de Atributos:** Cálculo de pontuação baseado em múltiplos fatores como velocidade, aceleração e resistência.
* **Simulação de Eventos:** Uso de `.random()` para simular o desgaste mecânico imprevisível dos veículos.
* **Ordenação Complexa:** Uso de `sortedWith` e `compareByDescending` para definir o ranking, priorizando quem não abandonou a prova.

---

## 🏁 Como Funciona a Pontuação

O desempenho de cada volta é calculado por uma fórmula que pondera o estado atual do veículo e a perícia do condutor:

1. **Status do Carro:** Calculado pela soma ponderada de Velocidade ($40\%$), Aceleração ($20\%$), Freio ($20\%$) e Resistência ($20\%$).
2. **Habilidade do Piloto:** Atua como um multiplicador que potencializa o desempenho bruto do carro.
3. **Fator Clima:** O sistema aplica penalidades ou bônus dependendo da habilidade do piloto em condições de **Chuva** ou **Neblina**.

---

## ▶️ Como Rodar

1. Abre o arquivo no Intellij IDEIA
2. Execute o arquivo 'Main.kt'

   ```

---

## 📋 Estrutura do Projeto

| Classe | Responsabilidade |
| :--- | :--- |
| `Piloto` | Armazena nome, idade e o nível de habilidade (0-100). |
| `Carro` | Gerencia atributos técnicos, consumo de combustível e integridade física. |
| `Dupla` | Une piloto e máquina, processando a lógica de cada volta e verificando abandonos. |
| `Pista` | Controla o fluxo da corrida, o clima e a geração do placar final estilizado. |

---
> **Nota:** Este projeto foca em lógica limpa e uma interface de terminal intuitiva (o famoso "frufru" visual) para facilitar a leitura dos resultados.
> **Obs:** Foi utilizado codigo com base em IA para facilitar a vida de todos ja que é apenas uma atividade. Além disso Max Verstappen tantanran.
```

fun main() {
    Calculadora.rodar()
}


object Calculadora {

    // Variáveis que antes eram globais agora vivem aqui dentro
    val pilha = mutableMapOf<Int, String>()
    var nivel = 0
    var aux   = ""

    fun rodar() {
        val regex = Regex("^[0-9 .+\\-*/()]+\$")

        println("╔═════════════════════════════════╗")
        println("║    CALCULADORA INTELIGENTE      ║")
        println("╚═════════════════════════════════╝")
        println("Mínimo de 9 caracteres | Operadores: + - * / ( )")
        println("Digite 'sair' se quiser sair\n")

        while (true) {
            pilha.clear()
            nivel = 0
            aux   = ""

            print("▶ Digite a expressão: ")
            val entrada = readln()

            if (entrada.trim().lowercase() == "sair") {
                println("\ntchau")
                break
            }

            val exp = entrada.replace(" ", "")

            if (entrada.length < 9) {
                println("⚠  Mínimo de 9 caracteres! Você digitou ${entrada.length}.\n")
                continue
            }

            if (!entrada.matches(regex)) {
                println("⚠  Expressão negada! Use: números, espaços, + - * / ( )\n")
                continue
            }

            println("✔  Expressão aceita!\n")

            exp.forEach { c ->
                val nivelAtual = nivel
                prioridade(c)          // sobe/desce o nível conforme selecionado

                if (nivel == nivelAtual) {
                    pilha[nivel] = (pilha[nivel] ?: "") + c.toString()
                } else {
                    aux = ""
                }
            }

            println("Pilha montada")
            pilha.forEach { (i, s) ->
                println("  Nível $i → \"$s\"")
            }
            println("───────────────────────────────────────")

            //Resolução da expressão
            try {
                var expressaoFinal = exp

                while (expressaoFinal.contains('(')) {
                    expressaoFinal = resolverParenteseMaisInterno(expressaoFinal)
                }

                val resultado = calcular(expressaoFinal)

                val resultadoFormatado = if (resultado == resultado.toLong().toDouble()) {
                    resultado.toLong().toString()
                } else {
                    "%.4f".format(resultado).trimEnd('0').trimEnd('.')
                }

                println("\n $exp = $resultadoFormatado\n")

            } catch (e: ArithmeticException) {
                println(" Erro matemático: ${e.message}\n")
            } catch (e: Exception) {
                println(" Erro na expressão: ${e.message}\n")
            }
        }
    }

    fun prioridade(c: Char): Int {
        return when (c) {
            '(' -> nivel++   // abre parêntese → sobe de nível
            ')' -> nivel--   // fecha parêntese → desce de nível
            else -> nivel    // continua no mesmo nível
        }
    }

    fun resolverParenteseMaisInterno(expr: String): String {
        val regexParentese = Regex("\\(([^()]+)\\)")
        val encontrado = regexParentese.find(expr) ?: return expr

        val conteudo  = encontrado.groupValues[1]
        val resultado = calcular(conteudo)

        val resultadoStr = if (resultado == resultado.toLong().toDouble()) {
            resultado.toLong().toString()
        } else {
            resultado.toString()
        }

        return expr.replaceFirst(encontrado.value, resultadoStr)
    }

    fun calcular(expr: String): Double {

        // Tokenização: "3+5*2" → ["3", "+", "5", "*", "2"]
        val tokens = mutableListOf<String>()
        var i = 0

        while (i < expr.length) {
            val c = expr[i]

            // Lê número completo (inteiro ou decimal)
            if (c.isDigit() || c == '.') {
                var num = ""
                while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                    num += expr[i]
                    i++
                }
                tokens.add(num)
                continue
            }

            // Sinal negativo unário: -3 no início ou após operador
            if (c == '-' && (tokens.isEmpty() || tokens.last() in listOf("+", "-", "*", "/"))) {
                var num = "-"
                i++
                while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                    num += expr[i]
                    i++
                }
                tokens.add(num)
                continue
            }

            // Operador normal
            if (c in listOf('+', '-', '*', '/')) {
                tokens.add(c.toString())
            }

            i++
        }

        if (tokens.isEmpty()) throw Exception("Expressão vazia ou inválida.")

        val tokens1 = tokens.toMutableList()
        var j = 0
        while (j < tokens1.size) {
            if (tokens1[j] == "*" || tokens1[j] == "/") {
                val esq = tokens1[j - 1].toDouble()
                val dir = tokens1[j + 1].toDouble()

                val res = when (tokens1[j]) {
                    "*" -> esq * dir
                    "/" -> {
                        if (dir == 0.0) throw ArithmeticException("Divisão por zero!")
                        esq / dir
                    }
                    else -> 0.0
                }

                tokens1[j - 1] = res.toString()
                tokens1.removeAt(j)   // remove o operador
                tokens1.removeAt(j)   // remove o operando da direita
                j--
            }
            j++
        }

        var resultado = tokens1[0].toDouble()
        var k = 1
        while (k < tokens1.size) {
            val op  = tokens1[k]
            val dir = tokens1[k + 1].toDouble()
            resultado = when (op) {
                "+" -> resultado + dir
                "-" -> resultado - dir
                else -> throw Exception("Operador inesperado: '$op'")
            }
            k += 2
        }

        return resultado
    }

} // fim do object
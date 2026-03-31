class Piloto(
    var nome: String,
    var idade: Int,
    var habilidade: Int
) {
    fun apresentar() {
        println("  Piloto: $nome | Idade: $idade | Habilidade: $habilidade/100")
    }
}

class Carro(
    var velocidade: Int,
    var aceleracao: Int,
    var freio: Int,
    var tanque: Double,
    var consumo: Double,
    var resistencia: Int
) {
    // Calcula a pontuação bruta por volta
    fun pontuacaoPorVolta(): Double {
        return (velocidade * 0.4) +
                (aceleracao * 0.2) +
                (freio      * 0.2) +
                (resistencia * 0.2)
    }

    fun temCombustivel(): Boolean = tanque > 0

    fun consumirCombustivel() {
        tanque -= consumo
        if (tanque < 0) tanque = 0.0
    }

    fun desgastar() {
        resistencia -= (2..6).random()
        if (resistencia < 0) resistencia = 0
    }
}


class Dupla(
    val piloto: Piloto,
    val carro: Carro
) {
    var pontuacaoTotal: Double = 0.0
    var voltasCompletas: Int   = 0
    var abandonou: Boolean     = false

    // Calcula a pontuação de uma volta levando em conta
    // a habilidade do piloto e o estado atual do carro
    fun correrVolta(clima: String): Double {
        if (abandonou) return 0.0

        if (!carro.temCombustivel() || carro.resistencia <= 0) {
            abandonou = true
            return 0.0
        }

        val multClima = when (clima) {
            "Chuva" -> 0.7 + (piloto.habilidade / 100.0) * 0.5
            "Neblina" -> 0.8 + (piloto.habilidade / 100.0) * 0.3
            else -> 1.0  // Seco — sem penalidade
        }

        // Pontuação = (habilidade do piloto) × (desempenho do carro) × (clima)
        val habilidadeMult = 0.5 + (piloto.habilidade / 100.0) * 0.7
        val pontos = carro.pontuacaoPorVolta() * habilidadeMult * multClima

        carro.consumirCombustivel()
        carro.desgastar()
        voltasCompletas++
        pontuacaoTotal += pontos

        return pontos
    }
}

class Pista(
    val nome: String,
    val duplas: MutableList<Dupla>,
    val totalVoltas: Int,
    val clima: String   // "Seco", "Chuva" ou "Neblina"
) {

    val posicoes: MutableList<Dupla> = mutableListOf() //resultado pós

    fun simularCorrida() {
        println("\n╔══════════════════════════════════════════════╗")
        println("║         LARGADA — ${nome.padEnd(27)}║")
        println("║           Clima: ${clima.padEnd(29)}║")
        println("╚══════════════════════════════════════════════╝\n")

        for (volta in 1..totalVoltas) {
            println("── Volta $volta / $totalVoltas ────────────────────────────")

            for (dupla in duplas) {
                if (dupla.abandonou) {
                    println(" ${dupla.piloto.nome.padEnd(15)} ABANDONOU")
                    continue
                }

                val pontos = dupla.correrVolta(clima)

                if (dupla.abandonou) {
                    println(" ${dupla.piloto.nome.padEnd(15)} ABANDONOU nesta volta!")
                } else {
                    val tanqueStr = "%.1f".format(dupla.carro.tanque)
                    println(
                        " ${dupla.piloto.nome.padEnd(15)} " +
                                "| Pts volta: ${"%.1f".format(pontos).padStart(6)} " +
                                "| Total: ${"%.1f".format(dupla.pontuacaoTotal).padStart(8)} " +
                                "| Tanque: ${tanqueStr.padStart(5)}L " +
                                "| Resist: ${dupla.carro.resistencia}"
                    )
                }
            }
            println()
        }

        //posicoes final
        definirPosicoes()
    }

    //quem ganha ta no podio patrao
    fun definirPosicoes() {
        posicoes.clear()
        posicoes.addAll(
            duplas.sortedWith(
                compareByDescending<Dupla> { !it.abandonou }   // quem terminou vem antes
                    .thenByDescending { it.pontuacaoTotal }    // depois por pontuação
            )
        )
    }


    fun exibirPlacar() {
        println("╔══════════════════════════════════════════════╗")
        println("║                PLACAR FINAL                  ║")
        println("╠══════════════════════════════════════════════╣")
        println("║  Pos  │ Piloto          │  Pontos  │ Voltas  ║")
        println("╠══════════════════════════════════════════════╣")

        val medalhas = listOf("🥇", "🥈", "🥉")

        posicoes.forEachIndexed { index, dupla ->
            val medalha = medalhas.getOrElse(index) { "   " }
            val pos     = (index + 1).toString().padStart(2)
            val nome    = dupla.piloto.nome.padEnd(15)
            val pontos  = "%.1f".format(dupla.pontuacaoTotal).padStart(8)
            val voltas  = dupla.voltasCompletas.toString().padStart(6)
            val status  = if (dupla.abandonou) " X" else "  "

            println("║ $medalha $pos │ $nome │ $pontos │ $voltas  ║$status")
        }

        println("╚══════════════════════════════════════════════╝")
        println("\n Vencedor: ${posicoes.first().piloto.nome}!")
        println("   Pontuação total: ${"%.1f".format(posicoes.first().pontuacaoTotal)} pts")
        println("   Voltas completadas: ${posicoes.first().voltasCompletas} / $totalVoltas\n")
    }
}

fun main() {
    println("╔═════════════════════════════╗")
    println("║     SIMULADOR DE CORRIDA    ║")
    println("╚═════════════════════════════╝\n")

    //pilotos
    val ayrton   = Piloto("Ayrton Senna",   34, 98)
    val michael  = Piloto("Michael Schumacher", 29, 95)
    val alain    = Piloto("Alain Prost",    40, 90)
    val lewis    = Piloto("Lewis Hamilton", 28, 93)
    val max      = Piloto("Max Verstappen", 22, 99)

    //carros
    val carroSenna    = Carro(195,  88,  85,  60.0,  3.5,   90)
    val carroSchumacher = Carro(192, 90,  87,  58.0,  3.8,   88)
    val carroProst    = Carro(188,  82,  90,  65.0,  3.2,   85)
    val carroHamilton = Carro(198,  91,  88,  57.0,  4.0,   92)
    val carroMax      = Carro(200,  93,  86,  67.0,  2.1,   100)

    //dupla de piloto+carro
    val duplas = mutableListOf(
        Dupla(ayrton,  carroSenna),
        Dupla(michael, carroSchumacher),
        Dupla(alain,   carroProst),
        Dupla(lewis,   carroHamilton),
        Dupla(max,     carroMax)
    )

    //etapa do grid
    println("📋 GRID DE LARGADA:")
    duplas.forEach { it.piloto.apresentar() }

    //começa a corrida
    val pista = Pista(
        nome        = "Autódromo de Interlagos",
        duplas      = duplas,
        totalVoltas = 10,
        clima       = "Neblina"   // "Seco", "Chuva", "Neblina"
    )

    pista.simularCorrida()
    pista.exibirPlacar()
}
import java.io.File

fun main(){
    val jornada = JornadaDeTrabalho()
    jornada.lerArquivo()
}

class JornadaDeTrabalho{
    val patternEmpregado = ";Empregado:".toRegex()
    val patternDia = Regex("""\d{2}/\d{2} - \w{3}\;{3}\d{2}:\d{2}.+""")
    val patternJornada = Regex(""";Jornada.+ Abono\;{7}Jornada total\;{4}\d{2}:\d{2}""")

    fun lerArquivo(){
        val arquivo = "src/RelExtraPorPeriodo.csv"
        val linhas: List<String> = File(arquivo).readLines();

        linhas.forEach{ linha ->

            if(patternEmpregado.containsMatchIn(linha)){
                val empregado = linha.replace(";","", true)
                println("$empregado")
            }

            if(patternDia.containsMatchIn(linha)){
                val linhaInteira = patternDia.find(linha)!!.value
                val limite = linhaInteira.indexOf(";;;;;;;")
                val informacoes: String? = if(limite == -1) null
                                else linhaInteira.substring(0, limite)

                if(informacoes!= null){
                    val dia = informacoes!!.replace(";;;"," ")
                    println(dia)
                }
            }

            if(patternJornada.containsMatchIn(linha)){
                val linhaInteira = patternJornada.find(linha)!!.value
                val limite = linhaInteira.indexOf("Jornada total")
                val jornTot = linhaInteira.substring(limite)
                val informacoes:List<String> = jornTot.split(";;;;")
                if(informacoes != null)
                    println("${informacoes.get(0)} : ${informacoes.get(1)} ")
            }
        }
    }

}
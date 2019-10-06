import java.io.File

fun main(){
    val jornada = JornadaDeTrabalho()
    jornada.lerArquivo()
}

class JornadaDeTrabalho{
	//Expressao Regular do Empregado
    val patternEmpregado = ";Empregado:".toRegex()
	//Expressao Regular dos dias
    val patternDia = Regex("""\d{2}/\d{2} - \w{3}\;{3}\d{2}:\d{2}.+""")
	//Expressao Regular da jornada de Trabalho
    val patternJornada = Regex(""";Jornada.+ Abono\;{7}Jornada total\;{4}\d{2}:\d{2}""")

    fun lerArquivo(){
		//Abrindo e lendo o arquivo
        val arquivo = "src/RelExtraPorPeriodo.csv"
        val linhas: List<String> = File(arquivo).readLines();

		//Percorrendo o arquivo e executando o restante do codigo para cada linha do mesmo
        linhas.forEach{ linha ->
		
			//Se achar a expressão regular de empregado ...
            if(patternEmpregado.containsMatchIn(linha)){
				//tira os ;
                val empregado = linha.replace(";","", true)
                println("$empregado")
            }
			//Se achar a expressão regular dos dias ...
            if(patternDia.containsMatchIn(linha)){
				//le a linha inteira
                val linhaInteira = patternDia.find(linha)!!.value
				//limita ate onde a informacao e necessaria
                val limite = linhaInteira.indexOf(";;;;;;;")
				//retira as informacoes desnecessarias que estao depois dos ;;;;;;;;
                val informacoes: String? = if(limite == -1) null
                                else linhaInteira.substring(0, limite)
			
                if(informacoes!= null){
					//tira os ;
                    val dia = informacoes!!.replace(";;;"," ")
                    println(dia)
                }
            }
			//Se achar a expressão regular da jornada de trabalho ...
            if(patternJornada.containsMatchIn(linha)){
				//le a linha inteira
                val linhaInteira = patternJornada.find(linha)!!.value
				//limita ate onde a informacao e necessaria
                val limite = linhaInteira.indexOf("Jornada total")
				//retira as informacoes desnecessarias
                val jornTot = linhaInteira.substring(limite)
				//tira os ; e splita as informacoes
                val informacoes:List<String> = jornTot.split(";;;;")
                if(informacoes != null)
                    println("${informacoes.get(0)} : ${informacoes.get(1)} ")
            }
        }
    }

}
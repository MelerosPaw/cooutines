package meleros.paw.corrutinas

interface Calculadora {
    fun restar(numero: Double, otroNumero: Double) : Double
}

class CalculadoraConMemoria: Calculadora {

    private var ultimoResultado: Double? = null

    override fun restar(numero: Double, otroNumero: Double): Double =
        (numero - otroNumero).also { ultimoResultado = it }
}

class CalculadoraDePrueba: Calculadora {

    override fun restar(numero: Double, otroNumero: Double): Double = 0.0
}
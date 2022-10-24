package meleros.paw.corrutinas

import kotlin.concurrent.thread

class Esplatoon {

    interface IEsplatoon {
        fun hacerAlgo()
    }

    companion object {
        var ids: List<String>? = null
        var interfaz: IEsplatoon? = null
    }

    fun nuevoHilo(function: () -> Unit) {
        thread {
            Thread.sleep(4000L)
            function()
        }
    }
}

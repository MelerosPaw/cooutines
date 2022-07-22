package meleros.paw.corrutinas

import kotlinx.coroutines.*

interface ObtenerTelefonosUseCase {
    suspend operator fun invoke(nombres: List<String>): List<Int>
}

class ObtenerTelefonosCaseImpl : ObtenerTelefonosUseCase {

    override suspend fun invoke(nombres: List<String>): List<Int> =
//        sinCambiar(nombres)
        cambiandoUnaVez(nombres)
//        cambiandoCadaVez(nombres)

    private suspend fun sinCambiar(nombres: List<String>) = coroutineScope {
        nombres.map {
            async {
                BaseViewModel.printThreadName("async")
                buscarTelefonoEnLaAgenda(it)
            }
        }.awaitAll()
    }

    private suspend fun cambiandoUnaVez(nombres: List<String>) = withContext(Dispatchers.Default) {
        BaseViewModel.printThreadName("withContext")

        val deferred = nombres.mapIndexed { i, it ->
            async {
                BaseViewModel.printThreadName("Async ${i + 1}")
                buscarTelefonoEnLaAgenda(it)
            }
        }

        BaseViewModel.printThreadName("withContext de nuevo")
        val obtencion = deferred.awaitAll()
        obtencion
    }

    private suspend fun cambiandoCadaVez(nombres: List<String>) = coroutineScope {
        nombres.map {
            async(Dispatchers.Default) {
                BaseViewModel.printThreadName("async")
                buscarTelefonoEnLaAgenda(it)
            }
        }.awaitAll()
    }

    fun buscarTelefonoEnLaAgenda(nombre: String): Int {
        Thread.sleep(4000L)
        return 12345678
    }
}
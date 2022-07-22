package meleros.paw.corrutinas

import kotlinx.coroutines.*

interface HipotecaUseCase {
    suspend operator fun invoke(entradaPiso: Double): Boolean
}

class HipotecaUseCaseImpl : HipotecaUseCase {

    override suspend operator fun invoke(entradaPiso: Double): Boolean {
        delay(3000L)

        return medianteVariasCorrutinas(entradaPiso)
    }

    // Si lanzas withContext() con el mismo Dispatcher, mantiene el hilo y no añade coste
    private suspend fun medianteWithContexts(entradaPiso: Double) =
        withContext(Dispatchers.Default) {
            BaseViewModel.printThreadName("withContext 1")

            withContext(Dispatchers.Default) {
                BaseViewModel.printThreadName("withContext 2")

                withContext(Dispatchers.Default) {
                    BaseViewModel.printThreadName("withContext 3")

                    withContext(Dispatchers.Default) {
                        BaseViewModel.printThreadName("withContext 4")

                        withContext(Dispatchers.Default) {
                            BaseViewModel.printThreadName("withContext 5")
                            entradaPiso > 5_000.0
                        }
                    }
                }
            }
        }

    // Si lanzas una corrutina dentro de otra con el mismo Dispatcher, te puede cambiar de hilo, porque es lo deseable.
    private suspend fun medianteVariasCorrutinas(entradaPiso: Double) =
        coroutineScope {
            async(Dispatchers.Default) {
                BaseViewModel.printThreadName("async 1")

                async(Dispatchers.Default) {
                    BaseViewModel.printThreadName("async 2")

                    async(Dispatchers.Default) {
                        BaseViewModel.printThreadName("async 3")

                        async(Dispatchers.Default) {
                            BaseViewModel.printThreadName("async 4")

                            async(Dispatchers.Default) {
                                BaseViewModel.printThreadName("async 5")
                                entradaPiso > 5_000.0
                            }.await()
                        }.await()
                    }.await()
                }.await()
            }.await()
        }
}
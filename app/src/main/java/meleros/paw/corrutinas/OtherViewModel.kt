package meleros.paw.corrutinas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import meleros.paw.corrutinas.business.LibroBO
import meleros.paw.corrutinas.business.LibroDTO
import meleros.paw.corrutinas.business.LibroVO
import kotlin.concurrent.thread

class OtherViewModel : BaseViewModel() {

    companion object {
        val biblioteca = listOf(
            LibroDTO("Moby Dick", 1, "111-222-333"),
            LibroDTO("Cuento de Navidad", 2, "111-222-333"),
            LibroDTO("Teología aplicada al paganismo", 3, "111-222-333"),
            LibroDTO("Sopa de ganso para el fracasado", 4, "111-222-333"),
            LibroDTO("La sirenita", 5, "111-222-333"),
        )
    }

    private val loremIpsum: String =
        "NonGrouppedUniqueShippingMethods()`) SECUENCIALES pero muy seguidas en el tiempo " +
                "parecen estar provocando que la segunda dé un 500 y se queda el cargando pillado. 1. Se" +
                " hace una llamada cuando entras al `ShippingMethodFragment` de los métodos de envío " +
                "para tener la lista de métodos de envío y poder seleccionarlo." +
                "2. Seleccionas a domicilio y seleccionas la dirección y este mismo *fragment* hace " +
                "otra vez la misma llamada. ¿Para qué? ¿No puede ir directamente tras seleccionar la " +
                "dirección al *summary* e informar de la dirección seleccionada a quien sea que tiene " +
                "que informar? Hasta que no termina esa llamada no cambia el `ShippingMethodLiveData::" +
                "shippingDataHasBeenSelectedLiveData` cuyo *observer* manda abrir el " +
                "`OrderSummaryFragment`. Por lo tanto, al comprobación de que no se haga la llamada " +
                "mientras esté cargando es inútil pero no está de más." +
                "3. En el `OrderSummaryFragment` la lista de productos (`SummaryCartItemsFragment`) " +
                "inmediatamente hace otra vez la llamada para obtener los métodos de envío (que " +
                "deberíamos tener ya de la llamada anterior) para traer de nuevo todos los " +
                "`ShippingMethodBO`. Se queda con el que coincida con el `ShippingBundleBO` que haya " +
                "seleccionado en sesión con él calcula las fechas de entrega para pintar los artículos " +
                "divididos por fecha de entrega. Acabamos de entrar en la pantalla y los métodos están " +
                "cacheados. ¿Por qué no podemos usarlos? ¿Van a cambiar estando dentro del summary? " +
                "¿Se puede acceder al summary sin seleccionar método? Sería luego obligatorio pasar " +
                "por la pantalla de seleccionar método y, al volver, ya tendríamos los métodos " +
                "guardados para llamar de nuevo a lo que hubiera que llamar. ¿Hay algún peligro en " +
                "aceptar caché en esta llamada? 4. El resultado de esta llamada es devuelto a la " +
                "pantalla mediante el `linkDeliveryInfoByGroupCartItems`, que es un `MergedLiveData` " +
                "que contempla que, si falla la petición, tiene una *brand var* que dice que nanai de " +
                "la China de pintar los ítems sin los encabezados, pero que no vuelve a lanzar la " +
                "petición y deja el cargando puesto. 5. Cuando añades una tarjeta y sales, si el " +
                "cargando estaba puesto, se queda la pantalla completa cargando y no se puede avanzar."
    val librosLiveData: MutableLiveData<List<LibroVO>> = MutableLiveData()

    fun usarUnHilo() {
        thread {
            val resultado = operacionBloqueante()
            _textLiveData.postValue(resultado.toString())
            _loadingLiveData.postValue(false)

            printWithTag("He terminado")
        }
    }

    fun cooperarAntesDeCadaProcesoCostoso(ids: List<String>) {
        viewModelScope.launch(Dispatchers.Default) {
            _loadingLiveData.postValue(true)

            ensureActive()
            printAndPost("Mapeando identificadores...")
            val intIds = ids.mapNotNull(String::toIntOrNull)

            ensureActive()
            printAndPost("Obteniendo libros...")
            val dtos = getLibros(intIds)

            ensureActive()
            printAndPost("Mapeando a BO...")
            val bos = dtos.map { libro -> libro.toBo() }

            ensureActive()
            printAndPost("Mapeando a VO...")
            val vos = bos.map { libro -> libro.toVo() }

            printWithTag("Ya está")
            librosLiveData.postValue(vos)
            _loadingLiveData.postValue(false)
        }
    }

    fun cancelacionNormal() {
        val ceh = CoroutineExceptionHandler { context, throwable -> printWithTag("Petó con ${throwable::class.simpleName}")}
        viewModelScope.launch(ceh) {

            printWithTag("Launch ${coroutineContext[Job]?.toString() ?: "Sin job"}")

            val cosa = async(Dispatchers.Default) {
                printWithTag("Async ${coroutineContext[Job]?.toString() ?: "Sin job"}")
                usarAlgo()
            }

            launch(Dispatchers.Default) {
                delay(4000L)
                printWithTag("Yo tampoco me he muerto")
            }

            printWithTag("${cosa.await().isActive}")
        }
    }

    private suspend fun usarAlgo(): Job = withContext(Dispatchers.Default) {
        printWithTag("Dentro de algo: ${coroutineContext[Job]?.toString() ?: "Sin job"}")

        cancel()

        if (isActive) {
            printWithTag("Sigo activa")
        }

        launch(Dispatchers.Default) {
            delay(2000L)
            printWithTag("No me he muerto")
        }
    }

    fun usoDeSupervisorJob() {
        val ceh = CoroutineExceptionHandler { context, throwable -> printWithTag("Petó con ${throwable::class.simpleName}")}
        val miAmbito = CoroutineScope(SupervisorJob() + ceh)
        miAmbito.launch(Dispatchers.Default) {
            throw java.lang.IllegalStateException()
        }

        miAmbito.launch(Dispatchers.Default) {
            delay(2000L)
            printWithTag("Yo no salgo a seguir saliendo igual")
        }
    }


    suspend fun getLibros(idLibros: List<Int>): List<LibroDTO> {
        Thread.sleep(3000L)
        return idLibros.mapNotNull { id -> biblioteca.find { it.id == id } }
    }

    suspend fun getTitulos(idLibros: List<Int>): Map<Int, String> =
        withContext(Dispatchers.Default) {
            delay(1000L)
            idLibros.mapNotNull { id ->
                biblioteca.find { it.id == id }?.titulo?.let { Pair(id, it) }
            }.toMap()
        }

    suspend fun getISBN(idLibros: List<Int>): Map<Int, String> = withContext(Dispatchers.Default) {
        delay(1000L)
        idLibros.mapNotNull { id ->
            biblioteca.find { it.id == id }?.isbn?.let { Pair(id, it) }
        }.toMap()
    }

    fun LibroDTO.toBo(): LibroBO {
        Thread.sleep(1000L)
        return LibroBO(titulo, isbn)
    }

    fun LibroBO.toVo(): LibroVO {
        Thread.sleep(1000L)
        return LibroVO(titulo, isbn)
    }
}
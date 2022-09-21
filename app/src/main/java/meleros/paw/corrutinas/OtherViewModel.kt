package meleros.paw.corrutinas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class OtherViewModel : BaseViewModel() {

    val librosLiveData: MutableLiveData<List<LibroVO>> = MutableLiveData()
    val biblioteca = listOf(
        LibroDTO("Moby Dick", 1),
        LibroDTO("Cuento de Navidad", 2),
        LibroDTO("Teología aplicada al paganismo", 3),
        LibroDTO("Sopa de ganso para el fracasado", 4),
        LibroDTO("La sirenita", 5),
    )

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
            val idLibros = ids.mapNotNull(String::toIntOrNull)
            printAndPost("Mapeo de identificadores finalizado...")

            val dtos = getLibros(idLibros)
            printAndPost("Libros obtenidos...")

            val bos = dtos.map { libro -> libro.toBo() }
            printAndPost("Mapeado a BO finalizado...")

            val vos = bos.map { libro -> libro.toVo() }
            printAndPost("Mapeado a VO finalizado...")

            librosLiveData.postValue(vos)
        }
    }

    fun getLibros(idLibros: List<Int>): List<LibroDTO> {
        Thread.sleep(3000L)
        return idLibros.mapNotNull { id -> biblioteca.find { it.id == id } }
    }

    class LibroDTO(val titulo: String, val id: Int)

    fun LibroDTO.toBo(): LibroBO {
        Thread.sleep(1000L)
        return LibroBO(titulo)
    }

    class LibroBO(val titulo: String)

    fun LibroBO.toVo(): LibroVO {
        Thread.sleep(1000L)
        return LibroVO(titulo)
    }

    class LibroVO(val titulo: String)
}
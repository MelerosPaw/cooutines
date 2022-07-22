package meleros.paw.corrutinas

import kotlin.concurrent.thread

class OtherViewModel : BaseViewModel() {

    fun usarUnHilo() {
        thread {
            val resultado = operacionBloqueante()
            _textLiveData.postValue(resultado.toString())
            _loadingLiveData.postValue(false)

            printWithTag("He terminado")
        }
    }
}
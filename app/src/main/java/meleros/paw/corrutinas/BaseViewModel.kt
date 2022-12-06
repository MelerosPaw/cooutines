package meleros.paw.corrutinas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import java.text.DecimalFormat

open class BaseViewModel : ViewModel() {

    companion object {
        const val TAG = "WIP"

        fun operacionBloqueante(): Float = (1..1_000_000_000).fold(1f) { acc, i -> i * acc }

        fun printThreadName(name: String) {
            Log.i(TAG, "$name se está ejecutando en ${Thread.currentThread().name} (${System.nanoTime()})")
        }

        fun printWithTag(message: String) {
            println("$TAG: $message")
        }
    }

    protected val exceptionHandler = CoroutineExceptionHandler { context, throwable -> printWithTag(throwable.javaClass.name)}

    protected val _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    protected val _textLiveData: MutableLiveData<String> = MutableLiveData()
    val textLiveData: LiveData<String>
        get() = _textLiveData

    fun printWithTag(message: String) {
        BaseViewModel.printWithTag(message)
    }

    fun operacionMenosBloqueante(): Float = (1..100_000_000).fold(1f) { acc, i -> i * acc }

    protected inline fun <T> medirTiempo(showOnScreen: Boolean = true, block: () -> T): T {
        val startTime = System.currentTimeMillis()
        val result = block()
        val endTime = System.currentTimeMillis()

        val secs = calculateTimeDiffInSeconds(endTime, startTime)
        val resultado = "Duración: $secs segundos"
        printWithTag(resultado)

        if (showOnScreen) {
            _textLiveData.postValue(resultado)
            _loadingLiveData.postValue(false)
        }

        return result
    }

    protected fun calculateTimeDiffInSeconds(endTime: Long, startTime: Long): String {
        val millis = (endTime - startTime).toDouble() / 1000
        return doubleToString(millis)
    }

    protected fun printAndPost(text: String) {
        _textLiveData.postValue(text)
        printWithTag(text)
    }

    private fun doubleToString(millis: Double): String {
        val formatter = DecimalFormat.getInstance()
        formatter.maximumFractionDigits = 3
        return formatter.format(millis)
    }
}
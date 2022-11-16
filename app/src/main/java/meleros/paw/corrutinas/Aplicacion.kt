package meleros.paw.corrutinas

import android.app.Application
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Aplicacion: Application() {

    val esplatoon: Esplatoon = Esplatoon()
    val ceh = CoroutineExceptionHandler { c, th -> BaseViewModel.printWithTag(th.javaClass.name)}

    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch(ceh) {
            throw java.lang.IllegalStateException()
        }
    }
}
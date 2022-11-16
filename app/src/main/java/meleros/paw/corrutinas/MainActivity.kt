package meleros.paw.corrutinas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private var viewModel: MainViewModel? = null
    private val boton: Button by lazy { findViewById(R.id.boton) }

    @LayoutRes
    override fun getLayout() = R.layout.activity_main

    override fun getBaseViewModel(): BaseViewModel =
        viewModel ?: ViewModelProvider(this)[MainViewModel::class.java].also { viewModel = it }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        boton.setOnClickListener {
            // Java
//            startActivity(Intent(this, ActivityB::class.java))

            // Kotlin
//            viewModel?.cancelarJob()
//            viewModel?.delayEstaConfabuladoConElMain()
//            viewModel?.falloVsCancelacion()
//            viewModel?.serSupervisor()
//            startActivity(Intent(this, OtherActivity::class.java))
        }

        viewModel?.let { vm ->

            // Comparación de hilo con corrutina
//            vm.corrutina()
//            vm.hilosVsCorrutinas()

            // Esto es una corrutina
//            vm.estoEsUnaCorrutina()

            // Se ejecutan en el hilo que le indiques con el Dispatcher
//            vm.queHiloEjecutaCadaDispatcher()

            // Cuando hay varias corrutinas, estas pueden suceder a la vez
//            vm.ejecucionSincronaYConcurrente()
//            vm.ejecucionSincronaYConcurrenteAnidada()
//            vm.concurrenciaEnBucles()
//            vm.launchAsyncIgualDeConcurrentes()

            // Solo podemos garantizar el orden usando async
//            vm.esperarAlResultado()

            // Funciones de suspensión
            // Sirven para decirle a la ejecución "espérate aquí hasta que termine esto otro".
            // Solo tiene sentido cuando queremos esperar a que termine algo que se va a ejecutar de forma asíncrona.
//            vm.hacerAlgoSuspendiendo()

            // Las funciones de suspensión siempre esperan el completado de las corrutinas hija
//            vm.esperarCompletado()

            // Excepciones en corrutinas
            // Usar un try-catch
//            vm.tryCatch()

            // O un CoroutineExceptionHandler
//            vm.exceptionHandler()

            // Cancelación
            // Cancelar es cooperativo
//            vm.cancelarYCooperar()

            // Cooperar siempre antes de los procesos costosos

            // Funciones de suspensión cooperativas con la cancelación
//            vm.seCancelaConDelay()
//            vm.seCancelaConWithContext()
//            vm.seCancelaTrasCoroutineScope()
//            vm.noSeCancelaPorLanzarOtraCorrutinaPeroNoSeLanzan()
//            vm.noSeCancelaPorSuspension()

            // No debemos cancelar scopes gestionados por Android
//            vm.nuncaCancelarScopesGestionados()

            // Solo cancelamos el interno o el nuestro
//            vm.cancelarJobsOScopesInternos()
//            vm.cancelarScopesPropios()

            // El async encapsula no lanza CancellationException hasta que se llama a await()
//            vm.cancelarUnAsync()

            // Cancelar en una función de suspensión es cancelar la corrutina en a que se te llama
//            vm.cancelarDesdeUnaFuncionDeSuspension()
//            vm.falloVsCancelacion()

            // Siendo supervisor podemos ignorar los fallos
            vm.otraFormaDeSupervisar()

            // Un trabajo NonCancellable crea una estructura nueva de padres hijos

            // Si ejecutas una en el hilo principal, el hilo se va a bloquear
//            vm.bloquearHiloPrincipal()

            // Nuestro trabajo - Solo ida
//            vm.operacionSoloDeIda()

            // Nuestro trabajo - Ida y vuelta
//            vm.operacionDeIdaYVuelta()

            // Cuidado, que no te das cuenta
//            vm.corrutinaConTrampa()

            // Desde otro hilo no se puede cambiar la UI, pero...
//            vm.uiNoUi()
//            uiNoUi()

            // Todas las corrutinas tienen un job
//            vm.unJob()

            // El job de async() permite devolver el resultado
//            vm.obtenerVariosResultados()

            // Suspensión es hacer que la ejecución se detenga en una línea hasta que no termine de ejecutarse.
            // Solo tiene sentido si dentro vamos a hacer una llamada asíncrona (cambio de hilo).
            // Una función de suspensión tiene como objetivo llamar otras funciones de suspensión para cambiar de contexto
            // Una función de suspensión por sí sola no hace nada si no cambiamos de contexto
//             vm.suspension()

            // La concurrencia estructurada se crea con los jobs
//            vm.corrutinaHija()

            // Ejemplo de varios hijos
//            vm.corrutinaConVariosHijos()

            // Es muy fácil cargarnos la concurrencia estructurada
//            vm.romperLaConcurenciaEstructurada()

            // No son tan concurrentes como deberían
//            vm.cuantaConcurrenciaHayEnLasCorrutinas()

            // Unconfined
//            vm.ejecucionSinConfinar()

            // Las excepciones que suceden dentro se captura con un CoroutineExceptionHandler
//            vm.exceptionHandler()

            // Cómo se deberían usar las corrutinas en los casos de uso
//            vm.logicaDeNegocio()

            // Comprobar qué dispatcher se está usando
//            vm.comprobarDispatchers(lifecycleScope)

            // No uséis el GlobalScope, por Dios
//            vm.nadaDeGlobalScope()

            // Cancelar un scope interno no cancela el original
//            vm.cancelarScopeInterno()
        }
    }

    private fun uiNoUi() {
        lifecycleScope.launch(Dispatchers.IO) {
            BaseViewModel.printThreadName("Primero")
            textView?.text = "Texto cambiado desde un hilo secundario"
            loader?.isVisible = false
            BaseViewModel.printThreadName("Y luego")
        }
    }

    fun interface Callback {
        fun onFinished(result: String)
    }
}
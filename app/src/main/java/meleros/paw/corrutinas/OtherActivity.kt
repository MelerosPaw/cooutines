package meleros.paw.corrutinas

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OtherActivity : BaseActivity() {

    private val botonLibros: Button by lazy { findViewById(R.id.boton_libros) }

    private var otherViewModel: OtherViewModel? = null

    override fun getLayout() = R.layout.activity_other

    override fun getBaseViewModel(): BaseViewModel = otherViewModel
        ?: ViewModelProvider(this)[OtherViewModel::class.java].also { otherViewModel = it }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeBooks()
        setButtonListener()
//        fugaDeMemoria()
//        otherViewModel?.usarUnHilo()
    }

    private fun observeBooks() {
        otherViewModel?.librosLiveData?.observe(this) { books ->
            botonLibros.isEnabled = true
            textView?.text = books.joinToString("\n") { it.titulo }
        }
    }

    private fun setButtonListener() {
        botonLibros.setOnClickListener {
            botonLibros.isEnabled = false
            val ids = listOf("1", "3", "5")
//            otherViewModel?.cooperarAntesDeCadaProcesoCostoso(ids)
            otherViewModel?.sinCancelar()
        }
    }

    private fun fugaDeMemoria() {
//        Eswplatoon.ids = this.ids
//        Esplatoon().nuevoHilo {
//            Log.i("WIP", "${ids?.joinToString() ?: "Vacía"}")
//        }
    }
}
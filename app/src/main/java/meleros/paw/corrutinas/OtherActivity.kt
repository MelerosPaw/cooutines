package meleros.paw.corrutinas

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class OtherActivity : BaseActivity() {

    private var otherViewModel: OtherViewModel? = null

    override fun getLayout() = R.layout.activity_other

    override fun getBaseViewModel(): BaseViewModel =
        otherViewModel
            ?: ViewModelProvider(this)[OtherViewModel::class.java].also { otherViewModel = it }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        otherViewModel?.usarUnHilo()
        otherViewModel?.cooperarAntesDeCadaProcesoCostoso(
            listOf("1", "3", "5")
        )
        otherViewModel?.librosLiveData?.observe(this) {
            textView?.text = it.joinToString("\n") { it.titulo }
        }
    }
}
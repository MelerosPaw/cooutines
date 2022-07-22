package meleros.paw.corrutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible

abstract class BaseActivity : AppCompatActivity() {

    private val viewModel: BaseViewModel by lazy { getBaseViewModel() }
    protected val loader: ProgressBar? by lazy { findViewById(R.id.loader) }
    protected val textView: TextView? by lazy { findViewById(R.id.texto) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        viewModel.loadingLiveData.observe(this, ::showLoading)
        textView?.let { viewModel.textLiveData.observe(this, it::setText) }
    }

    protected fun showLoading(isLoading: Boolean) {
        loader?.isVisible = isLoading
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun getBaseViewModel(): BaseViewModel
}
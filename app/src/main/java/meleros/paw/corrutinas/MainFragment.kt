package meleros.paw.corrutinas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import meleros.paw.corrutinas.di.DIManager
import meleros.paw.corrutinas.di.DaggerAppComponent
import javax.inject.Inject

class MainFragment: Fragment() {

    @Inject
    lateinit var calculadora: Calculadora

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DIManager.getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        viewLifecycleOwner.lifecycleScope
    }
}
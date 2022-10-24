package meleros.paw.corrutinas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import meleros.paw.corrutinas.JEsplatoon.EjecutarEnUnHilo
import meleros.paw.corrutinas.di.DIManager
import meleros.paw.corrutinas.di.DaggerAppComponent
import javax.inject.Inject

class MainFragment: Fragment(), Esplatoon.IEsplatoon {

    @Inject
    lateinit var calculadora: Calculadora

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DIManager.getAppComponent().inject(this)
        Esplatoon.interfaz = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        viewLifecycleOwner.lifecycleScope
    }

    override fun hacerAlgo() {
        Log.i("WIP", "En un fragment")
    }
}
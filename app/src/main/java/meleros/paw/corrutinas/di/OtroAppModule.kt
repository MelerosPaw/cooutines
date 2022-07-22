package meleros.paw.corrutinas.di

import meleros.paw.corrutinas.Calculadora
import meleros.paw.corrutinas.CalculadoraDePrueba

class OtroAppModule: AppModule() {

    override fun provideCalculadora(): Calculadora {
        return CalculadoraDePrueba()
    }
}
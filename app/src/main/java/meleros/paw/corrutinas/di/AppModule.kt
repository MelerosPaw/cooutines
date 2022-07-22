package meleros.paw.corrutinas.di

import dagger.Module
import dagger.Provides
import meleros.paw.corrutinas.*
import javax.inject.Singleton

@Module
open class AppModule {

    @Provides
    @Singleton
    open fun provideCalculadora(): Calculadora {
        return CalculadoraConMemoria()
    }

    @Provides
    open fun provideHipotecaUseCase(calculadora: Calculadora): HipotecaUseCase {
        return HipotecaUseCaseImpl(calculadora)
    }
}
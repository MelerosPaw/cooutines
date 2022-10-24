package meleros.paw.corrutinas.di

import dagger.Component
import meleros.paw.corrutinas.HipotecaUseCase
import meleros.paw.corrutinas.HipotecaUseCaseImpl
import meleros.paw.corrutinas.MainFragment
import meleros.paw.corrutinas.MainViewModel
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(viewModel: MainViewModel)
    fun inject(fragment: MainFragment)
}
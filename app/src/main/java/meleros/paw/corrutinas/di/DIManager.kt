package meleros.paw.corrutinas.di

class DIManager {

    companion object {

        private var appComponent: AppComponent? = null

        fun getAppComponent(): AppComponent = appComponent
            ?: DaggerAppComponent.builder()
                .appModule(AppModule())
                .build().also { appComponent = it }

    }
}
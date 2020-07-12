package id.koridor50.jesika

import android.app.Application
import com.facebook.stetho.Stetho
import id.koridor50.jesika.di.AppComponent
import id.koridor50.jesika.di.AppModule
import id.koridor50.jesika.di.DaggerAppComponent

class JesikaApp : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = initDagger(this)
        Stetho.initializeWithDefaults(this)
    }

    private fun initDagger(app: JesikaApp): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}
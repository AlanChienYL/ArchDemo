package alan

import alan.practice.archdemo.di.databaseModule
import alan.practice.archdemo.di.repositoryModule
import alan.practice.archdemo.di.viewModelModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ArchDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(databaseModule, viewModelModule, repositoryModule)
            androidContext(this@ArchDemoApplication)
        }
    }
}
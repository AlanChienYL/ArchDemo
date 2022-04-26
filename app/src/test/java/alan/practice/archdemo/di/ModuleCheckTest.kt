package alan.practice.archdemo.di

import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.AutoCloseKoinTest
import org.koin.test.category.CheckModuleTest
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@Category(CheckModuleTest::class)
class ModuleCheckTest : AutoCloseKoinTest() {

    @Test
    fun checkModules() = org.koin.test.check.checkModules {
        androidContext(ApplicationProvider.getApplicationContext())
        modules(databaseModule, repositoryModule, viewModelModule)
    }
}

package alan.practice.archdemo.demo

import alan.practice.archdemo.R
import alan.practice.archdemo.currency.CurrencyAdapter
import alan.practice.archdemo.currency.CurrencyListViewHolder
import alan.practice.archdemo.data.local.room.CurrencyInfo
import alan.practice.archdemo.di.databaseModule
import alan.practice.archdemo.di.repositoryModule
import android.os.Looper
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode


@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class DemoActivityTest : AutoCloseKoinTest() {

    private lateinit var viewModel: DemoViewModel
    private val repository: DemoRepository = mockk()
    private val viewModelModule = module {
        viewModel {
            spyk(DemoViewModel(repository = repository)).also {
                viewModel = it
            }
        }
    }

    @Before
    fun beforeTest() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(repositoryModule, databaseModule, viewModelModule)
        }
    }

    @Test
    fun `loadCurrencyInfoList has called with null data and buttonSort is enable after click buttonLoad`() {
        // Give null list
        mockQueryList()
        launchActivity<DemoActivity>().use { scenario ->
            scenario.onActivity { activity ->
                val buttonLoad = activity.findViewById<View>(R.id.bt_load)
                val buttonSort = activity.findViewById<View>(R.id.bt_sort)
                // When
                buttonLoad.performClick()
                Shadows.shadowOf(Looper.getMainLooper()).idle()
                // Then
                verify(exactly = 1) { viewModel.loadCurrencyInfoList() }
                assertThat(buttonSort.isEnabled).isTrue()
            }
        }
    }

    @Test
    fun `loadCurrencyInfoList has called with on item in array and buttonSort is enable after click buttonLoad`() {
        // Give
        mockQueryList(arrayListOf(CurrencyInfo("fakeId", "fake name", "fake symbol")))
        launchActivity<DemoActivity>().use { scenario ->
            scenario.onActivity { activity ->
                // Give
                val buttonLoad = activity.findViewById<View>(R.id.bt_load)
                val buttonSort = activity.findViewById<View>(R.id.bt_sort)
                // When click load button
                buttonLoad.performClick()
                Shadows.shadowOf(Looper.getMainLooper()).idle()
                // Then
                verify(exactly = 1) { viewModel.loadCurrencyInfoList() }
                assertThat(buttonSort.isEnabled).isTrue()
            }
        }
    }

    @Test
    fun `sortCurrencyInfoList has called after click buttonSort`() {
        launchActivity<DemoActivity>().use { scenario ->
            scenario.onActivity { activity ->
                val buttonLoad = activity.findViewById<View>(R.id.bt_sort)
                buttonLoad.performClick()
                verify(exactly = 1) { viewModel.sortCurrencyInfoList() }
            }
        }
    }

    @Test
    fun `submitList has be called after received list`() {
        // Give
        mockQueryList(arrayListOf(CurrencyInfo("fakeId", "fake name", "fake symbol")))
        mockkConstructor(CurrencyAdapter::class)
        every { anyConstructed<CurrencyAdapter>().submitList(any()) } returns Unit
        launchActivity<DemoActivity>().use { scenario ->
            scenario.onActivity { activity ->
                val buttonLoad = activity.findViewById<View>(R.id.bt_load)
                // When
                buttonLoad.performClick()
                Shadows.shadowOf(Looper.getMainLooper()).idle()
                // Then
                verify(exactly = 1) { anyConstructed<CurrencyAdapter>().submitList(any()) }
            }
        }
    }

    @Test
    fun `view holder's bind has bee called after received item`() {
        // Give
        mockQueryList(arrayListOf(CurrencyInfo("fakeId", "fake name", "fake symbol")))
        mockkConstructor(CurrencyListViewHolder::class)
        every { anyConstructed<CurrencyListViewHolder>().bind(any()) } returns Unit
        launchActivity<DemoActivity>().use { scenario ->
            scenario.onActivity { activity ->
                val buttonLoad = activity.findViewById<View>(R.id.bt_load)
                // When
                buttonLoad.performClick()
                Shadows.shadowOf(Looper.getMainLooper()).idle()
                // Then
                verify(exactly = 1) { anyConstructed<CurrencyListViewHolder>().bind(any()) }
            }
        }
    }

    private fun mockQueryList(list: List<CurrencyInfo>? = null) {
        coEvery { repository.fetchCurrencyInfo() } returns list
    }
}



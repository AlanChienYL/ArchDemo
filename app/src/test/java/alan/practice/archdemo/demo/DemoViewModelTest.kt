package alan.practice.archdemo.demo

import alan.practice.archdemo.data.local.room.CurrencyInfo
import alan.practice.archdemo.demo.interfaces.IDemoRepository
import alan.practice.archdemo.extensions.getOrAwaitValue
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class DemoViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: IDemoRepository = mockk()
    private val viewModel: DemoViewModel by lazy { DemoViewModel(repository) }

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun loadCurrencyInfoList_nullData_getEmpty() = runTest {
        // Give
        mockFetchList(null)

        // When
        viewModel.loadCurrencyInfoList()

        // Then
        assertThat(viewModel.currencyInfosLivData.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun loadCurrencyInfoList_onlyOneCurrencyInfo_getOneItem() = runTest {
        // Give
        mockFetchList(arrayListOf(CurrencyInfo("fakeId", "fake name", "fake symbol")))

        // When
        viewModel.loadCurrencyInfoList()

        // Then
        assertThat(viewModel.currencyInfosLivData.getOrAwaitValue()).hasSize(1)
    }

    @Test
    fun sortCurrencyInfoList_notLoadFirst_getEmpty() = runTest {
        // Give Nothing

        // When
        viewModel.sortCurrencyInfoList()

        // Then
        assertThat(viewModel.currencyInfosLivData.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun sortCurrencyInfoList_giveThreeItems_arrayBeSorted() = runTest {
        // Give
        val data = arrayListOf(
            CurrencyInfo("fakeId3", "fake name", "fake symbol"),
            CurrencyInfo("fakeId2", "fake name", "fake symbol"),
            CurrencyInfo("fakeId", "fake name", "fake symbol")
        )
        mockFetchList(data)
        viewModel.loadCurrencyInfoList()

        // When
        viewModel.sortCurrencyInfoList()

        // Then
        data.sortBy { it.id }
        assertThat(viewModel.currencyInfosLivData.getOrAwaitValue()).containsExactlyElementsIn(data)
    }

    private fun mockFetchList(list: List<CurrencyInfo>? = null) {
        coEvery { repository.fetchCurrencyInfo() } returns list
    }
}
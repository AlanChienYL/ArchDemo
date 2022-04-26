package alan.practice.archdemo.demo

import alan.practice.archdemo.data.local.room.CurrencyDatabase
import alan.practice.archdemo.data.local.room.CurrencyInfo
import alan.practice.archdemo.data.local.room.dao.CurrencyInfoDao
import alan.practice.archdemo.demo.interfaces.IDemoRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class DemoRepositoryTest {

    private val repository: IDemoRepository by lazy { DemoRepository(database, UnconfinedTestDispatcher()) }
    private val database: CurrencyDatabase = mockk()

    @Test
    fun fetchCurrencyInfo_nullDataInDatabase_getNull() = runTest {
        // Give
        mockQueryList(null)

        // When
        val data = repository.fetchCurrencyInfo()

        // Then
        assertThat(data).isNull()
    }

    @Test
    fun fetchCurrencyInfo_emptyDataInDatabase_getEmpty() = runTest {
        // Give
        mockQueryList(arrayListOf())

        // When
        val data = repository.fetchCurrencyInfo()

        // Then
        assertThat(data).isEmpty()
    }

    @Test
    fun fetchCurrencyInfo_noneEmptyDataInDatabase_getList() = runTest {
        // Give
        mockQueryList(arrayListOf(CurrencyInfo("fakeId", "fake name", "fake symbol")))

        // When
        val data = repository.fetchCurrencyInfo()

        // Then
        assertThat(data).hasSize(1)
    }

    @Test
    fun fetchCurrencyInfo_throwsException_getNull() = runTest {
        // Give
        mockCrash()

        // When
        val data = repository.fetchCurrencyInfo()

        // Then
        assertThat(data).isNull()
    }

    private fun mockQueryList(list: List<CurrencyInfo>? = null) {
        val mockDao: CurrencyInfoDao = mockk()
        every { database.currencyDao() } returns mockDao
        coEvery { mockDao.getCurrencyInfoList() } returns list
    }

    private fun mockCrash() {
        val mockDao: CurrencyInfoDao = mockk()
        every { database.currencyDao() } returns mockDao
        coEvery { mockDao.getCurrencyInfoList() } throws Exception()
    }
}
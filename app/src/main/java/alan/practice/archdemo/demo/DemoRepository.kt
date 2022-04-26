package alan.practice.archdemo.demo

import alan.practice.archdemo.data.local.room.CurrencyDatabase
import alan.practice.archdemo.data.local.room.CurrencyInfo
import alan.practice.archdemo.demo.interfaces.IDemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DemoRepository(
    private val database: CurrencyDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IDemoRepository {
    override suspend fun fetchCurrencyInfo(): List<CurrencyInfo>? = withContext(ioDispatcher) {
        kotlin.runCatching {
            database.currencyDao().getCurrencyInfoList()
        }.getOrNull()
    }
}
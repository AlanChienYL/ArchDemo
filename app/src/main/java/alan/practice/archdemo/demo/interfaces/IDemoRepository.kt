package alan.practice.archdemo.demo.interfaces

import alan.practice.archdemo.data.local.room.CurrencyInfo

interface IDemoRepository {
    suspend fun fetchCurrencyInfo(): List<CurrencyInfo>?
}
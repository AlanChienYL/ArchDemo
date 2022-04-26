package alan.practice.archdemo.data.local.room.dao

import alan.practice.archdemo.data.local.room.CurrencyInfo
import androidx.room.Dao
import androidx.room.Query

@Dao
interface CurrencyInfoDao {
    @Query("SELECT * FROM currency_info")
    suspend fun getCurrencyInfoList(): List<CurrencyInfo>?
}
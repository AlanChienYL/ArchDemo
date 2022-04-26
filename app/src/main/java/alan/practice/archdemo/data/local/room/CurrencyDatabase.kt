package alan.practice.archdemo.data.local.room

import alan.practice.archdemo.data.local.room.dao.CurrencyInfoDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CurrencyInfo::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyInfoDao

    companion object{
        const val DATABASE_NAME = "CurrencyDatabase.db"
    }
}
package alan.practice.archdemo.di

import alan.practice.archdemo.data.local.room.CurrencyDatabase
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(context = get()) }
}

private fun provideDatabase(context: Context): CurrencyDatabase =
    Room.databaseBuilder(context, CurrencyDatabase::class.java, CurrencyDatabase.DATABASE_NAME)
        .createFromAsset(ASSETS_CURRENCY_LIST)
        .build()

private const val ASSETS_CURRENCY_LIST = "prepopulate.db"

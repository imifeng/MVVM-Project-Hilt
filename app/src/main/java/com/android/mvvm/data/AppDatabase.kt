package com.android.mvvm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.mvvm.BuildConfig
import com.android.mvvm.data.dao.RepoBeanDao
import com.android.mvvm.data.typeconverters.DateAdapters
import com.android.mvvm.service.SharedPrefService

@Database(entities = [RepoBean::class], version = 1)
@TypeConverters(DateAdapters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val TAG = "AppDatabase"
        private const val DATABASE_NAME = "mvvm_database"

        fun initialize(context: Context, sp: SharedPrefService): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .apply {
                    fallbackToDestructiveMigration()
                    if (!BuildConfig.DEBUG) {
                        // 数据加密，如有需要，去掉注释
//                        openHelperFactory(SafeHelperFactory(sp.deviceSerialNumber.toCharArray()))
                    }
                    addMigrations(
//                        MIGRATION_1_2
                    )
                }.build()
        }

        // 如果数据库有改动，则需要更新数据库版本来同步更新数据库，以下为示例方法：
//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("DROP TABLE IF EXISTS Media")
//                database.execSQL("CREATE TABLE IF NOT EXISTS `Media` (`id` INTEGER NOT NULL, `checksum` TEXT NOT NULL, `fileEndpoint` TEXT NOT NULL, `dateAvailable` INTEGER NOT NULL, `mimetype` TEXT, `name` TEXT, `fileLocalLocation` TEXT NOT NULL, PRIMARY KEY(`id`))")
//            }
//        }
    }

    abstract fun noteDao(): RepoBeanDao

}
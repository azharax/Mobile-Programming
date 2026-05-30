package com.example.studentapp.data

import android.content.Context
import androidx.room.*

@Database(entities = [Siswa::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun siswaDao(): SiswaDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "siswa_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
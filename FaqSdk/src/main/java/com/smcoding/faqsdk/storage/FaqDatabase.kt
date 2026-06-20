package com.smcoding.faqsdk.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FaqChunk::class
    ],
    version = 2,
    exportSchema = false
)
abstract class FaqDatabase : RoomDatabase() {

    abstract fun faqDao(): FaqDao

    companion object {

        @Volatile
        private var INSTANCE: FaqDatabase? = null

        fun getDatabase(
            context: Context
        ): FaqDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        FaqDatabase::class.java,
                        "faq_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                INSTANCE = instance

                instance
            }
        }
    }
}
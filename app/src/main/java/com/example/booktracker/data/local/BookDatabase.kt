package com.example.booktracker.data.local

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.booktracker.data.local.book.*
import com.example.booktracker.data.local.review.ReviewDao
import com.example.booktracker.data.local.review.ReviewEntity
import kotlinx.coroutines.*

@Database(
    entities = [BookEntity::class, ReviewEntity::class],
    version = 2,
    exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BookDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(BookDatabaseCallback(context, scope))
                    .build()
                INSTANCE = instance
                instance
            }
    }

    private class BookDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val defaultBooks = BookJsonLoader.loadBooksFromJson(context)
                    database.bookDao().insertAll(defaultBooks)
                }
            }
        }
    }
}
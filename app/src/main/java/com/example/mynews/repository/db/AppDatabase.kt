package com.example.mynews.repository.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mynews.repository.NewsRepository
import com.example.mynews.repository.data.Doc
import com.example.mynews.repository.roomdata.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(TopArticles::class, DocEntity::class, MediaMetaEntity::class,
    MediaEntity::class, MultimediaEntity::class, MultimediaXEntity::class,
    SharedArticle::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topArticlesDao(): TopArticlesDao
    abstract fun mediaDao(): MediaDao
    abstract fun multimediaXDao(): MultimediaXDao
    abstract fun mediaMetadataDao(): MediaMetaDao
    abstract fun sharedArticleDao(): SharedArticleDao
    abstract fun docDao(): DocDao
    abstract fun multimediaDao(): MultimediaDao
    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
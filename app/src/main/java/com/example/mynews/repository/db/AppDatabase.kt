package com.example.mynews.repository.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mynews.repository.NewsRepository
import com.example.mynews.repository.roomdata.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(TopArticles::class, DocEntity::class, MediaMetaEntity::class, MediaEntity::class, MultimediaEntity::class, MultimediaXEntity::class,
    SharedArticle::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topArticlesDao(): TopArticlesDao
    abstract fun mediaDao(): MediaDao
    abstract fun multimediaXDao(): MultimediaXDao
    abstract fun mediaMetadataDao(): MediaMetaDao


//    private class WordDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    var topArticlesDao = database.topArticlesDao()
//                    val newsRepository = NewsRepository(database)
//
//                    try {
//                        val primaryNewsResponse = newsRepository.saveFromApiToDb("home")
//                        if (primaryNewsResponse.isSuccessful) {
//                            newsRepository.topStoriesRepository.storeResultInDbTopStories(primaryNewsResponse.body()!!)
//                            Log.i("Db", "Hello**************")
//                            //Do your thing
//                        } else {
//                            //Handle unsuccessful response
//                        }
//                    } catch (e: Exception) {
//                        //Handle error
//                    }
//                }
//            }
//        }
//    }

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
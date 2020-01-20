package com.example.mynews.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.SearchData
import com.example.mynews.repository.db.*
import com.example.mynews.repository.roomdata.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NewsRepository(val db: AppDatabase, application: Application) {

    val apiCaller = ApiCaller()

    val topArticlesDao = db.topArticlesDao()
    val mediaDao = db.mediaDao()
    val multimediaXDao = db.multimediaXDao()
    val mediaMetadataDao = db.mediaMetadataDao()
    val sharedArticleDao = db.sharedArticleDao()
    val docDao = db.docDao()
    val multimediaDao = db.multimediaDao()

    val topStoriesRepository = TopStoriesRepository(apiCaller, topArticlesDao, multimediaXDao, application)

    val sharedArticlesRepository = SharedArticlesRepository(apiCaller, sharedArticleDao, mediaDao, mediaMetadataDao)

    val searchResultRepository = SearchResultRepository(apiCaller, docDao, multimediaDao)


    fun allStories(section: String): LiveData<List<TopArticlesAndMultimediaX>>{
        Log.i("Db Stories", "I am here")

        return topArticlesDao.getTopStories(section)

        /*val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(2)
            .build()

        val boundary = TopStoriesBoundaryCallBack(topStoriesRepository, section)

        return LivePagedListBuilder<Int, TopArticles>(factory, 2)
            .setBoundaryCallback(boundary)
            .build()*/
    }

    fun mostPopular(): DataSource.Factory<Int, SharedArticleAndMedia>{
        return sharedArticlesRepository.getAllArticleWithMedia()
    }

    /*fun mostPopularMedias(id: Long): DataSource.Factory<Int, MediaAndMeta>{
        return sharedArticlesRepository.getAllMetaWithMedia(id)
    }*/

    suspend fun getFromApi(section: String): DataResults {
        return GlobalScope.async(Dispatchers.IO) {
            topStoriesRepository.getFromApiTopStories(section)
        }.await()
    }

    fun saveFromApiToDbMostPopular(period: Int) {

    }

    suspend fun searchResultFromApi(q: String, fq: List<String>): SearchData {
        return searchResultRepository.getFromApiSearchResult(q,fq)
    }

   /* fun searchResult(): List<DocEntity>{
        return searchResultRepository.getAllMultimediaWithDocs()
    }*/
}
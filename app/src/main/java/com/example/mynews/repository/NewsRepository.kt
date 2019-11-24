package com.example.mynews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.SearchData
import com.example.mynews.repository.db.*
import com.example.mynews.repository.roomdata.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NewsRepository(val db: AppDatabase) {

    val apiCaller = ApiCaller()

    val topArticlesDao = db.topArticlesDao()
    val mediaDao = db.mediaDao()
    val multimediaXDao = db.multimediaXDao()
    val mediaMetadataDao = db.mediaMetadataDao()
    val sharedArticleDao = db.sharedArticleDao()
    val docDao = db.docDao()
    val multimediaDao = db.multimediaDao()

    val topStoriesRepository = TopStoriesRepository(apiCaller, topArticlesDao, multimediaXDao)

    val sharedArticlesRepository = SharedArticlesRepository(apiCaller, sharedArticleDao, mediaDao, mediaMetadataDao)

    val searchResultRepository = SearchResultRepository(apiCaller, docDao, multimediaDao)


    fun allStories(section: String): LiveData<PagedList<TopArticles>>{
        Log.i("Db Stories", "I am here")

        val factory = topArticlesDao.getTopStories(section)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(2)
            .build()

        val boundary = TopStoriesBoundaryCallBack(topStoriesRepository, section)

        return LivePagedListBuilder<Int, TopArticles>(factory, 2)
            .setBoundaryCallback(boundary)
            .build()
    }

    fun mostPopular(): DataSource.Factory<Int, SharedArticleAndMedia>{
        return sharedArticlesRepository.getAllArticleWithMedia()
    }

    /*fun mostPopularMedias(id: Long): DataSource.Factory<Int, MediaAndMeta>{
        return sharedArticlesRepository.getAllMetaWithMedia(id)
    }*/

    fun saveFromApiToDb(section: String) {

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
package com.example.mynews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.SearchData
import com.example.mynews.repository.db.*
import com.example.mynews.repository.roomdata.DocEntity
import com.example.mynews.repository.roomdata.MultimediaXEntity
import com.example.mynews.repository.roomdata.SharedArticle
import com.example.mynews.repository.roomdata.TopArticles
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


    fun allStories(section: String): LiveData<List<TopArticles>> {
        return topStoriesRepository.getAllMultimediaWithTopArticles(section)
    }

    fun mostPopular(): LiveData<List<SharedArticle>>{
        return sharedArticlesRepository.getAllArticleWithMedia()
    }

    fun saveFromApiToDb(section: String) {
         topStoriesRepository.getFromApiTopStories(section)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                topStoriesRepository.storeResultInDbTopStories(it)
            },
                { Log.i("Stories", "${it.message}")}
            )

    }

    fun saveFromApiToDbMostPopular(period: Int) {
        sharedArticlesRepository.getFromApiMostPopular(period)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                sharedArticlesRepository.storeResultInDbMostPopular(it)
            },
                { Log.i("Stories", "${it.message}")}
            )

    }

    fun searchResultFromApi(q: String, fq: List<String>): Observable<SearchData> {
        return searchResultRepository.getFromApiSearchResult(q,fq)
    }

    fun searchResult(): List<DocEntity>{
        return searchResultRepository.getAllMultimediaWithDocs()
    }
}
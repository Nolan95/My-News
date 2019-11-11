package com.example.mynews.repository

import androidx.lifecycle.LiveData
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.db.*
import com.example.mynews.repository.roomdata.TopArticles
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NewsRepository(val db: AppDatabase) {

    val apiCaller = ApiCaller()

    val topArticlesDao = db.topArticlesDao()
    val mediaDao = db.mediaDao()
    val multimediaXDao = db.multimediaXDao()
    val mediaMetadataDao = db.mediaMetadataDao()

    val topStoriesRepository = TopStoriesRepository(apiCaller, topArticlesDao, multimediaXDao)


    val allStories: LiveData<List<TopArticles>> = topStoriesRepository.getTopStoriesFormDb()

    fun saveFromApiToDb(section: String): Observable<DataResults> {
        return topStoriesRepository.getFromApiTopStories(section)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }
}
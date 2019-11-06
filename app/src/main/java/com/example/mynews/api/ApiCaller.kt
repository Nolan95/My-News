package com.example.mynews.api

import android.util.Log
import com.example.mynews.NewsAdapter
import com.example.mynews.data.DataResults
import com.example.mynews.data.SearchData
import com.example.mynews.utils.API_KEY
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ApiCaller {

    private val newsApiService by lazy {
        NewsApiService.create()
    }

    fun fetchTopStories(section: String): Observable<DataResults> {
        return  newsApiService.getTopStories(section, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun  fetchMostPopular(period: Int): Observable<DataResults> {
        return newsApiService.getMostPopularNews(period, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun fetchSearchResult(q: String, fq: List<String>): Observable<SearchData> {
        return newsApiService.getSearch(q, fq, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
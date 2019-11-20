package com.example.mynews.repository.api

import com.example.mynews.repository.api.NewsApiService
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.NotificationServerResponse
import com.example.mynews.repository.data.SearchData
import com.example.mynews.utils.API_KEY
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ApiCaller {

    private val newsApiService by lazy {
        NewsApiService.create()
    }

    private val notificationApiService by lazy {
        NotificationApiService.create()
    }

    fun fetchTopStories(section: String): Observable<DataResults> {
        return  newsApiService.getTopStories(section, API_KEY)
    }

    fun  fetchMostPopular(period: Int): Observable<DataResults> {
        return newsApiService.getMostPopularNews(period, API_KEY)
    }

    fun fetchSearchResult(q: String, fq: List<String>): Observable<SearchData> {
        return newsApiService.getSearch(q, fq, API_KEY)
    }

    fun getNotified(q: String, fq: List<String>): Single<SearchData> {
        return newsApiService.getNews(q, fq, API_KEY)
    }

    fun sendTokenToServer(token: String): Observable<NotificationServerResponse>{
        return notificationApiService.sendTokenToServer(Token(token))
    }

}
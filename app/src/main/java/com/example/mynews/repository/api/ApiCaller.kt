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

    suspend fun fetchTopStories(section: String): DataResults{
        return  newsApiService.getTopStories(section, API_KEY)
    }

    suspend fun  fetchMostPopular(period: Int): DataResults {
        return newsApiService.getMostPopularNews(period, API_KEY)
    }

    suspend fun fetchSearchResult(q: String, fq: List<String>): SearchData {
        return newsApiService.getSearch(q, fq, API_KEY)
    }

    fun getNotified(q: String, fq: List<String>): Single<SearchData> {
        return newsApiService.getNews(q, fq, API_KEY)
    }

    fun sendTokenToServer(token: String): Observable<NotificationServerResponse>{
        return notificationApiService.sendTokenToServer(Token(token))
    }

}
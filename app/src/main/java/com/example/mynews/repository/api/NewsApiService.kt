package com.example.mynews.repository.api

import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.SearchData
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface NewsApiService {

    @GET("mostpopular/v2/viewed/{period}.json")
    suspend fun getMostPopularNews(@Path("period") period: Int,
                           @Query("api-key") api_key: String) : DataResults

    @GET("search/v2/articlesearch.json")
    suspend fun getSearch(@Query("q") q: String,
                  @Query("fq") fq: List<String>,
                  @Query("api-key") api_key: String) : SearchData

    @GET("topstories/v2/{section}.json")
    suspend fun getTopStories(@Path("section") section: String,
                      @Query("api-key") api_key: String) : DataResults

    @GET("search/v2/articlesearch.json")
    fun getNews(@Query("q") q: String,
                  @Query("fq") fq: List<String>,
                  @Query("api-key") api_key: String) : Single<SearchData>


    companion object {
        fun create(): NewsApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.nytimes.com/svc/")
                .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }

}
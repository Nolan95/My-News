package com.example.mynews.api

import androidx.annotation.Nullable
import com.example.mynews.data.DataResults
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NewsApiService {

    @GET("mostpopular/v2/viewed/{period}.json")
    fun getMostPopularNews(@Path("period") period: Int,
                           @Query("api-key") api_key: String) : Observable<DataResults>

    @GET("search/v2/articlesearch.json")
    fun getSearch(@Query("q") q: String,
                  @Query("fq") fq: List<String>,
                  @Nullable @Query("begin_date") begin_date: String,
                  @Nullable @Query("end_date") end_date: String) : Observable<DataResults>

    @GET("topstories/v2/{section}.json")
    fun getTopStories(@Path("section") section: String,
                      @Query("api-key") api_key: String) : Observable<DataResults>

    companion object {
        fun create(): NewsApiService {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.nytimes.com/svc/")
                .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }

}
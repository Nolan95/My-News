package com.example.mynews.repository.api

import com.example.mynews.repository.data.NotificationServerResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class Token(val token: String)


interface NotificationApiService {

    @POST("fcm/notifications")
    fun sendTokenToServer(@Body token: Token): Observable<NotificationServerResponse>

    companion object {
        fun create(): NotificationApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("http://192.168.1.17:3000/api/v1/")
                .build()

            return retrofit.create(NotificationApiService::class.java)
        }
    }
}
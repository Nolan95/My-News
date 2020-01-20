package com.example.mynews.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.MultimediaX
import com.example.mynews.repository.db.*
import com.example.mynews.repository.roomdata.MultimediaXEntity
import com.example.mynews.repository.roomdata.TopArticles
import io.reactivex.Observable
import androidx.paging.DataSource
import com.example.mynews.repository.roomdata.TopArticlesAndMultimediaX
import com.example.mynews.utils.ARTICLES
import com.example.mynews.utils.jsonToList
import com.example.mynews.utils.listToJson
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class TopStoriesRepository(val apiCaller: ApiCaller,
                           val topArticlesDao: TopArticlesDao,
                           val multimediaXDao: MultimediaXDao, application: Application) {


    //function to populate multimedia in every Articles
   /* fun getAllMultimediaWithTopArticles(section: String): DataSource.Factory<Int, TopArticles> {
        return  topArticlesDao.getTopStories(section)
    }*/


    val sharedPreferences: SharedPreferences = application.getSharedPreferences("articlePrefs", Context.MODE_PRIVATE)

    val gson = Gson()

    suspend fun getFromApiTopStories(section: String): DataResults {
        Log.i("Api", "Calling Api...")
        return GlobalScope.async(Dispatchers.IO) {
            apiCaller.fetchTopStories(section)
        }.await()
    }


    fun storeResultInDbTopStories(results: DataResults) {
        resulToTopArticles(results)
    }

    private fun rmultimediaToEntity(results: List<MultimediaX>?, id: Long): List<MultimediaXEntity> {
        val multimedias = mutableListOf<MultimediaXEntity>()
        var multimedia = MultimediaXEntity()

        results?.let {
            for(r in results){
                multimedia.apply{
                    url = r.url
                    type = r.type
                    topArticlesId = id
                }

                //multimediaXDao.insertMultimediax(multimedia)
                multimedias.add(multimedia)
            }
        }


        return multimedias
    }

    private fun resulToTopArticles(results: DataResults){
        var article = TopArticles()
        val articleList = mutableListOf<TopArticles>()
        Log.w("Class Name", "${TopArticles::class.java.name.split(".").last()}")
        var storeArticles = gson.jsonToList(sharedPreferences, TopArticles::class.java.name) as MutableList<TopArticles>
        for(r in results.results){
            article.section = r.section
            article.published_date = r.published_date
            article.title = r.title
            article.subsection = r.subsection
            article.url = r.url
            article.type = results.section
            if (storeArticles.isNotEmpty()) {
                if (!storeArticles.contains(article)){
                    val id = topArticlesDao.insertTopStories(article)
                    val multimedia = rmultimediaToEntity(r.multimedia, id)
                    multimediaXDao.insertAllMultimediax(multimedia)
                    articleList.add(article)
                }
            } else {
                val id = topArticlesDao.insertTopStories(article)
                val multimedia = rmultimediaToEntity(r.multimedia, id)
                multimediaXDao.insertAllMultimediax(multimedia)
                articleList.add(article)
            }


        }
        sharedPreferences.edit().putString(ARTICLES, gson.listToJson(articleList))
    }


}
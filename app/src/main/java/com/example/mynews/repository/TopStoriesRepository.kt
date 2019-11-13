package com.example.mynews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.MultimediaX
import com.example.mynews.repository.data.Result
import com.example.mynews.repository.db.*
import com.example.mynews.repository.roomdata.MultimediaXEntity
import com.example.mynews.repository.roomdata.TopArticles
import io.reactivex.Observable
import androidx.lifecycle.MediatorLiveData



class TopStoriesRepository(val apiCaller: ApiCaller,
                           val topArticlesDao: TopArticlesDao,
                           val multimediaXDao: MultimediaXDao) {


    //function to populate multimedia in every Articles
    fun getAllMultimediaWithTopArticles(section: String): LiveData<List<TopArticles>> {
        val articlesLiveData = topArticlesDao.getTopStories(section)
        Log.w("section" ,"${section}")
        return Transformations.switchMap<List<TopArticles>, List<TopArticles>>(articlesLiveData) { inputTopArticles ->
            val topArticlesMediatorLiveData = MediatorLiveData<List<TopArticles>>()
            for (article in inputTopArticles) {
                topArticlesMediatorLiveData.addSource(multimediaXDao.getMultimediax(article.id)) { multimedia ->
                    article.multimedia = multimedia
                    topArticlesMediatorLiveData.postValue(inputTopArticles)
                }
            }

            topArticlesMediatorLiveData
        }
    }


    fun getFromApiTopStories(section: String): Observable<DataResults> {
        return apiCaller.fetchTopStories(section)
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


                multimedias.add(multimedia)
            }
        }


        return multimedias
    }

    private fun resulToTopArticles(results: DataResults){
        var article = TopArticles()
        for(r in results.results){
            article.section = r.section
            article.published_date = r.published_date
            article.title = r.title
            article.subsection = r.subsection
            article.url = r.url
            article.type = results.section
            var id = topArticlesDao.insertTopStories(article)
            var multimedia = rmultimediaToEntity(r.multimedia, id)
            multimediaXDao.insertAllMultimediax(multimedia)
        }
    }


}
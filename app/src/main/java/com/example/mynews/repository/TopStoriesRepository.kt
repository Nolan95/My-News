package com.example.mynews.repository

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
import io.reactivex.schedulers.Schedulers


class TopStoriesRepository(val apiCaller: ApiCaller,
                           val topArticlesDao: TopArticlesDao,
                           val multimediaXDao: MultimediaXDao) {


    //function to populate multimedia in every Articles
    fun getAllMultimediaWithTopArticles(section: String): DataSource.Factory<Int, TopArticlesAndMultimediaX> {
        return  topArticlesDao.getTopStories(section)
    }


    fun getFromApiTopStories(section: String): Observable<DataResults> {
        val dataSource = apiCaller.fetchTopStories(section)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

        return dataSource
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

                multimediaXDao.insertMultimediax(multimedia)
                //multimedias.add(multimedia)
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
            rmultimediaToEntity(r.multimedia, id)
            //multimediaXDao.insertAllMultimediax(multimedia)
        }
    }


}
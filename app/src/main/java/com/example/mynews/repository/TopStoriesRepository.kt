package com.example.mynews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.MultimediaX
import com.example.mynews.repository.data.Result
import com.example.mynews.repository.db.*
import com.example.mynews.repository.roomdata.MultimediaXEntity
import com.example.mynews.repository.roomdata.TopArticles
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

class TopStoriesRepository(val apiCaller: ApiCaller,
                           val topArticlesDao: TopArticlesDao,
                           val multimediaXDao: MultimediaXDao) {

//    fun getAllResult(result: List<Result>){
//
//        for(r in result){
//            if(mediaDao.getMedias(r.resultId).isNullOrEmpty()){
//                r.multimedia = multimediaXDao.getMultimediax(r.resultId)
//            }else{
//                r.media = mediaDao.getMedias(r.resultId)
//                var tmp = r.media
//                tmp?.forEach { it.mediaMetadata = mediaMetaDao.getMediaMeta(it.mediaxId) }
//                r.media = tmp
//            }
//        }
//
//    }

    fun getTopStoriesFormDb(): LiveData<List<TopArticles>>{
        return topArticlesDao.getTopStories()
    }


    fun getFromApiTopStories(section: String): Observable<DataResults> {
        return apiCaller.fetchTopStories(section)
    }


    fun storeResultInDbTopStories(results: DataResults) {
        var articles = ResulToTopArticles(results.results)
        var temp = results.results
        topArticlesDao.insertAllTopStories(articles)

        for(r in temp){
            var multimedia = RmultimediaToEntity(r.multimedia)
            multimediaXDao.insertAllMultimediax(multimedia)
        }
    }

    private fun RmultimediaToEntity(results: List<MultimediaX>?): List<MultimediaXEntity> {
        val multimedias = mutableListOf<MultimediaXEntity>()
        var multimedia = MultimediaXEntity()

        results?.let {
            for(r in results){
                multimedia.url = r.url
                multimedia.type = r.type

                multimedias.add(multimedia)
            }
        }


        return multimedias
    }

    private fun ResulToTopArticles(results: List<Result>): List<TopArticles> {

        val articles = mutableListOf<TopArticles>()
        var article = TopArticles()
        for(r in results){
            article.section = r.section
            article.published_date = r.published_date
            article.title = r.title
            article.subsection = r.subsection
            article.url = r.url
            articles.add(article)
        }

        return articles
    }


//    fun getAllTopStrories(section: String): Observable<DataResults>{
//        return Observable.concatArray(
//            getResultFormDb(),
//            getResultFromApiTopStories(section))
//    }
}
package com.example.mynews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.Multimedia
import com.example.mynews.repository.data.MultimediaX
import com.example.mynews.repository.data.SearchData
import com.example.mynews.repository.db.DocDao
import com.example.mynews.repository.db.MultimediaDao
import com.example.mynews.repository.db.MultimediaXDao
import com.example.mynews.repository.db.TopArticlesDao
import com.example.mynews.repository.roomdata.DocEntity
import com.example.mynews.repository.roomdata.MultimediaEntity
import com.example.mynews.repository.roomdata.MultimediaXEntity
import com.example.mynews.repository.roomdata.TopArticles
import io.reactivex.Observable

class SearchResultRepository(val apiCaller: ApiCaller,
                             val docDao: DocDao,
                             val multimediaDao: MultimediaDao) {


    //function to populate multimedia in every Articles
    fun getAllMultimediaWithDocs(): List<DocEntity> {
        val docs = docDao.getAllDocs()
        for(article in docs){
            article.multimedia = multimediaDao.getAllMultimedia(article.docId)
        }

        return docs
    }


    fun getFromApiSearchResult(q: String, fq: List<String>): Observable<SearchData> {
        return apiCaller.fetchSearchResult(q, fq)
    }


    fun storeSearchResultInDb(results: SearchData) {
        resulToArticles(results)
    }

    private fun rmultimediaToEntity(results: List<Multimedia>?, id: Long): List<MultimediaEntity> {
        val multimedias = mutableListOf<MultimediaEntity>()
        var multimedia = MultimediaEntity()

        results?.let {
            for(r in results){
                multimedia.apply{
                    url = r.url
                    type = r.type
                    docId = id
                }


                multimedias.add(multimedia)
            }
        }


        return multimedias
    }

    private fun resulToArticles(results: SearchData){
        var article = DocEntity()
        for(r in results.response.docs){
            article.apply {
                 pub_date = r.pub_date
                 section_name = r.section_name
                 snippet = r.snippet
                 source = r.source
                 subsection_name = r.subsection_name
                 uri = r.uri
                 web_url = r.web_url
                 word_count = r.word_count
            }

            var id = docDao.insertDoc(article)
            var multimedia = rmultimediaToEntity(r.multimedia, id)
            multimediaDao.insertAll(multimedia)
        }
    }

}
package com.example.mynews.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.mynews.repository.NewsRepository
import com.example.mynews.repository.db.AppDatabase
import androidx.lifecycle.Transformations
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.SearchData
import com.example.mynews.repository.roomdata.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class NewsViewModel(application: Application):AndroidViewModel(application){

    val db = AppDatabase.getDatabase(application)!!
    val newsRepository: NewsRepository = NewsRepository(db, application)

    //val data: DataSource.Factory<Int, TopArticlesAndMultimediaX> = newsRepository.allStories("home")
    //val allTopStories: LiveData<PagedList<TopArticlesAndMultimediaX>> = LivePagedListBuilder(data, 10).build()

    //val businessStories = newsRepository.allBusinessStories


    fun allStoriesBySection(section: String): LiveData<List<TopArticlesAndMultimediaX>>{
        Log.i("Insertion", "I am here")
        return newsRepository.allStories(section)
    }

    fun mostPopular(): LiveData<PagedList<SharedArticleAndMedia>>{
        return LivePagedListBuilder(newsRepository.mostPopular(), 10).build()
    }

    fun searchData(q: String, fq: List<String>): LiveData<SearchData>{
         return liveData(Dispatchers.IO) {

            val result = newsRepository.searchResultFromApi(q,fq)

            emit(result)
        }
    }

    fun saveFromApiToDb(section: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = newsRepository.getFromApi(section)
            newsRepository.topStoriesRepository.storeResultInDbTopStories(result)
        }
    }

    /*fun mostPopularMedias(id: Long): LiveData<PagedList<MediaAndMeta>>{
        return LivePagedListBuilder(newsRepository.mostPopularMedias(id), 10).build()
    }*/

}
package com.example.mynews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mynews.repository.NewsRepository
import com.example.mynews.repository.db.AppDatabase
import androidx.lifecycle.Transformations
import androidx.lifecycle.LiveData
import com.example.mynews.repository.roomdata.SharedArticle
import com.example.mynews.repository.roomdata.TopArticles


class NewsViewModel(application: Application):AndroidViewModel(application){

    val db = AppDatabase.getDatabase(application)!!
    val newsRepository: NewsRepository = NewsRepository(db)

    //val allTopStories = newsRepository.allStories

    //val businessStories = newsRepository.allBusinessStories

    fun allStoriesBySection(section: String): LiveData<List<TopArticles>>{
        return newsRepository.allStories(section)
    }

    fun mostPopular(): LiveData<List<SharedArticle>>{
        return newsRepository.mostPopular()
    }



}
package com.example.mynews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.repository.NewsRepository
import com.example.mynews.repository.db.AppDatabase

class NewsViewModel(application: Application):AndroidViewModel(application){

    val db = AppDatabase.getDatabase(application)!!
    val newsRepository: NewsRepository = NewsRepository(db)

    val AllTopStories = newsRepository.allStories


}
package com.example.mynews.repository.roomdata

import android.util.Log
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import com.example.mynews.repository.TopStoriesRepository
import com.example.mynews.repository.api.ApiCaller

class BoundaryCondition(private val topStoriesRepository: TopStoriesRepository,
                        private val section: String) : PagedList.BoundaryCallback<TopArticlesAndMultimediaX>() {

    private var lastRequestedPage = 1

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        requestAndSaveData(section)
    }

    override fun onItemAtEndLoaded(itemAtEnd: TopArticlesAndMultimediaX) {
        super.onItemAtEndLoaded(itemAtEnd)
        requestAndSaveData(section)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    private fun requestAndSaveData(section: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        topStoriesRepository.getFromApiTopStories(section)
            .subscribe({
                if(it != null){
                    Log.i("Boundary", "I am here")
                    topStoriesRepository.storeResultInDbTopStories(it)
                    lastRequestedPage++
                    isRequestInProgress = false
                    Log.i("Boundary", "I am here")
                }
            },{
                isRequestInProgress = false
            })
    }
}
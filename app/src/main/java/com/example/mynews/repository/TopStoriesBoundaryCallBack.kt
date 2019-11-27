package com.example.mynews.repository

import androidx.paging.PagedList
import com.example.mynews.repository.roomdata.TopArticles
import com.example.mynews.repository.roomdata.TopArticlesAndMultimediaX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TopStoriesBoundaryCallBack(private val topStoriesRepository: TopStoriesRepository,
                                 private val section: String) : PagedList.BoundaryCallback<TopArticles>() {

    private var lastRequestedPage = 1

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        requestAndSaveData(section)
    }

    override fun onItemAtEndLoaded(itemAtEnd: TopArticles) {
        super.onItemAtEndLoaded(itemAtEnd)
        requestAndSaveData(section)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    private fun requestAndSaveData(section: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        GlobalScope.launch(Dispatchers.IO) {
            val result = topStoriesRepository.getFromApiTopStories(section)
            topStoriesRepository.storeResultInDbTopStories(result)
            lastRequestedPage++
        }
    }
}
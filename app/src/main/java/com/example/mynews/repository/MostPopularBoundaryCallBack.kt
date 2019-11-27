package com.example.mynews.repository

import androidx.paging.PagedList
import com.example.mynews.repository.roomdata.SharedArticleAndMedia
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MostPopularBoundaryCallBack(private val period: Int,
                                  private val sharedArticlesRepository: SharedArticlesRepository) : PagedList.BoundaryCallback<SharedArticleAndMedia>(){

        private var lastRequestedPage = 1

        // avoid triggering multiple requests in the same time
        private var isRequestInProgress = false

        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            requestAndSaveData(period)
        }

        override fun onItemAtEndLoaded(itemAtEnd: SharedArticleAndMedia) {
            super.onItemAtEndLoaded(itemAtEnd)
            requestAndSaveData(period)
        }

        companion object {
            private const val NETWORK_PAGE_SIZE = 50
        }

        private fun requestAndSaveData(period: Int) {
            if (isRequestInProgress) return

            isRequestInProgress = true

            GlobalScope.launch(Dispatchers.IO){
                val result = sharedArticlesRepository.getFromApiMostPopular(period)
                sharedArticlesRepository.storeResultInDbMostPopular(result)
                lastRequestedPage++
            }
        }
}
package com.example.mynews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.DataResults
import com.example.mynews.repository.data.MediaX
import com.example.mynews.repository.data.MultimediaX
import com.example.mynews.repository.db.*
import com.example.mynews.repository.roomdata.*
import io.reactivex.Observable

class SharedArticlesRepository(val apiCaller: ApiCaller,
                               val sharedArticleDao: SharedArticleDao,
                               val mediaDao: MediaDao,
                               val mediaMetaDao: MediaMetaDao) {



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

//    fun getTopStoriesFormDb(): LiveData<List<TopArticles>>{
//        return topArticlesDao.getTopStories()
//    }

    //function to populate Mediameta in every Medias
    fun getAllMetaWithMedia(id: Long): LiveData<List<MediaEntity>> {
        val mediaLiveData = mediaDao.getMedias(id)
        return Transformations.switchMap<List<MediaEntity>, List<MediaEntity>>(mediaLiveData) { inputMediaEntity ->
            val mediaMediatorLiveData = MediatorLiveData<List<MediaEntity>>()
            for (media in inputMediaEntity) {
                mediaMediatorLiveData.addSource(mediaMetaDao.getMediaMeta(media.mediaId)) { meta ->
                    media.mediaMetadata = meta
                    mediaMediatorLiveData.postValue(inputMediaEntity)
                }
            }

            mediaMediatorLiveData
        }
    }

    //function to populate Medias in every Artciles
    fun getAllArticleWithMedia(): LiveData<List<SharedArticle>>{
        val articles = sharedArticleDao.getSharedArticles()
        return Transformations.switchMap<List<SharedArticle>, List<SharedArticle>>(articles) { inputArticles ->
            val articleMediatorLiveData = MediatorLiveData<List<SharedArticle>>()
            for (article in inputArticles) {
                articleMediatorLiveData.addSource(getAllMetaWithMedia(article.sharedArticleId)) { medias ->
                    article.medias = medias
                    articleMediatorLiveData.postValue(inputArticles)
                }
            }

            articleMediatorLiveData
        }
    }

    fun getFromApiMostPopular(period: Int): Observable<DataResults> {
        return apiCaller.fetchMostPopular(period)
    }


    fun storeResultInDbMostPopular(results: DataResults) {
        resulToSharedArticles(results)
    }

    private fun rmediaToEntity(results: List<MediaX>?, id: Long) {
        val multimedias = mutableListOf<MediaEntity>()
        var medias = MediaEntity()
        var meta = MediaMetaEntity()
        var metas = mutableListOf<MediaMetaEntity>()
        results?.let {
            for(media in results){
                medias.apply{
                    sharedArticleId = id
                }
                val id = mediaDao.insertMedias(medias)

                for(m in media.mediaMetadata){
                    meta.apply {
                        url = m.url
                        width = m.width
                        height = m.height
                        mediaId = id
                    }
                    metas.add(meta)
                }
                mediaMetaDao.insertAllMediaMeta(metas)
            }
        }
    }

    private fun resulToSharedArticles(results: DataResults){
        var article = SharedArticle()
        for(r in results.results){
            article.section = r.section
            article.published_date = r.published_date
            article.title = r.title
            article.url = r.url
            var id = sharedArticleDao.insertArticle(article)
            rmediaToEntity(r.media, id)
        }
    }


}
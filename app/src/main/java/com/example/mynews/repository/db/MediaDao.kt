package com.example.mynews.repository.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.MediaX
import com.example.mynews.repository.roomdata.MediaAndMeta
import com.example.mynews.repository.roomdata.MediaEntity

@Dao
interface MediaDao {
    @Query("SELECT * FROM medias WHERE shared_article_id = :sharedArticlesId")
    fun getMedias(sharedArticlesId: Long): DataSource.Factory<Int, MediaAndMeta>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedias(medias: MediaEntity): Long


}
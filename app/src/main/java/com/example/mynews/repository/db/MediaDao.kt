package com.example.mynews.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.MediaX
import com.example.mynews.repository.roomdata.MediaEntity

@Dao
interface MediaDao {
    @Query("SELECT * FROM medias WHERE shared_article_id = :sharedArticlesId")
    fun getMedias(sharedArticlesId: Long): LiveData<List<MediaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedias(medias: MediaEntity): Long


}
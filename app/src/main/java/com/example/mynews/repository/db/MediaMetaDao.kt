package com.example.mynews.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.MediaMetadata
import com.example.mynews.repository.roomdata.MediaMetaEntity

@Dao
interface MediaMetaDao {

    @Query("SELECT * FROM mediametas WHERE media_id = :mediaId")
    fun getMediaMeta(mediaId: Long): LiveData<List<MediaMetaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMediaMeta(mediaMeta: List<MediaMetaEntity>)
}
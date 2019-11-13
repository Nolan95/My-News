package com.example.mynews.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.MultimediaX
import com.example.mynews.repository.roomdata.MultimediaXEntity

@Dao
interface MultimediaXDao {

    @Query("SELECT * FROM multimediax WHERE top_articles_id = :topArticlesId")
    fun getMultimediax(topArticlesId: Long): LiveData<List<MultimediaXEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMultimediax(multimediax: List<MultimediaXEntity>?)
}
package com.example.mynews.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.Multimedia
import com.example.mynews.repository.roomdata.MultimediaEntity

@Dao
interface MultimediaDao {

    @Query("SELECT * FROM multimedias WHERE doc_id = :docId")
    fun getAllMultimedia(docId: Long): List<MultimediaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(multimedia: List<MultimediaEntity>)
}
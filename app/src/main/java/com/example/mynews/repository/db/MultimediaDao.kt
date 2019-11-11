package com.example.mynews.repository.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.Multimedia


interface MultimediaDao {

    @Query("SELECT * FROM multimedias WHERE doc_id = :docId")
    fun getAllMultimedia(docId: Int): List<Multimedia>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(multimedia: List<Multimedia>)
}
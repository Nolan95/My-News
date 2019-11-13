package com.example.mynews.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.repository.data.Doc
import com.example.mynews.repository.roomdata.DocEntity
import io.reactivex.Single

@Dao
interface DocDao{

    @Query("SELECT * FROM docs")
    fun getAllDocs(): List<DocEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(docs: List<DocEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoc(doc: DocEntity): Long

}
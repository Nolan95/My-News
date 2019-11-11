package com.example.mynews.repository.data

import androidx.room.*
import kotlinx.android.parcel.IgnoredOnParcel

data class Result(
    val `abstract`: String = "",
    val dataResultsId: Int,
    val published_date: String = "",
    val section: String = "",
    val short_url: String = "",
    val subsection: String = "",
    val title: String = "",
    val updated_date: String = "",
    val url: String,
    val id: Long = 0,
    val views: Int = 0,
    val source: String = "",
    val pub_date: String = "",
    val section_name: String = "",
    val web_url: String = "",
    val subsection_name: String = "",
    val snippet: String = "",
    val multimedia: List<MultimediaX>? = null,
    val media: List<MediaX>? = null
)
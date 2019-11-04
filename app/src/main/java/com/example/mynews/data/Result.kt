package com.example.mynews.data

data class Result(
    val `abstract`: String,
    val byline: String,
    val created_date: String,
    val des_facet: List<String>,
    val item_type: String,
    val kicker: String,
    val material_type_facet: String,
    val multimedia: List<MultimediaX>? = null,
    val media: List<MediaX>? = null,
    val org_facet: List<String>,
    val published_date: String,
    val section: String,
    val short_url: String,
    val subsection: String,
    val title: String,
    val updated_date: String,
    val url: String,
    val id: Long = 0,
    val views: Int = 0,
    val source: String = ""
)
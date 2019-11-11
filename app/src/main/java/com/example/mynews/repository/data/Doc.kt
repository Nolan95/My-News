package com.example.mynews.repository.data

import androidx.room.*

data class Doc(
    val _id: String,
    val `abstract`: String,
    val document_type: String,
    val lead_paragraph: String,
    val news_desk: String,
    val print_page: String,
    val pub_date: String,
    val section_name: String,
    val snippet: String,
    val source: String,
    val subsection_name: String,
    val type_of_material: String,
    val uri: String,
    val web_url: String,
    val word_count: Int,
    val multimedia: List<Multimedia>
)
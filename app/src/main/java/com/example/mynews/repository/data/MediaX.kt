package com.example.mynews.repository.data

import com.google.gson.annotations.SerializedName


data class MediaX(
    val approved_for_syndication: Int,
    val caption: String,
    val copyright: String,
    @SerializedName("media-metadata") var mediaMetadata: List<MediaMetadata>,
    val subtype: String,
    val type: String
)
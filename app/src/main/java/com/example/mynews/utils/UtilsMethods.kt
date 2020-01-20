package com.example.mynews.utils

import android.content.SharedPreferences
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.example.mynews.repository.roomdata.TopArticles
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat


fun String.formatDate(): String {
    val dateFormat = listOf("yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ssZ")
    for (formatString in dateFormat) {
        try {
            val parser = SimpleDateFormat(formatString)
            val formatter = SimpleDateFormat("dd/MM/yy")
            val output = formatter.format(parser.parse(this))
            return output
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }
    return ""
}


fun String.formatDateResult(): String {
    val parser = SimpleDateFormat("dd/MM/yy")
    val formatter = SimpleDateFormat("yyyyMMdd")
    val output = formatter.format(parser.parse(this))
    return output
}

fun String.createUri(): Uri? {
    var uri: Uri? = null
    if(!TextUtils.isEmpty(this)){
        val f = File(this)
        uri = Uri.fromFile(f)
    }
    return uri
}

fun Gson.listToJson(objectList: Any): String {
    val gson = Gson()
    return gson.toJson(objectList)
}


fun Gson.jsonToList(sharedPreferences: SharedPreferences, className: String): MutableList<Any>? {
    val gson = Gson()
    val clazz = className.split(".").last()
    var articleList = mutableListOf<Any>()
    when(clazz){
        "TopArticles" -> {
            val groupListType = object : TypeToken<MutableList<TopArticles>>() {}.type
            val articleJson = sharedPreferences.getString(ARTICLES, "")
            if (articleJson == "")
                articleList
            else
                articleList = gson.fromJson("", groupListType)
        }
    }

    return articleList

}
package com.example.mynews.utils

import android.net.Uri
import android.text.TextUtils
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


fun String.formatDateResult(): String{
    val parser = SimpleDateFormat("dd/MM/yy")
    val formatter = SimpleDateFormat("yyyyMMdd")
    val output = formatter.format(parser.parse(this))
    return output
}

fun String.createUri(): Uri?{
    var uri: Uri? = null
    if(!TextUtils.isEmpty(this)){
        val f = File(this)
        uri = Uri.fromFile(f)
    }
    return uri
}
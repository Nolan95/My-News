package com.example.mynews.utils

import java.text.ParseException
import java.text.SimpleDateFormat


fun formatDate(date: String): String {
    val dateFormat = listOf("yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ssZ")
    for (formatString in dateFormat) {
        try {
            val parser = SimpleDateFormat(formatString)
            val formatter = SimpleDateFormat("dd/MM/yy")
            val output = formatter.format(parser.parse(date))
            return output
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }
    return ""
}


fun formatDateResult(date: String): String{
    val parser = SimpleDateFormat("dd/MM/yy")
    val formatter = SimpleDateFormat("yyyyMMdd")
    val output = formatter.format(parser.parse(date))
    return output
}
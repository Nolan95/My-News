package com.example.mynews.extensions

fun String.fromStringToArray(separator: String): List<String>{
    return this.split(separator)
}

fun List<String>.fromArrayToString(): String {
    return this.joinToString()
}
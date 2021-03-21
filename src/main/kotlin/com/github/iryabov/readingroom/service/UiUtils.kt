package com.github.iryabov.readingroom.service

fun shorten(text: String?, length: Int): String {
    if (text == null)
        return ""
    return if (text.length < length)
        text
    else
        "${text.substring(0, length - 1)}..."
}

fun split(text: String?, separator: String = ", "): List<String> {
    return if (text.isNullOrEmpty()) return emptyList() else text.split(separator)
}
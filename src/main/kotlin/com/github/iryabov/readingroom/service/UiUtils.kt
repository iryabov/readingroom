package com.github.iryabov.readingroom.service

fun short(text: String?, length: Int): String {
    if (text == null)
        return ""
    return if (text.length < length)
        text
    else
        "${text.substring(0, length - 1)}..."
}
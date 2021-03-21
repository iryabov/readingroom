package com.github.iryabov.readingroom.entity

enum class BookStatus(val color: String) {
    AVAILABLE("success"),
    NOT_AVAILABLE("warning")
}
package com.github.iryabov.readingroom.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(schema = "library", name = "book")
data class Book(
        var title: String,
        var authors: List<String>,
        var description: String?,
        var language: Language?,
        var published: LocalDate?,
        var publisher: String?,
        var coverLg: String?,
        var coverMd: String?,
        var coverSm: String?,
        var categories: List<String>?,
        @ManyToOne var createdBy: Member,
        var createdAt: LocalDate,
        var isbn13: String?,
        var pagecount: Int?,
        var previewUrl: String?,
        var status: BookStatus,
        var format: BookFormat,
        var place: String?,
        @Id @GeneratedValue var id: Int? = null
)
package com.github.iryabov.readingroom.entity

import com.vladmihalcea.hibernate.type.array.ListArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "book")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType::class
        )
data class Book(
        var title: String,
        @Type(type = "list-array")
        @Column(name = "authors", columnDefinition = "varchar[]")
        var authors: List<String>,
        var description: String?,
        var language: Language?,
        var published: LocalDate?,
        var publisher: String?,
        var coverLg: String?,
        var coverMd: String?,
        var coverSm: String?,
        @Type(type = "list-array")
        @Column(name = "categories", columnDefinition = "varchar[]")
        var categories: List<String>?,
        @ManyToOne
        @JoinColumn(name = "created_by")
        var createdBy: Member,
        var createdAt: LocalDate,
        var isbn: String?,
        var pagecount: Int?,
        var previewUrl: String?,
        var status: BookStatus,
        var format: BookFormat,
        var place: String?,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", columnDefinition = "serial")
        var id: Int? = null
)
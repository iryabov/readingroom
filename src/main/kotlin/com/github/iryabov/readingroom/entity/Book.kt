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
class Book(
        var title: String,
        @Type(type = "list-array")
        @Column(name = "authors", columnDefinition = "varchar[]")
        var authors: List<String>,
        var description: String? = null,
        var language: Language? = null,
        var published: LocalDate? = null,
        var publisher: String? = null,
        var cover: String? = null,
        @Type(type = "list-array")
        @Column(name = "categories", columnDefinition = "varchar[]")
        var categories: List<String> = emptyList(),
        @ManyToOne
        @JoinColumn(name = "created_by")
        var createdBy: Member,
        var createdAt: LocalDate = LocalDate.now(),
        var isbn: String? = null,
        var pagecount: Int? = null,
        var previewUrl: String? = null,
        var status: BookStatus = BookStatus.ADDED,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", columnDefinition = "serial")
        var id: Int? = null
)
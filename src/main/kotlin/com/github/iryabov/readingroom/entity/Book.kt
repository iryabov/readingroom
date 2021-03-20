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
        @Column(name = "description", columnDefinition = "text")
        var description: String? = null,
        var language: Language? = null,
        var published: Int? = null,
        var publisher: String? = null,
        @Column(name = "cover", columnDefinition = "text")
        var cover: String? = null,
        @Type(type = "list-array")
        @Column(name = "categories", columnDefinition = "varchar[]")
        var categories: List<String>? = emptyList(),
        @ManyToOne
        @JoinColumn(name = "created_by")
        var createdBy: Member? = null,
        var createdAt: LocalDate? = LocalDate.now(),
        var pagecount: Int? = null,
        @Column(name = "preview_url", columnDefinition = "text")
        var previewUrl: String? = null,
        var status: BookStatus? = BookStatus.ADDED,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        @ManyToMany(targetEntity = Member::class)
        @JoinTable(
                name = "wanttoread",
                joinColumns = [JoinColumn(name = "member_id")],
                inverseJoinColumns = [JoinColumn(name = "book_id")])
        var wantToRead: Set<Member>? = emptySet(),
        @ManyToMany(targetEntity = Member::class)
        @JoinTable(
                name = "haveread",
                joinColumns = [JoinColumn(name = "member_id")],
                inverseJoinColumns = [JoinColumn(name = "book_id")])
        var haveRead: Set<Member>? = emptySet()
) {
        constructor(): this("", emptyList())
}
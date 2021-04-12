package com.github.iryabov.readingroom.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "review")
data class Review(
        @ManyToOne
        var book: Book,
        @ManyToOne
        var member: Member,
        @Column(name = "description", columnDefinition = "text")
        var description: String,
        var createdAt: LocalDate = LocalDate.now(),
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null
) {
        constructor(): this(Book(), Member(), "")
}
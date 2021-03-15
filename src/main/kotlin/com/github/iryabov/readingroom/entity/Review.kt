package com.github.iryabov.readingroom.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "review")
class Review(
        @ManyToOne
        var book: Book,
        @ManyToOne
        var member: Member,
        var summary: String? = null,
        var description: String,
        var rate: Int? = null,
        var createdAt: LocalDate = LocalDate.now(),
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null
)
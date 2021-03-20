package com.github.iryabov.readingroom.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "rating", uniqueConstraints = [UniqueConstraint(name = "rating_book_member_unique", columnNames = [ "book_id", "member_id" ])])
class Rating(
        @ManyToOne
        var book: Book,
        @ManyToOne
        var member: Member,
        var rate: Int,
        var createdAt: LocalDate = LocalDate.now(),
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null
)
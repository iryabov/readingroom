package com.github.iryabov.readingroom.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "booking")
class Booking(
        @ManyToOne
        var book: Book,
        @ManyToOne
        var member: Member,
        var bookingFrom: LocalDate? = null,
        var bookingUntil: LocalDate? = null,
        var createdAt: LocalDate = LocalDate.now(),
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null
) {
        constructor(): this(Book(), Member())
}
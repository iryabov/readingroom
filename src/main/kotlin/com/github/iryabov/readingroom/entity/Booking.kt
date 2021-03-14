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
        var bookingFrom: LocalDate,
        var bookingUntil: LocalDate,
        var createdAt: LocalDate = LocalDate.now(),
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", columnDefinition = "serial")
        var id: Int? = null
)
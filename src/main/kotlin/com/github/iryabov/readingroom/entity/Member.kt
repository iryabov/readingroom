package com.github.iryabov.readingroom.entity

import javax.persistence.*

@Entity
@Table(name = "member")
class Member(
        var name: String,
        var email: String,
        var avatar: String? = null,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null
)
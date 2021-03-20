package com.github.iryabov.readingroom.entity

import javax.persistence.*

@Entity
@Table(name = "member")
class Member(
        var name: String,
        var email: String,
        @Column(name = "avatar", columnDefinition = "text")
        var avatar: String? = null,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null
)
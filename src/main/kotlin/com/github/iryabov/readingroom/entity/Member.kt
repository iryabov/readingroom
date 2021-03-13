package com.github.iryabov.readingroom.entity

import javax.persistence.*

@Entity
@Table(name = "member")
data class Member (
        var name: String,
        var email: String,
        var avatar: String,
        @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", columnDefinition = "serial")
        var id: Int? = null
)
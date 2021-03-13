package com.github.iryabov.readingroom.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "library", name = "member")
data class Member (
        var name: String,
        var email: String,
        var avatar: String,
        @Id @GeneratedValue var id: Int? = null
)
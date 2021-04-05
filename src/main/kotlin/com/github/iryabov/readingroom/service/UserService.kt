package com.github.iryabov.readingroom.service

import com.github.iryabov.readingroom.entity.Member
import com.github.iryabov.readingroom.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class UserService(val repo: MemberRepository) {
    fun loadUser(email: String, name: String, avatar: String): Int {
        var member: Member? = repo.findByEmail(email)
        if (member != null) {
            repo.save(Member(id = member.id, email = email, name = name, avatar = avatar))
        } else {
            member = repo.save(Member(email = email, name = name, avatar = avatar))
        }
        return member.id!!
    }
}
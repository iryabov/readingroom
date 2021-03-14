package com.github.iryabov.readingroom.repository

import com.github.iryabov.readingroom.entity.Member
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: CrudRepository<Member, Int> {
}
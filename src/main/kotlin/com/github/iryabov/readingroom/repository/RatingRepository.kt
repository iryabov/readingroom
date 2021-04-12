package com.github.iryabov.readingroom.repository

import com.github.iryabov.readingroom.entity.Rating
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RatingRepository: CrudRepository<Rating, Int> {
    fun findByBookIdAndMemberId(bookId: Int, memberId: Int): Rating?
}
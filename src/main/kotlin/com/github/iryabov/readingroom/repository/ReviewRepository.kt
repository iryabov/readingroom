package com.github.iryabov.readingroom.repository

import com.github.iryabov.readingroom.entity.Review
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: CrudRepository<Review, Int> {
}
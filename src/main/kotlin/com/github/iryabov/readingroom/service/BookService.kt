package com.github.iryabov.readingroom.service

import com.github.iryabov.readingroom.entity.Book
import com.github.iryabov.readingroom.entity.Member
import com.github.iryabov.readingroom.entity.Rating
import com.github.iryabov.readingroom.entity.Review
import com.github.iryabov.readingroom.repository.RatingRepository
import com.github.iryabov.readingroom.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(val reviewRepository: ReviewRepository,
                  val ratingRepository: RatingRepository) {
    @Transactional
    fun saveReview(bookId: Int, memberId: Int, rating: Int?, description: String?) {
        val book = Book()
        book.id = bookId
        val member = Member()
        member.id = memberId
        if (!description.isNullOrEmpty()) {
            val foundReview = reviewRepository.findByBookIdAndMemberId(bookId, memberId)
            val review = Review(book = book, member = member, description = description, id = foundReview?.id)
            reviewRepository.save(review)
        }
        if (rating != null) {
            val foundRating = ratingRepository.findByBookIdAndMemberId(bookId, memberId)
            ratingRepository.save(Rating(book = book, member = member, rate = rating, id = foundRating?.id))
        }
    }
}
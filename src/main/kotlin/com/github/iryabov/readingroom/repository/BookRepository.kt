package com.github.iryabov.readingroom.repository

import com.github.iryabov.readingroom.entity.Book
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: CrudRepository<Book, Int> {
}
package com.github.iryabov.readingroom

import com.github.iryabov.readingroom.entity.Book
import com.github.iryabov.readingroom.entity.Language
import com.github.iryabov.readingroom.entity.Member
import com.github.iryabov.readingroom.repository.BookRepository
import com.github.iryabov.readingroom.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoriesTest @Autowired constructor(
        val bookRepo: BookRepository,
        val memberRepo: MemberRepository
) {
    @Test
    fun `Create, find and delete book`() {
        val igor = memberRepo.save(Member(
                email = "to.iryabov@gmail.com",
                name = "Igor"))
        //create
        val harry = bookRepo.save(Book(
                title = "Harry Potter and Philosopher's stone",
                authors = listOf("Joanne Rowling"),
                description = "The Adventures of Harry and His Friends",
                language = Language.EN,
                published = 2000,
                categories = listOf("Adventure", "Fantasy"),
                createdBy = igor
        ))

        //find
        assertThat(bookRepo.findById(igor.id!!).isPresent).isTrue
        assertThat(bookRepo.findById(harry.id!!).isPresent).isTrue
        val found = bookRepo.findById(harry.id!!).get()
        assertThat(found.categories).allMatch { it == "Adventure" || it == "Fantasy" }

//        //update
//        harry.haveRead = setOf(igor)
//        harry.wantToRead = setOf(igor)
//        bookRepo.save(harry)
//        found = bookRepo.findById(harry.id!!).get()
//        assertThat(found.wantToRead).allMatch { it.id == igor.id }
//        assertThat(found.haveRead).allMatch { it.id == igor.id }

        //delete
        bookRepo.delete(harry)
        assertThat(bookRepo.findById(harry.id!!).isEmpty).isTrue
    }
}
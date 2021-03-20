package com.github.iryabov.readingroom

import com.github.iryabov.readingroom.service.GlobalSearchService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GlobalSearchTest @Autowired constructor(
        val service: GlobalSearchService
) {
    @Test
    fun `test global search`() {
        assertThat(service.search("Harry Potter"))
                .anyMatch { it.title.contains("Harry Potter") }

    }
}
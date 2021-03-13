package com.github.iryabov.readingroom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication
class ReadingRoomApplication {
}

fun main(args: Array<String>) {
	runApplication<ReadingRoomApplication>(*args)
}

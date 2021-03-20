package com.github.iryabov.readingroom.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@Service
class GlobalSearchService {
    val client: WebClient = WebClient.builder()
            .baseUrl("https://www.googleapis.com/books/v1")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchangeStrategies(ExchangeStrategies.builder()
                    .codecs {
                        it.registerDefaults(false)
                        it.customCodecs().register(Jackson2JsonEncoder(jacksonObjectMapper(), APPLICATION_JSON))
                        it.customCodecs().register(Jackson2JsonDecoder(jacksonObjectMapper(), APPLICATION_JSON))
                    }.build())
            .build()

    fun search(query: String?, startIndex: Int = 0, maxResults: Int = 30): GoogleBook {
        if (query?.length ?: 0 < 3)
            return GoogleBook(totalItems = 0, items = emptyList())
        val response = client.get().uri {
            it.path("/volumes")
                    .queryParam("q", query ?: "")
                    .queryParam("printType", "books")
                    .build()
        }.retrieve().bodyToMono(GoogleBook::class.java)
        return response.block() ?: GoogleBook(totalItems = 0, items = emptyList())
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
class GoogleBook(
        var totalItems: Int,
        var items: List<BookInfo>
)

@JsonIgnoreProperties(ignoreUnknown = true)
class BookInfo(
        var id: String,
        var etag: String,
        var volumeInfo: BookVolume
)

@JsonIgnoreProperties(ignoreUnknown = true)
class BookVolume(
        var title: String,
        var authors: List<String>?,
        var publisher: String?,
        var publishedDate: String?,
        var description: String?,
        var pageCount: Int?,
        var averageRating: Int?,
        var imageLinks: BookImage?,
        var language: String?,
        var previewLink: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
class BookImage(
        var thumbnail: String
)
package com.demo.book.api

import com.demo.book.BaseIntegrationSpec
import com.demo.book.movie.entity.Screen
import com.demo.book.movie.request.ScreenRequest
import com.demo.book.utils.get
import com.demo.book.utils.post
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus

class ScreenApiTest : BaseIntegrationSpec() {

    init {
        "should save screen" {
            // Given
            val screenReq = newScreenRequest()

            // When
            val response = createNewScreen(screenReq)

            // Then
            response.status shouldBe HttpStatus.OK
            response.body.get() shouldBe 1
        }

        "should get all saved movies" {
            // Given
            val screenReq = newScreenRequest()
            createNewScreen(screenReq)

            // When
            val response = httpClient.get<List<Screen>>("/screens")

            // Then
            response.status shouldBe HttpStatus.OK
            val savedScreen = response.body.get()
            savedScreen.size shouldBe 1
            jsonString(savedScreen[0]) shouldBe """
                |{
                |  "id" : 1,
                |  "title" : "Screen 1",
                |  "capacity" : 50
                |}
            """.trimMargin().trimIndent()
        }
    }

    private fun createNewScreen(screenReq: ScreenRequest): HttpResponse<Any> {
        return httpClient.post(
            url = "/screens",
            body = jsonMapper.writeValueAsString(screenReq)
        )
    }

    private fun newScreenRequest(): ScreenRequest {
        return ScreenRequest(
            "Screen 1",
            50
        )
    }
}

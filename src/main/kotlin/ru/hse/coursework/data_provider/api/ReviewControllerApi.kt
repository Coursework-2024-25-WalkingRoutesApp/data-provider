package ru.hse.coursework.data_provider.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import ru.hse.coursework.data_provider.controller.USER_ID_HEADER
import ru.hse.coursework.data_provider.dto.ReviewDto
import java.time.LocalDateTime
import java.util.*

@Tag(name = "Контроллер отзывов", description = "Контроллер для работы с отзывами к маршрутам: просмотр и добавление отзывов")
@ApiResponses(
    value = [
        ApiResponse(responseCode = "200", description = "Успешный запрос"),
        ApiResponse(responseCode = "201", description = "Создано"),
        ApiResponse(responseCode = "400", description = "Некорректный запрос",
            content = [Content(mediaType = "text/plain", schema = Schema(implementation = String::class))]),
        ApiResponse(responseCode = "401", description = "Не авторизован",
            content = [Content(mediaType = "text/plain", schema = Schema(implementation = String::class))]),
        ApiResponse(responseCode = "404", description = "Не найдено",
            content = [Content(mediaType = "text/plain", schema = Schema(implementation = String::class))]),
        ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = [Content(mediaType = "text/plain", schema = Schema(implementation = String::class))])
    ]
)
interface ReviewControllerApi {

    @Operation(
        summary = "Получение отзывов к маршруту",
        description = "Возвращает все отзывы к маршруту и идентификатор текущего пользователя"
    )
    fun getReviews(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam routeId: UUID
    ): ReviewDto

    @Operation(
        summary = "Добавление или обновление отзыва",
        description = "Создаёт новый отзыв или обновляет существующий от пользователя к маршруту"
    )
    fun addReview(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam routeId: UUID,
        @RequestParam mark: Int,
        @RequestParam reviewText: String,
        @RequestParam createdAt: LocalDateTime
    ): ResponseEntity<String>
}

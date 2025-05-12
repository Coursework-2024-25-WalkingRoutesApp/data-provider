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
import ru.hse.coursework.data_provider.dto.RouteCartDto
import java.util.*

@Tag(
    name = "Контроллер избранных маршрутов",
    description = "Контроллер для получения, добавления и удаления избранных маршрутов пользователя"
)
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
interface FavoriteRoutesControllerApi {

    @Operation(
        summary = "Получить избранные маршруты пользователя",
        description = "Возвращает список избранных маршрутов с учётом координат пользователя"
    )
    fun getFavourites(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): List<RouteCartDto>

    @Operation(
        summary = "Добавить маршрут в избранное",
        description = "Добавляет маршрут в список избранных для указанного пользователя"
    )
    fun addFavourite(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam routeId: UUID
    ): ResponseEntity<String>

    @Operation(
        summary = "Удалить маршрут из избранного",
        description = "Удаляет маршрут из списка избранных для указанного пользователя"
    )
    fun deleteFavourite(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam routeId: UUID
    ): ResponseEntity<String>
}

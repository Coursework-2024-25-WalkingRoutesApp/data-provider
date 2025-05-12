package ru.hse.coursework.data_provider.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import ru.hse.coursework.data_provider.controller.USER_ID_HEADER
import ru.hse.coursework.data_provider.dto.RouteCartDto
import ru.hse.coursework.data_provider.dto.RouteSessionDto

import java.util.*

@Tag(name = "Контроллер сессий маршрутов", description = "Контроллер для работы с сессиями маршрутов: получение завершённых/незавершённых, создание/обновление, получение по id")
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
interface RouteSessionControllerApi {

    @Operation(
        summary = "Получение пройденных маршрутов пользователя",
        description = "Возвращает список завершённых маршрутов пользователя по координатам"
    )
    fun getFinished(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): List<RouteCartDto>

    @Operation(
        summary = "Получение незавершённых сессий пользователя",
        description = "Возвращает список незавершённых маршрутов пользователя по координатам"
    )
    fun getUnfinished(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): List<RouteCartDto>

    @Operation(
        summary = "Создание или обновление сессии маршрута",
        description = "Создаёт новую сессию маршрута или обновляет существующую, включая чекпоинты"
    )
    fun addSession(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestBody routeSessionDto: RouteSessionDto
    ): ResponseEntity<String>

    @Operation(
        summary = "Получение сессии маршрута по routeId",
        description = "Возвращает сессию маршрута пользователя по ID маршрута"
    )
    fun getSession(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam routeId: UUID
    ): ResponseEntity<RouteSessionDto>?
}

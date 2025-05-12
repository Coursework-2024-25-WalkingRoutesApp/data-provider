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
import ru.hse.coursework.data_provider.dto.RouteDto
import ru.hse.coursework.data_provider.dto.RoutePageDto
import java.util.*

@Tag(name = "Контроллер маршрутов", description = "Контроллер для работы с маршрутами: создание, удаление, поиск и получение маршрутов")
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
interface RouteControllerApi {

    @Operation(
        summary = "Создание или обновление маршрута",
        description = "Создаёт новый маршрут или обновляет существующий по id"
    )
    fun addRoute(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestBody routeDto: RouteDto
    ): ResponseEntity<String>

    @Operation(
        summary = "Удаление маршрута",
        description = "Удаляет маршрут пользователя по id"
    )
    fun deleteRoute(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam routeId: UUID
    ): ResponseEntity<String>

    @Operation(
        summary = "Получение черновиков пользователя",
        description = "Возвращает список черновиков маршрутов, созданных пользователем"
    )
    fun getDrafts(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): List<RouteCartDto>

    @Operation(
        summary = "Получение опубликованных маршрутов пользователя",
        description = "Возвращает список опубликованных маршрутов пользователя"
    )
    fun getPublished(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): List<RouteCartDto>

    @Operation(
        summary = "Получение полной информации о маршруте",
        description = "Возвращает детальную информацию о маршруте по id"
    )
    fun getRoutePage(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam routeId: UUID
    ): RoutePageDto

    @Operation(
        summary = "Поиск маршрутов по названию",
        description = "Позволяет искать маршруты по подстроке названия и координатам"
    )
    fun getRouteByName(
        @RequestParam searchValue: String,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam(required = false) radius: Long?
    ): List<RouteCartDto>

    @Operation(
        summary = "Получение маршрутов по координатам и категориям",
        description = "Возвращает список маршрутов по заданным координатам, радиусу и категориям"
    )
    fun getRoutes(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam(required = false) categories: List<String>?,
        @RequestParam(required = false) radius: Long?
    ): List<RouteCartDto>
}

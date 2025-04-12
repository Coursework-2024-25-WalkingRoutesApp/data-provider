package ru.hse.coursework.routes_provider.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.coursework.routes_provider.dto.RouteCartDto
import ru.hse.coursework.routes_provider.dto.RouteSessionDto
import ru.hse.coursework.routes_provider.dto.UserCoordinateDto
import ru.hse.coursework.routes_provider.service.RouteSessionService
import java.util.*

@RestController
@RequestMapping(SESSION_BASE_PATH_URL)
class RouteSessionController(
    private val routeSessionService: RouteSessionService
) {

    @GetMapping(GET_FINISHED_URL)
    fun getFinished(@RequestHeader(USER_ID_HEADER) userId: UUID, @RequestParam latitude: Double, @RequestParam longitude: Double): List<RouteCartDto> =
        routeSessionService.getUserFinishedRoutes(userId, UserCoordinateDto(latitude, longitude))

    @GetMapping(GET_UNFINISHED_URL)
    fun getUnfinished(@RequestHeader(USER_ID_HEADER) userId: UUID, @RequestParam latitude: Double, @RequestParam longitude: Double): List<RouteCartDto> =
        routeSessionService.getUserUnfinishedRoutes(userId, UserCoordinateDto(latitude, longitude))

    @PostMapping(ADD_SESSION_URL)
    fun addSession(@RequestHeader(USER_ID_HEADER) userId: UUID, @RequestBody routeSessionDto: RouteSessionDto): ResponseEntity<String> =
        routeSessionService.createOrUpdateSession(routeSessionDto, userId)

    @GetMapping(GET_SESSION_URL)
    fun getSession(@RequestHeader(USER_ID_HEADER) userId: UUID, @RequestParam routeId: UUID): ResponseEntity<RouteSessionDto>? =
        routeSessionService.getSession(userId, routeId)
}
package ru.hse.coursework.data_provider.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.coursework.data_provider.dto.RouteCartDto
import ru.hse.coursework.data_provider.dto.RouteSessionDto
import ru.hse.coursework.data_provider.dto.UserCoordinateDto
import ru.hse.coursework.data_provider.service.RouteSessionService
import java.util.*

@RestController
@RequestMapping(ru.hse.coursework.data_provider.controller.SESSION_BASE_PATH_URL)
class RouteSessionController(
    private val routeSessionService: RouteSessionService
) {

    @GetMapping(ru.hse.coursework.data_provider.controller.GET_FINISHED_URL)
    fun getFinished(@RequestHeader(ru.hse.coursework.data_provider.controller.USER_ID_HEADER) userId: UUID, @RequestParam latitude: Double, @RequestParam longitude: Double): List<RouteCartDto> =
        routeSessionService.getUserFinishedRoutes(userId, UserCoordinateDto(latitude, longitude))

    @GetMapping(ru.hse.coursework.data_provider.controller.GET_UNFINISHED_URL)
    fun getUnfinished(@RequestHeader(ru.hse.coursework.data_provider.controller.USER_ID_HEADER) userId: UUID, @RequestParam latitude: Double, @RequestParam longitude: Double): List<RouteCartDto> =
        routeSessionService.getUserUnfinishedRoutes(userId, UserCoordinateDto(latitude, longitude))

    @PostMapping(ru.hse.coursework.data_provider.controller.ADD_SESSION_URL)
    fun addSession(@RequestHeader(ru.hse.coursework.data_provider.controller.USER_ID_HEADER) userId: UUID, @RequestBody routeSessionDto: RouteSessionDto): ResponseEntity<String> =
        routeSessionService.createOrUpdateSession(routeSessionDto, userId)

    @GetMapping(ru.hse.coursework.data_provider.controller.GET_SESSION_URL)
    fun getSession(@RequestHeader(ru.hse.coursework.data_provider.controller.USER_ID_HEADER) userId: UUID, @RequestParam routeId: UUID): ResponseEntity<RouteSessionDto>? =
        routeSessionService.getSession(userId, routeId)
}
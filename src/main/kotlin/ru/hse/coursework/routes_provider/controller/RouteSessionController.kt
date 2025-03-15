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
    fun getFinished(@RequestParam userId: UUID, @RequestBody userCoordinateDto: UserCoordinateDto): List<RouteCartDto> =
        routeSessionService.getUserFinishedRoutes(userId, userCoordinateDto)

    @GetMapping(GET_UNFINISHED_URL)
    fun getUnfinished(@RequestParam userId: UUID, @RequestBody userCoordinateDto: UserCoordinateDto): List<RouteCartDto> =
        routeSessionService.getUserUnfinishedRoutes(userId, userCoordinateDto)

    @PostMapping(ADD_SESSION_URL)
    fun addSession(@RequestBody routeSessionDto: RouteSessionDto, @RequestParam userId: UUID): ResponseEntity<String> =
        routeSessionService.createOrUpdateSession(routeSessionDto, userId)
}
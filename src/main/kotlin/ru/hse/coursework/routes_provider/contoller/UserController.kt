package ru.hse.coursework.routes_provider.contoller

import org.springframework.web.bind.annotation.*
import ru.hse.coursework.routes_provider.model.RouteSession
import ru.hse.coursework.routes_provider.repository.FavoriteRepository
import ru.hse.coursework.routes_provider.repository.RouteSessionRepository
import ru.hse.coursework.routes_provider.repository.UserCheckpointRepository
import ru.hse.coursework.routes_provider.service.UserService

@RestController
@RequestMapping("/api/routes-provider/users")
class UserController (
    private val routeSessionRepository: RouteSessionRepository,
    private val userCheckpointRepository: UserCheckpointRepository,
    private val UserService: UserService
) {
    @GetMapping("/route-sessions/all")
    fun getAllRouteSessions() = routeSessionRepository.findAll()

    @GetMapping("/route-sessions/{id}")
    fun getRouteSessionById(@PathVariable id: String) = routeSessionRepository.findById(id)

//    @GetMapping("/route-sessions/active")
//    fun getActiveRouteSessions() = routeSessionRepository.findAllByIsFinishedFalse()
//
//    @GetMapping("/route-sessions/finished")
//    fun getFinishedRouteSessions() = routeSessionRepository.findAllByIsFinishedTrue()

    @GetMapping("/user/page/{userId}")
    fun getUserPage(@PathVariable userId: String) = UserService.getUserPage(userId)

    @GetMapping("route-sessions/checkpoints/{id}/")
    fun getCheckpointsByRouteSessionId(@PathVariable id: String) = userCheckpointRepository.findAllByRouteSessionId(id)

    @PostMapping("/route-sessions/add")
    fun addRouteSession(@RequestBody routeSession: RouteSession) = routeSessionRepository.save(routeSession)

}
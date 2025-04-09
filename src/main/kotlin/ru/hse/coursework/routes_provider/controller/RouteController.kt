package ru.hse.coursework.routes_provider.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.hse.coursework.routes_provider.dto.RouteCartDto
import ru.hse.coursework.routes_provider.dto.RouteDto
import ru.hse.coursework.routes_provider.dto.RoutePageDto
import ru.hse.coursework.routes_provider.dto.UserCoordinateDto
import ru.hse.coursework.routes_provider.service.RouteService
import java.util.ArrayList
import java.util.UUID

@RestController
@RequestMapping(ROUTE_BASE_PATH_URL)
class RouteController(
    private val routeService: RouteService,
    @Value("\${radius-in-meters}")
    private val radiusInMeters: Long
) {

    @PostMapping(ADD_ROUTE_URL)
    fun addRoute(@RequestBody routeDto: RouteDto, @RequestParam userId: UUID): ResponseEntity<String> =
        routeService.createOrUpdateRoute(routeDto, userId)

    @GetMapping(GET_DRAFTS_URL)
    fun getDrafts(@RequestParam userId: UUID, @RequestParam latitude: Double, @RequestParam longitude: Double): List<RouteCartDto> =
        routeService.getUserRouteDrafts(userId, UserCoordinateDto(latitude, longitude))

    @GetMapping(GET_PUBLISHED_URL)
    fun getPublished(@RequestParam userId: UUID, @RequestParam latitude: Double, @RequestParam longitude: Double): List<RouteCartDto> =
        routeService.getUserPublishedRoutes(userId, UserCoordinateDto(latitude, longitude))


    @DeleteMapping(DELETE_ROUTE_URL)
    fun deleteRoute(@RequestParam routeId: UUID, @RequestParam userId: UUID): ResponseEntity<String> =
        routeService.deleteRoute(routeId, userId)

    @GetMapping(GET_ROUTE_PAGE_URL)
    fun getRoutePage(@RequestParam routeId: UUID, @RequestParam userId: UUID): RoutePageDto =
        routeService.getRoutePage(routeId, userId)

    @GetMapping(GET_ROUTE_BY_SEARCH_VALUE_URL)
    fun getRouteByName(
        @RequestParam searchValue: String,
        @RequestParam latitude: Double, @RequestParam longitude: Double,
        @RequestParam(required = false) radius: Long?
    ): List<RouteCartDto> =
        routeService.getRouteBySearchValue(searchValue, UserCoordinateDto(latitude, longitude), radius ?: radiusInMeters)

    @GetMapping(GET_ROUTES_URL)
    fun getRoutes(
        @RequestParam latitude: Double, @RequestParam longitude: Double,
        @RequestParam(required = false) categories: List<String>?,
        @RequestParam(required = false) radius: Long?
    ): List<RouteCartDto> =
        routeService.getRoutes(UserCoordinateDto(latitude, longitude), categories ?: ArrayList<String>(), radius ?: radiusInMeters)
}

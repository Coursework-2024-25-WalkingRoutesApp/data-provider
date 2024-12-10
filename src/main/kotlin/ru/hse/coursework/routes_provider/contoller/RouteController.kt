package ru.hse.coursework.routes_provider.contoller

import org.springframework.web.bind.annotation.*
import ru.hse.coursework.routes_provider.model.NewRoute
import ru.hse.coursework.routes_provider.model.Route
import ru.hse.coursework.routes_provider.model.RouteCategory
import ru.hse.coursework.routes_provider.model.RouteCoordinate
import ru.hse.coursework.routes_provider.repository.ReviewRepository
import ru.hse.coursework.routes_provider.repository.RouteCategoryRepository
import ru.hse.coursework.routes_provider.repository.RouteCoordinateRepository
import ru.hse.coursework.routes_provider.repository.RouteRepository
import ru.hse.coursework.routes_provider.service.RouteService

@RestController
@RequestMapping("/api/routes-provider/routes")
class RouteController(
    private val routeRepository: RouteRepository,
    private val routeService: RouteService
) {

    @GetMapping("/all")
    fun getAllRoutes() = routeRepository.findAll()

//    @GetMapping("/route/{id}")
//    fun getRouteById(@PathVariable id: String) = routeRepository.findById(id)
//
//    @GetMapping("/route/coordinates/{id}")
//    fun getRouteCoordinates(@PathVariable id: String) = routeCoordinateRepository.findAllByRouteId(id)
//
//    @GetMapping("/route/category/{id}")
//    fun getRouteCategories(@PathVariable id: String) = routeCategoryRepository.findAllByRouteId(id)
//
//    @GetMapping("/route/reviews/{id}")
//    fun getRouteReviews(@PathVariable id: String) = reviewRepository.findAllByRouteId(id)

    @GetMapping("/route/page/{routeId}/")
    fun getRoutePage(@PathVariable routeId: String) = routeService.getRoutePage(routeId)

    @PostMapping("/add")
    fun addRoute(@RequestBody newRoute: NewRoute) = routeService.addRoute(newRoute)
}

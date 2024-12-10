package ru.hse.coursework.routes_provider.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.routes_provider.model.NewRoute
import ru.hse.coursework.routes_provider.model.RoutePage
import ru.hse.coursework.routes_provider.repository.ReviewRepository
import ru.hse.coursework.routes_provider.repository.RouteCategoryRepository
import ru.hse.coursework.routes_provider.repository.RouteCoordinateRepository
import ru.hse.coursework.routes_provider.repository.RouteRepository

@Service
class RouteService (
    private val routeRepository: RouteRepository,
    private val coordinateRepository: RouteCoordinateRepository,
    private val routeCategoryRepository: RouteCategoryRepository,
    private val reviewRepository: ReviewRepository
) {

    @Transactional
    fun getRoutePage(routeId: String): RoutePage {
        val route = routeRepository.findById(routeId).orElseThrow { RuntimeException("Route not found") }
        val coordinates = coordinateRepository.findAllByRouteId(routeId)
        val categories = routeCategoryRepository.findAllByRouteId(routeId)
        val reviews = reviewRepository.findAllByRouteId(routeId)

        return RoutePage(route, coordinates, categories, reviews)
    }

    @Transactional
    fun addRoute(newRoute: NewRoute) {
        val route = routeRepository.save(newRoute.route)
        val coordinates = newRoute.coordinates.map { it.copy(routeId = route.id) }
        val categories = newRoute.categories.map { it.copy(routeId = route.id) }

        coordinateRepository.saveAll(coordinates)
        routeCategoryRepository.saveAll(categories)
    }
}
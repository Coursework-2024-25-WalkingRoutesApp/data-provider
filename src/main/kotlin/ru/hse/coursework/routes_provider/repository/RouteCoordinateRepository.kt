package ru.hse.coursework.routes_provider.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.model.RouteCoordinate

@Repository
interface RouteCoordinateRepository : CrudRepository<RouteCoordinate, String> {

    fun findAllByRouteId(routeId: String): List<RouteCoordinate>
}

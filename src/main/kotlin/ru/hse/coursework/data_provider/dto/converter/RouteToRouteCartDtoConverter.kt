package ru.hse.coursework.data_provider.dto.converter

import com.vividsolutions.jts.geom.Point
import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.RouteCartDto
import ru.hse.coursework.data_provider.dto.RouteDto
import ru.hse.coursework.data_provider.model.Route
import ru.hse.coursework.data_provider.model.RouteCategory
import ru.hse.coursework.data_provider.model.RouteCoordinate
import ru.hse.coursework.data_provider.repository.RouteCoordinateRepository

@Component
class RouteToRouteCartDtoConverter(
    private val routeCoordinateRepository: RouteCoordinateRepository
) {

    fun convert(
        route: Route,
        routeCategories: List<RouteCategory>,
        routeCoordinate: RouteCoordinate?,
        userCurrentPoint: Point
    ): RouteCartDto {
        return RouteCartDto(
            id = route.id ?: null!!,
            routeName = route.routeName,
            duration = route.duration,
            length = route.length,
            routePreview = route.routePreview,
            distanceToUser = routeCoordinate?.let { routeCoordinateRepository.findDistanceBetweenCoordinates(
                startPoint = userCurrentPoint,
                endPoint = it.point!!
            ) },
            categories = routeCategories.map { routeCategory ->
                RouteDto.Categories(
                    routeId = routeCategory.routeId,
                    categoryName = routeCategory.categoryName
                )
            }
        )
    }
}

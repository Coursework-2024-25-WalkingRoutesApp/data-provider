package ru.hse.coursework.data_provider.dto.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.RouteDto
import ru.hse.coursework.data_provider.dto.RouteDto.Categories
import ru.hse.coursework.data_provider.dto.RoutePageDto
import ru.hse.coursework.data_provider.model.Route
import ru.hse.coursework.data_provider.model.RouteCategory
import ru.hse.coursework.data_provider.model.RouteCoordinate

@Component
class RouteToRoutePageDto {

    fun convert(
        route: Route,
        routeCategories: List<RouteCategory>,
        routeCoordinates: List<RouteCoordinate>,
        checkIsFavorite: Boolean
    ): RoutePageDto {
        return RoutePageDto(
            id = route.id!!,
            routeName = route.routeName,
            description = route.description,
            duration = route.duration,
            length = route.length,
            startPoint = route.startPoint,
            endPoint = route.endPoint,
            isFavourite = checkIsFavorite,
            routePreview = route.routePreview,
            routeCoordinate = routeCoordinates.map { routeCoordinate ->
                RouteDto.RouteCoordinate(
                    id = routeCoordinate.id,
                    routeId = routeCoordinate.routeId,
                    latitude = (routeCoordinate.point)?.coordinate?.y, //todo: проверить корректность преобразования
                    longitude = (routeCoordinate.point)?.coordinate?.x,
                    orderNumber = routeCoordinate.orderNumber,
                    title = routeCoordinate.title,
                    description = routeCoordinate.description
                )
            },
            categories = routeCategories.map { routeCategory ->
                Categories(
                    routeId = routeCategory.routeId,
                    categoryName = routeCategory.categoryName
                )
            }
        )
    }
}

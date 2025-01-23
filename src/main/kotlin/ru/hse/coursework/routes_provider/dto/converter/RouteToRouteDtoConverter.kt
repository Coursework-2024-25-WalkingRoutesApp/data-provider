package ru.hse.coursework.routes_provider.dto.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.routes_provider.dto.RouteDto
import ru.hse.coursework.routes_provider.dto.RouteDto.Categories
import ru.hse.coursework.routes_provider.model.Route
import ru.hse.coursework.routes_provider.model.RouteCategory
import ru.hse.coursework.routes_provider.model.RouteCoordinate

@Component
class RouteToRouteDtoConverter {

    fun convert(
        route: Route,
        routeCategories: List<RouteCategory>,
        routeCoordinates: List<RouteCoordinate>
    ): RouteDto {
        return RouteDto(
            id = route.id,
            routeName = route.routeName,
            description = route.description,
            duration = route.duration,
            length = route.length,
            startPoint = route.startPoint,
            endPoint = route.endPoint,
            routePreview = route.routePreview,
            isDraft = route.isDraft,
            routeCoordinate = routeCoordinates.map { routeCoordinate ->
                RouteDto.RouteCoordinate(
                    id = routeCoordinate.id,
                    routeId = routeCoordinate.routeId,
                    latitude = routeCoordinate.point?.coordinate?.x, //todo: проверить корректность преобразования
                    longitude = routeCoordinate.point?.coordinate?.y,
                    orderNumber = routeCoordinate.orderNumber
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

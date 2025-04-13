package ru.hse.coursework.data_provider.model.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.RouteDto
import ru.hse.coursework.data_provider.model.Route
import java.time.LocalDateTime

@Component
class RouteDtoToRouteConverter: Converter<RouteDto, Route> {

    override fun convert(source: RouteDto): Route {
        return Route(
            id = source.id,
            userId = null,
            routeName = source.routeName,
            description = source.description,
            duration = source.duration,
            length = source.length,
            startPoint = source.startPoint,
            endPoint = source.endPoint,
            routePreview = source.routePreview,
            isDraft = source.isDraft,
            lastModifiedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now()
        )
    }
}

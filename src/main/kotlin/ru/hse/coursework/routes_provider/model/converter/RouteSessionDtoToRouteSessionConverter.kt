package ru.hse.coursework.routes_provider.model.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.hse.coursework.routes_provider.dto.RouteSessionDto
import ru.hse.coursework.routes_provider.model.RouteSession

@Component
class RouteSessionDtoToRouteSessionConverter: Converter<RouteSessionDto, RouteSession> {

    override fun convert(source: RouteSessionDto): RouteSession {
        return RouteSession(
            id = source.id,
            userId = null,
            routeId = source.routeId,
            isFinished = source.isFinished,
            startedAt = source.startedAt,
            endedAt = source.endedAt
        )
    }
}
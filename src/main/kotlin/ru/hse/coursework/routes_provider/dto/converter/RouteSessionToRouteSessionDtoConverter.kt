package ru.hse.coursework.routes_provider.dto.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.routes_provider.dto.RouteSessionDto
import ru.hse.coursework.routes_provider.model.RouteSession
import ru.hse.coursework.routes_provider.model.UserCheckpoint

@Component
class RouteSessionToRouteSessionDtoConverter {

    fun convert(routeSession: RouteSession, userCheckpoints: List<UserCheckpoint>): RouteSessionDto {
        return RouteSessionDto(
            id = routeSession.id,
            routeId = routeSession.routeId,
            isFinished = routeSession.isFinished,
            startedAt = routeSession.startedAt,
            endedAt = routeSession.endedAt,
            userCheckpoint = userCheckpoints.map { userCheckpoint ->
                RouteSessionDto.UserCheckpoint(
                    coordinateId = userCheckpoint.coordinateId,
                    createdAt = userCheckpoint.createdAt
                )
            }
        )
    }
}

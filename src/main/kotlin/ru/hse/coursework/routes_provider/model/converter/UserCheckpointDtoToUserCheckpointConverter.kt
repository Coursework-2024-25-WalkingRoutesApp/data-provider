package ru.hse.coursework.routes_provider.model.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.routes_provider.dto.RouteSessionDto
import ru.hse.coursework.routes_provider.model.UserCheckpoint
import java.util.UUID

@Component
class UserCheckpointDtoToUserCheckpointConverter {

    fun convert(source: RouteSessionDto.UserCheckpoint, routeSessionId: UUID): UserCheckpoint {
        return UserCheckpoint(
            routeSessionId = routeSessionId,
            coordinateId = source.coordinateId,
            createdAt = source.createdAt
        )
    }
}

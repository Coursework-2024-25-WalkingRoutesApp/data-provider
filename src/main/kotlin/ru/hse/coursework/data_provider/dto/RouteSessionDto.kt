package ru.hse.coursework.data_provider.dto

import java.time.LocalDateTime
import java.util.UUID

data class RouteSessionDto(
    var id: UUID? = null,
    var routeId: UUID,
    var isFinished: Boolean?,
    var startedAt: LocalDateTime?,
    var endedAt: LocalDateTime?,
    var userCheckpoint: List<UserCheckpoint>
) {

    data class UserCheckpoint(
        var coordinateId: UUID,
        var createdAt: LocalDateTime?
    )
}

package ru.hse.coursework.routes_provider.dto

import java.time.LocalTime
import java.util.UUID

data class RouteCartDto(
    var id: UUID,
    var routeName: String?,
    var duration: LocalTime?,
    var length: Long?,
    var routePreview: String?,
    var distanceToUser: Double,
    var categories: List<RouteDto.Categories>?
)

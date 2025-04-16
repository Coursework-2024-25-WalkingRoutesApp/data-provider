package ru.hse.coursework.data_provider.dto

import java.util.UUID

data class RouteCartDto(
    var id: UUID,
    var routeName: String?,
    var duration: Double?,
    var length: Double?,
    var routePreview: String?,
    var distanceToUser: Double?,
    var categories: List<RouteDto.Categories>?
)

package ru.hse.coursework.routes_provider.dto

import java.util.*

data class RoutePageDto(
    var id: UUID,
    var routeName: String?,
    var description: String?,
    var duration: Double?,
    var length: Double?,
    var startPoint: String?,
    var endPoint: String?,
    var routePreview: String?,
    var isFavourite: Boolean?,
    var routeCoordinate: List<RouteDto.RouteCoordinate>?,
    var categories: List<RouteDto.Categories>?
)

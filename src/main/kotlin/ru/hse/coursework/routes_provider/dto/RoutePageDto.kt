package ru.hse.coursework.routes_provider.dto

import java.time.LocalTime
import java.util.*

data class RoutePageDto(
    var id: UUID,
    var routeName: String?,
    var description: String?,
    var duration: LocalTime?,
    var length: Long?,
    var startPoint: String?,
    var endPoint: String?,
    var routePreview: String?,
    var isFavourite: Boolean?,
    var routeCoordinate: List<RouteDto.RouteCoordinate>?,
    var categories: List<RouteDto.Categories>?
)

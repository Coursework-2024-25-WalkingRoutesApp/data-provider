package ru.hse.coursework.routes_provider.dto

import java.util.UUID

data class RouteDto(
    var id: UUID? = null,
    var routeName: String?,
    var description: String?,
    var duration: Double?,
    var length: Double?,
    var startPoint: String?,
    var endPoint: String?,
    var routePreview: String?,
    var isDraft: Boolean?,
    var routeCoordinate: List<RouteCoordinate>?,
    var categories: List<Categories>?
) {

    data class RouteCoordinate(
        var id: UUID? = null,
        var routeId: UUID?, //todo: подумать стоит ли оставлять
        var latitude: Double?,
        var longitude: Double?,
        var orderNumber: Int?,
        var title: String? = null,
        var description: String? = null
    )

    data class Categories(
        var routeId: UUID? = null,
        var categoryName: String
    )
}

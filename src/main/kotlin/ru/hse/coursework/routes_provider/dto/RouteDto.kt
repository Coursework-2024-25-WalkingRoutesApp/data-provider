package ru.hse.coursework.routes_provider.dto

import com.vividsolutions.jts.geom.Geometry
import java.time.LocalTime
import java.util.UUID

data class RouteDto(
    var id: UUID,
    var routeName: String?,
    var description: String?,
    var duration: LocalTime?,
    var length: Long?,
    var startPoint: String?,
    var endPoint: String?,
    var routePreview: String?,
    var isDraft: Boolean?,
    var routeCoordinate: List<RouteCoordinate>?,
    var categories: List<Categories>?
) {

    data class RouteCoordinate(
        var id: UUID,
        var routeId: UUID,
        var point: Geometry?,
        var orderNumber: Int?
    )

    data class Categories(
        var routeId: UUID,
        var categoryName: String
    )
}

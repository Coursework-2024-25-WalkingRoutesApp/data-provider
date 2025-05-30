package ru.hse.coursework.data_provider.model.converter

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.RouteDto
import ru.hse.coursework.data_provider.model.RouteCoordinate

@Component
class RouteDtoCoordinateToRouteCoordinateConverter(
    private val geometryFactory: GeometryFactory = GeometryFactory()
) : Converter<RouteDto.RouteCoordinate, RouteCoordinate> {

    override fun convert(source: RouteDto.RouteCoordinate): RouteCoordinate {
        return RouteCoordinate(
            id = source.id,
            routeId = source.routeId,
            point = /*WKTWriter().write*/(
                geometryFactory.createPoint(Coordinate(source.longitude!!, source.latitude!!))
            ),
            orderNumber = source.orderNumber,
            title = source.title,
            description = source.description
        )
    }
}
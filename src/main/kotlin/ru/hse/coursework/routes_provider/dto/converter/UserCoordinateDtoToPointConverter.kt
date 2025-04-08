package ru.hse.coursework.routes_provider.dto.converter

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Point
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.hse.coursework.routes_provider.dto.UserCoordinateDto

@Component
class UserCoordinateDtoToPointConverter : Converter<UserCoordinateDto, Point> {

    override fun convert(source: UserCoordinateDto): Point {
        val geometryFactory = GeometryFactory()
        val coordinateSequence =
            geometryFactory.coordinateSequenceFactory.create(arrayOf(Coordinate(source.longitude, source.latitude)))
        return geometryFactory.createPoint(coordinateSequence)
    }
}

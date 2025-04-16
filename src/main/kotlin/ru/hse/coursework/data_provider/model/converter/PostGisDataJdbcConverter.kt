package ru.hse.coursework.data_provider.model.converter

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.io.WKTReader
import com.vividsolutions.jts.io.WKTWriter
import org.postgis.PGgeometry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.stereotype.Component
import org.postgis.Point as PostgisPoint

@Configuration
class DataJdbcConfig : AbstractJdbcConfiguration() {

    @Bean
    override fun jdbcCustomConversions(): JdbcCustomConversions {
        return JdbcCustomConversions(
            listOf(
                PointToPgGeometryConverter(),
                PgGeometryToPointConverter(),
                StringToPointConverter(),
                PointToStringConverter()
            )
        )
    }
}

@WritingConverter
class PointToPgGeometryConverter : Converter<Point, PGgeometry> {
    override fun convert(source: Point): PGgeometry {
        // Преобразуем JTS Point в PGgeometry
        return PGgeometry(source.toText())
    }
}

@ReadingConverter
class PgGeometryToPointConverter : Converter<PGgeometry, Point> {

    private val geometryFactory = GeometryFactory()

    override fun convert(source: PGgeometry): Point {
        // Получаем org.postgis.Point из PGgeometry
        val postgisPoint = source.geometry as PostgisPoint

        // Преобразуем org.postgis.Point в com.vividsolutions.jts.geom.Point
        return geometryFactory.createPoint(
            Coordinate(postgisPoint.x, postgisPoint.y)
        )
    }
}

@Component
class StringToPointConverter : Converter<String, Point> {

    private val wktReader = WKTReader()

    override fun convert(source: String): Point {
        // Преобразуем строку в формате WKT в Point
        return wktReader.read(source) as Point
    }
}

@Component
class PointToStringConverter : Converter<Point, String> {

    private val wktWriter = WKTWriter()

    override fun convert(source: Point): String {
        // Преобразуем Point в строку в формате WKT
        return wktWriter.write(source)
    }
}

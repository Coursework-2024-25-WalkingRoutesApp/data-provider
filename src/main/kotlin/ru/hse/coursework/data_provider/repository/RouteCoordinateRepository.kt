package ru.hse.coursework.data_provider.repository

import com.vividsolutions.jts.geom.Point
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.data_provider.model.RouteCoordinate
import java.util.*

@Repository
interface RouteCoordinateRepository : CrudRepository<RouteCoordinate, UUID> {

    @Query(
        """
            select id, route_id, ST_AsText(point) as point, order_number
            from route_coordinate 
            where route_id = :routeId
            and order_number = 1
        """
    )
    fun findStartPointByRouteId(routeId: UUID): RouteCoordinate?

    @Modifying
    @Query(
        """
            delete from route_coordinate
            where route_id = :routeId
        """
    )
    fun deleteByRouteId(routeId: UUID): Int

    @Query(
        """
            select * 
            from route_coordinate 
            where route_id = :routeId
            order by order_number
        """
    )
    fun findByRouteId(routeId: UUID): List<RouteCoordinate>

    @Query(
        """
            insert into route_coordinate (route_id, point, order_number, title, description)
            values (:#{#routeCoordinate.routeId}, 
                    ST_GeomFromText(:#{#routeCoordinate.pointWkt}, 4326),
                    :#{#routeCoordinate.orderNumber},
                    :#{#routeCoordinate.title},
                    :#{#routeCoordinate.description})
            returning *
        """
    )
    fun saveRouteCoordinate(routeCoordinate: RouteCoordinate): RouteCoordinate

    @Query(
        """
            select ST_Distance(
                ST_SetSRID(ST_MakePoint(:#{#startPoint.x}, :#{#startPoint.y}), 4326)::geography,
                ST_SetSRID(ST_MakePoint(:#{#endPoint.x}, :#{#endPoint.y}), 4326)::geography
            )
        """
    )
    fun findDistanceBetweenCoordinates(
        startPoint: Point,
        endPoint: Point
    ): Double?
}

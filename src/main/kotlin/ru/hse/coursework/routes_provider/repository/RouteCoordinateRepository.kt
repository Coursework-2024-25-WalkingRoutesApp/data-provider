package ru.hse.coursework.routes_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.model.RouteCoordinate
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
}

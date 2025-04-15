package ru.hse.coursework.data_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.data_provider.dto.UserCoordinateDto
import ru.hse.coursework.data_provider.model.Route
import java.util.*

@Repository
interface RouteRepository : CrudRepository<Route, UUID> {

    @Query(
        """
            select * 
            from route 
            where id = :routeId
        """
    )
    fun findRouteById(routeId: UUID): Route

    @Query(
        """
            select exists(
            select 1 
            from route 
            where id = :routeId 
                and user_id = :userId
                )
        """
    )
    fun existByIdAndUserId(routeId: UUID, userId: UUID): Boolean

    @Modifying
    @Query(
        """
            update route
            set 
                route_name = :#{#route.routeName},
                description = :#{#route.description},
                duration = :#{#route.duration},
                length = :#{#route.length},
                start_point = :#{#route.startPoint},
                end_point = :#{#route.endPoint},
                route_preview = :#{#route.routePreview},
                is_draft = :#{#route.isDraft},
                last_modified_at = now()
            where id = :#{#route.id} 
            and user_id = :#{#route.userId}
        """
    )
    fun updateRouteData(route: Route): Int

    @Query(
        """
            select * 
            from route 
            where user_id = :userId
            and is_draft = true
        """
    )
    fun findDraftsByUserId(userId: UUID): List<Route>

    @Query(
        """
            select * 
            from route 
            where user_id = :userId
            and is_draft = false
        """
    )
    fun findPublishedByUserId(userId: UUID): List<Route>

    @Modifying
    @Query(
        """
            delete 
            from route 
            where id = :routeId
            and user_id = :userId
        """
    )
    fun deleteByRouteIdAndUserId(routeId: UUID, userId: UUID): Int

    @Query(
        """
            select *
            from route 
            join route_coordinate on route.id = route_coordinate.route_id
            where route_coordinate.order_number = 1
            and ST_DWithin(
                    ST_SetSRID(ST_MakePoint(:#{#userPoint.longitude}, :#{#userPoint.latitude}), 4326)::geography,
                    route_coordinate.point::geography,
                    :radiusInMeters
                )
            and is_draft = false
        """
    )
    fun findClosestRoute(userPoint: UserCoordinateDto, radiusInMeters: Long): List<Route>

    @Query(
        """
            select *
            from route
                join route_coordinate on route.id = route_coordinate.route_id
            where route_coordinate.order_number = 1
            and ST_DWithin(
                    ST_SetSRID(ST_MakePoint(:#{#userPoint.longitude}, :#{#userPoint.latitude}), 4326)::geography,
                    route_coordinate.point::geography,
                    :radiusInMeters
                )
            and is_draft = false
            and route.id in (
                select route_id
                from route_category
                where category_name in (:categories)
                group by route_id
                having count(distinct category_name) = :#{#categories.size()}
            )
        """
    )
    fun findClosestRouteByCategories(userPoint: UserCoordinateDto, radiusInMeters: Long, categories: List<String>): List<Route>

    //todo: подумать как сделать поиск по похожим словам
    @Query(
        """
            select *
            from route
            join route_coordinate on route.id = route_coordinate.route_id
            where route.route_name ilike :routeName
            and route_coordinate.order_number = 1
            and ST_DWithin(
                    ST_SetSRID(ST_MakePoint(:#{#userPoint.longitude}, :#{#userPoint.latitude}), 4326)::geography,
                    route_coordinate.point::geography,
                    :radiusInMeters
                )
            and is_draft = false
        """
    )
    fun findAllClosestByName(userPoint: UserCoordinateDto, radiusInMeters: Long, routeName: String): List<Route>

    @Query(
        """
            select *
            from route
            where is_draft = false
        """
    )
    fun findAllPublishedRoutes(): List<Route>

    @Query(
        """
            select count(*)
            from route
            where user_id = :userId
            and is_draft = false
        """
    )
    fun findPublishedRoutesCountByUserId(userId: UUID): Long
}

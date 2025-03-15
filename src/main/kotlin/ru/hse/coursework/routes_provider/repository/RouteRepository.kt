package ru.hse.coursework.routes_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.dto.UserCoordinateDto
import ru.hse.coursework.routes_provider.model.Route
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

    //todo: подумать как сделать поиск по похожим словам
    @Query(
        """
            select * 
            from route 
            where route.route_name ilike :routeName
        """
    )
    fun findAllByName(routeName: String): List<Route>



    @Query(
        """
            select *
            from route 
            join route_coordinate on route.id = route_coordinate.route_id
            where route_coordinate.order_number = 1
            and ST_Distance(
                    ST_SetSRID(ST_MakePoint(:#{#userPoint.longitude}, :#{#userPoint.latitude}), 4326),
                    route_coordinate.point
            ) < :radiusInMeters
        """
    )
    fun findClosestRoute(userPoint: UserCoordinateDto, radiusInMeters: Long): List<Route>


    //todo: исправить поиск по категориям, так как сейчас он *****
    @Query(
        """
            select *
            from route
            join route_category on route.id = route_category.route_id
            join route_coordinate on route.id = route_coordinate.route_id
            where route_category.category_name in (:categories)
            and route_coordinate.order_number = 1
            and ST_Distance(
                    ST_SetSRID(ST_MakePoint(:#{#userPoint.longitude}, :#{#userPoint.latitude}), 4326),
                    route_coordinate.point
            ) < :radiusInMeters
        """
    )
    fun findClosestRouteByCategories(userPoint: UserCoordinateDto, radiusInMeters: Long, categories: List<String>): List<Route>
}

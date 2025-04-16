package ru.hse.coursework.data_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.data_provider.model.RouteCategory
import java.util.*

@Repository
interface RouteCategoryRepository : CrudRepository<RouteCategory, UUID> {

    @Query(
        """
            select * 
            from route_category 
            where route_id = :routeId
        """
    )
    fun findByRouteId(routeId: UUID): List<RouteCategory>

    @Modifying
    @Query(
        """
            delete from route_category
            where route_id = :routeId
        """
    )
    fun deleteByRouteId(routeId: UUID): Int

    @Query(
        """
            insert into route_category(route_id, category_name)
            values (:#{#routeCategory.routeId}, :#{#routeCategory.categoryName})
            returning *
        """
    )
    fun saveRouteCategory(routeCategory: RouteCategory): RouteCategory
}

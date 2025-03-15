package ru.hse.coursework.routes_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.model.Favorite
import java.util.*

@Repository
interface FavoriteRepository : CrudRepository<Favorite, UUID> {

    @Query(
        """
            select * 
            from favorite 
            where user_id = :userId
        """
    )
    fun findFavoritesByUserId(userId: UUID): List<Favorite>

    @Modifying
    @Query
    (
        """
            insert into favorite (user_id, route_id) 
            values (:userId, :routeId)
        """
    )
    fun saveFavoriteRoute(userId: UUID, routeId: UUID): Int

    @Modifying
    @Query(
        """
            delete 
            from favorite 
            where user_id = :userId
            and route_id = :routeId
        """
    )
    fun deleteByUserIdAndRouteId(userId: UUID, routeId: UUID): Int

    @Query(
        """
            select exists(
                select 1 
                from favorite 
                where route_id = :routeId
                and user_id = :userId
            )
        """
    )
    fun existsByRouteIdAndUserId(routeId: UUID, userId: UUID): Boolean
}
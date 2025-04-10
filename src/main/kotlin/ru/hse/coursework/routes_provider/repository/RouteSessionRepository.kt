package ru.hse.coursework.routes_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.model.RouteSession
import java.util.UUID

@Repository
interface RouteSessionRepository : CrudRepository<RouteSession, UUID> {

    @Query(
        """
            select * 
            from route_session 
            where user_id = :userId
            and is_finished = true
        """
    )
    fun findFinishedRoutesByUserId(userId: UUID): List<RouteSession>

    @Query(
        """
            select * 
            from route_session 
            where user_id = :userId
            and is_finished = false
        """
    )
    fun findUnfinishedRoutesByUserId(userId: UUID): List<RouteSession>

    @Query(
        """
            select 1 
            from route_session 
            where id = :routeSessionId
            and user_id = :userId
        """
    )
    fun existByIdAndUserId(routeSessionId: UUID, userId: UUID): Boolean

    @Modifying
    @Query(
        """
            update route_session
            set is_finished = :#{#routeSession.isFinished},
                started_at = :#{#routeSession.startedAt},
                ended_at = :#{#routeSession.endedAt}
            where id = :#{#routeSession.id}
            and user_id = :#{#routeSession.userId}
            and route_id = :#{#routeSession.routeId}
        """
    )
    fun updateRouteSessionData(routeSession: RouteSession): Int

    @Query(
        """
            select * 
            from route_session 
            where user_id = :userId
            and route_id = :routeId
        """
    )
    fun findByUserIdAndRouteId(userId: UUID, routeId: UUID): RouteSession?
}

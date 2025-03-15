package ru.hse.coursework.routes_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.model.UserCheckpoint
import java.util.*

@Repository
interface UserCheckpointRepository : CrudRepository<UserCheckpoint, UUID> {

    @Modifying
    @Query(
        """
            insert into user_checkpoint (route_session_id, coordinate_id, created_at)
            values (:#{#userCheckpoint.routeSessionId}, :#{#userCheckpoint.coordinateId}, :#{#userCheckpoint.createdAt})
        """
    )
    fun addUserCheckpoint(userCheckpoint: UserCheckpoint)
}

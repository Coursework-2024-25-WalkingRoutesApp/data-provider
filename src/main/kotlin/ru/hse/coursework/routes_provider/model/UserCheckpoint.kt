package ru.hse.coursework.routes_provider.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.routes_provider.model.UserCheckpoint.Companion.TABLE_NAME
import java.time.LocalDateTime

@Table(TABLE_NAME)
data class UserCheckpoint(
    @Column(ROUTE_SESSION_ID_COLUMN_NAME)
    val routeSessionId: String,

    @Column(COORDINATE_ID_COLUMN_NAME)
    val coordinateId: String,

    @Column(CREATED_AT_COLUMN_NAME)
    val createdAt: LocalDateTime?
) {
    companion object {
        const val TABLE_NAME = "user_checkpoint"

        const val ROUTE_SESSION_ID_COLUMN_NAME = "route_session_id"
        const val COORDINATE_ID_COLUMN_NAME = "coordinate_id"
        const val CREATED_AT_COLUMN_NAME = "created_at"
    }
}

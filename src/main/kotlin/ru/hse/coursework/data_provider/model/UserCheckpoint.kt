package ru.hse.coursework.data_provider.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.data_provider.model.UserCheckpoint.Companion.TABLE_NAME
import java.time.LocalDateTime
import java.util.UUID

@Table(TABLE_NAME)
data class UserCheckpoint(
    @Id
    @Column(ROUTE_SESSION_ID_COLUMN_NAME)
    val routeSessionId: UUID,

    @Column(COORDINATE_ID_COLUMN_NAME)
    val coordinateId: UUID,

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

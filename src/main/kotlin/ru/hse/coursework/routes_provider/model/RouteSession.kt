package ru.hse.coursework.routes_provider.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.routes_provider.model.RouteSession.Companion.TABLE_NAME
import java.time.LocalDateTime
import java.util.UUID

@Table(TABLE_NAME)
data class RouteSession(
    @Id
    @Column(ID_COLUMN_NAME)
    val id: UUID,

    @Column(USER_ID_COLUMN_NAME)
    val userId: UUID,

    @Column(ROUTE_ID_COLUMN_NAME)
    val routeId: UUID,

    @Column(IS_FINISHED_COLUMN_NAME)
    val isFinished: Boolean?,

    @Column(STARTED_AT_COLUMN_NAME)
    val startedAt: LocalDateTime?,

    @Column(ENDED_AT_COLUMN_NAME)
    val endedAt: LocalDateTime?
) {
    companion object {
        const val TABLE_NAME = "route_session"

        const val ID_COLUMN_NAME = "id"
        const val USER_ID_COLUMN_NAME = "user_id"
        const val ROUTE_ID_COLUMN_NAME = "route_id"
        const val IS_FINISHED_COLUMN_NAME = "is_finished"
        const val STARTED_AT_COLUMN_NAME = "started_at"
        const val ENDED_AT_COLUMN_NAME = "ended_at"
    }
}

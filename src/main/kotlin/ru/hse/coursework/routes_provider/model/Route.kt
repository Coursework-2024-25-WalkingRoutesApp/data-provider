package ru.hse.coursework.routes_provider.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.routes_provider.model.Route.Companion.TABLE_NAME
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@Table(TABLE_NAME)
data class Route(
    @Id
    @Column(ID_COLUMN_NAME)
    val id: UUID,

    @Column(USER_ID_COLUMN_NAME)
    val userId: UUID,

    @Column(ROUTE_NAME_COLUMN_NAME)
    val routeName: String?,

    @Column(DESCRIPTION_COLUMN_NAME)
    val description: String?,

    @Column(DURATION_COLUMN_NAME)
    val duration: LocalTime?,

    @Column(LENGTH_COLUMN_NAME)
    val length: Long?,

    @Column(START_POINT_COLUMN_NAME)
    val startPoint: String?,

    @Column(END_POINT_COLUMN_NAME)
    val endPoint: String?,

    @Column(ROUTE_PREVIEW_COLUMN_NAME)
    val routePreview: String?,

    @Column(IS_DRAFT_COLUMN_NAME)
    val isDraft: Boolean?,

    @Column(LAST_MODIFIED_AT_COLUMN_NAME)
    val lastModifiedAt: LocalDateTime?,

    @Column(CREATED_AT_COLUMN_NAME)
    val createdAt: LocalDateTime?
) {
    companion object {
        const val TABLE_NAME = "route"

        const val ID_COLUMN_NAME = "id"
        const val USER_ID_COLUMN_NAME = "user_id"
        const val ROUTE_NAME_COLUMN_NAME = "route_name"
        const val DESCRIPTION_COLUMN_NAME = "description"
        const val DURATION_COLUMN_NAME = "duration"
        const val LENGTH_COLUMN_NAME = "length"
        const val START_POINT_COLUMN_NAME = "start_point"
        const val END_POINT_COLUMN_NAME = "end_point"
        const val ROUTE_PREVIEW_COLUMN_NAME = "route_preview"
        const val IS_DRAFT_COLUMN_NAME = "is_draft"
        const val LAST_MODIFIED_AT_COLUMN_NAME = "last_modified_at"
        const val CREATED_AT_COLUMN_NAME = "created_at"
    }
}

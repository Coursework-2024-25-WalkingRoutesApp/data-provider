package ru.hse.coursework.data_provider.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.data_provider.model.Review.Companion.TABLE_NAME
import java.time.LocalDateTime
import java.util.UUID

@Table(TABLE_NAME)
data class Review(

    @Id
    @Column(USER_ID_COLUMN_NAME)
    val userId: UUID,

    @Column(ROUTE_ID_COLUMN_NAME)
    val routeId: UUID,

    @Column(MARK_COLUMN_NAME)
    val mark: Int?,

    @Column(REVIEW_TEXT_COLUMN_NAME)
    val reviewText: String?,

    @Column(CREATED_AT_COLUMN_NAME)
    val createdAt: LocalDateTime?
) {
    companion object {
        const val TABLE_NAME = "review"

        const val USER_ID_COLUMN_NAME = "user_id"
        const val ROUTE_ID_COLUMN_NAME = "route_id"
        const val MARK_COLUMN_NAME = "mark"
        const val REVIEW_TEXT_COLUMN_NAME = "review_text"
        const val CREATED_AT_COLUMN_NAME = "created_at"
    }
}

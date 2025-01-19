package ru.hse.coursework.routes_provider.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.routes_provider.model.Review.Companion.TABLE_NAME

@Table(TABLE_NAME)
data class Review(
    @Column(USER_ID_COLUMN_NAME)
    val userId: String,

    @Column(ROUTE_ID_COLUMN_NAME)
    val routeId: String,

    @Column(MARK_COLUMN_NAME)
    val mark: Int?,

    @Column(REVIEW_TEXT_COLUMN_NAME)
    val reviewText: String?
) {
    companion object {
        const val TABLE_NAME = "review"

        const val USER_ID_COLUMN_NAME = "user_id"
        const val ROUTE_ID_COLUMN_NAME = "route_id"
        const val MARK_COLUMN_NAME = "mark"
        const val REVIEW_TEXT_COLUMN_NAME = "review_text"
    }
}

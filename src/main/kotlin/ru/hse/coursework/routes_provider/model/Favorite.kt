package ru.hse.coursework.routes_provider.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.routes_provider.model.Favorite.Companion.TABLE_NAME


@Table(TABLE_NAME)
data class Favorite(

    @Column(USER_ID_COLUMN_NAME)
    val userId: String,

    @Column(ROUTE_ID_COLUMN_NAME)
    val routeId: String
) {

    companion object {
        const val TABLE_NAME = "favorite"

        const val USER_ID_COLUMN_NAME = "user_id"
        const val ROUTE_ID_COLUMN_NAME = "route_id"
    }
}

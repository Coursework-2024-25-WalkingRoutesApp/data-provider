package ru.hse.coursework.routes_provider.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.routes_provider.model.Favorite.Companion.TABLE_NAME
import java.util.UUID


@Table(TABLE_NAME)
data class Favorite(

    @Id
    @Column(USER_ID_COLUMN_NAME)
    val userId: UUID,

    @Column(ROUTE_ID_COLUMN_NAME)
    val routeId: UUID
) {

    companion object {
        const val TABLE_NAME = "favorite"

        const val USER_ID_COLUMN_NAME = "user_id"
        const val ROUTE_ID_COLUMN_NAME = "route_id"
    }
}

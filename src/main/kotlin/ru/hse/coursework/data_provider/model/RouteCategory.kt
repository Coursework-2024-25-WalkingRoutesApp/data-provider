package ru.hse.coursework.data_provider.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.data_provider.model.RouteCategory.Companion.TABLE_NAME
import java.util.UUID

@Table(TABLE_NAME)
data class RouteCategory(
    @Column(ROUTE_ID_COLUMN_NAME)
    var routeId: UUID? = null,

    @Column(CATEGORY_NAME_COLUMN_NAME)
    val categoryName: String
) {
    companion object {
        const val TABLE_NAME = "route_category"

        const val ROUTE_ID_COLUMN_NAME = "route_id"
        const val CATEGORY_NAME_COLUMN_NAME = "category_name"
    }
}

package ru.hse.coursework.data_provider.model

import com.vividsolutions.jts.geom.Point
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.data_provider.model.RouteCoordinate.Companion.TABLE_NAME
import java.util.*

@Table(TABLE_NAME)
data class RouteCoordinate(
    @Id
    @Column(ID_COLUMN_NAME)
    val id: UUID? = null,

    @Column(ROUTE_ID_COLUMN_NAME)
    var routeId: UUID? = null,

    @Column(POINT_COLUMN_NAME)
    val point: Point?,

    @Column(ORDER_NUMBER_COLUMN_NAME)
    val orderNumber: Int?,

    @Column(TITLE_COLUMN_NAME)
    val title: String? = null,

    @Column(DESCRIPTION_COLUMN_NAME)
    val description: String? = null
) {

    val pointWkt: String?
        get() = point?.let { "POINT(${it.x} ${it.y})" }

    companion object {
        const val TABLE_NAME = "route_coordinate"

        const val ID_COLUMN_NAME = "id"
        const val ROUTE_ID_COLUMN_NAME = "route_id"
        const val POINT_COLUMN_NAME = "point"
        const val ORDER_NUMBER_COLUMN_NAME = "order_number"
        const val TITLE_COLUMN_NAME = "title"
        const val DESCRIPTION_COLUMN_NAME = "description"
    }
}

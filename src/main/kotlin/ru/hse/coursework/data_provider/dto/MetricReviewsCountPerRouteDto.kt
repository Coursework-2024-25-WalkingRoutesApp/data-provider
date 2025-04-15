package ru.hse.coursework.data_provider.dto

import java.util.UUID

data class MetricReviewsCountPerRouteDto(
    val totalReviews: Long,
    val reviewsCountPerUser: List<ReviewCountPerUser>? = emptyList()
) {
    data class ReviewCountPerUser(
        val routeId: UUID,
        val routeName: String,
        val reviewsCount: Long
    )
}

package ru.hse.coursework.data_provider.dto

import java.util.UUID

data class MetricRoutesLikesCountDto(
    var totalLikesCount: Long,
    var likesCountPerRoute: List<LikesCountPerRoute>
) {
    data class LikesCountPerRoute(
        var routeId: UUID,
        var routeName: String,
        var likesCount: Long
    )
}

package ru.hse.coursework.data_provider.dto

import java.util.UUID

data class MetricPublishedRoutesCountPerUserDto(
    val totalPublishedRoutes: Long,
    val publishedRoutesPerUser: List<PublishedRoutesPerUser>
) {

    data class PublishedRoutesPerUser(
        val userId: UUID,
        val userName: String,
        val publishedRoutesCount: Long,
    )
}

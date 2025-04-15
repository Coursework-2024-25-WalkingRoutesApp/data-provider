package ru.hse.coursework.data_provider.dto

import java.util.UUID

data class MetricFinishedRoutesPerUserDto (
    val totalFinishedRoutes: Long,
    val finishedRoutesPerUser: List<FinishedRoutesPerUser>? = emptyList()
) {

    data class FinishedRoutesPerUser(
        val userId: UUID,
        val userName: String,
        val finishedRoutesCount: Long
    )
}

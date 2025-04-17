package ru.hse.coursework.data_provider.dto.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.MetricFinishedRoutesPerUserDto
import ru.hse.coursework.data_provider.model.User

@Component
class MetricFinishedRoutesPerUserDtoConverter {

    fun convert(
        totalFinishedRoutes: Long,
        finishedRoutesPerUser: List<MetricFinishedRoutesPerUserDto.FinishedRoutesPerUser>
    ) =
        MetricFinishedRoutesPerUserDto(
            totalFinishedRoutes = totalFinishedRoutes,
            finishedRoutesPerUser = emptyList()
        )

    fun convertRoutesPerUser(user: User, finishedRoutesCount: Long) =
        MetricFinishedRoutesPerUserDto.FinishedRoutesPerUser(
            userId = user.id!!,
            userName = user.userName,
            finishedRoutesCount = finishedRoutesCount
        )
}

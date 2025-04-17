package ru.hse.coursework.data_provider.dto.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.MetricPublishedRoutesCountPerUserDto
import ru.hse.coursework.data_provider.model.User

@Component
class MetricPublishedRoutesCountPerUserDtoConverter {

    fun convert(
        totalPublishedRoutes: Long,
        publishedRoutesPerUser: List<MetricPublishedRoutesCountPerUserDto.PublishedRoutesPerUser>
    ) =
        MetricPublishedRoutesCountPerUserDto(
            totalPublishedRoutes = totalPublishedRoutes,
            publishedRoutesPerUser = publishedRoutesPerUser
        )

    fun convertRoutesPerUser(user: User, publishedRoutesCount: Long) =
        MetricPublishedRoutesCountPerUserDto.PublishedRoutesPerUser(
            userId = user.id!!,
            userName = user.userName,
            publishedRoutesCount = publishedRoutesCount
        )
}

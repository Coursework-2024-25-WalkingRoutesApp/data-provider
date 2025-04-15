package ru.hse.coursework.data_provider.dto.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.MetricRoutesLikesCountDto
import ru.hse.coursework.data_provider.model.Route

@Component
class MetricRoutesLikesCountDtoConverter {

    fun convert(totalLikesCount: Long, likesCountPerRoute: List<MetricRoutesLikesCountDto.LikesCountPerRoute>) =
        MetricRoutesLikesCountDto(
            totalLikesCount = totalLikesCount,
            likesCountPerRoute = likesCountPerRoute
        )

    fun convertLikesPerRoute(route: Route, likesCount: Long) =
        MetricRoutesLikesCountDto.LikesCountPerRoute(
            routeId = route.id!!,
            routeName = route.routeName!!,
            likesCount = likesCount
        )
}

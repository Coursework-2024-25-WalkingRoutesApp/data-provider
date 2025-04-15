package ru.hse.coursework.data_provider.dto.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.MetricReviewsCountPerRouteDto
import ru.hse.coursework.data_provider.model.Route

@Component
class MetricReviewsCountPerRouteDtoConverter {

    fun convert(totalReviews: Long, reviewsCountPerUser: List<MetricReviewsCountPerRouteDto.ReviewCountPerUser>) =
        MetricReviewsCountPerRouteDto(
            totalReviews = totalReviews,
            reviewsCountPerUser = reviewsCountPerUser
        )

    fun convertReviewsPerUser(route: Route, reviewsCount: Long) =
        MetricReviewsCountPerRouteDto.ReviewCountPerUser(
            routeId = route.id!!,
            routeName = route.routeName!!,
            reviewsCount = reviewsCount
        )
}

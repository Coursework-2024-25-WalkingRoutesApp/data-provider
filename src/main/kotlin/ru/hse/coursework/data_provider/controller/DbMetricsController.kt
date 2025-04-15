package ru.hse.coursework.data_provider.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.coursework.data_provider.dto.MetricFinishedRoutesPerUserDto
import ru.hse.coursework.data_provider.dto.MetricPublishedRoutesCountPerUserDto
import ru.hse.coursework.data_provider.dto.MetricReviewsCountPerRouteDto
import ru.hse.coursework.data_provider.dto.MetricRoutesLikesCountDto
import ru.hse.coursework.data_provider.service.DbMetricsService

@RestController
@RequestMapping(METRICS_BASE_PATH_URL)
class DbMetricsController(
    private val dbMetricsService: DbMetricsService
) {

    @GetMapping(GET_REGISTERED_USERS_COUNT_URL)
    fun getRegisteredUsersCount(): Long {
        return dbMetricsService.getRegisteredUsersCount()
    }

    @GetMapping(GET_TOTAL_LIKES_COUNT_URL)
    fun getTotalLikesCount(): MetricRoutesLikesCountDto {
        return dbMetricsService.getTotalLikesCount()
    }

    @GetMapping(GET_FINISHED_ROUTES_PER_USER_URL)
    fun getFinishedRoutesPerUser(): MetricFinishedRoutesPerUserDto {
        return dbMetricsService.getFinishedRoutesPerUser()
    }

    @GetMapping(GET_REVIEWS_COUNT_PER_ROUTE_URL)
    fun getReviewsCountPerRoute(): MetricReviewsCountPerRouteDto {
        return dbMetricsService.getReviewsCountPerRoute()
    }

    @GetMapping(GET_PUBLISHED_ROUTES_COUNT_PER_USER_URL)
    fun getPublishedRoutesCountPerUser(): MetricPublishedRoutesCountPerUserDto {
        return dbMetricsService.getPublishedRoutesCountPerUser()
    }
}

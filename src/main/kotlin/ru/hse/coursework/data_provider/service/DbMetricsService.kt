package ru.hse.coursework.data_provider.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.data_provider.dto.MetricFinishedRoutesPerUserDto
import ru.hse.coursework.data_provider.dto.MetricPublishedRoutesCountPerUserDto
import ru.hse.coursework.data_provider.dto.MetricReviewsCountPerRouteDto
import ru.hse.coursework.data_provider.dto.MetricRoutesLikesCountDto
import ru.hse.coursework.data_provider.dto.converter.MetricFinishedRoutesPerUserDtoConverter
import ru.hse.coursework.data_provider.dto.converter.MetricPublishedRoutesCountPerUserDtoConverter
import ru.hse.coursework.data_provider.dto.converter.MetricReviewsCountPerRouteDtoConverter
import ru.hse.coursework.data_provider.dto.converter.MetricRoutesLikesCountDtoConverter
import ru.hse.coursework.data_provider.repository.*

@Service
class DbMetricsService(
    private val metricRoutesLikesCountDtoConverter: MetricRoutesLikesCountDtoConverter,
    private val metricReviewsCountPerRouteDtoConverter: MetricReviewsCountPerRouteDtoConverter,
    private val metricPublishedRoutesCountPerUserDtoConverter: MetricPublishedRoutesCountPerUserDtoConverter,
    private val metricFinishedRoutesPerUserDtoConverter: MetricFinishedRoutesPerUserDtoConverter,
    private val userRepository: UserRepository,
    private val routeRepository: RouteRepository,
    private val favoriteRepository: FavoriteRepository,
    private val routeSessionRepository: RouteSessionRepository,
    private val reviewRepository: ReviewRepository,
) {

    @Transactional
    fun getRegisteredUsersCount(): Long {
        return userRepository.count()
    }

    @Transactional
    fun getTotalLikesCount(): MetricRoutesLikesCountDto =
        metricRoutesLikesCountDtoConverter.convert(
            favoriteRepository.count(),
            routeRepository.findAllPublishedRoutes().map {
                metricRoutesLikesCountDtoConverter.convertLikesPerRoute(
                    route = it,
                    likesCount = favoriteRepository.findLikesCountByRouteId(it.id!!)
                )
            }
        )

    @Transactional
    fun getFinishedRoutesPerUser(): MetricFinishedRoutesPerUserDto =
        metricFinishedRoutesPerUserDtoConverter.convert(
            routeSessionRepository.findFinishedRoutesCount(),
            userRepository.findAll().map {
                metricFinishedRoutesPerUserDtoConverter.convertRoutesPerUser(
                    user = it,
                    finishedRoutesCount = routeSessionRepository.findFinishedRoutesCountByUserId(it.id!!)
                )
            }
        )

    @Transactional
    fun getReviewsCountPerRoute(): MetricReviewsCountPerRouteDto =
        metricReviewsCountPerRouteDtoConverter.convert(
            reviewRepository.count(),
            routeRepository.findAllPublishedRoutes().map {
                metricReviewsCountPerRouteDtoConverter.convertReviewsPerUser(
                    route = it,
                    reviewsCount = reviewRepository.findReviewsCountByRouteId(it.id!!)
                )
            }
        )

    @Transactional
    fun getPublishedRoutesCountPerUser(): MetricPublishedRoutesCountPerUserDto =
        metricPublishedRoutesCountPerUserDtoConverter.convert(
            routeRepository.count(),
            userRepository.findAll().map {
                metricPublishedRoutesCountPerUserDtoConverter.convertRoutesPerUser(
                    user = it,
                    publishedRoutesCount = routeRepository.findPublishedRoutesCountByUserId(it.id!!)
                )
            }
        )
}

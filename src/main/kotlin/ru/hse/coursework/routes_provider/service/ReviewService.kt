package ru.hse.coursework.routes_provider.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.routes_provider.dto.ReviewDto
import ru.hse.coursework.routes_provider.dto.converter.ReviewToReviewDtoConverter
import ru.hse.coursework.routes_provider.model.converter.ReviewValuesToReviewConverter
import ru.hse.coursework.routes_provider.repository.ReviewRepository
import ru.hse.coursework.routes_provider.repository.UserRepository
import java.time.LocalDateTime
import java.util.*

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val reviewToReviewDtoConverter: ReviewToReviewDtoConverter,
    private val reviewValuesToReviewConverter: ReviewValuesToReviewConverter
) {

    @Transactional
    fun getRouteReviews(routeId: UUID, userId: UUID): ReviewDto {
        val reviews = reviewRepository.findAllByRouteId(routeId)
        val userList = reviews.map { userRepository.findUserById(it.userId) }

        return reviewToReviewDtoConverter.convert(
            reviews,
            userList,
        ).apply {
            curUserId = userId
        }
    }

    @Transactional
    fun addReview(
        userId: UUID,
        routeId: UUID,
        mark: Int,
        reviewText: String,
        createdAt: LocalDateTime
    ): ResponseEntity<String> {
        return try {
            reviewRepository.saveReview(reviewValuesToReviewConverter.convert(userId, routeId, mark, reviewText, createdAt))
            ResponseEntity.status(HttpStatus.CREATED).body("Отзыв добавлен")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при добавлении отзыва")
        }
    }
}

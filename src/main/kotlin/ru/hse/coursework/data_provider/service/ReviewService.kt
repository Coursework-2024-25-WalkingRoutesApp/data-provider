package ru.hse.coursework.data_provider.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.data_provider.dto.ReviewDto
import ru.hse.coursework.data_provider.dto.converter.ReviewToReviewDtoConverter
import ru.hse.coursework.data_provider.model.converter.ReviewValuesToReviewConverter
import ru.hse.coursework.data_provider.repository.ReviewRepository
import ru.hse.coursework.data_provider.repository.UserRepository
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
        logger.info("Getting reviews for route $routeId by user $userId")
        val reviews = reviewRepository.findAllByRouteId(routeId)

        val userList = reviews.mapNotNull { userRepository.findUserById(it.userId) }

        return reviewToReviewDtoConverter.convert(
            reviews,
            userList,
        ).apply {
            curUserId = userId
        }.also {
            logger.info("Successfully compiled reviews DTO for route $routeId")
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
        logger.info("Adding review by user $userId for route $routeId")
        return try {
            if(reviewRepository.existsByUserIdAndRouteId(userId, routeId)) {
                reviewRepository.updateReview(
                    reviewValuesToReviewConverter.convert(
                        userId, routeId, mark, reviewText, createdAt
                    )
                )

                logger.info("Review successfully updated by user $userId for route $routeId")
                ResponseEntity.status(HttpStatus.OK).body("Отзыв обновлен")
            } else {
                reviewRepository.saveReview(
                    reviewValuesToReviewConverter.convert(
                        userId, routeId, mark, reviewText, createdAt
                    )
                )
                logger.info("Review successfully added by user $userId for route $routeId")
                ResponseEntity.status(HttpStatus.CREATED).body("Отзыв добавлен")
            }
        } catch (e: Exception) {
            logger.error("Failed to add review by user $userId for route $routeId: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при добавлении отзыва")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ReviewService::class.java)
    }
}

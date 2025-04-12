package ru.hse.coursework.routes_provider.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.coursework.routes_provider.dto.ReviewDto
import ru.hse.coursework.routes_provider.service.ReviewService
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping(REVIEW_BASE_PATH_URL)
class ReviewController(
    private val reviewService: ReviewService
) {

    @GetMapping(GET_REVIEWS_URL)
    fun getReviews(@RequestHeader(USER_ID_HEADER) userId: UUID, @RequestParam routeId: UUID): ReviewDto =
        reviewService.getRouteReviews(routeId, userId)

    @PostMapping(ADD_REVIEW_URL)
    fun addReview(
        @RequestHeader(USER_ID_HEADER) userId: UUID,
        @RequestParam routeId: UUID,
        @RequestParam mark: Int,
        @RequestParam reviewText: String,
        @RequestParam createdAt: LocalDateTime
    ): ResponseEntity<String> =
        reviewService.addReview(userId, routeId, mark, reviewText, createdAt)
}

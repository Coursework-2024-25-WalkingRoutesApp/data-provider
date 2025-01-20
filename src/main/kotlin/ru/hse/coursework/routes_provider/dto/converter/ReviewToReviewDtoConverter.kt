package ru.hse.coursework.routes_provider.dto.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.routes_provider.dto.ReviewDto
import ru.hse.coursework.routes_provider.model.Review
import ru.hse.coursework.routes_provider.model.User

@Component
class ReviewToReviewDtoConverter {

    fun convert(reviews: List<Review>, users: List<User>): ReviewDto {
        return ReviewDto(
            reviews = reviews.map { reviewInfo ->
                ReviewDto.ReviewInfoDto(
                    userId = reviewInfo.userId,
                    userName = users.find { it.id == reviewInfo.userId }!!.userName,
                    userPhotoUrl = users.find { it.id == reviewInfo.userId }!!.photoUrl,
                    reviewText = reviewInfo.reviewText,
                    rating = reviewInfo.mark!!,
                    createdAt = reviewInfo.createdAt!!
                )
            }
        )
    }
}

package ru.hse.coursework.data_provider.model.converter

import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.model.Review
import java.time.LocalDateTime
import java.util.*

@Component
class ReviewValuesToReviewConverter {

    fun convert(userId: UUID, routeId: UUID, mark: Int, reviewText: String, createdAt: LocalDateTime): Review {
        return Review(
            userId = userId,
            routeId = routeId,
            mark = mark,
            reviewText = reviewText,
            createdAt = createdAt
        )
    }
}

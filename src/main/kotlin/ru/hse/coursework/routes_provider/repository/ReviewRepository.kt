package ru.hse.coursework.routes_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.model.Review
import java.util.UUID

@Repository
interface ReviewRepository : CrudRepository<Review, UUID> {

    @Query(
        """
            select * 
            from review 
            where route_id = :routeId
        """
    )
    fun findAllByRouteId(routeId: UUID): List<Review>

    @Modifying
    @Query(
        """
            insert into review (user_id, route_id, mark, review_text, created_at) 
            values (:#{#review.userId}, 
                    :#{#review.routeId}, 
                    :#{#review.mark}, 
                    :#{#review.reviewText}, 
                    :#{#review.createdAt}
            )
        """
    )
    fun saveReview(review: Review): Int

    @Query(
        """
            select exists(
                select 1 
                from review 
                where user_id = :userId 
                    and route_id = :routeId
            )
        """
    )
    fun existsByUserIdAndRouteId(userId: UUID, routeId: UUID): Boolean

    @Modifying
    @Query(
        """
            update review
            set 
                mark = :#{#review.mark},
                review_text = :#{#review.reviewText},
                created_at = :#{#review.createdAt}
            where user_id = :#{#review.userId} 
            and route_id = :#{#review.routeId}
        """
    )
    fun updateReview(review: Review): Int
}

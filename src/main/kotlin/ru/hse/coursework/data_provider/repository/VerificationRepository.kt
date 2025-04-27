package ru.hse.coursework.data_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.data_provider.model.Verification
import java.util.UUID

@Repository
interface VerificationRepository : CrudRepository<Verification, UUID> {

    @Query(
        """
            select *
            from verification
            where user_id = :userId
        """
    )
    fun findByUserId(userId: UUID): Verification?

    @Modifying
    @Query(
        """
            delete from verification
            where user_id = :userId
        """
    )
    fun deleteByUserId(userId: UUID): Int

    @Modifying
    @Query(
        """
            update verification
            set code = :verificationCode, created_at = now()
            where user_id = :userId
        """
    )
    fun updateByUserId(userId: UUID, verificationCode: String): Int
}

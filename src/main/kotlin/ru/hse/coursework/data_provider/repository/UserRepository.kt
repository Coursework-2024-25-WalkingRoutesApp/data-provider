package ru.hse.coursework.data_provider.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.data_provider.model.User
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, UUID> {

    @Query(
        """
            select * 
            from  public."user" 
            where id = :id
        """
    )
    fun findUserById(id: UUID): User?

    @Query(
        """
            select *
            from public."user"
            where email = :email
        """
    )
    fun findUserByEmail(email: String): User?

    @Query(
        """
            select exists(
                select 1
                from public."user"
                where email = :email
            )
        """
    )
    fun existsByEmail(email: String): Boolean

    @Modifying
    @Query(
        """
            update public."user"
            set is_verified = true
            where id = :userId
        """
    )
    fun updateUserVerifiedStatus(userId: UUID): Int
}
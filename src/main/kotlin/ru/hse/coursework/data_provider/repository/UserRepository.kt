package ru.hse.coursework.data_provider.repository

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
}
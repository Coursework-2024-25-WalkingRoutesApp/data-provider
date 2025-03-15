package ru.hse.coursework.routes_provider.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.model.User
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, UUID> {

    @Query(
        """
            select * 
            from "user" 
            where id = :id
        """
    )
    fun findUserById(id: UUID): User
}
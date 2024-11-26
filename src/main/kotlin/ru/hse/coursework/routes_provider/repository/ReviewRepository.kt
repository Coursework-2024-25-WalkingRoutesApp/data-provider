package ru.hse.coursework.routes_provider.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.routes_provider.model.Review

@Repository
interface ReviewRepository : CrudRepository<Review, String>

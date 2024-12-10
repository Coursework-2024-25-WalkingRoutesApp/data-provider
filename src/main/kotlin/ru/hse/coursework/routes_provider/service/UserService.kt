package ru.hse.coursework.routes_provider.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.routes_provider.model.UserPage
import ru.hse.coursework.routes_provider.repository.FavoriteRepository
import ru.hse.coursework.routes_provider.repository.RouteSessionRepository
import ru.hse.coursework.routes_provider.repository.UserRepository

@Service
class UserService (
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository,
    private val routeSessionRepository: RouteSessionRepository
) {

    @Transactional
    fun getUserPage(userId: String): UserPage {
        val user = userRepository.findById(userId).orElseThrow()
        val favorites = favoriteRepository.findAllByUserId(userId)
        val routeSessions = routeSessionRepository.findAllByUserId(userId)

        return UserPage(user, favorites, routeSessions)
    }
}
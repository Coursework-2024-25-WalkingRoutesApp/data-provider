package ru.hse.coursework.routes_provider.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.routes_provider.dto.RouteCartDto
import ru.hse.coursework.routes_provider.dto.UserCoordinateDto
import ru.hse.coursework.routes_provider.dto.converter.RouteToRouteCartDtoConverter
import ru.hse.coursework.routes_provider.dto.converter.UserCoordinateDtoToPointConverter
import ru.hse.coursework.routes_provider.repository.FavoriteRepository
import ru.hse.coursework.routes_provider.repository.RouteCategoryRepository
import ru.hse.coursework.routes_provider.repository.RouteCoordinateRepository
import ru.hse.coursework.routes_provider.repository.RouteRepository
import java.util.*

@Service
class FavoriteRoutesService(
    private val favoriteRepository: FavoriteRepository,
    private val routeRepository: RouteRepository,
    private val routeCategoryRepository: RouteCategoryRepository,
    private val routeCoordinateRepository: RouteCoordinateRepository,
    private val routeToRouteCartDtoConverter: RouteToRouteCartDtoConverter,
    private val userCoordinateDtoToPointConverter: UserCoordinateDtoToPointConverter
) {

    @Transactional
    fun getUserFavouriteRoutes(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        return favoriteRepository.findFavoritesByUserId(userId)
            .takeIf { it.isNotEmpty() }
            ?.let { favorites ->
                favorites.map { favourite ->
                    routeToRouteCartDtoConverter.convert(
                        routeRepository.findRouteById(favourite.routeId),
                        routeCategoryRepository.findByRouteId(favourite.routeId),
                        routeCoordinateRepository.findStartPointByRouteId(favourite.routeId),
                        userCoordinateDtoToPointConverter.convert(userPoint)
                    )
                }
            } ?: emptyList()
    }

    @Transactional
    fun addFavourite(userId: UUID, routeId: UUID): ResponseEntity<String> {
        return try {
            favoriteRepository.saveFavoriteRoute(userId, routeId)
            ResponseEntity.status(HttpStatus.CREATED).body("Маршрут добавлен в избранное")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Ошибка добавления маршрута в избранное")
        }
    }

    @Transactional
    fun deleteFavourite(userId: UUID, routeId: UUID): ResponseEntity<String> {
        return try {
            favoriteRepository.deleteByUserIdAndRouteId(userId, routeId)
            ResponseEntity.ok("Маршрут удален из избранного")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Ошибка удаления маршрута из избранного")
        }
    }
}

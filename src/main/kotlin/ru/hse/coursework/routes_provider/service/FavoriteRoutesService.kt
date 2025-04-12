package ru.hse.coursework.routes_provider.service

import org.slf4j.LoggerFactory
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
        logger.info("Getting favorite routes for user $userId")
        return favoriteRepository.findFavoritesByUserId(userId)
            .takeIf { it.isNotEmpty() }
            ?.let { favorites ->
                logger.info("Found ${favorites.size} favorite routes for user $userId")
                favorites.map { favourite ->
                    routeToRouteCartDtoConverter.convert(
                        routeRepository.findRouteById(favourite.routeId),
                        routeCategoryRepository.findByRouteId(favourite.routeId),
                        routeCoordinateRepository.findStartPointByRouteId(favourite.routeId),
                        userCoordinateDtoToPointConverter.convert(userPoint)
                    )
                }
            } ?: emptyList<RouteCartDto>().also {
                logger.info("No favorite routes found for user $userId")
            }
    }

    @Transactional
    fun addFavourite(userId: UUID, routeId: UUID): ResponseEntity<String> {
        logger.info("Adding favorite route $routeId for user $userId")
        return try {
            favoriteRepository.saveFavoriteRoute(userId, routeId)
            logger.info("Successfully added favorite route $routeId for user $userId")
            ResponseEntity.status(HttpStatus.CREATED).body("Маршрут добавлен в избранное")
        } catch (e: Exception) {
            logger.error("Failed to add favorite route $routeId for user $userId: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка добавления маршрута в избранное")
        }
    }

    @Transactional
    fun deleteFavourite(userId: UUID, routeId: UUID): ResponseEntity<String> {
        logger.info("Deleting favorite route $routeId for user $userId")
        return try {
            favoriteRepository.deleteByUserIdAndRouteId(userId, routeId)
            logger.info("Successfully deleted favorite route $routeId for user $userId")
            ResponseEntity.ok("Маршрут удален из избранного")
        } catch (e: Exception) {
            logger.error("Failed to delete favorite route $routeId for user $userId: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка удаления маршрута из избранного")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FavoriteRoutesService::class.java)
    }
}

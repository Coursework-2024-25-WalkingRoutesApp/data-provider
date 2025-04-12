package ru.hse.coursework.routes_provider.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.routes_provider.dto.RouteCartDto
import ru.hse.coursework.routes_provider.dto.RouteDto
import ru.hse.coursework.routes_provider.dto.RoutePageDto
import ru.hse.coursework.routes_provider.dto.UserCoordinateDto
import ru.hse.coursework.routes_provider.dto.converter.RouteToRouteCartDtoConverter
import ru.hse.coursework.routes_provider.dto.converter.RouteToRoutePageDto
import ru.hse.coursework.routes_provider.dto.converter.UserCoordinateDtoToPointConverter
import ru.hse.coursework.routes_provider.model.converter.RouteDtoCategoriesToRouteCategoryConverter
import ru.hse.coursework.routes_provider.model.converter.RouteDtoCoordinateToRouteCoordinateConverter
import ru.hse.coursework.routes_provider.model.converter.RouteDtoToRouteConverter
import ru.hse.coursework.routes_provider.repository.*
import java.util.UUID

@Service
class RouteService(
    private val routeRepository: RouteRepository,
    private val routeCategoryRepository: RouteCategoryRepository,
    private val routeCoordinateRepository: RouteCoordinateRepository,
    private val routeDtoToRouteConverter: RouteDtoToRouteConverter,
    private val routeDtoCategoriesToRouteCategoryConverter: RouteDtoCategoriesToRouteCategoryConverter,
    private val routeDtoCoordinateToRouteCoordinateConverter: RouteDtoCoordinateToRouteCoordinateConverter,
    private val routeToRouteCartDtoConverter: RouteToRouteCartDtoConverter,
    private val userCoordinateDtoToPointConverter: UserCoordinateDtoToPointConverter,
    private val favoriteRepository: FavoriteRepository,
    private val routeToRoutePageDto: RouteToRoutePageDto
) {

    @Transactional
    fun createOrUpdateRoute(routeDto: RouteDto, userId: UUID): ResponseEntity<String> {
        logger.info("Create/update route started for user $userId")
        return try {
            if (routeDto.id?.let { routeRepository.existByIdAndUserId(it, userId) } == true) {
                routeRepository.updateRouteData(routeDtoToRouteConverter.convert(routeDto).apply {
                    this.userId = userId
                })

                routeCategoryRepository.deleteByRouteId(routeDto.id!!)
                routeDto.categories?.forEach { category ->
                    routeCategoryRepository.saveRouteCategory(routeDtoCategoriesToRouteCategoryConverter.convert(category).apply {
                        this.routeId = routeDto.id!!
                    })
                }

                routeCoordinateRepository.deleteByRouteId(routeDto.id!!)
                routeDto.routeCoordinate?.forEach { coordinate ->
                    routeCoordinateRepository.saveRouteCoordinate(routeDtoCoordinateToRouteCoordinateConverter.convert(coordinate).apply {
                        this.routeId = routeDto.id!!
                    })
                }

                logger.info("Route ${routeDto.id} updated successfully")
                ResponseEntity.status(HttpStatus.OK).body("Данные о маршруте обновлены")
            } else {
                val savedRoute = routeRepository.save(routeDtoToRouteConverter.convert(routeDto).apply {
                    this.userId = userId
                })

                routeDto.categories?.forEach { category ->
                    routeCategoryRepository.saveRouteCategory(routeDtoCategoriesToRouteCategoryConverter.convert(category).apply {
                        this.routeId = savedRoute.id!!
                    })
                }

                routeDto.routeCoordinate?.forEach { coordinate ->
                    routeCoordinateRepository.saveRouteCoordinate(routeDtoCoordinateToRouteCoordinateConverter.convert(coordinate).apply {
                        this.routeId = savedRoute.id!!
                    })
                }

                logger.info("Route created successfully with ID: ${savedRoute.id}")
                ResponseEntity.status(HttpStatus.CREATED).body("Маршрут добавлен")
            }
        } catch (e: Exception) {
            logger.error("Create/update route failed: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при создании/обновлении маршрута")
        }
    }

    @Transactional
    fun getUserRouteDrafts(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        logger.info("Get drafts started for user $userId")
        return routeRepository.findDraftsByUserId(userId).map { draft ->
            routeToRouteCartDtoConverter.convert(
                draft,
                routeCategoryRepository.findByRouteId(draft.id!!),
                routeCoordinateRepository.findStartPointByRouteId(draft.id!!),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }.also {
            logger.info("Get drafts completed, found ${it.size} items")
        }
    }

    @Transactional
    fun getUserPublishedRoutes(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        logger.info("Get published routes started for user $userId")
        return routeRepository.findPublishedByUserId(userId).map { published ->
            routeToRouteCartDtoConverter.convert(
                published,
                routeCategoryRepository.findByRouteId(published.id!!),
                routeCoordinateRepository.findStartPointByRouteId(published.id!!),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }.also {
            logger.info("Get published routes completed, found ${it.size} items")
        }
    }

    @Transactional
    fun deleteRoute(routeId: UUID, userId: UUID): ResponseEntity<String> {
        logger.info("Delete route started for route $routeId, user $userId")
        return try {
            routeRepository.deleteByRouteIdAndUserId(routeId, userId)
            logger.info("Route $routeId deleted successfully")
            ResponseEntity.ok("Маршрут удален")
        } catch (e: Exception) {
            logger.error("Delete route failed: ${e.message}")
            ResponseEntity.badRequest().body("Ошибка удаления маршрута")
        }
    }

    @Transactional
    fun getRoutePage(routeId: UUID, userId: UUID): RoutePageDto {
        logger.info("Get route page started for route $routeId")
        return routeToRoutePageDto.convert(
            routeRepository.findRouteById(routeId),
            routeCategoryRepository.findByRouteId(routeId),
            routeCoordinateRepository.findByRouteId(routeId),
            favoriteRepository.existsByRouteIdAndUserId(routeId, userId)
        ).also {
            logger.info("Get route page completed for route $routeId")
        }
    }

    @Transactional
    fun getRouteBySearchValue(searchValue: String, userPoint: UserCoordinateDto, radiusInMeters: Long): List<RouteCartDto> {
        logger.info("Search routes started for '$searchValue'")
        return routeRepository.findAllClosestByName(userPoint, radiusInMeters, "%$searchValue%").map { route ->
            routeToRouteCartDtoConverter.convert(
                route,
                routeCategoryRepository.findByRouteId(route.id!!),
                routeCoordinateRepository.findStartPointByRouteId(route.id!!),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }.also {
            logger.info("Search completed, found ${it.size} routes")
        }
    }

    @Transactional
    fun getRoutes(userPoint: UserCoordinateDto, categories: List<String>, radiusInMeters: Long): List<RouteCartDto> {
        logger.info("Get routes started (categories: ${categories.size}, radius: $radiusInMeters)")
        return try {
            when (categories.isEmpty()) {
                true -> routeRepository.findClosestRoute(userPoint, radiusInMeters)
                    .map { route ->
                        routeToRouteCartDtoConverter.convert(
                            route,
                            routeCategoryRepository.findByRouteId(route.id!!),
                            routeCoordinateRepository.findStartPointByRouteId(route.id!!),
                            userCoordinateDtoToPointConverter.convert(userPoint)
                        )
                    }.also {
                        logger.info("Get all routes completed, found ${it.size} items")
                    }

                false -> routeRepository.findClosestRouteByCategories(
                    userPoint,
                    radiusInMeters,
                    categories,
                ).map { route ->
                    routeToRouteCartDtoConverter.convert(
                        route,
                        routeCategoryRepository.findByRouteId(route.id!!),
                        routeCoordinateRepository.findStartPointByRouteId(route.id!!),
                        userCoordinateDtoToPointConverter.convert(userPoint)
                    )
                }.also {
                    logger.info("Get filtered routes completed, found ${it.size} items")
                }
            }
        } catch (e: Exception) {
            logger.error("Get routes failed: ${e.message}")
            emptyList()
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RouteService::class.java)
    }
}

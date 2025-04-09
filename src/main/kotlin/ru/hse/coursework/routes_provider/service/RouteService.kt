package ru.hse.coursework.routes_provider.service

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
        return try {
            if (routeDto.id?.let { routeRepository.existByIdAndUserId(it, userId) } == true) {
                //todo: проверить корректность апдейтов
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

                ResponseEntity.status(HttpStatus.CREATED).body("Маршрут добавлен")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error")
        }
    }

    @Transactional
    fun getUserRouteDrafts(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        return routeRepository.findDraftsByUserId(userId).map { draft ->
            routeToRouteCartDtoConverter.convert(
                draft,
                routeCategoryRepository.findByRouteId(draft.id!!),
                routeCoordinateRepository.findStartPointByRouteId(draft.id!!),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }
    }

    @Transactional
    fun getUserPublishedRoutes(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        return routeRepository.findPublishedByUserId(userId).map { published ->
            routeToRouteCartDtoConverter.convert(
                published,
                routeCategoryRepository.findByRouteId(published.id!!),
                routeCoordinateRepository.findStartPointByRouteId(published.id!!),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }
    }

    @Transactional
    fun deleteRoute(routeId: UUID, userId: UUID): ResponseEntity<String> {
        return try {
            routeRepository.deleteByRouteIdAndUserId(routeId, userId)
            ResponseEntity.ok("Маршрут удален")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Ошибка удаления маршрута")
        }
    }

    @Transactional
    fun getRoutePage(routeId: UUID, userId: UUID): RoutePageDto {
        return routeToRoutePageDto.convert(
            routeRepository.findRouteById(routeId),
            routeCategoryRepository.findByRouteId(routeId),
            routeCoordinateRepository.findByRouteId(routeId),
            favoriteRepository.existsByRouteIdAndUserId(routeId, userId)
        )
    }

    @Transactional
    fun getRouteBySearchValue(searchValue: String, userPoint: UserCoordinateDto, radiusInMeters: Long): List<RouteCartDto> {
        return routeRepository.findAllClosestByName(userPoint, radiusInMeters, "%$searchValue%").map { route ->
            routeToRouteCartDtoConverter.convert(
                route,
                routeCategoryRepository.findByRouteId(route.id!!),
                routeCoordinateRepository.findStartPointByRouteId(route.id!!),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }
    }

    @Transactional
    fun getRoutes(userPoint: UserCoordinateDto, categories: List<String>, radiusInMeters: Long): List<RouteCartDto> {
        try {
            when (categories.isEmpty()) {
                true -> return routeRepository.findClosestRoute(
                    userPoint,
                    radiusInMeters
                )
                    .map { route ->
                        routeToRouteCartDtoConverter.convert(
                            route,
                            routeCategoryRepository.findByRouteId(route.id!!),
                            routeCoordinateRepository.findStartPointByRouteId(route.id!!),
                            userCoordinateDtoToPointConverter.convert(userPoint)
                        )
                    }

                false -> return routeRepository.findClosestRouteByCategories(
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
                }
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }
}

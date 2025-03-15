package ru.hse.coursework.routes_provider.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.routes_provider.dto.RouteCartDto
import ru.hse.coursework.routes_provider.dto.RouteSessionDto
import ru.hse.coursework.routes_provider.dto.UserCoordinateDto
import ru.hse.coursework.routes_provider.dto.converter.RouteToRouteCartDtoConverter
import ru.hse.coursework.routes_provider.dto.converter.UserCoordinateDtoToPointConverter
import ru.hse.coursework.routes_provider.model.converter.RouteSessionDtoToRouteSessionConverter
import ru.hse.coursework.routes_provider.model.converter.UserCheckpointDtoToUserCheckpointConverter
import ru.hse.coursework.routes_provider.repository.*
import java.util.*

@Service
class RouteSessionService(
    private val routeSessionRepository: RouteSessionRepository,
    private val routeRepository: RouteRepository,
    private val routeCategoryRepository: RouteCategoryRepository,
    private val routeCoordinateRepository: RouteCoordinateRepository,
    private val routeToRouteCartDtoConverter: RouteToRouteCartDtoConverter,
    private val userCoordinateDtoToPointConverter: UserCoordinateDtoToPointConverter,
    private val routeSessionDtoToRouteSessionConverter: RouteSessionDtoToRouteSessionConverter,
    private val userCheckpointRepository: UserCheckpointRepository,
    private val userCheckpointDtoToUserCheckpointConverter: UserCheckpointDtoToUserCheckpointConverter
) {

    @Transactional
    fun getUserFinishedRoutes(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        return routeSessionRepository.findFinishedRoutesByUserId(userId).map { finished ->
            routeToRouteCartDtoConverter.convert(
                routeRepository.findRouteById(finished.routeId),
                routeCategoryRepository.findByRouteId(finished.routeId),
                routeCoordinateRepository.findStartPointByRouteId(finished.routeId),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }
    }

    @Transactional
    fun getUserUnfinishedRoutes(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        return routeSessionRepository.findUnfinishedRoutesByUserId(userId).map { unfinished ->
            routeToRouteCartDtoConverter.convert(
                routeRepository.findRouteById(unfinished.routeId),
                routeCategoryRepository.findByRouteId(unfinished.routeId),
                routeCoordinateRepository.findStartPointByRouteId(unfinished.routeId),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }
    }

    @Transactional
    fun createOrUpdateSession(routeSessionDto: RouteSessionDto, userId: UUID): ResponseEntity<String> {
        return try {
            if (routeSessionDto.id?.let { routeSessionRepository.existByIdAndUserId(it, userId) } == true) {
                routeSessionRepository.updateRouteSessionData(routeSessionDtoToRouteSessionConverter.convert(routeSessionDto).apply {
                    this.userId = userId
                })

                userCheckpointRepository.deleteById(routeSessionDto.id!!)
                routeSessionDto.userCheckpoint.forEach { checkpoint ->
                    userCheckpointRepository.save(userCheckpointDtoToUserCheckpointConverter.convert(checkpoint, routeSessionDto.id!!))
                }

                ResponseEntity.status(HttpStatus.OK).body("Данные о сессии обновлены")
            } else {
                val newRouteSession = routeSessionRepository.save(routeSessionDtoToRouteSessionConverter.convert(routeSessionDto).apply {
                    this.userId = userId
                })

                routeSessionDto.userCheckpoint.forEach { checkpoint ->
                    userCheckpointRepository.addUserCheckpoint(userCheckpointDtoToUserCheckpointConverter.convert(checkpoint, newRouteSession.id!!))
                }

                ResponseEntity.status(HttpStatus.CREATED).body("Сессия создана")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при создании сессии")
        }
    }
}
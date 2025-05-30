package ru.hse.coursework.data_provider.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.data_provider.dto.RouteCartDto
import ru.hse.coursework.data_provider.dto.RouteSessionDto
import ru.hse.coursework.data_provider.dto.UserCoordinateDto
import ru.hse.coursework.data_provider.dto.converter.RouteSessionToRouteSessionDtoConverter
import ru.hse.coursework.data_provider.dto.converter.RouteToRouteCartDtoConverter
import ru.hse.coursework.data_provider.dto.converter.UserCoordinateDtoToPointConverter
import ru.hse.coursework.data_provider.model.converter.RouteSessionDtoToRouteSessionConverter
import ru.hse.coursework.data_provider.model.converter.UserCheckpointDtoToUserCheckpointConverter
import ru.hse.coursework.data_provider.repository.*
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
    private val userCheckpointDtoToUserCheckpointConverter: UserCheckpointDtoToUserCheckpointConverter,
    private val routeSessionToRouteSessionDtoConverter: RouteSessionToRouteSessionDtoConverter
) {

    @Transactional
    fun getUserFinishedRoutes(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        logger.info("Getting finished routes for user $userId")
        return routeSessionRepository.findFinishedRoutesByUserId(userId).map { finished ->
            routeToRouteCartDtoConverter.convert(
                routeRepository.findRouteById(finished.routeId),
                routeCategoryRepository.findByRouteId(finished.routeId),
                routeCoordinateRepository.findStartPointByRouteId(finished.routeId),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }.also {
            logger.info("Found ${it.size} finished routes for user $userId")
        }
    }

    @Transactional
    fun getUserUnfinishedRoutes(userId: UUID, userPoint: UserCoordinateDto): List<RouteCartDto> {
        logger.info("Getting unfinished routes for user $userId")
        return routeSessionRepository.findUnfinishedRoutesByUserId(userId).map { unfinished ->
            routeToRouteCartDtoConverter.convert(
                routeRepository.findRouteById(unfinished.routeId),
                routeCategoryRepository.findByRouteId(unfinished.routeId),
                routeCoordinateRepository.findStartPointByRouteId(unfinished.routeId),
                userCoordinateDtoToPointConverter.convert(userPoint)
            )
        }.also {
            logger.info("Found ${it.size} unfinished routes for user $userId")
        }
    }

    @Transactional
    fun createOrUpdateSession(routeSessionDto: RouteSessionDto, userId: UUID): ResponseEntity<String> {
        logger.info("Creating/updating session for user $userId")
        return try {
            if (routeSessionDto.id?.let { routeSessionRepository.existByIdAndUserId(it, userId) } == true) {
                routeSessionRepository.updateRouteSessionData(
                    routeSessionDtoToRouteSessionConverter.convert(
                        routeSessionDto
                    ).apply {
                        this.userId = userId
                    })

                userCheckpointRepository.deleteById(routeSessionDto.id!!)
                routeSessionDto.userCheckpoint.forEach { checkpoint ->
                    userCheckpointRepository.addUserCheckpoint(
                        userCheckpointDtoToUserCheckpointConverter.convert(
                            checkpoint,
                            routeSessionDto.id!!
                        )
                    )
                }

                logger.info("Session ${routeSessionDto.id} updated successfully")
                ResponseEntity.status(HttpStatus.OK).body("Данные о сессии обновлены")
            } else {
                val newRouteSession =
                    routeSessionRepository.save(routeSessionDtoToRouteSessionConverter.convert(routeSessionDto).apply {
                        this.userId = userId
                    })

                routeSessionDto.userCheckpoint.forEach { checkpoint ->
                    userCheckpointRepository.addUserCheckpoint(
                        userCheckpointDtoToUserCheckpointConverter.convert(
                            checkpoint,
                            newRouteSession.id!!
                        )
                    )
                }

                logger.info("New session created successfully with id ${newRouteSession.id}")
                ResponseEntity.status(HttpStatus.CREATED).body("Сессия создана")
            }
        } catch (e: Exception) {
            logger.error("Error creating/updating session: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при создании сессии")
        }
    }

    @Transactional
    fun getSession(userId: UUID, routeId: UUID): ResponseEntity<RouteSessionDto>? {
        logger.info("Getting session for user $userId and route $routeId")
        return routeSessionRepository.findByUserIdAndRouteId(userId, routeId)?.let { session ->
            ResponseEntity.status(HttpStatus.OK).body(
                routeSessionToRouteSessionDtoConverter.convert(
                    session,
                    userCheckpointRepository.findByUserIdAndSessionId(session.id!!)
                )
            ).also {
                logger.info("Successfully returned session data")
            }
        } ?: run {
            logger.info("No session found for user $userId and route $routeId")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RouteSessionService::class.java)
    }
}

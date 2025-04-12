package ru.hse.coursework.routes_provider.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.routes_provider.dto.UserDto
import ru.hse.coursework.routes_provider.dto.UserSecurityDto
import ru.hse.coursework.routes_provider.dto.converter.UserToUserDtoConverter
import ru.hse.coursework.routes_provider.dto.converter.UserToUserSecurityDtoConverter
import ru.hse.coursework.routes_provider.model.User
import ru.hse.coursework.routes_provider.repository.UserRepository
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userToUserDtoConverter: UserToUserDtoConverter,
    private val userToUserSecurityDtoConverter: UserToUserSecurityDtoConverter
) {

    @Transactional
    fun registerNewUser(name: String, email: String, password: String): ResponseEntity<String> {
        return try {
            if (userRepository.existsByEmail(email)) {
                logger.error("User with email $email already exists")
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким email уже существует")
            }

            userRepository.save(
                User(
                    userName = name,
                    email = email,
                    password = password
                )
            )

            logger.info("User with email $email successfully registered")
            ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован")
        } catch (e: Exception) {
            logger.error("Error while registering user", e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при регистрации пользователя")
        }
    }

    fun login(email: String, password: String): UserSecurityDto? {
        return userRepository.findUserByEmail(email)?.let { user ->
            userToUserSecurityDtoConverter.convert(user)
        } ?: run {
            logger.error("User with email $email does not exist")
            null
        }
    }

    @Transactional
    fun getUserInfo(userId: UUID): ResponseEntity<UserDto>? {
        return userRepository.findUserById(userId)?.let { user ->
            ResponseEntity.status(HttpStatus.OK).body(userToUserDtoConverter.convert(user))
        } ?: run {
            logger.error("User with id $userId not found")
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @Transactional
    fun updateUsername(userId: UUID, newUsername: String): ResponseEntity<String> {
        return try {
            val user = userRepository.findUserById(userId) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким userId не найден")

            userRepository.save(user.apply {
                userName = newUsername
            })

            logger.info("Username for user with id $userId successfully updated")
            ResponseEntity.status(HttpStatus.OK).body("Имя пользователя обновлено")
        } catch (e: Exception) {
            logger.error("Error while updating username", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обновлении имени пользователя")
        }
    }

    @Transactional
    fun getUserByEmail(email: String): UserSecurityDto? {
        return userRepository.findUserByEmail(email)?.let { user ->
            userToUserSecurityDtoConverter.convert(user)
        } ?: run {
            logger.error("User with email $email not found")
            null
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserService::class.java)
    }
}

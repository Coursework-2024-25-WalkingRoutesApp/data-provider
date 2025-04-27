package ru.hse.coursework.data_provider.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.coursework.data_provider.model.Verification
import ru.hse.coursework.data_provider.repository.UserRepository
import ru.hse.coursework.data_provider.repository.VerificationRepository
import java.time.LocalDateTime
import java.util.*

@Service
class VerificationService(
    private val userRepository: UserRepository,
    private val verificationRepository: VerificationRepository
) {

    @Transactional
    fun saveOrUpdateVerificationCode(
        userId: UUID,
        verificationCode: String
    ): ResponseEntity<String> {
        return try {
            userRepository.findUserById(userId)?.let { user ->
                if (user.isVerified == true) {
                    logger.error("User with id $userId is already verified")
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is already verified")
                }
            } ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found")

            if (verificationRepository.findByUserId(userId) != null) {
                logger.info("Updating verification code for user with id $userId")
                verificationRepository.updateByUserId(userId, verificationCode)
            } else {
                verificationRepository.save(Verification(
                    userId = userId,
                    code = verificationCode,
                    createdAt = LocalDateTime.now()
                ))
            }

            ResponseEntity.status(HttpStatus.OK).body("Verification code saved successfully")
        } catch (e: Exception) {
            logger.error("Error while saving verification code", e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while saving verification code")
        }
    }


    @Transactional
    fun checkVerified(userId: UUID): ResponseEntity<Boolean> {
        return try {
            val user = userRepository.findUserById(userId) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false)

            logger.info("User with id $userId is verified: ${user.isVerified}")
            ResponseEntity.status(HttpStatus.OK).body(user.isVerified)
        } catch (e: Exception) {
            logger.error("Error while checking if user is verified", e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false)
        }
    }

    @Transactional
    fun getVerificationCode(
        userId: UUID
    ): ResponseEntity<String> {
        return try {
            userRepository.findUserById(userId)?.let { user ->
                if (user.isVerified == true) {
                    logger.error("User with id $userId is already verified")
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is already verified")
                }
            } ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found")

            val verification = verificationRepository.findByUserId(userId)
                ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification code not found")

            ResponseEntity.status(HttpStatus.OK).body(verification.code)
        } catch (e: Exception) {
            logger.error("Error while verifying user", e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while verifying user")
        }
    }

    @Transactional
    fun updateVerificationStatus(
        userId: UUID
    ): ResponseEntity<String> {
        return try {
            val user = userRepository.findUserById(userId) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found")

            if (user.isVerified == true) {
                logger.error("User with id $userId is already verified")
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is already verified")
            }

            userRepository.updateUserVerifiedStatus(userId)

            verificationRepository.deleteByUserId(userId)

            ResponseEntity.status(HttpStatus.OK).body("User verified successfully")
        } catch (e: Exception) {
            logger.error("Error while verifying user", e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while verifying user")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VerificationService::class.java)
    }
}
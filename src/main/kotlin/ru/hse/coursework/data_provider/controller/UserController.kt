package ru.hse.coursework.data_provider.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.coursework.data_provider.dto.UserDto
import ru.hse.coursework.data_provider.dto.UserSecurityDto
import ru.hse.coursework.data_provider.service.UserService
import java.util.*
import kotlin.io.encoding.ExperimentalEncodingApi

@RestController
@RequestMapping(USER_BASE_PATH_URL)
@ExperimentalEncodingApi
class UserController(
    private val userService: UserService
) {
    @PostMapping(REGISTER_URL)
    fun register(
        @RequestParam username: String,
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<String> =
        userService.registerNewUser(username, email, password)

    @GetMapping(LOGIN_URL)
    fun login(@RequestParam email: String, @RequestParam password: String): UserSecurityDto? =
        userService.login(email, password)


    @GetMapping(GET_USER_INFO_URL)
    fun getUserInfo(@RequestParam userId: UUID) : ResponseEntity<UserDto>? =
        userService.getUserInfo(userId)

    @PutMapping(UPDATE_USERNAME_URL)
    fun updateUsername(@RequestParam newUsername: String, @RequestParam userId: UUID): ResponseEntity<String> =
        userService.updateUsername(userId, newUsername)

    @GetMapping(GET_USER_BY_EMAIL_URL)
    fun getUserByEmail(@RequestParam email: String): UserSecurityDto? =
                userService.getUserByEmail(email)

    @PutMapping(UPDATE_USER_PHOTO_URL)
    fun updateUserPhoto(
        @RequestParam userId: UUID,
        @RequestParam photoUrl: String
    ): ResponseEntity<String> =
        userService.updateUserPhoto(userId, photoUrl)
}
package ru.hse.coursework.routes_provider.contoller

import org.springframework.web.bind.annotation.*
import ru.hse.coursework.routes_provider.model.User
import ru.hse.coursework.routes_provider.repository.UserRepository

@RestController
@RequestMapping("/api/routes-provider/security/users")
class SecurityController (
    private val userRepository: UserRepository
) {

    @GetMapping("/all")
    fun getAllUsers() = userRepository.findAll()

    @GetMapping("/user/{id}")
    fun getUserById(@PathVariable id: String) = userRepository.findById(id)

    @PostMapping("/add")
    fun addUser(@RequestBody user: User) = userRepository.save(user)
}

package ru.hse.coursework.routes_provider.dto

import java.util.UUID

class UserSecurityDto(
    var id: UUID,
    var username: String,
    var email: String,
    var password: String,
    var roles : List<String>
)
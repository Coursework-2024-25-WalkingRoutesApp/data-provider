package ru.hse.coursework.data_provider.dto

import java.util.UUID

class UserSecurityDto(
    var id: UUID,
    var username: String,
    var email: String,
    var password: String,
    var roles : List<String>
)
package ru.hse.coursework.routes_provider.dto.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.hse.coursework.routes_provider.dto.UserSecurityDto
import ru.hse.coursework.routes_provider.model.User

@Component
class UserToUserSecurityDtoConverter: Converter<User, UserSecurityDto> {

    override fun convert(source: User): UserSecurityDto {
        return UserSecurityDto(
            username = source.userName,
            email = source.email,
            password = source.password
        )
    }
}
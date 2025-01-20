package ru.hse.coursework.routes_provider.dto.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.hse.coursework.routes_provider.dto.UserDto
import ru.hse.coursework.routes_provider.model.User

@Component
class UserToUserDtoConverter : Converter<User, UserDto> {

    override fun convert(source: User): UserDto {
        return UserDto(
            username = source.userName,
            email = source.email,
            photoUrl = source.photoUrl
        )
    }
}
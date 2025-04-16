package ru.hse.coursework.data_provider.dto.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.UserSecurityDto
import ru.hse.coursework.data_provider.model.User

@Component
class UserToUserSecurityDtoConverter: Converter<User, UserSecurityDto> {

    override fun convert(source: User): UserSecurityDto {
        return UserSecurityDto(
            id = source.id!!,
            username = source.userName,
            email = source.email,
            password = source.password,
            roles = source.roles?.map { it.name } ?: emptyList()
        )
    }
}

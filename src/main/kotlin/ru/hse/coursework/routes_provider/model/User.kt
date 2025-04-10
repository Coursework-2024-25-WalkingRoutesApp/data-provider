package ru.hse.coursework.routes_provider.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.routes_provider.model.User.Companion.TABLE_NAME
import java.util.UUID

@Table(TABLE_NAME)
data class User(
    @Id
    @Column(ID_COLUMN_NAME)
    val id: UUID? = null,

    @Column(USER_NAME_COLUMN_NAME)
    var userName: String,

    @Column(EMAIL_COLUMN_NAME)
    val email: String,

    @Column(PASSWORD_COLUMN_NAME)
    val password: String,

    @Column(ROLES_COLUMN_NAME)
    val roles: List<AuthorityType>? = listOf(AuthorityType.DEFAULT),

    @Column(PHOTO_URL_COLUMN_NAME)
    val photoUrl: String? = null
) {

    enum class AuthorityType {
        ADMIN,
        DEFAULT
    }

    companion object {
        const val TABLE_NAME = "user"

        const val ID_COLUMN_NAME = "id"
        const val USER_NAME_COLUMN_NAME = "user_name"
        const val EMAIL_COLUMN_NAME = "email"
        const val PASSWORD_COLUMN_NAME = "password"
        const val ROLES_COLUMN_NAME = "roles"
        const val PHOTO_URL_COLUMN_NAME = "photo_url"
    }
}

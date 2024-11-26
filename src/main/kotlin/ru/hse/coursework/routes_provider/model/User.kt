package ru.hse.coursework.routes_provider.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.hse.coursework.routes_provider.model.User.Companion.TABLE_NAME

@Table(TABLE_NAME)
data class User(
    @Id
    @Column(ID_COLUMN_NAME)
    val id: String,

    @Column(USER_NAME_COLUMN_NAME)
    val userName: String,

    @Column(EMAIL_COLUMN_NAME)
    val email: String,

    @Column(PASSWORD_COLUMN_NAME)
    val password: String
) {
    companion object {
        const val TABLE_NAME = "order_table"

        const val ID_COLUMN_NAME = "id"
        const val USER_NAME_COLUMN_NAME = "user_name"
        const val EMAIL_COLUMN_NAME = "email"
        const val PASSWORD_COLUMN_NAME = "password"
    }
}

package esperer.userservicekotlin.dto

import java.time.LocalDateTime

data class UserDto(
    val email: String,
    val name: String,
    val password: String,
    val userId: String,
    val createdAt: LocalDateTime,
    val encryptedPassword: String
)

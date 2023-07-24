package esperer.userservicekotlin.service

import esperer.userservicekotlin.dto.UserDto
import esperer.userservicekotlin.jpa.UserEntity
import esperer.userservicekotlin.vo.RequestUser

interface UserService {
    fun createUser(requestUser: RequestUser): UserDto
    fun getUserById(userId: String): UserDto
    fun getAllUser(): Iterable<UserEntity>
}
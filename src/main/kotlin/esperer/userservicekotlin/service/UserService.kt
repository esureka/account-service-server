package esperer.userservicekotlin.service

import esperer.userservicekotlin.dto.UserDto
import esperer.userservicekotlin.jpa.UserEntity
import esperer.userservicekotlin.vo.RequestUser
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun createUser(requestUser: RequestUser): UserDto
    fun getUserById(userId: String): UserDto
    fun getAllUser(): Iterable<UserEntity>
}
package esperer.userservicekotlin.service

import esperer.userservicekotlin.dto.UserDto
import esperer.userservicekotlin.vo.RequestUser

interface UserService {
    fun createUser(requestUser: RequestUser): UserDto
}
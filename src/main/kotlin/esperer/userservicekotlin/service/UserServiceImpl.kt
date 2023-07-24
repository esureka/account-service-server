package esperer.userservicekotlin.service

import esperer.userservicekotlin.dto.UserDto
import esperer.userservicekotlin.jpa.UserEntity
import esperer.userservicekotlin.jpa.UserRepository
import esperer.userservicekotlin.vo.RequestUser
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun createUser(requestUser: RequestUser): UserDto {
        val userDto = UserDto(
            email = requestUser.email,
            name = requestUser.name,
            password = requestUser.password,
            userId = UUID.randomUUID().toString(),
            createdAt = LocalDateTime.now(),
            encryptedPassword = "encryptedPassword"
        )

        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        val userEntity = mapper.map(userDto, UserEntity::class.java)

        userRepository.save(userEntity)
        return userDto
    }
}
package esperer.userservicekotlin.service

import esperer.userservicekotlin.dto.UserDto
import esperer.userservicekotlin.jpa.UserEntity
import esperer.userservicekotlin.jpa.UserRepository
import esperer.userservicekotlin.vo.RequestUser
import esperer.userservicekotlin.vo.ResponseOrder
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun createUser(requestUser: RequestUser): UserDto {
        val userDto = UserDto(
            email = requestUser.email,
            name = requestUser.name,
            password = requestUser.password,
            userId = UUID.randomUUID().toString(),
            createdAt = LocalDateTime.now(),
            encryptedPassword = passwordEncoder.encode(requestUser.password),
            responseOrders = ArrayList()
        )

        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        val userEntity = mapper.map(userDto, UserEntity::class.java)

        userRepository.save(userEntity)
        return userDto
    }

    override fun getUserById(userId: String): UserDto {

        val userEntity = userRepository.findByUserId(userId)
            ?: throw UsernameNotFoundException("User not found")

        val userDto = ModelMapper().map(userEntity, UserDto::class.java)

        val orders = arrayListOf<ResponseOrder>()
        userDto.responseOrders = orders

        return userDto
    }

    override fun getAllUser(): Iterable<UserEntity> {
        return userRepository.findAll()
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException(username)
        return User(user.email, user.encryptedPassword,
            true, true, true, true,
            listOf())
    }
}
package esperer.userservicekotlin.service

import esperer.userservicekotlin.client.OrderServiceClient
import esperer.userservicekotlin.dto.UserDto
import esperer.userservicekotlin.jpa.UserEntity
import esperer.userservicekotlin.jpa.UserRepository
import esperer.userservicekotlin.vo.RequestUser
import esperer.userservicekotlin.vo.ResponseOrder
import feign.FeignException
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val restTemplate: RestTemplate,
    private val env: Environment,
    private val orderServiceClient: OrderServiceClient
) : UserService {

    private val logger = LoggerFactory.getLogger("feign client")

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

        // val orders = arrayListOf<ResponseOrder>()
//        val orderUrl = String.format(env.getProperty("order_service.url")
//            ?: "http://localhost:8000/order-service/%s/orders", userId)
//
//        val ordersResponseBody = restTemplate.exchange(orderUrl,
//            HttpMethod.GET, null, object: ParameterizedTypeReference<List<ResponseOrder>>() {})
//
//        userDto.responseOrders = ordersResponseBody.body ?: arrayListOf()

        var orders: List<ResponseOrder>? = null

        try {
            orders = orderServiceClient.getOrders(UUID.fromString(userId))
        } catch (e: FeignException) {
            logger.info(e.message)
        }

        userDto.responseOrders = orders ?: arrayListOf()

        return userDto
    }

    override fun getAllUser(): Iterable<UserEntity> {
        return userRepository.findAll()
    }

    override fun getUserDetailsByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException(email)
        return ModelMapper().map(user, UserDto::class.java)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException(username)
        return User(user.email, user.encryptedPassword,
            true, true, true, true,
            listOf())
    }
}
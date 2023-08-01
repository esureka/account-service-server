package esperer.userservicekotlin.api

import esperer.userservicekotlin.service.UserService
import esperer.userservicekotlin.vo.Greeting
import esperer.userservicekotlin.vo.RequestUser
import esperer.userservicekotlin.vo.ResponseUser
import org.modelmapper.ModelMapper
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class UserWebApi(
    private val greeting: Greeting,
    private val userService: UserService,
    private val env: Environment
) {

    @GetMapping("/health-check")
    fun healthCheck(): String {
        return "It's working in user service on Port ${env.getProperty("local.server.port")}"
    }

    @GetMapping("/welcome")
    fun welcome(): String {
        return greeting.message
    }

    @PostMapping("/users")
    fun createUser(@RequestBody requestUser: RequestUser): ResponseEntity<Void> {
        userService.createUser(requestUser)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<ResponseUser>> {
        val userList = userService.getAllUser()
        val result = arrayListOf<ResponseUser>()

        userList.forEach {
            result.add(ModelMapper().map(it, ResponseUser::class.java))
        }

        return ResponseEntity.ok(result)
    }

    @GetMapping("/users/{userId}")
    fun getUserById(@PathVariable userId: String): ResponseEntity<ResponseUser> {
        val userDto = userService.getUserById(userId)
        val result = ModelMapper().map(userDto, ResponseUser::class.java)
        return ResponseEntity.ok(result)
    }
}
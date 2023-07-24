package esperer.userservicekotlin.api

import esperer.userservicekotlin.service.UserService
import esperer.userservicekotlin.vo.Greeting
import esperer.userservicekotlin.vo.RequestUser
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserWebApi(
    private val greeting: Greeting,
    private val userService: UserService
) {

    @GetMapping("/health-check")
    fun healthCheck(): String {
        return "It's working in user service"
    }

    @GetMapping("/welcome")
    fun welcome(): String {
        return greeting.message
    }

    @PostMapping("/users")
    fun createUser(@RequestBody requestUser: RequestUser): String {
        userService.createUser(requestUser)
        return "Create user method is called"
    }
}
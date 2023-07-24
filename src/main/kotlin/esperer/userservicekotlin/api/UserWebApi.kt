package esperer.userservicekotlin.api

import esperer.userservicekotlin.vo.Greeting
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserWebApi(
    private val greeting: Greeting
) {

    @GetMapping("/")
    fun healthCheck(): String {
        return "It's working in user service"
    }

    @GetMapping("/welcome")
    fun welcome(): String {
        return greeting.message
    }
}
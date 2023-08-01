package esperer.userservicekotlin.vo

import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class RequestLogin(

    @field:Size(min = 2, message = "Email not be less than two characters")
    @field:Email
    val email: String,

    @field:Size(min = 8, message = "Password must be equals or greater than 8 characters")
    val password: String
)

package esperer.userservicekotlin.vo

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class RequestUser(

    @field:NotNull(message = "Email cannot be null")
    @field:Size(min = 8, message = "Email not be less than 2 characters")
    @field:Email
    val email: String,

    @field:NotNull(message = "Name cannot be null")
    @field:Size(min = 2, message = "Name not be lss than 2 characters")
    val name: String,

    @field:NotNull(message = "Password cannot be null")
    @field:Size(min = 8, message = "Password must be equal or grater than 8 characters")
    val password: String
)
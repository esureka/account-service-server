package esperer.userservicekotlin.vo

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseUser(
    val email: String,
    val name: String,
    val userId: String,

    val orders: List<ResponseOrder>
)